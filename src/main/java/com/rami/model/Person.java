package com.rami.model;

public class Person {

    private String firstName;
    private String lastName;

    public Person(final String firstName, final String lastName) {
        this.firstName=firstName;
        this.lastName=lastName;
    }

    public String sayMyName() {
        return "My Name is " + firstName + " " + lastName+".";
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
