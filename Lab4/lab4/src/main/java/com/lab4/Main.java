package com.lab4;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        JsonProcessor JsonProcessor = new JsonProcessor();
        String usersUrl = "https://fake-json-api.mock.beeceptor.com/users";
        String companiesUrl = "https://fake-json-api.mock.beeceptor.com/companies";

        try {
            List<User> users = JsonProcessor.getUsers(usersUrl);
            System.out.println("List of users:");
            for (User user : users) {
                System.out.println(user);
            }

            List<Company> companies = JsonProcessor.getCompanies(companiesUrl);
            System.out.println("List of companies:");
            for (Company company : companies) {
                System.out.println(company);
            }
        } 
        catch (extendedException exc) {
            System.err.println("Error: " + exc.getMessage());
        }
    }
}