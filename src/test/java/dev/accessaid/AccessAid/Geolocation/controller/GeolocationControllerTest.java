package dev.accessaid.AccessAid.Geolocation.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import dev.accessaid.AccessAid.Geolocation.Response.GeolocationResponse;
import dev.accessaid.AccessAid.Geolocation.service.GeolocationService;

@ExtendWith(MockitoExtension.class)
public class GeolocationControllerTest {

    @Mock
    private GeolocationService geolocationService;

    @InjectMocks
    private GeolocationController geolocationController;
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(geolocationController).build();
    }

    @Test
    public void testGetGeolocationByAddress() throws Exception {
        String address = "123 Main St, Chicago, IL";
        GeolocationResponse response = new GeolocationResponse();
        response.setLatitude(42.0339357);
        response.setLongitude(-87.6721867);
        response.setFormattedAddress("123 Main St, Evanston, IL 60202, USA");
        response.setPlaceId("ChIJDYp7EELQD4gRDzMUL_0DFlU");
        Mockito.when(geolocationService.getGeolocationByAddress(address)).thenReturn(response);

        System.out.println(response.getLatitude());
        mockMvc.perform(get("/api/geolocation/byaddress")
                .param("address", address))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lat").value(response.getLatitude()))
                .andExpect(jsonPath("$.lng").value(response.getLongitude()))
                .andExpect(jsonPath("$.formatted_address").value(response.getFormattedAddress()))
                .andExpect(jsonPath("$.place_id").value(response.getPlaceId()))
                .andExpect(status().isOk());

    }

    @Test
    public void testGetGeolocationByCoordinates() throws Exception {
        double latitude = 42.0339357;
        double longitude = -87.6721867;
        GeolocationResponse response = new GeolocationResponse();
        response.setFormattedAddress("123 Main St, Evanston, IL 60202, USA");
        response.setPlaceId("ChIJDYp7EELQD4gRDzMUL_0DFlU");
        Mockito.when(geolocationService.getGeolocationByCoordinates(latitude, longitude)).thenReturn(response);

        mockMvc.perform(get("/api/geolocation/bycoordinates")
                .param("latitude", String.valueOf(latitude))
                .param("longitude", String.valueOf(longitude)))
                .andExpect(jsonPath("$.formatted_address").value(response.getFormattedAddress()))
                .andExpect(jsonPath("$.place_id").value(response.getPlaceId()))
                .andExpect(status().isOk());

    }

    @Test
    public void testGetGeolocationByAddressThrowsException() throws Exception {
        String address = "123 Main St, Chicago, IL";
        Mockito.when(geolocationService.getGeolocationByAddress(address)).thenThrow(new Exception());

        mockMvc.perform(get("/api/geolocation/byaddress")
                .param("address", address))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("Error"));
    }

    @Test
    public void testGetGeolocationByCoordinatesThrowsException() throws Exception {
        double latitude = 41.8781;
        double longitude = -87.6298;
        Mockito.when(geolocationService.getGeolocationByCoordinates(latitude, longitude)).thenThrow(new Exception());

        mockMvc.perform(get("/api/geolocation/bycoordinates")
                .param("latitude", String.valueOf(latitude))
                .param("longitude", String.valueOf(longitude)))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("Error"));
    }

}
