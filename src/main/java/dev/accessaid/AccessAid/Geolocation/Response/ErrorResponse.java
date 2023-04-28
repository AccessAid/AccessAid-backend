package dev.accessaid.AccessAid.Geolocation.Response;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Hidden
public class ErrorResponse {
    private String message;

}