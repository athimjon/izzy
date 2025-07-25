package org.example.izzy.mapper.helper;

import lombok.RequiredArgsConstructor;
import org.example.izzy.model.entity.Category;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CategoryMapperHelper {



    @Named("mapChildrenToChildrenIds")
    public Set<UUID> mapChildrenIds(Set<Category> children) {
        if (children==null) return null;
        return children.stream()
                .map(Category::getId)
                .collect(Collectors.toSet());
    }

    @Named("mapChildrenToChildrenNames")
    public Set<String> mapChildrenNames(Set<Category> children) {
        if (children==null) return null;
        return children.stream()
                .map(Category::getName)
                .collect(Collectors.toSet());
    }
}
