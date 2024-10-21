package com.revature.DAOs;

import com.revature.models.Car;
import com.revature.utils.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;

public class CarDAO implements CarDAOInterface {

    public void createTable() {
        // Try to open a Connection to the DB
        try (Connection conn = ConnectionUtil.getConnection()) {
            // A String that represents our SQL query
            // Note the "?", which just means  it's variable we need to fill up
            String sql = "CREATE TABLE cars(" +
                    "car_id serial PRIMARY KEY," +
                    "car_make TEXT," +
                    "car_model TEXT," +
                    "car_year int," +
                    "owner_id_fk int REFERENCES owners(owner_id));";

            // We can use a Statement object instead of PreparedStatement since there are no
            // variables
            Statement s = conn.createStatement();

            s.execute(sql);

        } catch (SQLException e) {
            e.printStackTrace(); // Tells us what went wrong
            System.out.println("Couldn't create cars table.");
        }
    }

    @Override
    public Car insertCar(Car car) {

        // We will always Try to open a Connection to the DB first, before we can run any SQL
        try (Connection conn = ConnectionUtil.getConnection()) {
            // Create our SQL statement
            // Note the "?", which just means  it's variable we need to fill up
            String sql= "INSERT INTO cars (car_make, car_model, car_year," +
                    "owner_id_fk) " +
                    "VALUES (?, ?, ?, ?)";

            // Use PreparedStatement to fill in the values of our variables
            // It takes the SQL String we made above
            PreparedStatement ps = conn.prepareStatement(sql);

            // Use the .set() methods to fill in the values
            ps.setString(1, car.getCar_make());
            ps.setString(2, car.getCar_model());
            ps.setInt(3, car.getCar_year());
            ps.setInt(4, car.getOwner_id_fk());

            // Now that we've filled in the values, we can send the command to the DB
            ps.executeUpdate();
            // Note: executeUpdate() is used with INSERT, UPDATE, and DELETE...
            // ...while executeQuery() is used with SELECT

            // We can then return the new Car object (we can just use the method
                // parameter)
            return car;
        }
        catch (SQLException e) {
            e.printStackTrace(); // Tells us what went wrong
            System.out.println("Couldn't insert car.");
        }

        // catch-all return statement - If something goes wrong, we'll return null
        return null;
    }

    public ArrayList<Car> selectCars() {

        // Try to open a Connection to the DB
        try (Connection conn = ConnectionUtil.getConnection()) {
            // A String that represents our SQL query
            String sql = "SELECT * FROM cars;";

            // We can use a Statement object instead of PreparedStatement since there are no
                // variables
            Statement s = conn.createStatement();

            // Execute the query, saving the results in ResultSet
            ResultSet rs = s.executeQuery(sql);

            // We need an ArrayList to store our Cars
            ArrayList<Car> cars = new ArrayList<>();

            OwnerDAO oDAO = new OwnerDAO();

            while (rs.next()) {
                // For every Car found, add it to the ArrayList
                // We will need to instantiate a new Car object for each row in the RS
                // We can get each piece of Car data with rs.get() methods

                Car car = new Car(rs.getInt("car_id"),
                        rs.getString("car_make"),
                        rs.getString("car_model"),
                        rs.getInt("car_year"),
                        rs.getInt("owner_id_fk"),
                        oDAO.getOwnerById(rs.getInt("owner_id_fk")));

                // Now, we can finally add the Car to our ArrayList
                cars.add(car);

            } // end of while loop

            return cars;

        } catch (SQLException e) {
            e.printStackTrace(); // Tells us what went wrong
            System.out.println("Couldn't select cars.");
        }

        return null;
    }

    public ArrayList<Car> getCarsByOwner(int owner_id_fk) {

        // We need to open a Connection to the DB
        try (Connection conn = ConnectionUtil.getConnection()) {

            // SQL String
            String sql = "SELECT * FROM cars WHERE owner_id_fk = ?;";

            // Use PreparedStatement to fill in the values of our variables
            // It takes the SQL String we made above
            PreparedStatement ps = conn.prepareStatement(sql);

            // Use the .set() methods to fill in the values
            ps.setInt(1, owner_id_fk);

            // Execute the query, saving the results in ResultSet
            ResultSet rs = ps.executeQuery();

            // We need an ArrayList to store our Cars
            ArrayList<Car> cars = new ArrayList<>();

            // Loop through the ResultSet with rs.next();
            // rs.next() will return false when there are no more rows in the ResultSet

            // Let's use OwnerDAO.getOwnerById() to add the Owner to our Car
            // "Get a new Owner by using the owner_id_fk from the DB"
            OwnerDAO oDAO = new OwnerDAO();

            while (rs.next()) {
                // For every car found, add it to the ArrayList
                // We will need to instantiate a new car object for each row in the RS
                // We can get each piece of car data with rs.get() methods

                Car car = new Car(rs.getInt("car_id"),
                        rs.getString("car_make"),
                        rs.getString("car_model"),
                        rs.getInt("car_year"),
                        rs.getInt("owner_id_fk"),
                        oDAO.getOwnerById(owner_id_fk));

                // Now, we can finally add the Car to our ArrayList
                cars.add(car);

            } // end of while loop

            // When the while loop breaks, we can finally return the full ArrayList
            return cars;
        }
        catch (SQLException e) {
            e.printStackTrace(); // Tells us what went wrong
            System.out.println("Couldn't get all cars.");
        }

        return null;
    }

    @Override
    public void deleteCar(int id) {

        // Try to open a Connection to the DB
        try(Connection conn = ConnectionUtil.getConnection()){

            // SQL statement
            String sql = "DELETE FROM cars WHERE car_id = ?;";

            // Create a PreparedStatement to fill in the variables
            PreparedStatement ps = conn.prepareStatement(sql);

            // ps.set() to set the variables values
            ps.setInt(1, id);

            // Execute the update!
            ps.executeUpdate();

        } catch (SQLException e){
            e.printStackTrace();
            System.out.println("Couldn't delete car.");
        }
    }
}
