/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.models.storage;

import core.models.Location;
import java.util.ArrayList;

/**
 *
 * @author juans
 */
public class LocationStorage {

    // Instancia Singleton
    private static LocationStorage instance;

    // Atributos del Storage
    private ArrayList<Location> locations;

    private LocationStorage() {
        this.locations = new ArrayList<>();
    }

    public static LocationStorage getInstance() {
        if (instance == null) {
            instance = new LocationStorage();
        }
        return instance;
    }

    public boolean add(Location location) {
        for (Location l : locations) {
            if (l.getAirportId().equals(location.getAirportId())) {
                return false;
            }
        }

        return locations.add(location);
    }

    public Location get(String id) {
        for (Location l : locations) {
            if (l.getAirportId().equals(id)) {
                return l;
            }
        }
        return null;
    }

    public ArrayList<Location> getAll() {
        return locations;
    }
}
