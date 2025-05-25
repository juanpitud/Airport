/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controllers;

import core.controllers.utils.Response;
import core.controllers.utils.Status;
import core.models.Plane;
import core.models.storage.PlaneStorage;

/**
 *
 * @author juans
 */
public class PlaneController {

    public static Response createPlane(String id, String brand, String model, int maxCapacity, String airline) {
        if (id.isEmpty()) {
            return new Response("Plane Id must not be empty.", Status.BAD_REQUEST);
        }

        if (!id.matches("^[A-Z]{2}\\d{5}$")) {
            return new Response("Plane Id must follow AZ01289 format.", Status.BAD_REQUEST);
        }

        if (brand.isEmpty()) {
            return new Response("Brand must be valid.", Status.BAD_REQUEST);
        }

        if (model.isEmpty()) {
            return new Response("Model must be valid.", Status.BAD_REQUEST);
        }

        if (maxCapacity <= 0) {
            return new Response("Max capacity must be greater than 0.", Status.BAD_REQUEST);
        }

        if (airline.isEmpty()) {
            return new Response("Airline must be valid.", Status.BAD_REQUEST);
        }

        Plane plane = new Plane(id, brand, model, maxCapacity, airline);

        boolean ok = PlaneStorage.getInstance().add(plane);
        if (!ok) {
            return new Response("Plane with this ID already exists.", Status.BAD_REQUEST);
        }

        return new Response("Plane created successfully.", Status.CREATED);
    }

    public static Response getPlane(String id) {
        Plane plane = PlaneStorage.getInstance().get(id);
        if (plane == null) {
            return new Response("Plane not found.", Status.BAD_REQUEST);
        }

        return new Response("Plane found.", Status.OK, plane);
    }

    public static Response updatePlane(String id, String brand, String model, String airline) {
        PlaneStorage storage = PlaneStorage.getInstance();
        Plane plane = storage.get(id);

        if (plane == null) {
            return new Response("Plane not found.", Status.NOT_FOUND);
        }

        if (brand == null || brand.isEmpty()) {
            return new Response("Brand must be valid.", Status.BAD_REQUEST);
        }

        if (model == null || model.isEmpty()) {
            return new Response("Model must be valid.", Status.BAD_REQUEST);
        }

        if (airline == null || airline.isEmpty()) {
            return new Response("Airline must be valid.", Status.BAD_REQUEST);
        }

        plane.setBrand(brand);
        plane.setModel(model);
        plane.setAirline(airline);

        return new Response("Plane updated successfully.", Status.CREATED);
    }

    public static Response deletePlane(String id) {
        boolean deleted = PlaneStorage.getInstance().delete(id);
        if (!deleted) {
            return new Response("Plane not found.", Status.BAD_REQUEST);
        }
        return new Response("Plane deleted successfully.", Status.CREATED);
    }

    public static Response getAllPlanes() {
        return new Response("Locations retrieved successfully.", Status.OK, PlaneStorage.getInstance().getAll());
    }
}
