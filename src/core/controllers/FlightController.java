/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controllers;

import core.controllers.utils.Response;
import core.controllers.utils.Status;
import core.models.Flight;
import core.models.FlightStorage;
import core.models.Location;
import core.models.Plane;
import java.time.LocalDateTime;

/**
 *
 * @author david
 */
public class FlightController {

    public static Response createFlight(String id, Plane plane, Location departureLocation, Location arrivalLocation, LocalDateTime departureDate, int hoursDurationArrival, int minutesDurationArrival) {
        Flight flight = new Flight(id, plane, departureLocation, arrivalLocation, departureDate, hoursDurationArrival, minutesDurationArrival);
        FlightStorage.getInstance().add(flight);
        // add verifications

        return new Response("Flight created successfully.", Status.CREATED);
    }
}
