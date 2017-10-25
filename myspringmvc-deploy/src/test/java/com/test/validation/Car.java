package com.test.validation;

import com.test.validation.myconsistent.ConsistentDateParameters;
import com.test.validation.myconsistent.ValidPassengerCount;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@ValidPassengerCount
public class Car {
    @NotNull
    @Valid
    private Person person;
    private int seatCount;
    private List<Person> passengers = new ArrayList<Person>();

    public int getSeatCount() {
        return seatCount;
    }

    public void setSeatCount(int seatCount) {
        this.seatCount = seatCount;
    }

    public List<Person> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<Person> passengers) {
        this.passengers = passengers;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}