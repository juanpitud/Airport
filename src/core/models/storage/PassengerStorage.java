/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.models.storage;

import core.models.Passenger;
import java.util.ArrayList;

/**
 *
 * @author juans
 */
public class PassengerStorage {

    // Instancia Singleton
    private static PassengerStorage instance;

    // Atributos del Storage
    private ArrayList<Passenger> passengers;

    private PassengerStorage() {
        this.passengers = new ArrayList<>();
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

    public Passenger get(long id) {
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

    public boolean delete(long id) {
        return passengers.removeIf(passenger -> passenger.getId() == id);
    }
}
