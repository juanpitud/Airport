/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.models.storage.json;

import core.models.Passenger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author david
 */
public class PassengerReader {

    public static ArrayList<Passenger> read(String path) {
        ArrayList<Passenger> passengers = new ArrayList<>();

        try {
            String content = new String(Files.readAllBytes(Paths.get(path)));
            JSONArray passengerData = new JSONArray(content);
            for (int i = 0; i < passengerData.length(); i++) {
                JSONObject obj = passengerData.getJSONObject(i);

                Passenger passenger = new Passenger(
                        obj.getLong("id"),
                        obj.getString("firstname"),
                        obj.getString("lastname"),
                        LocalDate.parse(obj.getString("birthDate")),
                        obj.getInt("countryPhoneCode"),
                        obj.getLong("phone"),
                        obj.getString("country")
                );

                passengers.add(passenger);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return passengers;
    }
}
