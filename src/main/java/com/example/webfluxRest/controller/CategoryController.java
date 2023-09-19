package com.example.webfluxRest.controller;

import com.example.webfluxRest.domain.Category;
import com.example.webfluxRest.repository.CategoryRepository;
import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;


@RestController
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/") // -> / address will be defined by web request (GET, PUT, POST, etc)
    Flux<Category> getAllCategories(){
        return categoryRepository.findAll();
    }

    @GetMapping("/{id}")
    Mono<Category> findCategoryByID(@PathVariable String id){
        return categoryRepository.findById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/")
    Mono<Void> createCategory(@RequestBody Publisher<Category> categoryStream){
        return categoryRepository.saveAll(categoryStream).then();
    }

    @PutMapping("/{id}")
    Mono<Category> updateCategory(@PathVariable String id, @RequestBody Category category){ //requestBody for changing something in body of web(add, update, etc) with request method
        category.setId(id);
        return categoryRepository.save(category);
    }

    @PatchMapping("/{id}")
    Mono<Category> patchCategory(@PathVariable String id, @RequestBody Category category){
        Category foundCategory = categoryRepository.findById(id).block();

        assert foundCategory != null;
        if(!Objects.equals(foundCategory.getDescription(), category.getDescription())){
            foundCategory.setDescription(category.getDescription());
            return categoryRepository.save(foundCategory);
        }

        return Mono.just(foundCategory);
    }

    @DeleteMapping("/{id}")
    Mono<Void> deleteCategory(@PathVariable String id){
        return categoryRepository.deleteById(id);
    }
}
