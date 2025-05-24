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

        boolean ok = PassengerStorage.getInstance().add(passenger);
        if (!ok) {
            return new Response("Passenger with this ID already exists.", Status.BAD_REQUEST);
        }

        return new Response("Passenger created successfully.", Status.CREATED);
    }

    public static Response getPassenger(long id) {
        Passenger passenger = PassengerStorage.getInstance().get(id);
        if (passenger == null) {
            return new Response("Passenger not found.", Status.NOT_FOUND);
        }

        return new Response("Passenger found.", Status.OK, passenger);
    }

    public static Response updatePassenger(long id, String firstname, String lastname, LocalDate birthDate, int countryPhoneCode, long phone, String country) {
        PassengerStorage storage = PassengerStorage.getInstance();
        Passenger passenger = storage.get(id);

        if (passenger == null) {
            return new Response("Passenger not found.", Status.NOT_FOUND);
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

        if (phone <= 0 || phone >= 1000000000000L) {
            return new Response("Phone number must be valid.", Status.BAD_REQUEST);
        }

        if (country.isEmpty()) {
            return new Response("Country must be valid.", Status.BAD_REQUEST);
        }

        passenger.setBirthDate(birthDate);
        passenger.setFirstname(firstname);
        passenger.setLastname(lastname);
        passenger.setCountryPhoneCode(countryPhoneCode);
        passenger.setPhone(phone);
        passenger.setCountry(country);

        return new Response("Passenger updated successfully.", Status.OK);

    }

    public static Response deletePassenger(long id) {
        boolean deleted = PassengerStorage.getInstance().delete(id);
        if (!deleted) {
            return new Response("Passenger not found.", Status.NOT_FOUND);
        }

        return new Response("Passenger deleted successfully.", Status.OK);
    }
}
