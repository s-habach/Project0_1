package com.revature.models;

public class Car {

    private int car_id;
    private String car_make;
    private String car_model;
    private int car_year;

    private Owner owner;

    private int owner_id_fk;

    public Car() {
    }

    public Car(int car_id, String car_make, String car_model, int car_year, int owner_id_fk, Owner owner) {
        this.car_id = car_id;
        this.car_make = car_make;
        this.car_model = car_model;
        this.car_year = car_year;
        this.owner_id_fk = owner_id_fk;
        this.owner = owner;
    }

    public Car(String car_make, String car_model, int car_year, int owner_id_fk) {
        this.car_make = car_make;
        this.car_model = car_model;
        this.car_year = car_year;
        this.owner_id_fk = owner_id_fk;
    }

    public int getCar_id() {
        return car_id;
    }

    public void setCar_id(int car_id) {
        this.car_id = car_id;
    }

    public String getCar_make() {
        return car_make;
    }

    public void setCar_make(String car_make) {
        this.car_make = car_make;
    }

    public String getCar_model() {
        return car_model;
    }

    public void setCar_model(String car_model) {
        this.car_model = car_model;
    }

    public int getCar_year() {
        return car_year;
    }

    public void setCar_year(int car_year) {
        this.car_year = car_year;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public int getOwner_id_fk() {
        return owner_id_fk;
    }

    public void setOwner_id_fk(int owner_id_fk) {
        this.owner_id_fk = owner_id_fk;
    }

    @Override
    public String toString() {
        return "car_id=" + car_id +
                ", car_make='" + car_make + '\'' +
                ", car_model='" + car_model + '\'' +
                ", car_year=" + car_year +
                ", owner_id_fk=" + owner_id_fk +
                ", owner=" + owner;
    }
}
