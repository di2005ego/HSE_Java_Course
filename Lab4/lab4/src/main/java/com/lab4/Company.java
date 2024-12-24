package com.lab4;

public class Company {
    private int id;
    private String name;
    private String address;
    private String zip;
    private String country;
    private String employeeCount;
    private String industry;
    private String marketCap;
    private String domain;
    private String logo;
    private String ceoName;

    public String toString() {
        return "Company ID: " + id + "\n" +
                "Name: " + name + "\n" +
                "Address: " + address + "\n" +
                "ZIP: " + zip + "\n" +
                "Country: " + country + "\n" +
                "Number of employees: " + employeeCount + "\n" +
                "Industry: " + industry + "\n" +
                "Market Capitalization: " + marketCap + "\n" +
                "Domain: " + domain + "\n" +
                "Logo: " + logo + "\n" +
                "CEO: " + ceoName + "\n";
    }
}
