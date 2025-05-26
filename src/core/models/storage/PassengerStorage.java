/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.models.storage;

import core.models.Passenger;
import core.models.storage.json.PassengerReader;
import java.util.ArrayList;

/**
 *
 * @author juans
 */
public class PassengerStorage implements IStorage<Passenger, Long> {

    // Instancia Singleton
    private static PassengerStorage instance;

    // Atributos del Storage
    private ArrayList<Passenger> passengers;

    private PassengerStorage() {
        this.passengers = new ArrayList<>();
        this.loadFromJSON();
    }

    public void loadFromJSON() {
        ArrayList<Passenger> passengers = PassengerReader.read("./json/passengers.json");
        for (Passenger passenger : passengers) {
            this.add(passenger);
        }
    }

    public static PassengerStorage getInstance() {
        if (instance == null) {
            instance = new PassengerStorage();
        }
        return instance;
    }

    public boolean add(Passenger passenger) {
        for (Passenger l : passengers) {
            if (l.getId() == passenger.getId()) {
                return false;
            }
        }

        return passengers.add(passenger);
    }

    public Passenger get(Long id) {
        for (Passenger l : passengers) {
            if (l.getId() == id) {
                return l;
            }
        }
        return null;
    }

    public ArrayList<Passenger> getAll() {
        return new ArrayList<>(passengers);
    }

    public boolean delete(Long id) {
        return passengers.removeIf(passenger -> passenger.getId() == id);
    }
}
