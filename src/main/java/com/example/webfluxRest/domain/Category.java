package com.example.webfluxRest.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
@Builder
@Document
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    @Id
    private String id;

    private String description;
}


