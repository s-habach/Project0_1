package com.revature.controllers;

import com.revature.DAOs.OwnerDAO;
import com.revature.models.Car;
import com.revature.models.Owner;
import com.revature.utils.ConnectionUtil;
import io.javalin.http.Handler;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;

public class OwnerController {

    // We need a OwnerDAO to use its methods
    OwnerDAO oDAO = new OwnerDAO();

    // This handler will create owners table
    public Handler createOwnersTable = ctx -> {

        // Create owners table
        oDAO.createTable();

        // Respond with a message
        ctx.result("Owners table have been created.");

        // Set status code to OK
        ctx.status(200);
    };

    // This handler will add owners data to table
    public Handler addOwnersData = ctx -> {

        // Insert owners into owners table
        ArrayList<Owner> ownerList = new ArrayList<>();
        ownerList.add(new Owner("Saad", "Habach"));
        ownerList.add(new Owner("John", "Summit"));
        ownerList.add(new Owner("Sarah", "Habach"));
        ownerList.add(new Owner("Don", "Main"));

        ownerList.forEach(iOwner -> oDAO.insertOwner(iOwner));

        // Respond with a message
        ctx.result("Owners information have been added.");

        // Set status code to OK
        ctx.status(200);
    };

    // This handler will insert a new owner and respond with the new owner
    public Handler insertOwner = ctx -> {

        // Convert JSON to Java and store new owner
         Owner newOwner = ctx.bodyAsClass(Owner.class);

        // Check if incoming car has valid input
        if (newOwner.getFirst_name() == null || newOwner.getFirst_name().isBlank() ||
                newOwner.getLast_name() == null || newOwner.getLast_name().isBlank()) {
            ctx.result("Bad request! \n\n\n" +
                    "Owner information entered is invalid. \n\n\n" +
                    "Please enter in the body the information in the following format: \n\n" +
                    "{ \n" +
                    "    \"first_name\": \"<first name>\", \n" +
                    "    \"last_name\": \"<last name>\" \n" +
                    "}");

            // Set status code to BAD REQUEST
            ctx.status(400);
        }
        else {
            // Insert car to the DB
            Owner insertedOwner = oDAO.insertOwner(newOwner);

            // Respond to the client with the car added
            ctx.json(insertedOwner);

            // Set status code to OK
            ctx.status(200);
        }
    };

    // This handler will update owner information
    public Handler updateOwner = ctx -> {

        // Extract the path parameter from the HTTP request URL
        int owner_id = Integer.parseInt(ctx.pathParam("owner_id"));

        // Convert JSON to Java and store new car
        Owner updateOwner = ctx.bodyAsClass(Owner.class);

        // Get Owner
        Owner owner = oDAO.getOwnerById(owner_id);

        // Check if client requests a valid owner
        if (owner_id <= 0 || owner == null ) {
            ctx.result("Bad request! \n\n\n" +
                    "owner_id entered and/or owner information is invalid. ID must be " +
                    "greater than zero and owner must be valid. Also, check below for more " +
                    "information about updating owner's first or last names. \n\n\n" +
                    "Please enter the owner ID in the URL as follows: \n\n" +
                    "localhost:7000/owners/<owner_id> \n\n\n" +
                    "The body with owner information should look like this: \n\n" +
                    "{ \n" +
                    "    \"first_name\": \"<first name>\" \n" +
                    "} \n\n" +
                    "OR \n\n" +
                    "{ \n" +
                    "    \"last_name\": \"<last name>\" \n" +
                    "}");

            // Set status code to BAD REQUEST
            ctx.status(400);
        }
        else if ((updateOwner.getFirst_name() == null ||
                    updateOwner.getFirst_name().isBlank()) &&
                (updateOwner.getLast_name() == null ||
                        updateOwner.getLast_name().isBlank())) {

            ctx.result("Bad request! \n\n\n" +
                    "owner_id entered and/or owner information is invalid. ID must be " +
                    "greater than zero and owner must be valid. Also, check below for more " +
                    "information about updating owner's first or last names. \n\n\n" +
                    "Please enter the owner ID in the URL as follows: \n\n" +
                    "localhost:7000/owners/<owner_id> \n\n\n" +
                    "The body with owner information should look like this: \n\n" +
                    "{ \n" +
                    "    \"first_name\": \"<first name>\" \n" +
                    "} \n\n" +
                    "OR \n\n" +
                    "{ \n" +
                    "    \"last_name\": \"<last name>\" \n" +
                    "}");

            // Set status code to BAD REQUEST
            ctx.status(400);
        }
        else {
            if (updateOwner.getFirst_name() != null && (updateOwner.getLast_name() == null ||
            updateOwner.getLast_name().isBlank())) {
                // Update the owner first name
                oDAO.updateOwner(owner.getOwner_id(), updateOwner.getFirst_name(),
                        owner.getLast_name());

            }
            else if ((updateOwner.getFirst_name() == null ||
                    updateOwner.getFirst_name().isBlank()) &&
                    updateOwner.getLast_name() != null) {
                // Update the owner last name
                oDAO.updateOwner(owner.getOwner_id(), owner.getFirst_name(),
                        updateOwner.getLast_name());
            }
            else {
                // Update the owner last name
                oDAO.updateOwner(owner.getOwner_id(), updateOwner.getFirst_name(),
                        updateOwner.getLast_name());
            }

            // Respond with new owner information
            ctx.json(owner);

            // Set status code to OK
            ctx.status(200);
        }
    };

    // This handler deletes a car
    public Handler deleteOwner = ctx -> {

        // Extract the path parameter from the HTTP request URL
        int owner_id = Integer.parseInt(ctx.pathParam("owner_id"));

        // We need a OwnerDAO to access its methods
        OwnerDAO oDAO = new OwnerDAO();

        // Get owner by owner_id_fk
        Owner owner = oDAO.getOwnerById(owner_id);

        // Check if client is deleting a valid car
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
            oDAO.deleteOwner(owner_id);

            ctx.result("Owner with ID: " + owner_id + " and associated cars" +
                    " have been deleted.");

            // Set status code to OK
            ctx.status(200);
        }
    };

    // This handler responses with all owners and their cars
    public Handler selectOwners = ctx -> {

        // try to open a connection
        try (Connection conn = ConnectionUtil.getConnection()) {

            // Get DB data
            DatabaseMetaData metaData = conn.getMetaData();
            try (ResultSet rs = metaData.getTables(null, null,
                    "owners", new String[] {"TABLE"} )) {

                // If there is no table
                if (!rs.next()){
                    ctx.result("Bad request! \n\n\n" +
                            "Owners table does not exist.");
                    ctx.status(500);
                }
                // There is a table
                else {
                    // Put all owners in ArrayList
                    ArrayList<Owner> owners = oDAO.selectOwners();

                    //Convert cars to JSON
                    ctx.json(owners);

                    // Set status code to OK
                    ctx.status(200);
                }
            }
        }
    };
}
