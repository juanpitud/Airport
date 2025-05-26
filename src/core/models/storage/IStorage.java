/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package core.models.storage;

import java.util.ArrayList;

/**
 *
 * @author juans
 */
public interface IStorage<T, I> {

    void loadFromJSON();

    boolean add(T type);

    T get(I id);

    ArrayList<T> getAll();

    boolean delete(I id);
}
