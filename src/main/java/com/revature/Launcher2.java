package com.revature;

import com.revature.controllers.CarController;
import com.revature.controllers.OwnerController;
import com.revature.controllers.AuthController;
import io.javalin.Javalin;

public class Launcher2 {
    public static void main(String[] args) {

        var app = Javalin.create().start(7000);

        CarController cc = new CarController();
        OwnerController oc = new OwnerController();
        AuthController ac = new AuthController();

        app.before("/owners", ctx -> {
            if (AuthController.ses == null) {
                throw new IllegalArgumentException("You must log in before doing " +
                        "this!");
            }
        });

        app.before("/cars", ctx -> {
            if (AuthController.ses == null) {
                throw new IllegalArgumentException("You must log in before doing " +
                        "this!");
            }
        });

        app.exception(IllegalArgumentException.class, (e, ctx) -> {
            ctx.result(e.getMessage()); // We set this e.getMessage() in the throw
            ctx.status(401);
        });

        // Login user
        app.post("/auth", ac.loginHandler);

        // Creates owners table
        app.post("/create/owners", oc.createOwnersTable);

        // Creates cars table
        app.post("/create/cars", cc.createCarsTable);

        // Add owners data
        app.post("/add/owners", oc.addOwnersData);

        // Add cars data
        app.post("/add/cars", cc.addCarsData);

        // Send all cars
        app.get("/cars", cc.selectCars);

        // Send cars by owner
        app.get("/cars/{owner_id}", cc.getCarsByOwner);

        // Receive new car from client
        app.post("/cars", cc.insertCar);

        // Receive new owner from client
        app.post("/owners", oc.insertOwner);

        // Update an owner
        app.patch("/owners/{owner_id}", oc.updateOwner);

        // Delete a car
        app.delete("/cars/{car_id}", cc.deleteCar);

        // Delete an owner
        app.delete("/owners/{owner_id}", oc.deleteOwner);

        // Send all owners
        app.get("/owners", oc.selectOwners);
    }
}
