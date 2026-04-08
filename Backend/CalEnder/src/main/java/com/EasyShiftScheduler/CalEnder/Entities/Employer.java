package com.EasyShiftScheduler.CalEnder.Entities;

import jakarta.persistence.Entity;

@Entity
public class Employer extends User{
    private int employerID;
    private EmployerOperations operations;
    public Employer(String username, String email, String password, UserOperations userOperations, int ID, EmployerOperations ops){
        super(username, email, password, userOperations);
        employerID = ID;
        operations = ops;
    }
}
