package com.revature.DAOs;

import com.revature.models.Car;

import java.util.ArrayList;

public interface CarDAOInterface {

    void createTable();
    Car insertCar(Car car);
    ArrayList<Car> selectCars();
    ArrayList<Car> getCarsByOwner(int owner_id_fk);
    void deleteCar(int id);
}
