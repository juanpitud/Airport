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
import core.models.Passenger;
import core.models.storage.LocationStorage;
import core.models.storage.PassengerStorage;
import core.models.storage.PlaneStorage;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 *
 * @author david
 */
public class FlightController {

    public static Response createFlight(String id, String planeId, String departureId, String scaleId, String arrivalId, String yearDeparture, String monthDeparture, String dayDeparture, String hourDeparture, String minutesDeparture, String hoursDurationArrivalText, String minutesDurationArrivalText, String hoursDurationScaleText, String minutesDurationScaleText) {
        if (id.trim().isEmpty()) {
            return new Response("Id must not be empty.", Status.BAD_REQUEST);
        }

        if (!id.matches("^[A-Z]{3}\\d{3}$")) {
            return new Response("Id must follow ABZ019 format.", Status.BAD_REQUEST);
        }

        Plane plane = PlaneStorage.getInstance().get(planeId);
        if (plane == null) {
            return new Response("Plane must be valid.", Status.BAD_REQUEST);
        }

        LocationStorage locationStorage = LocationStorage.getInstance();
        Location departure = locationStorage.get(departureId);
        if (departure == null) {
            return new Response("Departure location must be valid.", Status.BAD_REQUEST);
        }

        Location arrival = locationStorage.get(arrivalId);
        if (arrival == null) {
            return new Response("Arrival location must be valid.", Status.BAD_REQUEST);
        }

        Location scale = null;
        if (scaleId != null && !scaleId.trim().isEmpty()) {
            scale = locationStorage.get(scaleId);
            if (scale == null) {
                return new Response("Scale location must be valid.", Status.BAD_REQUEST);
            }
        }

        LocalDateTime departureDate;
        try {
            int year = Integer.parseInt(yearDeparture);
            int month = Integer.parseInt(monthDeparture);
            int day = Integer.parseInt(dayDeparture);
            int hour = Integer.parseInt(hourDeparture);
            int minutes = Integer.parseInt(minutesDeparture);

            departureDate = LocalDateTime.of(year, month, day, hour, minutes);
        } catch (Exception e) {
            return new Response("Departure date must be a date.", Status.BAD_REQUEST);
        }

        int hoursArrival, minutesArrival, hoursScale, minutesScale;

        try {
            hoursArrival = Integer.parseInt(hoursDurationArrivalText);
            minutesArrival = Integer.parseInt(minutesDurationArrivalText);
        } catch (Exception e) {
            return new Response("Duration of arrival must be numeric.", Status.BAD_REQUEST);
        }

        if (hoursArrival + minutesArrival <= 0) {
            return new Response("Time of flight must be valid.", Status.BAD_REQUEST);
        }

        try {
            hoursScale = Integer.parseInt(hoursDurationScaleText);
            minutesScale = Integer.parseInt(minutesDurationScaleText);
        } catch (Exception e) {
            return new Response("Duration of scale must be numeric.", Status.BAD_REQUEST);
        }

        if (scale != null && (hoursScale + minutesScale <= 0)) {
            return new Response("Time of scale must be valid.", Status.BAD_REQUEST);
        }

        if (scale == null && (hoursScale + minutesScale > 0)) {
            return new Response("Time of scale must be zero if there is no scale.", Status.BAD_REQUEST);
        }

        Flight flight = new Flight(id.trim(), plane, departure, scale, arrival, departureDate, hoursArrival, minutesArrival, hoursScale, minutesScale);

        boolean ok = FlightStorage.getInstance().add(flight);
        if (!ok) {
            return new Response("Flight with this ID already exists.", Status.BAD_REQUEST);
        }

        return new Response("Flight created successfully.", Status.CREATED);
    }

    public static Response getFlight(String id) {
        if (id.trim().isEmpty()) {
            return new Response("Flight ID must be provided.", Status.BAD_REQUEST);
        }

        Flight flight = FlightStorage.getInstance().get(id.trim());
        if (flight == null) {
            return new Response("Flight not found.", Status.NOT_FOUND);
        }

        return new Response("Flight found.", Status.OK, flight);
    }

    public static Response deleteFlight(String id) {
        if (id.trim().isEmpty()) {
            return new Response("Flight Id must be provided.", Status.BAD_REQUEST);
        }

        FlightStorage storage = FlightStorage.getInstance();
        boolean removed = storage.delete(id.trim());

        if (!removed) {
            return new Response("Flight not found.", Status.NOT_FOUND);
        }

        return new Response("Flight deleted successfully.", Status.OK);
    }

    public static Response getAllFlights() {
        return new Response("Flights retrieved successfully.", Status.OK, FlightStorage.getInstance().getAll());
    }

    public static Response addPassengerToFlight(String flightIdText, String passengerIdText) {
        long passengerId;
        try {
            passengerId = Long.parseLong(passengerIdText);
        } catch (Exception e) {
            return new Response("Passenger Id must be a valid number.", Status.BAD_REQUEST);
        }

        Flight flight = FlightStorage.getInstance().get(flightIdText);
        if (flight == null) {
            return new Response("Flight not found.", Status.NOT_FOUND);
        }

        Passenger passenger = PassengerStorage.getInstance().get(passengerId);
        if (passenger == null) {
            return new Response("Passenger not found.", Status.NOT_FOUND);
        }

        flight.addPassenger(passenger);
        passenger.addFlight(flight);

        return new Response("Passenger added to flight successfully.", Status.OK);
    }

    public static Response getByPassenger(String passengerIdText) {
        long id;
        try {
            id = Long.parseLong(passengerIdText);
        } catch (Exception e) {
            return new Response("Invalid passenger Id.", Status.BAD_REQUEST);
        }

        Passenger passenger = PassengerStorage.getInstance().get(id);
        if (passenger == null) {
            return new Response("Passenger not found.", Status.NOT_FOUND);
        }

        // copia segura
        ArrayList<Flight> flights = new ArrayList<>(passenger.getFlights());

        return new Response("Flights found.", Status.OK, flights);
    }

    public static Response delayFlight(String id, String hoursText, String minutesText) {
        if (id.trim().isEmpty()) {
            return new Response("Flight Id must be provided.", Status.BAD_REQUEST);
        }

        Flight flight = FlightStorage.getInstance().get(id.trim());
        if (flight == null) {
            return new Response("Flight not found.", Status.NOT_FOUND);
        }

        int hours, minutes;
        try {
            hours = Integer.parseInt(hoursText);
            minutes = Integer.parseInt(minutesText);
        } catch (Exception e) {
            return new Response("Delay must be numeric.", Status.BAD_REQUEST);
        }

        if (hours + minutes <= 0) {
            return new Response("Delay time must be valid.", Status.BAD_REQUEST);
        }

        flight.delay(hours, minutes);
        return new Response("Flight delayed.", Status.OK);
    }

}
