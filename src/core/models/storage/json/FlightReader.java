/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.models.storage.json;

import java.nio.file.Files;
import java.nio.file.Paths;
import org.json.JSONArray;
import core.models.Flight;
import core.models.Location;
import core.models.Plane;
import core.models.storage.LocationStorage;
import core.models.storage.PlaneStorage;
import java.time.LocalDateTime;
import java.util.ArrayList;
import org.json.JSONObject;

/**
 *
 * @author juans
 */
public class FlightReader {

    public static ArrayList<Flight> read(String path) {
        ArrayList<Flight> flights = new ArrayList<>();

        try {
            String content = new String(Files.readAllBytes(Paths.get(path)));
            JSONArray flightsData = new JSONArray(content);
            for (int i = 0; i < flightsData.length(); i++) {
                JSONObject obj = flightsData.getJSONObject(i);

                Plane plane = PlaneStorage.getInstance().get(obj.getString("plane"));
                Location departure = LocationStorage.getInstance().get(obj.getString("departureLocation"));
                Location arrival = LocationStorage.getInstance().get(obj.getString("arrivalLocation"));

                Location scale = null;
                if (!obj.isNull("scaleLocation")) {
                    scale = LocationStorage.getInstance().get(obj.getString("scaleLocation"));
                }

                Flight flight = new Flight(
                        obj.getString("id"),
                        plane,
                        departure,
                        scale,
                        arrival,
                        LocalDateTime.parse(obj.getString("departureDate")),
                        obj.getInt("hoursDurationArrival"),
                        obj.getInt("minutesDurationArrival"),
                        obj.getInt("hoursDurationScale"),
                        obj.getInt("minutesDurationScale")
                );

                flights.add(flight);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return flights;
    }
}
