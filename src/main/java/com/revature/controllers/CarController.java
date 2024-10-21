package com.revature.controllers;

import com.revature.DAOs.CarDAO;
import com.revature.DAOs.OwnerDAO;
import com.revature.models.Car;
import com.revature.models.Owner;
import com.revature.utils.ConnectionUtil;
import io.javalin.http.Handler;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;

public class CarController {

    // We need a CarDAO to use its methods
    CarDAO cDAO = new CarDAO();

    // This handler will create cars table
    public Handler createCarsTable = ctx -> {

        // Create owners table
        cDAO.createTable();

        // Respond with a message
        ctx.result("Cars table have been created.");

        // Set status code to OK
        ctx.status(200);
    };

    // This handler will add cars data to table
    public Handler addCarsData = ctx -> {

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

        // Respond with a message
        ctx.result("Cars information have been added.");

        // Set status code to OK
        ctx.status(200);
    };

    // This handler will handle GET request to all cars
    public Handler selectCars = ctx -> {

        // try to open a connection
        try (Connection conn = ConnectionUtil.getConnection()) {

            // Get DB data
            DatabaseMetaData metaData = conn.getMetaData();
            try (ResultSet rs = metaData.getTables(null, null,
                    "cars", new String[]{"TABLE"})) {

                // If there is no table
                if (!rs.next()) {
                    ctx.result("Bad request! \n\n\n" +
                            "Cars table does not exist.");
                    ctx.status(500);
                }
                // There is a table
                else {
                    // Put all cars in ArrayList
                    ArrayList<Car> cars = cDAO.selectCars();

                    //Convert cars to JSON
                    ctx.json(cars);

                    // Set status code to OK
                    ctx.status(200);
                }
            }
        }
    };

    // This handler will get cars by owner
    public Handler getCarsByOwner = ctx -> {

        // Extract the path parameter from the HTTP request URL
        int owner_id = Integer.parseInt(ctx.pathParam("owner_id"));

        // We need a OwnerDAO to access its methods
        OwnerDAO oDAO = new OwnerDAO();

        // Get Owner
        Owner owner = oDAO.getOwnerById(owner_id);

        // Check if client requests a valid owner
        if (owner_id <= 0 || owner == null) {
            ctx.result("Bad request! \n\n\n" +
                    "owner_id entered is invalid. ID must be greater than zero and owner " +
                    "must be valid. \n\n\n" +
                    "Please enter the owner ID in the URL as follows: \n\n" +
                    "localhost:7000/cars/<owner_id>");

            // Set status code to BAD REQUEST
            ctx.status(400);
        }
        else {
            // Get cars by owner
            ArrayList<Car> carsByOwner = cDAO.getCarsByOwner(owner_id);

            //Convert cars to JSON and forward them to the client
            ctx.json(carsByOwner);

            // Set status code to OK
            ctx.status(200);
        }
    };

    // This handler will insert a new car to the DB and respond with the new car
    public Handler insertCar = ctx -> {


        // Convert JSON to Java and store new car
        Car newCar = ctx.bodyAsClass(Car.class);

        // We need a OwnerDAO to access its methods
        OwnerDAO oDAO = new OwnerDAO();

        // Get owner by owner_id_fk
        Owner owner = oDAO.getOwnerById(newCar.getOwner_id_fk());

        // Check if incoming car has valid input
        if (newCar.getCar_make() == null || newCar.getCar_make().isBlank() ||
                newCar.getCar_model() == null || newCar.getCar_model().isBlank() ||
                newCar.getCar_year() == 0 || newCar.getOwner_id_fk() == 0 ||
                owner == null) {
            ctx.result("Bad request! \n\n\n" +
                    "Car information entered is invalid. \n\n\n" +
                    "Please enter in the body the information in the following format: \n\n" +
                    "{ \n" +
                    "    \"car_make\": \"<car make>\", \n" +
                    "    \"car_model\": \"<car model\", \n" +
                    "    \"car_year\": <car year>, \n" +
                    "    \"owner_id_fk\": <owner id fk> \n" +
                    "}");

            // Set status code to BAD REQUEST
            ctx.status(400);
        }
        else {
            // Insert car to the DB
            Car insertedCar = cDAO.insertCar(newCar);

            // Respond to the client with the car added
            ctx.json(insertedCar);

            // Set status code to OK
            ctx.status(200);
        }
    };

    // This handler deletes a car
    public Handler deleteCar = ctx -> {

        // Extract the path parameter from the HTTP request URL
        int car_id = Integer.parseInt(ctx.pathParam("car_id"));

        // Check if client is deleting a valid car
        if (car_id <= 0) {
            ctx.result("Bad request! \n\n\n" +
                    "car_id entered is invalid. ID must be greater than zero and car " +
                    "must be valid. \n\n\n" +
                    "Please enter the car ID in the URL as follows: \n\n" +
                    "localhost:7000/cars/<owner_id>");

            // Set status code to BAD REQUEST
            ctx.status(400);
        }
        else {
            cDAO.deleteCar(car_id);

            ctx.result("Car with ID: " + car_id + " has been deleted.");

            // Set status code to OK
            ctx.status(200);
        }
    };
}
