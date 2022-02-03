package com.example.junit.ch10;

import lombok.AllArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
public class Address {
    public final String road;
    public final String city;
    public final String state;
    public final String zip;
    public final String houseNumber;
}
