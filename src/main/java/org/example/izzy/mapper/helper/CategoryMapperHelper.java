package org.example.izzy.mapper.helper;

import lombok.RequiredArgsConstructor;
import org.example.izzy.model.entity.Category;
import org.example.izzy.repo.CategoryRepository;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CategoryMapperHelper {

    private final CategoryRepository categoryRepository;


    @Named("mapChildrenIdsToCategories")
    public Set<Category> mapChildrenIdsToCategories(Set<Long> childrenIds) {
        if (childrenIds == null || childrenIds.isEmpty()) {
            return new HashSet<>();
        }
        Set<Category> children = new HashSet<>(categoryRepository.findAllById(childrenIds));
        for (Category child : children) {
            child.setParent(null); // avoid cyclic dependency if needed
        }
        return children;
    }

    @Named("mapChildrenToChildrenIds")
    public Set<Long> mapChildrenIds(Set<Category> children) {
        return children.stream()
                .map(Category::getId)
                .collect(Collectors.toSet());
    }

    @Named("mapChildrenToChildrenNames")
    public Set<String> mapChildrenNames(Set<Category> children) {
        return children.stream()
                .map(Category::getName)
                .collect(Collectors.toSet());
    }
}
