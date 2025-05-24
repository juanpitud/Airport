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
import core.models.storage.PlaneStorage;
import java.time.LocalDateTime;

/**
 *
 * @author david
 */
public class FlightController {

    public static Response createFlight(String id, String planeId, String departureId, String arrivalId, LocalDateTime departureDate, int hoursDurationArrival, int minutesDurationArrival) {
        if (id.isEmpty()) {
            return new Response("Id must be not empty.", Status.BAD_REQUEST);
        }

        if (!id.matches("^[A-Z]{3}\\d{3}$")) {
            return new Response("Id must follow ABZ019 format.", Status.BAD_REQUEST);
        }

        PlaneStorage planeStorage = PlaneStorage.getInstance();
        Plane plane = planeStorage.get(planeId);
        if (plane == null) {
            return new Response("Plane must be valid.", Status.BAD_REQUEST);
        }

        LocationStorage locationStorage = LocationStorage.getInstance();
        Location departure = locationStorage.get(departureId);
        Location arrival = locationStorage.get(arrivalId);
        if (departure == null) {
            return new Response("Departure location must be valid.", Status.BAD_REQUEST);
        }

        if (arrival == null) {
            return new Response("Arrival location must be valid.", Status.BAD_REQUEST);
        }

        if (hoursDurationArrival + minutesDurationArrival <= 0) {
            return new Response("Time of flight must be valid.", Status.BAD_REQUEST);
        }

        Flight flight = new Flight(id, plane, departure, arrival, departureDate, hoursDurationArrival, minutesDurationArrival);

        boolean ok = FlightStorage.getInstance().add(flight);
        if (!ok) {
            return new Response("Flight with this ID already exists.", Status.BAD_REQUEST);
        }

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
        FlightStorage storage = FlightStorage.getInstance();
        Flight flight = storage.get(id);

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

        if (hoursDurationArrival + minutesDurationArrival <= 0) {
            return new Response("Time of flight must be valid.", Status.BAD_REQUEST);
        }

        return new Response("Flight edited successfully.", Status.OK);

    }

    public static Response deleteFlight(String id) {
        Flight flight = FlightStorage.getInstance().get(id);
        if (flight == null) {
            return new Response("Flight not found.", Status.NOT_FOUND);
        }

        return new Response("Flight deleted successfully.", Status.OK);
    }

    public static Response getAllFlights() {
        return new Response("Flights retrieved successfully.", Status.OK, FlightStorage.getInstance().getAll());
    }
}
