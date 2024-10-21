package com.revature.DAOs;

import com.revature.models.Car;
import com.revature.models.Owner;
import com.revature.utils.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;

public class OwnerDAO implements OwnerDAOInterface {

    public void createTable() {

        // Try to open a Connection to the DB
        try (Connection conn = ConnectionUtil.getConnection()) {
            // A String that represents our SQL query
            String sql = "CREATE TABLE owners(" +
                    "owner_id serial PRIMARY KEY," +
                    "first_name TEXT UNIQUE," +
                    "last_name TEXT);";

            // We can use a Statement object instead of PreparedStatement since there are no
            // variables
            Statement s = conn.createStatement();

            s.execute(sql);

        } catch (SQLException e) {
            e.printStackTrace(); // Tells us what went wrong
            System.out.println("Couldn't create owners table.");
        }
    }

    @Override
    public Owner insertOwner(Owner owner) {

        // We will always Try to open a Connection to the DB first, before we can run any SQL
        try (Connection conn = ConnectionUtil.getConnection()) {
            // Create our SQL statement
            // Note the "?", which just means  it's variable we need to fill up
            String sql= "INSERT INTO owners (first_name, last_name) " +
                    "VALUES (?, ?)";

            // Use PreparedStatement to fill in the values of our variables
            // It takes the SQL String we made above
            PreparedStatement ps = conn.prepareStatement(sql);

            // Use the .set() methods to fill in the values
            ps.setString(1, owner.getFirst_name());
            ps.setString(2, owner.getLast_name());

            // Now that we've filled in the values, we can send the command to the DB
            ps.executeUpdate();
            // Note: executeUpdate() is used with INSERT, UPDATE, and DELETE...
            // ...while executeQuery() is used with SELECT

            // We can then return the new Owner object (we can just use the method
                // parameter)
            return owner;
        }
        catch (SQLException e) {
            e.printStackTrace(); // Tells us what went wrong
            System.out.println("Couldn't insert owner.");
        }

        // catch-all return statement - If something goes wrong, we'll return null
        return null;
    }

    @Override
    public Owner getOwnerById(int id) {

        // Try to open a Connection to the DB
        try (Connection conn = ConnectionUtil.getConnection()) {
            // A String that represents our SQL query
            // Note the "?", which just means  it's variable we need to fill up
            String sql= "SELECT * FROM owners WHERE owner_id = ?";

            // We need a PreparedStatement to fill in the variable (id)
            // It takes the SQL String we made above
            PreparedStatement ps = conn.prepareStatement(sql);

            // We can now use the id parameter to set the variable with ps.set() methods
            ps.setInt(1, id);

            // Execute the query, save the results in ResultSet
            ResultSet rs = ps.executeQuery(); // Executing the query stored in the
            // PreparedStatement

            // Extract the data from the ResultSet into a Owner object
            // If there is a value in the next index of the ResultSet...
            if (rs.next()) {
                // Then extract the data into Java Owner object! Using the all-args constructor
                // We can use rs.get() to get values from the ResultSet
                Owner owner = new Owner(rs.getInt("owner_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"));

                // Return the new Owner!
                return owner;
            }
        }
        catch (SQLException e) {
            e.printStackTrace(); // Tells us what went wrong
            System.out.println("Couldn't get owner by ID.");
        }
        // This is just a catch-all. If nothing gets returned (bad SQL query?) we get null
        return null;
    }

    @Override
    public void updateOwner(int id, String newFirstName, String newLastName) {

        // Try to open a Connection to the DB
        try(Connection conn = ConnectionUtil.getConnection()){

            // SQL statement
            String sql = "UPDATE owners SET first_name = ?, last_name = ? " +
                    "WHERE owner_id = ?";

            // Create a PreparedStatement to fill in the variables
            PreparedStatement ps = conn.prepareStatement(sql);

            // ps.set() to set the variables values
            ps.setString(1, newFirstName);
            ps.setString(2, newLastName);
            ps.setInt(3, id);

            // Execute the update!
            ps.executeUpdate();


        } catch (SQLException e){
            e.printStackTrace();
            System.out.println("Couldn't update owner.");
        }
    }

    public void deleteOwner(int id) {

        // Try to open a Connection to the DB
        try(Connection conn = ConnectionUtil.getConnection()){

            // Instantiate a cDAO to use CarDAO methods
            CarDAO cDAO = new CarDAO();

            // Get cars by owner
            ArrayList<Car> carsByOwner = cDAO.getCarsByOwner(id);

            // Delete cars associated with owner
            for (int i = carsByOwner.size() - 1; i >= 0; i--) {
                cDAO.deleteCar(carsByOwner.get(i).getCar_id());
            }

            // Delete Owner
            // SQL statement
            String sql = "DELETE FROM owners WHERE owner_id = ?;";

            // Create a PreparedStatement to fill in the variables
            PreparedStatement ps = conn.prepareStatement(sql);

            // ps.set() to set the variables values
            ps.setInt(1, id);

            // Execute the update!
            ps.executeUpdate();

        } catch (SQLException e){
            e.printStackTrace();
            System.out.println("Couldn't delete owner.");
        }
    }

    public ArrayList<Owner> selectOwners() {

        // Try to open a Connection to the DB
        try (Connection conn = ConnectionUtil.getConnection()) {
            // A String that represents our SQL query
            String sql = "SELECT * FROM owners;";

            // We can use a Statement object instead of PreparedStatement since there are no
                // variables
            Statement s = conn.createStatement();

            // Execute the query, saving the results in ResultSet
            ResultSet rs = s.executeQuery(sql);

            // We need an ArrayList to store our owners
            ArrayList<Owner> owners = new ArrayList<>();

            while (rs.next()) {
                // For every owner found, add it to the ArrayList
                // We will need to instantiate a new owner object for each row in the RS
                // We can get each piece of Car data with rs.get() methods

                Owner owner = new Owner(rs.getInt("owner_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"));

                // Now, we can finally add the owner to our ArrayList
                owners.add(owner);

            } // end of while loop

            return owners;

        } catch (SQLException e) {
            e.printStackTrace(); // Tells us what went wrong
            System.out.println("Couldn't select owners.");
        }

        return null;
    }
}
