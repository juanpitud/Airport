/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.models.storage.json;

import core.models.Location;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author david
 */
public class LocationReader {

    public static ArrayList<Location> read(String path) {
        ArrayList<Location> locations = new ArrayList<>();

        try {
            String content = new String(Files.readAllBytes(Paths.get(path)));
            JSONArray locationsData = new JSONArray(content);
            for (int i = 0; i < locationsData.length(); i++) {
                JSONObject obj = locationsData.getJSONObject(i);

                Location location = new Location(
                        obj.getString("airportId"),
                        obj.getString("airportName"),
                        obj.getString("airportCity"),
                        obj.getString("airportCountry"),
                        obj.getDouble("airportLatitude"),
                        obj.getDouble("airportLongitude")
                );

                locations.add(location);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return locations;
    }
}
