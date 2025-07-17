document.addEventListener('DOMContentLoaded', function () {
    // Global variables
    let categories = [];
    let currentPage = 1;
    const itemsPerPage = 10;

    // DOM Elements
    const categoriesTableBody = document.getElementById('categoriesTableBody');
    const pagination = document.getElementById('pagination');
    const addCategoryForm = document.getElementById('addCategoryForm');
    const editCategoryForm = document.getElementById('editCategoryForm');
    const saveCategoryBtn = document.getElementById('saveCategoryBtn');
    const updateCategoryBtn = document.getElementById('updateCategoryBtn');
    const confirmActionBtn = document.getElementById('confirmActionBtn');
    const parentCategorySelect = document.getElementById('parentCategory');
    const editParentCategorySelect = document.getElementById('editParentCategory');
    const childrenCheckboxes = document.getElementById('childrenCheckboxes');
    const editChildrenCheckboxes = document.getElementById('editChildrenCheckboxes');
    const imagePreview = document.getElementById('imagePreview');
    const imagePreviewContainer = document.getElementById('imagePreviewContainer');
    const editImagePreview = document.getElementById('editImagePreview');
    const editImagePreviewContainer = document.getElementById('editImagePreviewContainer');
    const categoryImageInput = document.getElementById('categoryImage');
    const editCategoryImageInput = document.getElementById('editCategoryImage');

    // Modal instances
    const addCategoryModal = new bootstrap.Modal(document.getElementById('addCategoryModal'));
    const editCategoryModal = new bootstrap.Modal(document.getElementById('editCategoryModal'));
    const confirmationModal = new bootstrap.Modal(document.getElementById('confirmationModal'));


    // Add this with other modal instances
    const viewCategoryModal = new bootstrap.Modal(document.getElementById('viewCategoryModal'));

// Add this in the renderCategories function after setting up other button event listeners
    document.querySelectorAll('.view-btn').forEach(btn => {
        btn.addEventListener('click', function () {
            const categoryId = parseInt(this.getAttribute('data-id'));
            openViewModal(categoryId);
        });
    });

// Add this function to open the view modal
    function openViewModal(categoryId) {
        const category = categories.find(c => c.id === categoryId);
        if (!category) return;

        // Set modal title
        document.getElementById('viewCategoryModalLabel').textContent = `Category: ${category.name}`;

        // Set image
        const viewImage = document.getElementById('viewImage');
        viewImage.src = category.attachmentUrl;
        viewImage.alt = category.name;

        // Set name and status
        document.getElementById('viewName').textContent = category.name;
        const statusBadge = document.getElementById('viewStatus');
        statusBadge.textContent = category.isActive ? 'Active' : 'Inactive';
        statusBadge.className = `badge rounded-pill mt-2 ${category.isActive ? 'bg-success' : 'bg-danger'}`;

        // Set parent category
        document.getElementById('viewParent').textContent = category.parentName || 'None';

        // Set audit fields
        document.getElementById('viewCreatedBy').textContent = category.createdBy || 'N/A';
        document.getElementById('viewCreatedAt').textContent = formatDateTime(category.createdAt);
        document.getElementById('viewUpdatedBy').textContent = category.updatedBy || 'N/A';
        document.getElementById('viewUpdatedAt').textContent = formatDateTime(category.updatedAt);

        // Set children
        const childrenList = document.getElementById('viewChildren');
        childrenList.innerHTML = '';

        if (category.childrenNames && category.childrenNames.size > 0) {
            category.childrenNames.forEach(childName => {
                const li = document.createElement('li');
                li.className = 'list-group-item';
                li.textContent = childName;
                childrenList.appendChild(li);
            });
        } else {
            const li = document.createElement('li');
            li.className = 'list-group-item text-muted';
            li.textContent = 'No child categories';
            childrenList.appendChild(li);
        }

        viewCategoryModal.show();
    }

    // Initialize the page
    fetchCategories();

    // Event Listeners
    categoryImageInput.addEventListener('change', function (e) {
        handleImagePreview(e, imagePreview, imagePreviewContainer);
    });

    editCategoryImageInput.addEventListener('change', function (e) {
        handleImagePreview(e, editImagePreview, editImagePreviewContainer);
    });

    saveCategoryBtn.addEventListener('click', saveCategory);
    updateCategoryBtn.addEventListener('click', updateCategory);
    confirmActionBtn.addEventListener('click', handleConfirmation);

    // Functions
    function fetchCategories() {
        fetch('/api/v1/admin/category')
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => {
                categories = data;
                renderCategories();
                renderPagination();
                populateParentSelects();
                populateChildrenCheckboxes();
            })
            .catch(error => {
                showToast('Error fetching categories: ' + error.message, 'error');
                console.error('Error:', error);
            });
    }

    function renderCategories() {
        categoriesTableBody.innerHTML = '';

        const startIndex = (currentPage - 1) * itemsPerPage;
        const endIndex = Math.min(startIndex + itemsPerPage, categories.length);

        for (let i = startIndex; i < endIndex; i++) {
            const category = categories[i];
            const row = document.createElement('tr');

            row.innerHTML = `
            <td>${i + 1}</td>
            <td>${category.name}</td>
            <td><img src="${category.attachmentUrl}" alt="${category.name}" class="img-thumbnail" style="max-height: 50px;"></td>
            <td>${category.parentName || 'None'}</td>
            <td><span class="status-badge ${category.isActive ? 'status-active' : 'status-inactive'}">${category.isActive ? 'Active' : 'Inactive'}</span></td>
            <td>${formatDateTime(category.createdAt)}</td>
            <td>
                <button class="btn btn-sm btn-outline-info action-btn view-btn" data-id="${category.id}" title="View">
                    <i class="bi bi-eye"></i>
                </button>
                <button class="btn btn-sm btn-outline-primary action-btn edit-btn" data-id="${category.id}" title="Edit">
                    <i class="bi bi-pencil"></i>
                </button>
                <button class="btn btn-sm ${category.isActive ? 'btn-outline-danger' : 'btn-outline-success'} action-btn toggle-btn" 
                        data-id="${category.id}" data-status="${category.isActive}" 
                        title="${category.isActive ? 'Deactivate' : 'Activate'}">
                    <i class="bi ${category.isActive ? 'bi-x-circle' : 'bi-check-circle'}"></i>
                </button>
            </td>
        `;

            categoriesTableBody.appendChild(row);
        }

        // Add event listeners to the buttons
        document.querySelectorAll('.view-btn').forEach(btn => {
            btn.addEventListener('click', function() {
                const categoryId = parseInt(this.getAttribute('data-id'));
                openViewModal(categoryId);
            });
        });

        document.querySelectorAll('.edit-btn').forEach(btn => {
            btn.addEventListener('click', function() {
                const categoryId = parseInt(this.getAttribute('data-id'));
                openEditModal(categoryId);
            });
        });

        document.querySelectorAll('.toggle-btn').forEach(btn => {
            btn.addEventListener('click', function() {
                const categoryId = parseInt(this.getAttribute('data-id'));
                const currentStatus = this.getAttribute('data-status') === 'true';
                showConfirmationModal(
                    currentStatus ? 'Deactivate Category' : 'Activate Category',
                    currentStatus ? 'Are you sure you want to deactivate this category?' : 'Are you sure you want to activate this category?',
                    () => toggleCategoryStatus(categoryId)
                );
            });
        });
    }

    function renderPagination() {
        pagination.innerHTML = '';
        const pageCount = Math.ceil(categories.length / itemsPerPage);

        if (pageCount <= 1) return;

        // Previous button
        const prevLi = document.createElement('li');
        prevLi.className = `page-item ${currentPage === 1 ? 'disabled' : ''}`;
        prevLi.innerHTML = `<a class="page-link" href="#" aria-label="Previous"><span aria-hidden="true">&laquo;</span></a>`;
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
        for (let i = 1; i <= pageCount; i++) {
            const li = document.createElement('li');
            li.className = `page-item ${currentPage === i ? 'active' : ''}`;
            li.innerHTML = `<a class="page-link" href="#">${i}</a>`;
            li.addEventListener('click', (e) => {
                e.preventDefault();
                currentPage = i;
                renderCategories();
                renderPagination();
            });
            pagination.appendChild(li);
        }

        // Next button
        const nextLi = document.createElement('li');
        nextLi.className = `page-item ${currentPage === pageCount ? 'disabled' : ''}`;
        nextLi.innerHTML = `<a class="page-link" href="#" aria-label="Next"><span aria-hidden="true">&raquo;</span></a>`;
        nextLi.addEventListener('click', (e) => {
            e.preventDefault();
            if (currentPage < pageCount) {
                currentPage++;
                renderCategories();
                renderPagination();
            }
        });
        pagination.appendChild(nextLi);
    }

    function populateParentSelects() {
        // Clear existing options except the first "None" option
        while (parentCategorySelect.options.length > 1) {
            parentCategorySelect.remove(1);
        }

        while (editParentCategorySelect.options.length > 1) {
            editParentCategorySelect.remove(1);
        }

        // Add categories as options
        categories.forEach(category => {
            const option = document.createElement('option');
            option.value = category.id;
            option.textContent = category.name;
            parentCategorySelect.appendChild(option.cloneNode(true));
            editParentCategorySelect.appendChild(option);
        });
    }

    function populateChildrenCheckboxes() {
        childrenCheckboxes.innerHTML = '';
        editChildrenCheckboxes.innerHTML = '';

        categories.forEach(category => {
            const div = document.createElement('div');
            div.className = 'form-check';

            const input = document.createElement('input');
            input.className = 'form-check-input';
            input.type = 'checkbox';
            input.value = category.id;
            input.id = `child-${category.id}`;
            input.name = 'childrenIds';

            const label = document.createElement('label');
            label.className = 'form-check-label';
            label.htmlFor = `child-${category.id}`;
            label.textContent = category.name;

            div.appendChild(input);
            div.appendChild(label);

            childrenCheckboxes.appendChild(div.cloneNode(true));
            editChildrenCheckboxes.appendChild(div);
        });
    }

    function handleImagePreview(event, previewElement, previewContainer) {
        const file = event.target.files[0];
        if (file) {
            const reader = new FileReader();
            reader.onload = function (e) {
                previewElement.src = e.target.result;
                previewContainer.classList.remove('d-none');
            };
            reader.readAsDataURL(file);
        }
    }

    function saveCategory() {
        // Reset validation
        resetValidation(addCategoryForm);

        // Validate form
        if (!addCategoryForm.checkValidity()) {
            addCategoryForm.classList.add('was-validated');
            return;
        }

        // Get form data
        const formData = new FormData();
        formData.append('file', categoryImageInput.files[0]);

        // First save the attachment
        fetch('/api/v1/admin/attachment', {
            method: 'POST',
            body: formData
        })
            .then(response => {
                if (!response.ok) {
                    return response.json().then(err => {
                        throw err;
                    });
                }
                return response.json();
            })
            .then(attachmentId => {
                // Now prepare the category data
                const categoryData = {
                    name: document.getElementById('categoryName').value,
                    parentId: parentCategorySelect.value ? parseInt(parentCategorySelect.value) : null,
                    attachmentId: attachmentId,
                    childrenIds: getSelectedChildrenIds()
                };

                // Save the category
                return fetch('/api/v1/admin/category', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(categoryData)
                });
            })
            .then(response => {
                if (!response.ok) {
                    return response.json().then(err => {
                        throw err;
                    });
                }
                return response.json();
            })
            .then(data => {
                showToast('Category created successfully!', 'success');
                addCategoryModal.hide();
                addCategoryForm.reset();
                imagePreviewContainer.classList.add('d-none');
                fetchCategories();
            })
            .catch(error => {
                handleError(error);
            });
    }

    function updateCategory() {
        // Reset validation
        resetValidation(editCategoryForm);

        // Validate form
        if (!editCategoryForm.checkValidity()) {
            editCategoryForm.classList.add('was-validated');
            return;
        }

        const categoryId = document.getElementById('editCategoryId').value;
        const currentAttachmentId = document.getElementById('currentAttachmentId').value;

        // Check if a new image was uploaded
        if (editCategoryImageInput.files.length > 0) {
            // First save the new attachment
            const formData = new FormData();
            formData.append('file', editCategoryImageInput.files[0]);

            fetch('/api/v1/admin/attachment', {
                method: 'POST',
                body: formData
            })
                .then(response => {
                    if (!response.ok) {
                        return response.json().then(err => {
                            throw err;
                        });
                    }
                    return response.json();
                })
                .then(newAttachmentId => {
                    // Update the category with the new attachment
                    return updateCategoryData(categoryId, newAttachmentId);
                })
                .then(data => {
                    showToast('Category updated successfully!', 'success');
                    editCategoryModal.hide();
                    fetchCategories();
                })
                .catch(error => {
                    handleError(error);
                });
        } else {
            // No new image, update with current attachment
            updateCategoryData(categoryId, currentAttachmentId)
                .then(data => {
                    showToast('Category updated successfully!', 'success');
                    editCategoryModal.hide();
                    fetchCategories();
                })
                .catch(error => {
                    handleError(error);
                });
        }
    }

    function updateCategoryData(categoryId, attachmentId) {
        const categoryData = {
            name: document.getElementById('editCategoryName').value,
            parentId: editParentCategorySelect.value ? parseInt(editParentCategorySelect.value) : null,
            attachmentId: attachmentId,
            childrenIds: getSelectedChildrenIds(true)
        };

        return fetch(`/api/v1/admin/category/${categoryId}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(categoryData)
        })
            .then(response => {
                if (!response.ok) {
                    return response.json().then(err => {
                        throw err;
                    });
                }
                return response.json();
            });
    }

    function toggleCategoryStatus(categoryId) {
        fetch(`/api/v1/admin/category/${categoryId}`, {
            method: 'PATCH'
        })
            .then(response => {
                if (!response.ok) {
                    return response.json().then(err => {
                        throw err;
                    });
                }
                return response.text();
            })
            .then(message => {
                showToast(`Category ${message}`, 'success');
                confirmationModal.hide();
                fetchCategories();
            })
            .catch(error => {
                showToast(error.message || 'Error toggling category status', 'error');
                console.error('Error:', error);
            });
    }

    function openEditModal(categoryId) {
        const category = categories.find(c => c.id === categoryId);
        if (!category) return;

        // Set form values
        document.getElementById('editCategoryId').value = category.id;
        document.getElementById('editCategoryName').value = category.name;
        document.getElementById('currentAttachmentId').value = category.attachmentId;

        // Set parent category
        if (category.parentId) {
            editParentCategorySelect.value = category.parentId;
        } else {
            editParentCategorySelect.value = '';
        }

        // Set image preview
        editImagePreview.src = category.attachmentUrl;
        editImagePreviewContainer.classList.remove('d-none');

        // Set children checkboxes
        const checkboxes = editChildrenCheckboxes.querySelectorAll('input[type="checkbox"]');
        checkboxes.forEach(checkbox => {
            checkbox.checked = category.childrenIds ? category.childrenIds.includes(parseInt(checkbox.value)) : false;
        });

        editCategoryModal.show();
    }

    function showConfirmationModal(title, message, callback) {
        document.getElementById('confirmationModalLabel').textContent = title;
        document.getElementById('confirmationModalBody').textContent = message;
        confirmActionBtn.onclick = callback;
        confirmationModal.show();
    }

    function getSelectedChildrenIds(isEdit = false) {
        const container = isEdit ? editChildrenCheckboxes : childrenCheckboxes;
        const checkboxes = container.querySelectorAll('input[type="checkbox"]:checked');
        return Array.from(checkboxes).map(checkbox => parseInt(checkbox.value));
    }

    function resetValidation(form) {
        form.classList.remove('was-validated');
        const invalidElements = form.querySelectorAll('.is-invalid');
        invalidElements.forEach(el => el.classList.remove('is-invalid'));
    }

    function handleError(error) {
        if (error.fieldErrors) {
            // Handle field errors
            for (const [field, message] of Object.entries(error.fieldErrors)) {
                let errorElement;

                if (field === 'name') {
                    errorElement = document.getElementById('nameError');
                    document.getElementById('categoryName').classList.add('is-invalid');
                } else if (field === 'file') {
                    errorElement = document.getElementById('imageError');
                    document.getElementById('categoryImage').classList.add('is-invalid');
                } else if (field === 'attachment_id') {
                    errorElement = document.getElementById('imageError');
                    document.getElementById('categoryImage').classList.add('is-invalid');
                }

                if (errorElement) {
                    errorElement.textContent = message;
                }
            }

            showToast('Please correct the errors in the form', 'error');
        } else {
            // Handle general error
            const errorMessage = error.message || error.error || 'An unexpected error occurred';
            showToast(errorMessage, 'error');
        }
    }

    function showToast(message, type = 'info') {
        const toastContainer = document.getElementById('toastContainer');

        const toast = document.createElement('div');
        toast.className = `toast show align-items-center text-white bg-${type} border-0`;
        toast.setAttribute('role', 'alert');
        toast.setAttribute('aria-live', 'assertive');
        toast.setAttribute('aria-atomic', 'true');

        toast.innerHTML = `
            <div class="d-flex">
                <div class="toast-body">
                    ${message}
                </div>
                <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast" aria-label="Close"></button>
            </div>
        `;

        toastContainer.appendChild(toast);

        // Auto hide after 4 seconds
        setTimeout(() => {
            toast.classList.remove('show');
            toast.classList.add('hide');
            setTimeout(() => toast.remove(), 500);
        }, 4000);
    }

    function formatDateTime(dateTimeString) {
        if (!dateTimeString) return '';

        const date = new Date(dateTimeString);
        return date.toLocaleString('en-US', {
            year: 'numeric',
            month: 'short',
            day: 'numeric',
            hour: '2-digit',
            minute: '2-digit'
        });
    }
});