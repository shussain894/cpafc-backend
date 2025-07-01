package com.cpafc.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "children")
public class Child {

    @Id
    private String id;

    private String firstName;
    private String lastName;
    private int age;
    private String team;

    // Constructors, Getters, and Setters
    public Child() {}

    public Child(String firstName, String lastName, int age, String team) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.team = team;
    }

    public String getId() { return id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getTeam() { return team; }
    public void setTeam(String team) { this.team = team; }

    public void setId(String id) {
        this.id = id;
    }
}
