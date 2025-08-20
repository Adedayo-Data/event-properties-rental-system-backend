package com.eventful.service;

import com.eventful.models.Centers;
import com.eventful.repository.CentersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CentersService {

    @Autowired
    private CentersRepository centersRepository;

    public List<Centers> getAllCenters() {
        return centersRepository.findAll();
    }

    public Optional<Centers> getCenterById(Long id) {
        return centersRepository.findById(id);
    }

    public Centers addCenter(Centers center) {
        return centersRepository.save(center);
    }

    public Centers updateCenter(Long id, Centers updatedCenter) {
        return centersRepository.findById(id).map(center -> {
            center.setName(updatedCenter.getName());
            center.setPrice(updatedCenter.getPrice());
            center.setCapacity(updatedCenter.getCapacity());
            center.setAmenities(updatedCenter.getAmenities());
            center.setImage(updatedCenter.getImage());
            center.setAvailableDates(updatedCenter.getAvailableDates());
            return centersRepository.save(center);
        }).orElse(null);
    }

    public boolean deleteCenter(Long id) {
        if (centersRepository.existsById(id)) {
            centersRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<String> getAvailableDates(Long id) {
        Optional<Centers> optCenter = getCenterById(id);
        if(optCenter.isEmpty()){
            return null;
        }
        Centers center = optCenter.get();
        return center.getAvailableDates();
    }
}
