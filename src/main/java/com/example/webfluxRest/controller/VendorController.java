package com.example.webfluxRest.controller;

import com.example.webfluxRest.domain.Vendor;
import com.example.webfluxRest.repository.VendorRepository;
import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

@RestController
@RequestMapping("/vendors")
public class VendorController {
    private final VendorRepository vendorRepository;

    public VendorController(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }

    @GetMapping("/list")
    public Flux<Vendor> getAllVendors(){
        return vendorRepository.findAll();
    }

    @GetMapping("/{ID}")
    public Mono<Vendor> findVendorByID(@PathVariable String ID){
        return vendorRepository.findById(ID);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/")
    Mono<Void> createVendor(@RequestBody Publisher<Vendor> vendorStream){
        return vendorRepository.saveAll(vendorStream).then();
    }

    @PutMapping("/{id}")
    Mono<Vendor> updateVendor(@PathVariable String id, @RequestBody Vendor vendor){
        vendor.setId(id);
        return vendorRepository.save(vendor);
    }

    @PatchMapping("/{id}")
    Mono<Vendor> patchVendor(@PathVariable String id, @RequestBody Vendor vendor){
        Vendor foundVendor = vendorRepository.findById(id).block();

        assert foundVendor != null;
        if(!Objects.equals(foundVendor.getFirstName(), vendor.getFirstName())){
            foundVendor.setFirstName(vendor.getFirstName());

            return vendorRepository.save(foundVendor);
        }

        return Mono.just(foundVendor);
    }

    @DeleteMapping("/{id}")
    Mono<Void> deleteVendor(@PathVariable String id){
        return vendorRepository.deleteById(id);
    }
}
