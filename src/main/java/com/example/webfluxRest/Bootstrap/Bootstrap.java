package com.example.webfluxRest.Bootstrap;

import com.example.webfluxRest.domain.Category;
import com.example.webfluxRest.domain.Vendor;
import com.example.webfluxRest.repository.CategoryRepository;
import com.example.webfluxRest.repository.VendorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap implements CommandLineRunner {
    private final CategoryRepository categoryRepository;
    private final VendorRepository vendorRepository;

    public Bootstrap(CategoryRepository categoryRepository, VendorRepository vendorRepository) {
        this.categoryRepository = categoryRepository;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadCategories();
        loadVendors();
    }
    private void loadCategories() {
        System.out.println("#### LOADING DATA ON BOOTSTRAP #####");

        categoryRepository.save(Category.builder()
                .description("Fruits").build()).block();

        categoryRepository.save(Category.builder()
                .description("Nuts").build()).block();

        categoryRepository.save(Category.builder()
                .description("Breads").build()).block();

        categoryRepository.save(Category.builder()
                .description("Meats").build()).block();

        categoryRepository.save(Category.builder()
                .description("Eggs").build()).block();

        System.out.println("Loaded Categories: " + categoryRepository.count().block());
    }
    private void loadVendors() {
        vendorRepository.save(Vendor.builder()
                .firstName("Joe")
                .lastName("Buck").build()).block();

        vendorRepository.save(Vendor.builder()
                .firstName("Micheal")
                .lastName("Weston").build()).block();

        vendorRepository.save(Vendor.builder()
                .firstName("Jessie")
                .lastName("Waters").build()).block();

        vendorRepository.save(Vendor.builder()
                .firstName("Bill")
                .lastName("Nershi").build()).block();

        vendorRepository.save(Vendor.builder()
                .firstName("Jimmy")
                .lastName("Buffett").build()).block();

        System.out.println("Loaded Vendors: " + vendorRepository.count().block());
    }
}
