package com.revature.DAOs;

import com.revature.models.Owner;

import java.util.ArrayList;

public interface OwnerDAOInterface {

    void createTable();
    Owner insertOwner(Owner owner);
    Owner getOwnerById(int id);
    void updateOwner(int id, String newFirstName, String newLastName);
    void deleteOwner(int id);
    ArrayList<Owner> selectOwners();
}
