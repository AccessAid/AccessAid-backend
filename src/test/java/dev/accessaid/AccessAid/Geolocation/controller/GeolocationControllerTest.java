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

    private static final String FORMATTED_ADDRESS = "123 Main St, Evanston, IL 60202, USA";
    private static final double LATITUDE = 42.0339357;
    private static final double LONGITUDE = -87.6721867;
    private static final String PLACE_ID = "ChIJDYp7EELQD4gRDzMUL_0DFlU";

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
        GeolocationResponse response = new GeolocationResponse();
        response.setLatitude(LATITUDE);
        response.setLongitude(LONGITUDE);
        response.setFormattedAddress(FORMATTED_ADDRESS);
        response.setPlaceId(PLACE_ID);
        Mockito.when(geolocationService.getGeolocationByAddress(FORMATTED_ADDRESS)).thenReturn(response);

        System.out.println(response.getLatitude());
        mockMvc.perform(get("/api/geolocation/byaddress")
                .param("address",
                        FORMATTED_ADDRESS))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lat").value(response.getLatitude()))
                .andExpect(jsonPath("$.lng").value(response.getLongitude()))
                .andExpect(jsonPath("$.formatted_address").value(response.getFormattedAddress()))
                .andExpect(jsonPath("$.place_id").value(response.getPlaceId()))
                .andExpect(status().isOk());

    }

    @Test
    public void testGetGeolocationByCoordinates() throws Exception {
        GeolocationResponse response = new GeolocationResponse();
        response.setFormattedAddress(FORMATTED_ADDRESS);
        response.setPlaceId(PLACE_ID);
        Mockito.when(geolocationService.getGeolocationByCoordinates(LATITUDE, LONGITUDE)).thenReturn(response);

        mockMvc.perform(get("/api/geolocation/bycoordinates")
                .param("latitude", String.valueOf(LATITUDE))
                .param("longitude", String.valueOf(LONGITUDE)))
                .andExpect(jsonPath("$.formatted_address").value(response.getFormattedAddress()))
                .andExpect(jsonPath("$.place_id").value(response.getPlaceId()))
                .andExpect(status().isOk());

    }

    @Test
    public void testGetGeolocationByAddressThrowsException() throws Exception {
        Mockito.when(geolocationService.getGeolocationByAddress(FORMATTED_ADDRESS)).thenThrow(new Exception());

        mockMvc.perform(get("/api/geolocation/byaddress")
                .param("address",
                        FORMATTED_ADDRESS))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("Error"));
    }

    @Test
    public void testGetGeolocationByCoordinatesThrowsException() throws Exception {
        Mockito.when(geolocationService.getGeolocationByCoordinates(LATITUDE, LONGITUDE)).thenThrow(new Exception());

        mockMvc.perform(get("/api/geolocation/bycoordinates")
                .param("latitude", String.valueOf(LATITUDE))
                .param("longitude", String.valueOf(LONGITUDE)))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("Error"));
    }

}