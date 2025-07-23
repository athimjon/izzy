package org.example.izzy.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SaleDiscountConsistencyValidator.class)
public @interface SaleDiscountConsistency {
    String message() default "Discount is required when status is SALE, and status must be SALE when discount is assigned!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
