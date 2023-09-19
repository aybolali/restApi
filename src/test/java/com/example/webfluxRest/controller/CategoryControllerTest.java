package com.example.webfluxRest.controller;

import com.example.webfluxRest.domain.Category;
import com.example.webfluxRest.repository.CategoryRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.reactivestreams.Publisher;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class CategoryControllerTest {
    WebTestClient webTestClient;
    CategoryController controller;
    CategoryRepository repository;

    @Before
    public void setUp() throws Exception {
        repository = Mockito.mock(CategoryRepository.class);
        controller = new CategoryController(repository);
        webTestClient = WebTestClient.bindToController(controller).build();
    }

    @Test
    public void getAllCategories() {
        given(repository.findAll())
                .willReturn(Flux.just(Category.builder().description("Object1").build(),
                        Category.builder().description("Object2").build()));

        webTestClient.get().uri("/categories/")
                .exchange()
                .expectBodyList(Category.class)
                .hasSize(2);
    }

    @Test
    public void findCategoryByID() {
        given(repository.findById("someID"))
                .willReturn(Mono.just(Category.builder().id("someID").description("some").build()));

        webTestClient.get().uri("/categories/someID")
                .exchange()
                .expectBodyList(Category.class);
    }

    @Test
    public void createCategory() {
        given(repository.saveAll(any(Publisher.class)))
                .willReturn(Flux.just(Category.builder().build()));

        Mono<Category> newObjectToSave = Mono.just(Category.builder().description("Hello").build());

        webTestClient.post()
                .uri("/categories/")
                .body(newObjectToSave, Category.class)
                .exchange()
                .expectStatus()
                .isCreated();
    }

    @Test
    public void updateCategory(){
        given(repository.save(any(Category.class)))
                .willReturn(Mono.just(Category.builder().build()));

        Mono<Category> updateObject = Mono.just(Category.builder().description("update").build());

        webTestClient.put() //PUT(update) Request
                .uri("/categories/someRandomID")
                .body(updateObject, Category.class)
                .exchange().expectStatus()
                .isOk();
    }

    @Test
    public void patchCategoryWithChanges(){
        given(repository.findById(anyString()))
                .willReturn(Mono.just(Category.builder().build()));

        given(repository.save(any(Category.class)))
                .willReturn(Mono.just(Category.builder().build()));

        Mono<Category> updateObject = Mono.just(Category.builder().description("patch").build());

        webTestClient.patch()
                .uri("/categories/someRandomID")
                .body(updateObject, Category.class)
                .exchange()
                .expectStatus()
                .isOk();

        verify(repository).save(any());
        /*verify(repository.save(any())); uncorrect verify*/
    }

    @Test
    public void patchCategoryWithNoChanges(){
        given(repository.findById(anyString()))
                .willReturn(Mono.just(Category.builder().build()));

        given(repository.save(any(Category.class)))
                .willReturn(Mono.just(Category.builder().build()));

        Mono<Category> updateObject = Mono.just(Category.builder().build());

        webTestClient.patch() //PUT(update) Request
                .uri("/categories/someRandomID")
                .body(updateObject, Category.class)
                .exchange().expectStatus()
                .isOk();

        verify(repository, never()).save(any());
    }

    @Test
    public void deleteCategory(){
        given(repository.findById(anyString()))
                .willReturn(Mono.just(Category.builder().build()));


        webTestClient.delete()
                .uri("/categories/someRandomID")
                .exchange().expectStatus()
                .isOk();

        verify(repository).deleteById(anyString());
    }
}