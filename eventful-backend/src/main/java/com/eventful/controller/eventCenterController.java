package com.eventful.controller;

import com.eventful.models.Centers;
import com.eventful.service.CentersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/venues")
public class eventCenterController {

    @Autowired
    CentersService centersService;

    @GetMapping
    public ResponseEntity<List<Centers>> getAllCenters(){
        System.out.println("Get All centers Controller hit");
        return ResponseEntity.ok(centersService.getAllCenters());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Centers>> getCenterById(@PathVariable Long id){
        System.out.println("Testing getCenterById Controller");
        return ResponseEntity.ok(centersService.getCenterById(id));
    }

    @PostMapping
    public ResponseEntity<Centers> addCenter(@RequestBody Centers centers){
        System.out.println("Testing Add center!");
        return ResponseEntity.ok(centersService.addCenter(centers));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Centers> updateCenter(@PathVariable Long id,
                                                @RequestBody Centers centers){
        System.out.println("Testing update center!");
        return ResponseEntity.ok(centersService.updateCenter(id, centers));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteCenter(@PathVariable Long id){

        System.out.println("Testing delete Controller!");
        return ResponseEntity.ok(centersService.deleteCenter(id));
    }
}
