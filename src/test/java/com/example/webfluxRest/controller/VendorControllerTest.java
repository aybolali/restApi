package com.example.webfluxRest.controller;

import com.example.webfluxRest.domain.Vendor;
import com.example.webfluxRest.repository.VendorRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
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


public class VendorControllerTest {

    VendorController controller;
    VendorRepository repository;
    WebTestClient webTestClient;

    @Before
    public void setUp() {
        repository = Mockito.mock(VendorRepository.class);
        controller = new VendorController(repository);

        webTestClient = WebTestClient.bindToController(controller).build();
    }

    @Test
    public void getAllVendors() {
        BDDMockito.given(repository.findAll())
                .willReturn(Flux.just(
                        Vendor.builder().firstName("aybolali").lastName("sartbay").build(),
                        Vendor.builder().firstName("A").lastName("S").build()
                ));

        webTestClient.get().uri("/vendors/list")
                .exchange()
                .expectBodyList(Vendor.class)
                .hasSize(2);
    }

    @Test
    public void findVendorByID() {
        BDDMockito.given(repository.findById("somethingID"))
                .willReturn(Mono.just(
                Vendor.builder().id("somethingID").firstName("a").lastName("s").build()
        ));

        webTestClient.get().uri("/vendors/somethingID")
                .exchange()
                .expectBodyList(Vendor.class);
    }

    @Test
    public void createVendor(){
        BDDMockito.given(repository.saveAll(any(Publisher.class))) //flux for saveAll
                .willReturn(Flux.just(Vendor.builder().build()));

        Mono<Vendor> createdNewObject = Mono.just(Vendor.builder().firstName("a").lastName("s").build());

        webTestClient.post()
                .uri("/vendors/")
                .body(createdNewObject, Vendor.class)
                .exchange()
                .expectStatus()
                .isCreated();
    }

    @Test
    public void updateVendor(){
        BDDMockito.given(repository.save(any(Vendor.class)))
                .willReturn(Mono.just(Vendor.builder().build()));

        Mono<Vendor> forUpdate = Mono.just(Vendor.builder().firstName("a").lastName("s").build());

        webTestClient.put()
                .uri("/vendors/someRandomID")
                .body(forUpdate, Vendor.class)
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    public void patchCategoryWithNoChanges(){
        given(repository.findById(anyString()))
                .willReturn(Mono.just(Vendor.builder().build()));

        given(repository.save(any(Vendor.class)))
                .willReturn(Mono.just(Vendor.builder().build()));

        Mono<Vendor> updateObject = Mono.just(Vendor.builder().build());

        webTestClient.patch() //PUT(update) Request
                .uri("/vendors/someRandomID")
                .body(updateObject, Vendor.class)
                .exchange().expectStatus()
                .isOk();

        verify(repository, never()).save(any());
    }

    @Test
    public void patchCategoryWithChanges(){
        given(repository.findById(anyString()))
                .willReturn(Mono.just(Vendor.builder().build()));

        given(repository.save(any(Vendor.class)))
                .willReturn(Mono.just(Vendor.builder().build()));

        Mono<Vendor> updateObject = Mono.just(Vendor.builder().firstName("a").lastName("s").build());

        webTestClient.patch() //PUT(update) Request
                .uri("/vendors/someRandomID")
                .body(updateObject, Vendor.class)
                .exchange().expectStatus()
                .isOk();

        verify(repository).save(any());
    }

    @Test
    public void deleteVendor(){
        given(repository.findById(anyString()))
                .willReturn(Mono.just(Vendor.builder().build()));

        webTestClient.delete() //PUT(update) Request
                .uri("/vendors/someRandomID")
                .exchange().expectStatus()
                .isOk();

        verify(repository).deleteById(anyString());
    }
}