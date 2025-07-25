package org.example.izzy.mapper.helper;

import lombok.RequiredArgsConstructor;
import org.example.izzy.model.entity.Product;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductMapperHelper {

    @Named("calculateStock")
    public static Integer calculateStock(Product product) {
        if (product == null || product.getColourVariants() == null) return 0;
        return product.getColourVariants().stream()
                .flatMap(cv -> cv.getSizeVariants().stream())
                .mapToInt(size -> size.getQuantity() != null ? size.getQuantity() : 0)
                .sum();
    }

    @Named("countColourVariants")
    public static Integer countColourVariants(Product product) {
        return product.getColourVariants() != null ? product.getColourVariants().size() : 0;
    }

    @Named("countSizeVariants")
    public static Integer countSizeVariants(Product product) {
        if (product.getColourVariants() == null) return 0;
        return product.getColourVariants().stream()
                .mapToInt(cv -> cv.getSizeVariants() != null ? cv.getSizeVariants().size() : 0)
                .sum();
    }
}
