package dev.accessaid.AccessAid.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.accessaid.AccessAid.model.PlaceModel;

public interface PlaceRepository extends JpaRepository<PlaceModel, Long>{
    
}