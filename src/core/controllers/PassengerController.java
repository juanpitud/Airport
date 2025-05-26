/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controllers;

import core.controllers.utils.Response;
import core.controllers.utils.Status;
import core.models.Flight;
import core.models.Passenger;
import core.models.storage.FlightStorage;
import core.models.storage.PassengerStorage;
import java.time.LocalDate;

/**
 *
 * @author juans
 */
public class PassengerController {

    public static Response createPassenger(String idText, String firstname, String lastname, String yearText, String monthText, String dayText, String countryPhoneCodeText, String phoneText, String country) {
        long id;
        int year, month, day;
        int countryPhoneCode;
        long phone;
        LocalDate birthDate;

        try {
            id = Long.parseLong(idText);
            if (id <= 0 || id >= 10000000000000000L) {
                return new Response("Id must be greater than 0 and have less than 16 digits.", Status.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new Response("Id must be a valid number.", Status.BAD_REQUEST);
        }

        if (firstname.trim().isEmpty()) {
            return new Response("First name must be valid.", Status.BAD_REQUEST);
        }

        if (lastname.trim().isEmpty()) {
            return new Response("Last name must be valid.", Status.BAD_REQUEST);
        }

        try {
            year = Integer.parseInt(yearText);
            month = Integer.parseInt(monthText);
            day = Integer.parseInt(dayText);
            birthDate = LocalDate.of(year, month, day);

            if (birthDate.isAfter(LocalDate.now())) {
                return new Response("Birthday must be a valid past date.", Status.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new Response("Birth date must be valid.", Status.BAD_REQUEST);
        }

        try {
            countryPhoneCode = Integer.parseInt(countryPhoneCodeText);
            if (countryPhoneCode <= 0 || countryPhoneCode >= 1000) {
                return new Response("Country code must be a valid number with up to 3 digits.", Status.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new Response("Country code must be a valid number.", Status.BAD_REQUEST);
        }

        try {
            phone = Long.parseLong(phoneText);
            if (phone <= 0 || phone >= 1000000000000L) {
                return new Response("Phone number must be a valid number with up to 11 digits.", Status.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new Response("Phone number must be a valid number.", Status.BAD_REQUEST);
        }

        if (country.trim().isEmpty()) {
            return new Response("Country must be valid.", Status.BAD_REQUEST);
        }

        Passenger passenger = new Passenger(id, firstname.trim(), lastname.trim(), birthDate, countryPhoneCode, phone, country.trim());

        boolean ok = PassengerStorage.getInstance().add(passenger);
        if (!ok) {
            return new Response("Passenger with this ID already exists.", Status.BAD_REQUEST);
        }

        return new Response("Passenger created successfully.", Status.CREATED);
    }

    public static Response updatePassenger(String idText, String firstname, String lastname, String yearText, String monthText, String dayText, String countryPhoneCodeText, String phoneText, String country) {
        long id;
        int year, month, day;
        int countryPhoneCode;
        long phone;
        LocalDate birthDate;

        try {
            id = Long.parseLong(idText);
        } catch (NumberFormatException e) {
            return new Response("Id must be a number.", Status.BAD_REQUEST);
        }

        PassengerStorage storage = PassengerStorage.getInstance();
        Passenger passenger = storage.get(id);

        if (passenger == null) {
            return new Response("Passenger not found.", Status.NOT_FOUND);
        }

        if (firstname.trim().isEmpty()) {
            return new Response("First name must be valid.", Status.BAD_REQUEST);
        }

        if (lastname.trim().isEmpty()) {
            return new Response("Last name must be valid.", Status.BAD_REQUEST);
        }

        try {
            year = Integer.parseInt(yearText);
            month = Integer.parseInt(monthText);
            day = Integer.parseInt(dayText);
            birthDate = LocalDate.of(year, month, day);

            if (birthDate.isAfter(LocalDate.now())) {
                return new Response("Birthday must be a valid past date.", Status.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new Response("Birth date must be valid.", Status.BAD_REQUEST);
        }

        try {
            countryPhoneCode = Integer.parseInt(countryPhoneCodeText);
            if (countryPhoneCode <= 0 || countryPhoneCode >= 1000) {
                return new Response("Country code must be valid.", Status.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new Response("Country code must be a valid number.", Status.BAD_REQUEST);
        }

        try {
            phone = Long.parseLong(phoneText);
            if (phone <= 0 || phone >= 1000000000000L) {
                return new Response("Phone number must be valid.", Status.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new Response("Phone number must be a valid number.", Status.BAD_REQUEST);
        }

        if (country == null || country.trim().isEmpty()) {
            return new Response("Country must be valid.", Status.BAD_REQUEST);
        }

        passenger.setFirstname(firstname.trim());
        passenger.setLastname(lastname.trim());
        passenger.setBirthDate(birthDate);
        passenger.setCountryPhoneCode(countryPhoneCode);
        passenger.setPhone(phone);
        passenger.setCountry(country.trim());

        return new Response("Passenger updated successfully.", Status.OK);
    }

    public static Response getPassenger(long id) {
        Passenger passenger = PassengerStorage.getInstance().get(id);
        if (passenger == null) {
            return new Response("Passenger not found.", Status.NOT_FOUND);
        }

        return new Response("Passenger found.", Status.OK, passenger);
    }

    public static Response deletePassenger(long id) {
        boolean deleted = PassengerStorage.getInstance().delete(id);
        if (!deleted) {
            return new Response("Passenger not found.", Status.NOT_FOUND);
        }

        return new Response("Passenger deleted successfully.", Status.OK);
    }

    public static Response getAllPassengers() {
        return new Response("Passengers retrieved successfully.", Status.OK, PassengerStorage.getInstance().getAll());
    }

    public static Response addPassengerToFlight(String flightId, long passengerId) {
        Flight flight = FlightStorage.getInstance().get(flightId);
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
}