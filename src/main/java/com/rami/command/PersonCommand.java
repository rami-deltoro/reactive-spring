package com.rami.command;

import com.rami.model.Person;

public class PersonCommand {

    private String firstName;

    private String lastName;

    public PersonCommand(Person person) {
        this.firstName=person.getFirstName();
        this.lastName=person.getLastName();
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
