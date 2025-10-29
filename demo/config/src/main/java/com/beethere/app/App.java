package com.beethere.app;

import com.beethere.app.Customer;



import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;

public class App {
    public static void main(String[] args){
        Customer customer;// = new Customer();

        Yaml yaml = new Yaml();
        FileInputStream inputStream;
        try {
            inputStream = new FileInputStream("customer.yaml");  
            customer = yaml.loadAs(inputStream, Customer.class);
            // System.out.println(System.getProperty("user.dir"));
            // System.out.println(inputStream);
            // HashMap yamlMap = yaml.load(inputStream); 
            // firstName = yamlMap.get("firstName");
            // lastName = yamlMap.get("lastName");
            // age = yamlMap.get("age");
            
         System.out.println(customer.getFirstName() + " " + customer.getAge());
        } catch (Exception e) {
            System.err.println(e);
        } 
    }

}
