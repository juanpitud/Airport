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

    public static Response createPlane(String id, String brand, String model, String maxCapacityText, String airline) {
        if (id.trim().isEmpty()) {
            return new Response("Plane Id must not be empty.", Status.BAD_REQUEST);
        }

        if (!id.matches("^[A-Z]{2}\\d{5}$")) {
            return new Response("Plane Id must follow format: two uppercase letters followed by five digits (e.g., AZ01289).", Status.BAD_REQUEST);
        }

        if (brand.trim().isEmpty()) {
            return new Response("Brand must be valid.", Status.BAD_REQUEST);
        }

        if (model.trim().isEmpty()) {
            return new Response("Model must be valid.", Status.BAD_REQUEST);
        }

        int maxCapacity;
        try {
            maxCapacity = Integer.parseInt(maxCapacityText);
            if (maxCapacity <= 0) {
                return new Response("Max capacity must be greater than 0.", Status.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new Response("Max capacity must be a valid number.", Status.BAD_REQUEST);
        }

        if (airline.trim().isEmpty()) {
            return new Response("Airline must be valid.", Status.BAD_REQUEST);
        }

        Plane plane = new Plane(id.trim(), brand.trim(), model.trim(), maxCapacity, airline.trim());

        boolean ok = PlaneStorage.getInstance().add(plane);
        if (!ok) {
            return new Response("Plane with this Id already exists.", Status.BAD_REQUEST);
        }

        return new Response("Plane created successfully.", Status.CREATED);
    }

    public static Response getPlane(String id) {
        if (id.trim().isEmpty()) {
            return new Response("Plane Id must be provided.", Status.BAD_REQUEST);
        }

        Plane plane = PlaneStorage.getInstance().get(id.trim());
        if (plane == null) {
            return new Response("Plane not found.", Status.NOT_FOUND);
        }

        return new Response("Plane found.", Status.OK, plane);
    }

    public static Response updatePlane(String id, String brand, String model, String airline) {
        if (id.trim().isEmpty()) {
            return new Response("Plane Id must be provided.", Status.BAD_REQUEST);
        }

        PlaneStorage storage = PlaneStorage.getInstance();
        Plane plane = storage.get(id.trim());

        if (plane == null) {
            return new Response("Plane not found.", Status.NOT_FOUND);
        }

        if (brand.trim().isEmpty()) {
            return new Response("Brand must be valid.", Status.BAD_REQUEST);
        }

        if (model.trim().isEmpty()) {
            return new Response("Model must be valid.", Status.BAD_REQUEST);
        }

        if (airline.trim().isEmpty()) {
            return new Response("Airline must be valid.", Status.BAD_REQUEST);
        }

        plane.setBrand(brand.trim());
        plane.setModel(model.trim());
        plane.setAirline(airline.trim());

        return new Response("Plane updated successfully.", Status.OK);
    }

    public static Response deletePlane(String id) {
        if (id.trim().isEmpty()) {
            return new Response("Plane Id must be provided.", Status.BAD_REQUEST);
        }

        boolean deleted = PlaneStorage.getInstance().delete(id.trim());
        if (!deleted) {
            return new Response("Plane not found.", Status.NOT_FOUND);
        }

        return new Response("Plane deleted successfully.", Status.OK);
    }

    public static Response getAllPlanes() {
        return new Response("Planes retrieved successfully.", Status.OK, PlaneStorage.getInstance().getAll());
    }
}
