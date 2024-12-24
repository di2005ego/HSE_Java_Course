package com.lab4;

public class User {
    private int id;
    private String name;
    private String company;
    private String username;
    private String email;
    private String address;
    private String zip;
    private String state;
    private String country;
    private String phone;
    private String photo;

    public String toString() {
        return "User ID: " + id + "\n" +
                "Name: " + name + "\n" +
                "Company: " + company + "\n" +
                "Username: " + username + "\n" +
                "Email: " + email + "\n" +
                "Address: " + address + "\n" +
                "ZIP: " + zip + "\n" +
                "State: " + state + "\n" +
                "Country: " + country + "\n" +
                "Phone number: " + phone + "\n" +
                "Photo: " + photo + "\n";
    }
}
