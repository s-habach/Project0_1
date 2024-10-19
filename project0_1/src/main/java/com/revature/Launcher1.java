package com.revature;

import com.revature.DAOs.CarDAO;
import com.revature.DAOs.OwnerDAO;
import com.revature.models.Car;
import com.revature.models.Owner;
import com.revature.utils.ConnectionUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class Launcher1 {

    public static void main(String[] args) {

        try (Connection conn = ConnectionUtil.getConnection()) {
            System.out.println("CONNECTION SUCCESSFUL!");
        }
        catch (SQLException e) {
            e.printStackTrace(); // This is what tells us our error message (the red text)
            System.out.println("CONNECTION FAILED!");
        }

        OwnerDAO oDAO = new OwnerDAO();
        CarDAO cDAO = new CarDAO();


        // Create Owners table
        oDAO.createTable();

        // Create Cars table
        cDAO.createTable();

        // Insert owners into owners table
        ArrayList<Owner> ownerList = new ArrayList<>();
        ownerList.add(new Owner("Saad", "Habach"));
        ownerList.add(new Owner("John", "Summit"));
        ownerList.add(new Owner("Sarah", "Habach"));
        ownerList.add(new Owner("Don", "Main"));

        ownerList.forEach(iOwner -> oDAO.insertOwner(iOwner));


        // Insert cars into cars table
        ArrayList<Car> carList = new ArrayList<>();
        carList.add(new Car("Kia", "Sportage", 2017,
                1));
        carList.add(new Car("Ford", "Mustang", 2018,
                1));
        carList.add(new Car("Dodge", "Charger", 2024,
                2));
        carList.add(new Car("Ford", "Raptor", 2023,
                2));
        carList.add(new Car("Toyota", "Camry", 2006,
                3));
        carList.add(new Car("Toyota", "Prado", 2012,
                4));
        carList.add(new Car("Nissan", "Rogue", 2015,
                4));
        carList.add(new Car("Chevrolet", "Camaro", 2015,
                4));

        carList.forEach(iCar -> cDAO.insertCar(iCar));


        // Select and Print all cars
        ArrayList<Car> cars = cDAO.selectCars();

        for (Car car : cars) {
            System.out.println(car);
        }
        System.out.println();

        /*
        // Print cars by owner
        ArrayList<Car> carsByOwner = cDAO.getCarsByOwner(1);
        for (Car car : carsByOwner) {
            System.out.println(car);
        }
        System.out.println();

        // Add a new Car
        Car newCar = new Car("Ford", "Mustang", 2019,
                1);
        cDAO.insertCar(newCar);

        // Select and Print all cars after update
        cars = cDAO.selectCars();
        for (Car car : cars) {
            System.out.println(car);
        }
        System.out.println();

        // Add a new Owner
        Owner newOwner = new Owner("Rob", "Summit");
        oDAO.insertOwner(newOwner);

        // Select and Print all cars after update
        cars = cDAO.selectCars();
        for (Car car : cars) {
            System.out.println(car);
        }
        System.out.println();

        // Update an owner
        oDAO.updateOwner(1, "Saad", "Habach");

        // Select and Print all cars after update
        cars = cDAO.selectCars();
        for (Car car : cars) {
            System.out.println(car);
        }
        System.out.println();

        // Delete a car
        cDAO.deleteCar(2);

        // Select and Print all cars
        cars = cDAO.selectCars();
        for (Car car : cars) {
            System.out.println(car);
        }
        System.out.println();

        // Select and Print all cars
        cars = cDAO.selectCars();
        for (Car car : cars) {
            System.out.println(car);
        }
        System.out.println();


        */

    }
}
