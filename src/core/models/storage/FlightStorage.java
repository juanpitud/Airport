/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.models.storage;

import core.models.Flight;
import core.models.storage.json.FlightReader;
import java.util.ArrayList;

/**
 *
 * @author david
 */
public class FlightStorage implements IStorage<Flight, String> {

    // Instancia Singleton
    private static FlightStorage instance;

    // Atributos del Storage
    private ArrayList<Flight> flights;

    private FlightStorage() {
        this.flights = new ArrayList<>();
        this.loadFromJSON();
    }

    public void loadFromJSON() {
        ArrayList<Flight> flights = FlightReader.read("./json/flights.json");
        for (Flight flight : flights) {
            this.add(flight);
        }
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
