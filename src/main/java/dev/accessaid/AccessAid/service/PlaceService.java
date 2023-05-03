package dev.accessaid.AccessAid.service;

import org.springframework.stereotype.Service;

import dev.accessaid.AccessAid.model.PlaceModel;
import dev.accessaid.AccessAid.repository.PlaceRepository;

@Service
public class PlaceService {
    private PlaceRepository placeRepository;

    public void savePlace(PlaceModel place){
        placeRepository.save(place);
    }
}
