/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.models.storage.json;

import java.nio.file.Files;
import java.nio.file.Paths;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author juans
 */
public class FlightReader {

    public void read(String path) {
        try {
            String content = Files.readAllBytes(Paths.get(path)).toString();
            JSONArray flightsData = new JSONArray(content);
            for (int i = 0; i < flightsData.length(); i++) {
                
            }
        } catch (Exception e) {
        }
    }
}
