document.addEventListener('DOMContentLoaded', function() {
    // Global variables
    const apiBaseUrl = 'http://localhost:8080/api/v1/admin';
    let currentPage = 1;
    const itemsPerPage = 10;
    let categories = [];
    let totalCategories = 0;

    // DOM Elements
    const categoriesTableBody = document.getElementById('categoriesTableBody');
    const pagination = document.getElementById('pagination');
    const saveCategoryBtn = document.getElementById('saveCategoryBtn');
    const updateCategoryBtn = document.getElementById('updateCategoryBtn');
    const categoryImage = document.getElementById('categoryImage');
    const imagePreview = document.getElementById('imagePreview');
    const imagePreviewContainer = document.getElementById('imagePreviewContainer');
    const editCategoryImage = document.getElementById('editCategoryImage');
    const editImagePreview = document.getElementById('editImagePreview');

    // Initialize the page
    fetchCategories();
    setupEventListeners();

    function setupEventListeners() {
        // Save category button
        saveCategoryBtn.addEventListener('click', saveCategory);

        // Update category button
        updateCategoryBtn.addEventListener('click', updateCategory);

        // Image preview for add modal
        categoryImage.addEventListener('change', function() {
            if (this.files && this.files[0]) {
                const reader = new FileReader();
                reader.onload = function(e) {
                    imagePreview.src = e.target.result;
                    imagePreviewContainer.style.display = 'block';
                };
                reader.readAsDataURL(this.files[0]);
            }
        });

        // Image preview for edit modal
        editCategoryImage.addEventListener('change', function() {
            if (this.files && this.files[0]) {
                const reader = new FileReader();
                reader.onload = function(e) {
                    editImagePreview.src = e.target.result;
                };
                reader.readAsDataURL(this.files[0]);
            }
        });
    }

    // Fetch all categories
    async function fetchCategories() {
        try {
            const response = await fetch(`${apiBaseUrl}/category`);
            if (!response.ok) throw new Error('Failed to fetch categories');

            const data = await response.json();
            categories = data;
            totalCategories = data.length;

            renderCategories();
            renderPagination();
        } catch (error) {
            console.error('Error fetching categories:', error);
            showAlert('danger', 'Failed to load categories. Please try again.');
        }
    }

    // Render categories in the table
    function renderCategories() {
        categoriesTableBody.innerHTML = '';

        const startIndex = (currentPage - 1) * itemsPerPage;
        const endIndex = Math.min(startIndex + itemsPerPage, totalCategories);

        for (let i = startIndex; i < endIndex; i++) {
            const category = categories[i];
            const row = document.createElement('tr');

            row.innerHTML = `
                <td>${category.id}</td>
                <td>${category.name}</td>
                <td>${category.parentName || '--'}</td>
                <td><span class="status-badge ${category.isActive ? 'status-active' : 'status-inactive'}">
                    ${category.isActive ? 'Active' : 'Inactive'}
                </span></td>
                <td>${new Date(category.createdAt).toLocaleDateString()}</td>
                <td class="action-buttons">
                    <button class="btn btn-primary btn-sm btn-action view-btn" data-id="${category.id}">
                        <i class="fas fa-eye"></i>
                    </button>
                    <button class="btn btn-info btn-sm btn-action edit-btn" data-id="${category.id}">
                        <i class="fas fa-edit"></i>
                    </button>
                    <button class="btn ${category.isActive ? 'btn-warning' : 'btn-success'} btn-sm btn-action toggle-btn" data-id="${category.id}">
                        <i class="fas ${category.isActive ? 'fa-ban' : 'fa-check'}"></i>
                    </button>
                </td>
            `;

            categoriesTableBody.appendChild(row);
        }

        // Add event listeners to action buttons
        document.querySelectorAll('.view-btn').forEach(btn => {
            btn.addEventListener('click', () => viewCategory(btn.dataset.id));
        });

        document.querySelectorAll('.edit-btn').forEach(btn => {
            btn.addEventListener('click', () => prepareEditCategory(btn.dataset.id));
        });

        document.querySelectorAll('.toggle-btn').forEach(btn => {
            btn.addEventListener('click', () => toggleCategoryStatus(btn.dataset.id));
        });
    }

    // Render pagination
    function renderPagination() {
        pagination.innerHTML = '';
        const totalPages = Math.ceil(totalCategories / itemsPerPage);

        if (totalPages <= 1) return;

        // Previous button
        const prevLi = document.createElement('li');
        prevLi.className = `page-item ${currentPage === 1 ? 'disabled' : ''}`;
        prevLi.innerHTML = `<a class="page-link" href="#" aria-label="Previous">&laquo;</a>`;
        prevLi.addEventListener('click', (e) => {
            e.preventDefault();
            if (currentPage > 1) {
                currentPage--;
                renderCategories();
                renderPagination();
            }
        });
        pagination.appendChild(prevLi);

        // Page numbers
        for (let i = 1; i <= totalPages; i++) {
            const pageLi = document.createElement('li');
            pageLi.className = `page-item ${i === currentPage ? 'active' : ''}`;
            pageLi.innerHTML = `<a class="page-link" href="#">${i}</a>`;
            pageLi.addEventListener('click', (e) => {
                e.preventDefault();
                currentPage = i;
                renderCategories();
                renderPagination();
            });
            pagination.appendChild(pageLi);
        }

        // Next button
        const nextLi = document.createElement('li');
        nextLi.className = `page-item ${currentPage === totalPages ? 'disabled' : ''}`;
        nextLi.innerHTML = `<a class="page-link" href="#" aria-label="Next">&raquo;</a>`;
        nextLi.addEventListener('click', (e) => {
            e.preventDefault();
            if (currentPage < totalPages) {
                currentPage++;
                renderCategories();
                renderPagination();
            }
        });
        pagination.appendChild(nextLi);
    }

    // Save new category
    async function saveCategory() {
        const name = document.getElementById('categoryName').value.trim();
        const parentId = document.getElementById('parentCategory').value;
        const imageFile = categoryImage.files[0];

        if (!name || !imageFile) {
            showAlert('warning', 'Please fill all required fields');
            return;
        }

        try {
            // First upload the image
            const formData = new FormData();
            formData.append('file', imageFile);

            const uploadResponse = await fetch(`${apiBaseUrl}/attachment`, {
                method: 'POST',
                body: formData
            });

            if (!uploadResponse.ok) throw new Error('Failed to upload image');

            const attachmentId = await uploadResponse.json();

            // Then create the category
            const categoryData = {
                name: name,
                parentId: parentId || null,
                attachmentId: attachmentId
            };

            const categoryResponse = await fetch(`${apiBaseUrl}/category`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(categoryData)
            });

            if (!categoryResponse.ok) throw new Error('Failed to create category');

            const newCategory = await categoryResponse.json();

            // Reset form and close modal
            document.getElementById('addCategoryForm').reset();
            imagePreviewContainer.style.display = 'none';
            bootstrap.Modal.getInstance(document.getElementById('addCategoryModal')).hide();

            // Refresh categories list
            fetchCategories();

            showAlert('success', 'Category created successfully!');
        } catch (error) {
            console.error('Error creating category:', error);
            showAlert('danger', 'Failed to create category. Please try again.');
        }
    }

    // Prepare edit category modal
    async function prepareEditCategory(categoryId) {
        try {
            const category = categories.find(c => c.id == categoryId);
            if (!category) throw new Error('Category not found');

            // Load parent categories (excluding current category)
            const parentSelect = document.getElementById('editParentCategory');
            parentSelect.innerHTML = '<option value="">-- No Parent --</option>';

            categories
                .filter(c => c.id != categoryId)
                .forEach(c => {
                    const option = document.createElement('option');
                    option.value = c.id;
                    option.textContent = c.name;
                    if (category.parentId === c.id) option.selected = true;
                    parentSelect.appendChild(option);
                });

            // Set form values
            document.getElementById('editCategoryId').value = category.id;
            document.getElementById('editCategoryName').value = category.name;
            editImagePreview.src = category.attachmentUrl;

            // Show modal
            const modal = new bootstrap.Modal(document.getElementById('editCategoryModal'));
            modal.show();
        } catch (error) {
            console.error('Error preparing edit:', error);
            showAlert('danger', 'Failed to prepare category for editing.');
        }
    }

    // Update category
    async function updateCategory() {
        const categoryId = document.getElementById('editCategoryId').value;
        const name = document.getElementById('editCategoryName').value.trim();
        const parentId = document.getElementById('editParentCategory').value;
        const imageFile = editCategoryImage.files[0];

        if (!name) {
            showAlert('warning', 'Category name is required');
            return;
        }

        try {
            let attachmentId = null;

            // If new image was provided, upload it first
            if (imageFile) {
                const formData = new FormData();
                formData.append('file', imageFile);

                const uploadResponse = await fetch(`${apiBaseUrl}/attachment/${categoryId}`, {
                    method: 'PUT',
                    body: formData
                });

                if (!uploadResponse.ok) throw new Error('Failed to update image');
                attachmentId = categoryId; // Using the same ID for attachment as category in this case
            }

            // Then update the category
            const categoryData = {
                name: name,
                parentId: parentId || null,
                attachmentId: attachmentId || undefined
            };

            const categoryResponse = await fetch(`${apiBaseUrl}/category/${categoryId}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(categoryData)
            });

            if (!categoryResponse.ok) throw new Error('Failed to update category');

            // Close modal and refresh
            bootstrap.Modal.getInstance(document.getElementById('editCategoryModal')).hide();
            fetchCategories();

            showAlert('success', 'Category updated successfully!');
        } catch (error) {
            console.error('Error updating category:', error);
            showAlert('danger', 'Failed to update category. Please try again.');
        }
    }

    // View category details
    async function viewCategory(categoryId) {
        try {
            const response = await fetch(`${apiBaseUrl}/category/${categoryId}`);
            if (!response.ok) throw new Error('Failed to fetch category details');

            const category = await response.json();

            // Populate modal
            document.getElementById('viewCategoryId').textContent = category.id;
            document.getElementById('viewCategoryName').textContent = category.name;
            document.getElementById('viewParentCategory').textContent = category.parentName || '--';
            document.getElementById('viewCategoryStatus').textContent = category.isActive ? 'Active' : 'Inactive';
            document.getElementById('viewCreatedAt').textContent = new Date(category.createdAt).toLocaleString();
            document.getElementById('viewCreatedBy').textContent = category.createdBy || '--';
            document.getElementById('viewCategoryImage').src = category.attachmentUrl;

            // Show modal
            const modal = new bootstrap.Modal(document.getElementById('viewCategoryModal'));
            modal.show();
        } catch (error) {
            console.error('Error viewing category:', error);
            showAlert('danger', 'Failed to load category details.');
        }
    }

    // Toggle category status
    async function toggleCategoryStatus(categoryId) {
        try {
            const response = await fetch(`${apiBaseUrl}/category/${categoryId}`, {
                method: 'PATCH'
            });

            if (!response.ok) throw new Error('Failed to toggle category status');

            const message = await response.text();
            fetchCategories();

            showAlert('success', `Category ${message.includes('ACTIVATED') ? 'activated' : 'deactivated'} successfully!`);
        } catch (error) {
            console.error('Error toggling category status:', error);
            showAlert('danger', 'Failed to change category status.');
        }
    }

    // Show alert message
    function showAlert(type, message) {
        const alertDiv = document.createElement('div');
        alertDiv.className = `alert alert-${type} alert-dismissible fade show position-fixed top-0 end-0 m-3`;
        alertDiv.style.zIndex = '1100';
        alertDiv.role = 'alert';
        alertDiv.innerHTML = `
            ${message}
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        `;

        document.body.appendChild(alertDiv);

        // Auto dismiss after 5 seconds
        setTimeout(() => {
            const bsAlert = new bootstrap.Alert(alertDiv);
            bsAlert.close();
        }, 5000);
    }

    // Load parent categories for add modal
    async function loadParentCategories() {
        try {
            const response = await fetch(`${apiBaseUrl}/category`);
            if (!response.ok) throw new Error('Failed to fetch parent categories');

            const data = await response.json();
            const parentSelect = document.getElementById('parentCategory');

            parentSelect.innerHTML = '<option value="">-- No Parent --</option>';
            data.forEach(category => {
                const option = document.createElement('option');
                option.value = category.id;
                option.textContent = category.name;
                parentSelect.appendChild(option);
            });
        } catch (error) {
            console.error('Error loading parent categories:', error);
        }
    }

    // Initialize parent categories when add modal is shown
    document.getElementById('addCategoryModal').addEventListener('show.bs.modal', loadParentCategories);
});