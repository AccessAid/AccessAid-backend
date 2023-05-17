package dev.accessaid.AccessAid.Accessibility.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.maps.model.AddressType;

import dev.accessaid.AccessAid.Accessibility.response.NearbyPlacesResponse;
import lombok.Getter;
import lombok.Setter;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Getter
@Setter
public class NearbyPlaceUtils {

        public static boolean isAccessible = false;

        public static Response getResponse(Double lat, Double lng, String type, String apikey) throws Exception {

                OkHttpClient client = new OkHttpClient().newBuilder()
                                .build();

                String accessible = "";
                AddressType addressType = null;
                if (type != "") {
                        addressType = Arrays.stream(AddressType.values())
                                        .filter(value -> value.name().equalsIgnoreCase(type))
                                        .findFirst()
                                        .orElse(null);
                        if (addressType != null) {
                                accessible = "&keyword=accessible";
                                isAccessible = true;
                        }
                }

                String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" +
                                lat + "%2C" + lng
                                + "&radius=1500&type=" + addressType + accessible + "&key=" + apikey;

                Request request = new Request.Builder()
                                .url(url)
                                .method("GET", null)
                                .build();
                Response response = client.newCall(request).execute();
                return response;

        }

        public static List<NearbyPlacesResponse> getNearbyPlaces(JsonElement results) {
                JsonArray jsonArray = results.getAsJsonArray();
                List<NearbyPlacesResponse> nearbyPlaces = new ArrayList<>();
                for (int i = 0; i < jsonArray.size(); i++) {
                        JsonObject placeObject = jsonArray.get(i).getAsJsonObject();

                        JsonObject geometryObject = placeObject.getAsJsonObject("geometry");
                        JsonObject locationObject = geometryObject.getAsJsonObject("location");
                        JsonElement latElement = locationObject.get("lat");
                        JsonElement lngElement = locationObject.get("lng");
                        Double latitud = latElement != null && !latElement.isJsonNull() ? latElement.getAsDouble()
                                        : null;
                        Double longitude = lngElement != null && !lngElement.isJsonNull() ? lngElement.getAsDouble()
                                        : null;

                        String place_id = placeObject.get("place_id") != null
                                        ? placeObject.get("place_id").getAsString()
                                        : null;
                        String name = placeObject.get("name") != null ? placeObject.get("name").toString() : null;

                        Boolean wheelchair_accessible_entrance = isAccessible ? true : null;

                        NearbyPlacesResponse nearbyPlaceResponse = new NearbyPlacesResponse(name,
                                        latitud, longitude, place_id, wheelchair_accessible_entrance);

                        nearbyPlaces.add(nearbyPlaceResponse);
                }
                return nearbyPlaces;
        }

}
