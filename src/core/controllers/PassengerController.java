/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controllers;

import core.controllers.utils.Response;
import core.controllers.utils.Status;
import core.models.Passenger;
import core.models.storage.PassengerStorage;
import java.time.LocalDate;

/**
 *
 * @author juans
 */
public class PassengerController {

    public static Response createPassenger(long id, String firstname, String lastname, LocalDate birthDate, int countryPhoneCode, long phone, String country) {
        if (id <= 0 || id >= 10000000000000000l) {
            return new Response("Id must be greater than 0 and have less than 16 digits.", Status.BAD_REQUEST);
        }
        
        if (birthDate.isAfter(LocalDate.now())) {
            return new Response("Birthday must be valid.", Status.BAD_REQUEST);
        }

        if (firstname.isEmpty()) {
            return new Response("First name must be valid.", Status.BAD_REQUEST);
        }

        if (lastname.isEmpty()) {
            return new Response("Last name must be valid.", Status.BAD_REQUEST);
        }

        if (countryPhoneCode <= 0 || countryPhoneCode >= 1000) {
            return new Response("Country code must be valid.", Status.BAD_REQUEST);
        }

        if (phone <= 0 || phone >= 1000000000000l) {
            return new Response("Phone number must be valid.", Status.BAD_REQUEST);
        }

        if (country.isEmpty()) {
            return new Response("Country must be valid.", Status.BAD_REQUEST);
        }
        
        Passenger passenger = new Passenger(id, firstname, lastname, birthDate, countryPhoneCode, phone, country);
        PassengerStorage.getInstance().add(passenger);
        
        return new Response("Passenger created successfully.", Status.CREATED);
    }

    public static Response getPassenger() {
        return new Response("", Status.NOT_IMPLEMENTED);
    }

    public static Response updatePassenger() {
        return new Response("", Status.NOT_IMPLEMENTED);
    }

    public static Response deletePassenger() {
        return new Response("", Status.NOT_IMPLEMENTED);
    }
}
