/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.models.storage;

import core.models.Plane;
import core.models.storage.json.PlaneReader;
import java.util.ArrayList;

/**
 *
 * @author juans
 */
public class PlaneStorage {

    // Instancia Singleton
    private static PlaneStorage instance;

    // Atributos del Storage
    private ArrayList<Plane> planes;

    private PlaneStorage() {
        this.planes = new ArrayList<>();
        this.loadFromJSON();
    }

    public void loadFromJSON() {
        ArrayList<Plane> planes = PlaneReader.read("C:\\Users\\david\\Desktop\\Airport\\json\\planes.json");
        for (Plane plane : planes) {
            this.add(plane);
        }
    }

    public static PlaneStorage getInstance() {
        if (instance == null) {
            instance = new PlaneStorage();
        }
        return instance;
    }

    public boolean add(Plane plane) {
        for (Plane p : planes) {
            if (p.getId().equals(plane.getId())) {
                return false;
            }
        }

        return planes.add(plane);
    }

    public Plane get(String id) {
        for (Plane p : planes) {
            if (p.getId().equals(id)) {
                return p;
            }
        }
        return null;
    }

    public ArrayList<Plane> getAll() {
        return new ArrayList<>(planes);
    }

    public boolean delete(String id) {
        return planes.removeIf(plane -> plane.getId().equals(id));
    }
}
