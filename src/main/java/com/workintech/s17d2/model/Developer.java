package com.workintech.s17d2.model;

public class Developer {

    private int id;
    private String name;
    private double salary;
    private Experience experience;

    public Developer(int id, String name, double salary, Experience experience) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.experience = experience;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getSalary() {
        return salary;
    }

    public Experience getExperience() {
        return experience;
    }
}
