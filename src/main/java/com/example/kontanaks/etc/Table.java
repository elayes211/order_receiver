package com.example.kontanaks.etc;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Table implements Serializable {
    private boolean trashes;
    private boolean reserved;
    private double price = 0;
    public HashMap<Product, Integer> map = new HashMap<Product, Integer>();

    public Table() {
    }

    public void addProduct(Product p) {
            Product found = getProductByName(p.getName());
            if (found == null){
                map.put(p, 1);
            } else{
                map.put(found, map.get(found) + 1);
            }
            price += p.getPrice();
    }


    public void addProduct(Product p, int i) {
        if (p == null || i == 0)
            return;

        Product found = getProductByName(p.getName());
        if (found == null){
            map.put(p, i);
        } else{
            map.put(found, map.get(found) + i);
        }
        price += p.getPrice()*i;
    }

    public HashMap<Product, Integer> getProducts() {
        return map;
    }

    public void addProducts(HashMap<Product, Integer> products) {
        // Create a list from elements of HashMap
        List<Map.Entry<Product, Integer> > list =
                new LinkedList<>(products.entrySet());

        for (Map.Entry<Product, Integer> aa : list) {
            addProduct(aa.getKey(), aa.getValue());
        }
    }

    public void removeProduct(Product p) {
        Integer oldValue = map.get(p);
        price -= p.getPrice();
        if (oldValue == 1){
            map.remove(p);
        } else{
            map.put(p, oldValue-1);
        }
    }

    public void removeProductByName(String name) {
        for (HashMap.Entry<Product, Integer> entry : map.entrySet()){
            Product p = entry.getKey();
            if (p.getName().equals(name)) {
                removeProduct(p);
                break;
            }
        }
    }

    public List<String> getProductNames() {
        map = sortByValue(map);

        List<String> list = new ArrayList<String>();
        for (HashMap.Entry<Product, Integer> entry : map.entrySet()){
                list.add(entry.getKey().getName());
            }
        return list;
    }

    // function to sort hashmap by values
    public static HashMap<Product, Integer> sortByValue(HashMap<Product, Integer> hm)
    {
        // Create a list from elements of HashMap
        List<Map.Entry<Product, Integer> > list =
                new LinkedList<>(hm.entrySet());

        // Sort the list
        Collections.sort(list, new Comparator<Map.Entry<Product, Integer> >() {
            public int compare(Map.Entry<Product, Integer> o1,
                               Map.Entry<Product, Integer> o2)
            {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        // put data from sorted list to hashmap
        HashMap<Product, Integer> temp = new LinkedHashMap<>();
        for (Map.Entry<Product, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }


        // reverse sort
        HashMap<Product, Integer> reversed = new LinkedHashMap<>();
        Product[] keys = temp.keySet().toArray(new Product[temp.size()]);

        for (int i = keys.length - 1; i >= 0; i--) {
            reversed.put(keys[i], temp.get(keys[i]));
        }

        return reversed;
    }

    public String getSerializedProducts() {
        String serializedProducts = "";
        String serializedProductsWithDetails = "";
        ArrayList<String> detail0;

        map = sortByValue(map);

        for (HashMap.Entry<Product, Integer> entry : map.entrySet()){
            Product p = entry.getKey();
            int count = entry.getValue();
            detail0 = p.getDetails();


            // Sort
            if (detail0.isEmpty() && !p.isServed())
                serializedProducts += count + " " + p.getName() + "\n";
            else
                serializedProductsWithDetails += count + " " + p.getName() + "\n";
        }

        return serializedProducts + serializedProductsWithDetails;
    }

    public Product getProductByName(String name) {

        for (HashMap.Entry<Product, Integer> entry : map.entrySet()){
            Product p = entry.getKey();
            if (p.getName().equals(name))
                return p;
        }
        return null;
    }

    public double getPrice() {
        return price;
    }

    public int countProducts() {
        return map.size();
    }

    public boolean isTrashed() {
        return trashes;
    }

    public boolean isReserved() {
        return reserved;
    }


}
