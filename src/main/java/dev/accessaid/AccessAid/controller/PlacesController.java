package dev.accessaid.AccessAid.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import dev.accessaid.AccessAid.model.PlaceModel;
import dev.accessaid.AccessAid.service.PlaceService;


@RestController
public class PlacesController {

    @Autowired
    private PlaceService placeService;

    @GetMapping("/places/{placeId}")
    public PlaceModel getPlace(@PathVariable String placeId){
        return new PlaceModel(placeId, placeId, placeId, 0, 0);
    }

}
