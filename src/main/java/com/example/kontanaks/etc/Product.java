package com.example.kontanaks.etc;

import com.example.kontanaks.MainActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class Product implements Serializable {
    private String name;
    private double price;
    private boolean served = false;
    private ArrayList<String> details = new ArrayList<>();
    static final String separator = ", ";

    public Product(Product p) {
        name = p.name;
        price = p.getPrice();
        details = p.getDetails();
    }

    public void serve() {
        served = true;
    }

    public boolean isServed() {
        return served;
    }

    public Product(String s, double i, ArrayList<String> d) {
        name = s;
        price = i;
        details = d;
    }

    public Product(String s, double i) {
        name = s;
        price = i;
    }

    public String getName() {
        String nameStr = name;

        String detailsStr = "";

        int i = details.size();
        Collections.sort(details);
        while (i > 0)
        {
            detailsStr += separator + details.get(i-1);
            i--;
        }

        nameStr += detailsStr;


        return nameStr;
    }

    public boolean addDetail(String s) {
        if (!(details.contains(s))) {
            details.add(s);
            return true;
        }
        return false;
    }

    public double getPrice() {
        return price;
    }

    public ArrayList<String> getDetails() {
        return details;
    }

    public void setPrice(double v) {
        price = v;
    }

    public void resetDetails() {
        details = new ArrayList<>();
    }
}
