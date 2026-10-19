package com.twilightyear.Ledger.category;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;



@RestController 
@RequestMapping("/categories")
public class CategoryController {

    private final Map<Long, CategoryResponse> store = new ConcurrentHashMap<>();
    private final AtomicLong sequence = new AtomicLong(0);

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryResponse create(@RequestBody CreateCategoryRequest request) {
        Long id = sequence.incrementAndGet();
        CategoryResponse category = new CategoryResponse(id, request.name(), request.type());
        store.put(id, category);
        return category;
    }

    @GetMapping
    public List<CategoryResponse> findAll() {
        return store.values().stream().sorted(Comparator.comparing(CategoryResponse::id)).toList();
    }
}