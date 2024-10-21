package com.revature.models;

import java.util.ArrayList;

public class Owner {

    private int owner_id;
    private String first_name;
    private String last_name;

    public Owner() {
    }

    public Owner(String first_name, String last_name) {
        this.first_name = first_name;
        this.last_name = last_name;
    }

    public Owner(int owner_id, String first_name, String last_name) {
        this.owner_id = owner_id;
        this.first_name = first_name;
        this.last_name = last_name;
    }

    public int getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(int owner_id) {
        this.owner_id = owner_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    @Override
    public String toString() {
        return "owner_id=" + owner_id +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'';
    }
}
