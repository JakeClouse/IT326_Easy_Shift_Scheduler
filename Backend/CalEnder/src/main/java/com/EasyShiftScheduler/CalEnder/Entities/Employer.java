package com.EasyShiftScheduler.CalEnder.Entities;

import com.EasyShiftScheduler.CalEnder.Entities.Abstract.User;
import com.EasyShiftScheduler.CalEnder.Entities.Utility.EmployerOperations;

import jakarta.persistence.Entity;

@Entity
public class Employer extends User{
    private int employerID;
    private EmployerOperations operations;
    public Employer(int ID, EmployerOperations ops){
        employerID = ID;
        operations = ops;
    }
}
