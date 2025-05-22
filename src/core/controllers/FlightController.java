/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controllers;

import core.controllers.utils.Response;
import core.controllers.utils.Status;
import core.models.Flight;
import core.models.storage.FlightStorage;
import core.models.Location;
import core.models.Plane;
import core.models.storage.LocationStorage;
import java.time.LocalDateTime;
import java.util.regex.Pattern;

/**
 *
 * @author david
 */
public class FlightController {

    public static Response createFlight(String id, Plane plane, Location departureLocation, Location arrivalLocation, LocalDateTime departureDate, int hoursDurationArrival, int minutesDurationArrival) {
        if (id.isEmpty()) {
            return new Response("Id must be not empty.", Status.BAD_REQUEST);
        }

        Pattern pattern = Pattern.compile("[A-Z]{3}\\d{3}");
        if (pattern.matcher(id).find()) {
            return new Response("Id must follow ABZ019 format.", Status.BAD_REQUEST);
        }

        Flight existentFlight = FlightStorage.getInstance().get(id);
        if (existentFlight != null) {
            return new Response("Id must be unique.", Status.BAD_REQUEST);
        }

        LocationStorage locationStorage = LocationStorage.getInstance();
        Location departure = locationStorage.get(departureLocation.getAirportId());
        Location arrival = locationStorage.get(arrivalLocation.getAirportId());
        if (departure == null) {
            return new Response("Departure location must be valid.", Status.BAD_REQUEST);
        }

        if (arrival == null) {
            return new Response("Arrival location must be valid.", Status.BAD_REQUEST);
        }

        // localizaciones   
        //        if (departureDate.)
        // comprobar si la fecha es valida
        if (hoursDurationArrival + minutesDurationArrival <= 0) {
            return new Response("Time of flight must be valid.", Status.BAD_REQUEST);
        }

        Flight flight = new Flight(id, plane, departureLocation, arrivalLocation, departureDate, hoursDurationArrival, minutesDurationArrival);
        FlightStorage.getInstance().add(flight);

        return new Response("Flight created successfully.", Status.CREATED);
    }

    public static Response getFlight(String id) {
        Flight flight = FlightStorage.getInstance().get(id);
        if (flight == null) {
            return new Response("Flight not found.", Status.NOT_FOUND);
        }

        return new Response("Flight found.", Status.OK, flight);
    }

    public static Response updateFlight(String id, Plane plane, Location departureLocation, Location arrivalLocation, LocalDateTime departureDate, int hoursDurationArrival, int minutesDurationArrival) {
        Flight flight = FlightStorage.getInstance().get(id);
        if (flight == null) {
            return new Response("Flight not found.", Status.NOT_FOUND);
        }

        LocationStorage locationStorage = LocationStorage.getInstance();
        Location departure = locationStorage.get(departureLocation.getAirportId());
        Location arrival = locationStorage.get(arrivalLocation.getAirportId());
        if (departure == null) {
            return new Response("Departure location must be valid.", Status.BAD_REQUEST);
        }

        if (arrival == null) {
            return new Response("Arrival location must be valid.", Status.BAD_REQUEST);
        }

///
        if (hoursDurationArrival + minutesDurationArrival <= 0) {
            return new Response("Time of flight must be valid.", Status.BAD_REQUEST);
        }

        return new Response("Flight edited successfully.", Status.NO_CONTENT);

    }

    public static Response deleteFlight(String id) {
        Flight flight = FlightStorage.getInstance().get(id);
        if (flight == null) {
            return new Response("Flight not found.", Status.NOT_FOUND);
        }

        return new Response("Flight deleted successfully.", Status.NO_CONTENT);
    }

    public static Response getAllFlights() {
        return new Response("Flight retrieved successfully.", Status.OK, FlightStorage.getInstance().getAll());
    }
}
