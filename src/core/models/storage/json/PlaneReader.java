package core.models.storage.json;

import core.models.Plane;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author david
 */
public class PlaneReader {

    public static ArrayList<Plane> read(String path) {
        ArrayList<Plane> planes = new ArrayList<>();

        try {
            String content = new String(Files.readAllBytes(Paths.get(path)));
            JSONArray planesData = new JSONArray(content);
            for (int i = 0; i < planesData.length(); i++) {
                JSONObject obj = planesData.getJSONObject(i);

                Plane plane = new Plane(
                        obj.getString("id"),
                        obj.getString("brand"),
                        obj.getString("model"),
                        obj.getInt("maxCapacity"),
                        obj.getString("airline")
                );

                planes.add(plane);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return planes;
    }
}
