/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controllers;

import core.controllers.utils.Response;
import core.controllers.utils.Status;
import core.models.Location;
import core.models.storage.LocationStorage;

/**
 *
 * @author juans
 */
public class LocationController {

    public static Response createLocation(String airportId, String name, String city, String country, String latitudeText, String longitudeText) {
        if (airportId == null || airportId.trim().isEmpty()) {
            return new Response("Airport Id must not be empty.", Status.BAD_REQUEST);
        }

        if (!airportId.matches("^[A-Z]{3}$")) {
            return new Response("Airport Id must be exactly 3 uppercase letters.", Status.BAD_REQUEST);
        }

        if (name == null || name.trim().isEmpty()) {
            return new Response("Name must be valid.", Status.BAD_REQUEST);
        }

        if (city == null || city.trim().isEmpty()) {
            return new Response("City must be valid.", Status.BAD_REQUEST);
        }

        if (country == null || country.trim().isEmpty()) {
            return new Response("Country must be valid.", Status.BAD_REQUEST);
        }

        double latitude, longitude;
        try {
            latitude = Double.parseDouble(latitudeText);
            if ((latitude * 10000) != Math.floor(latitude * 10000)) {
                return new Response("Latitude must have at most 4 decimal places.", Status.BAD_REQUEST);
            }
            if (latitude < -90 || latitude > 90) {
                return new Response("Latitude must be between -90 and 90.", Status.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new Response("Latitude must be a valid number.", Status.BAD_REQUEST);
        }

        try {
            longitude = Double.parseDouble(longitudeText);
            if ((longitude * 10000) != Math.floor(longitude * 10000)) {
                return new Response("Longitude must have at most 4 decimal places.", Status.BAD_REQUEST);
            }
            if (longitude < -180 || longitude > 180) {
                return new Response("Longitude must be between -180 and 180.", Status.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new Response("Longitude must be a valid number.", Status.BAD_REQUEST);
        }

        Location location = new Location(airportId.trim(), name.trim(), city.trim(), country.trim(), latitude, longitude);

        boolean ok = LocationStorage.getInstance().add(location);
        if (!ok) {
            return new Response("Location with this Id already exists.", Status.BAD_REQUEST);
        }

        return new Response("Location created successfully.", Status.CREATED);
    }

    public static Response updateLocation(String airportId, String name, String city, String country, String latitudeText, String longitudeText) {
        if (airportId.trim().isEmpty()) {
            return new Response("Airport Id must be provided.", Status.BAD_REQUEST);
        }

        LocationStorage storage = LocationStorage.getInstance();
        Location location = storage.get(airportId.trim());

        if (location == null) {
            return new Response("Location not found.", Status.NOT_FOUND);
        }

        if (name.trim().isEmpty()) {
            return new Response("Name must be valid.", Status.BAD_REQUEST);
        }

        if (city.trim().isEmpty()) {
            return new Response("City must be valid.", Status.BAD_REQUEST);
        }

        if (country.trim().isEmpty()) {
            return new Response("Country must be valid.", Status.BAD_REQUEST);
        }

        double latitude, longitude;
        try {
            latitude = Double.parseDouble(latitudeText);
            if ((latitude * 10000) != Math.floor(latitude * 10000)) {
                return new Response("Latitude must have at most 4 decimal places.", Status.BAD_REQUEST);
            }
            if (latitude < -90 || latitude > 90) {
                return new Response("Latitude must be between -90 and 90.", Status.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new Response("Latitude must be a valid number.", Status.BAD_REQUEST);
        }

        try {
            longitude = Double.parseDouble(longitudeText);
            if ((longitude * 10000) != Math.floor(longitude * 10000)) {
                return new Response("Longitude must have at most 4 decimal places.", Status.BAD_REQUEST);
            }
            if (longitude < -180 || longitude > 180) {
                return new Response("Longitude must be between -180 and 180.", Status.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new Response("Longitude must be a valid number.", Status.BAD_REQUEST);
        }

        location.setAirportName(name.trim());
        location.setAirportCity(city.trim());
        location.setAirportCountry(country.trim());
        location.setAirportLatitude(latitude);
        location.setAirportLongitude(longitude);

        return new Response("Location updated successfully.", Status.OK);
    }

    public static Response getLocation(String airportId) {
        if (airportId.trim().isEmpty()) {
            return new Response("Airport Id must be provided.", Status.BAD_REQUEST);
        }

        Location location = LocationStorage.getInstance().get(airportId.trim());
        if (location == null) {
            return new Response("Location not found.", Status.NOT_FOUND);
        }

        return new Response("Location found.", Status.OK, location);
    }

    public static Response deleteLocation(String airportId) {
        if (airportId == null || airportId.trim().isEmpty()) {
            return new Response("Airport Id must be provided.", Status.BAD_REQUEST);
        }

        boolean deleted = LocationStorage.getInstance().delete(airportId.trim());
        if (!deleted) {
            return new Response("Location not found.", Status.NOT_FOUND);
        }

        return new Response("Location deleted successfully.", Status.OK);
    }

    public static Response getAllLocations() {
        return new Response("Locations retrieved successfully.", Status.OK, LocationStorage.getInstance().getAll());
    }
}
