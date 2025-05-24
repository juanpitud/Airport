/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.models.storage;

import core.models.Flight;
import java.util.ArrayList;

/**
 *
 * @author david
 */
public class FlightStorage {

    // Instancia Singleton
    private static FlightStorage instance;

    // Atributos del Storage
    private ArrayList<Flight> flights;

    private FlightStorage() {
        this.flights = new ArrayList<>();
    }

    public static FlightStorage getInstance() {
        if (instance == null) {
            instance = new FlightStorage();
        }
        return instance;
    }

    public boolean add(Flight flight) {
        for (Flight f : flights) {
            if (f.getId().equals(flight.getId())) {
                return false;
            }
        }

        return flights.add(flight);
    }

    public Flight get(String id) {
        for (Flight fl : flights) {
            if (fl.getId().equals(id)) {
                return fl;
            }
        }
        return null;
    }

    public ArrayList<Flight> getAll() {
        return new ArrayList<>(flights);
    }

    public boolean delete(String id) {
        return flights.removeIf(flight -> flight.getId().equals(id));
    }
}
