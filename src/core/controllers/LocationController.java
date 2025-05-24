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

    public static Response createLocation(String airportId, String name, String city, String country, double latitude, double longitude) {
        if (airportId.isEmpty()) {
            return new Response("Airport Id must be not empty.", Status.BAD_REQUEST);
        }

        if (!airportId.matches("^[A-Z]{3}$")) {
            return new Response("Airport Id must be exactly 3 uppercase letters.", Status.BAD_REQUEST);
        }

        if (name.isEmpty()) {
            return new Response("Name must be valid.", Status.BAD_REQUEST);
        }

        if (city.isEmpty()) {
            return new Response("City must be valid.", Status.BAD_REQUEST);
        }

        if (country.isEmpty()) {
            return new Response("Country must be valid.", Status.BAD_REQUEST);
        }

        // compruebo si el número multiplicado por 10000, da el mismo que ese pero redondeado. así sabemos si hay más de 4 decimales
        if (latitude * 10000 != Math.floor(latitude * 10000)) {
            return new Response("Latitude must have at most 4 decimal places.", Status.BAD_REQUEST);
        }

        if (latitude < -90 || latitude > 90) {
            return new Response("Latitude must be between -90 and 90.", Status.BAD_REQUEST);
        }

        // compruebo si el número multiplicado por 10000, da el mismo que ese pero redondeado. así sabemos si hay más de 4 decimales
        if (longitude * 10000 != Math.floor(longitude * 10000)) {
            return new Response("Longitude must have at most 4 decimal places.", Status.BAD_REQUEST);
        }

        if (longitude < -180 || longitude > 180) {
            return new Response("Longitude must be between -180 and 180.", Status.BAD_REQUEST);
        }

        Location location = new Location(airportId, name, city, country, latitude, longitude);

        boolean ok = LocationStorage.getInstance().add(location);
        if (!ok) {
            return new Response("Location with this ID already exists.", Status.BAD_REQUEST);
        }

        return new Response("Location created successfully.", Status.CREATED);
    }

    public static Response getLocation(String airportId) {
        Location location = LocationStorage.getInstance().get(airportId);
        if (location == null) {
            return new Response("Location not found.", Status.NOT_FOUND);
        }

        return new Response("Location found.", Status.OK, location);
    }

    public static Response updateLocation(String airportId, String name, String city, String country, double latitude, double longitude) {
        LocationStorage storage = LocationStorage.getInstance();
        Location location = storage.get(airportId);

        if (location == null) {
            return new Response("Location not found.", Status.NOT_FOUND);
        }

        if (name.isEmpty()) {
            return new Response("Name must be valid.", Status.BAD_REQUEST);
        }

        if (city.isEmpty()) {
            return new Response("City must be valid.", Status.BAD_REQUEST);
        }

        if (country.isEmpty()) {
            return new Response("Country must be valid.", Status.BAD_REQUEST);
        }

        // compruebo si el número multiplicado por 10000, da el mismo que ese pero redondeado. así sabemos si hay más de 4 decimales
        if (latitude * 10000 != Math.floor(latitude * 10000)) {
            return new Response("Latitude must have at most 4 decimal places.", Status.BAD_REQUEST);
        }

        if (latitude < -90 || latitude > 90) {
            return new Response("Latitude must be between -90 and 90.", Status.BAD_REQUEST);
        }

        // compruebo si el número multiplicado por 10000, da el mismo que ese pero redondeado. así sabemos si hay más de 4 decimales
        if (longitude * 10000 != Math.floor(longitude * 10000)) {
            return new Response("Longitude must have at most 4 decimal places.", Status.BAD_REQUEST);
        }

        if (longitude < -180 || longitude > 180) {
            return new Response("Longitude must be between -180 and 180.", Status.BAD_REQUEST);
        }

        location.setAirportName(name);
        location.setAirportCity(city);
        location.setAirportCountry(country);
        location.setAirportLatitude(latitude);
        location.setAirportLongitude(longitude);

        return new Response("Location updated successfully.", Status.OK);
    }

    public static Response deleteLocation(String airportId) {
        boolean deleted = LocationStorage.getInstance().delete(airportId);
        if (!deleted) {
            return new Response("Location not found.", Status.NOT_FOUND);
        }

        return new Response("Location deleted successfully.", Status.OK);
    }

    public static Response getAllLocations() {
        return new Response("Locations retrieved successfully.", Status.OK, LocationStorage.getInstance().getAll());
    }

}
