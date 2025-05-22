/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controllers;

/**
 *
 * @author juans
 */
public class Controllers {

    private FlightController flightController;
    private LocationController locationController;
    private PassengerController passengerController;
    private PlaneController planeController;

    public Controllers() {
        this.flightController = new FlightController();
        this.locationController = new LocationController();
        this.passengerController = new PassengerController();
        this.planeController = new PlaneController();
    }

    public FlightController getFlightController() {
        return flightController;
    }

    public LocationController getLocationController() {
        return locationController;
    }

    public PassengerController getPassengerController() {
        return passengerController;
    }

    public PlaneController getPlaneController() {
        return planeController;
    }

}
