package com.eventful.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Centers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String location;
    private Double price;
    private int capacity;
    private List<String> amenities;
    private String status = "active";
    private List<String> image;
    private List<String> availableDates;

    @CreationTimestamp
    private LocalDate createdAt;
}

//{   "id":"1755086909199",
//    "name":"Abas hospital",
//    "location":"Fake Location",
//    "image":"/images/Background.jpeg",
//    "description":"fake stuff",
//    "price":350000,
//    "capacity":350000,
//    "status":"active",
//    "amenities":["aad","dssf"]
//}
