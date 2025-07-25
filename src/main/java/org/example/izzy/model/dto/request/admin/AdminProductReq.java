package org.example.izzy.model.dto.request.admin;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.example.izzy.model.enums.Gender;
import org.example.izzy.model.enums.ProductStatus;
import org.example.izzy.validation.SaleDiscountConsistency;

import java.util.UUID;

@SaleDiscountConsistency
public record AdminProductReq(
        @NotBlank(message = "Product Name is required !")
        String name,

        @NotNull(message = "Product Category must be selected!")
        UUID categoryId,

        @NotNull(message = "Product Price is required !")
        Integer price,

        @Nullable
        @Min(value = 1, message = "Discount must be at least 1%")
        @Max(value = 100, message = "Discount cannot be more than 100%")
        Integer discount,

        @NotNull(message = "Gender must be selected!")
        Gender gender,

        @NotNull(message = "ProductStatus must be selected!")
        ProductStatus status,


        @NotNull(message = "Is Active field must not be null")
        Boolean isActive,

        @NotBlank(message = "Product Description is required !")
        String description
) {
}
