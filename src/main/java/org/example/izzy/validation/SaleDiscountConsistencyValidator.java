package org.example.izzy.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.example.izzy.model.dto.request.admin.AdminProductReq;
import org.example.izzy.model.enums.ProductStatus;

public class SaleDiscountConsistencyValidator implements ConstraintValidator<SaleDiscountConsistency, AdminProductReq> {

    @Override
    public void initialize(SaleDiscountConsistency constraintAnnotation) {
    }

    @Override
    public boolean isValid(AdminProductReq value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // Let @NotNull handle null object
        }

        ProductStatus status = value.status();
        Integer discount = value.discount();

        // Check if status is SALE and discount is null
        if (status == ProductStatus.SALE && discount == null) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Discount percentage(%) is required when status is on SALE!")
                    .addPropertyNode("discount")
                    .addConstraintViolation();
            return false;
        }

        // Check if discount is assigned but status is not SALE
        if (discount != null && status != ProductStatus.SALE) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Status must be on SALE when discount percentage is assigned!")
                    .addPropertyNode("status")
                    .addConstraintViolation();
            return false;
        }

        return true;
    }
}
