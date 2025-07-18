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



    @Named("mapChildrenToChildrenIds")
    public Set<Long> mapChildrenIds(Set<Category> children) {
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
