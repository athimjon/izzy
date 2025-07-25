package org.example.izzy.mapper.helper;

import lombok.RequiredArgsConstructor;
import org.example.izzy.model.entity.ColourVariant;
import org.example.izzy.model.entity.SizeVariant;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ColourVariantMapperHelper {

    @Named("countSizeVariants")
    public Integer mapSizesCount(List<SizeVariant> sizeVariants) {
        return sizeVariants != null ? sizeVariants.size() : 0;
    }

    @Named("calculateTotalStock")
    public Integer calculateTotalStock(List<SizeVariant> sizeVariants) {
        return sizeVariants != null ? sizeVariants.stream()
                .mapToInt(SizeVariant::getQuantity)
                .sum() : 0;
    }
}
