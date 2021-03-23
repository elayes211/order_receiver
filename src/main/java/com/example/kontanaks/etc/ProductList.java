package com.example.kontanaks.etc;

import java.util.ArrayList;

public class ProductList {
    public static ArrayList<Product> anthrakouxa = new ArrayList<>(8);;
    public static ArrayList<Product> espresso = new ArrayList<>(40);
    public static ArrayList<Product> nescafe = new ArrayList<>(5);
    public static ArrayList<Product> tea = new ArrayList<>(3);
    public static ArrayList<Product> beers = new ArrayList<>(3);
    public static ArrayList<Product> lux = new ArrayList<>(4);
    public static ArrayList<Product> ximoi = new ArrayList<>(2);
    public static ArrayList<Product> toast = new ArrayList<>(3);
    public static ArrayList<Product> nero = new ArrayList<>(2);
    public static ArrayList<Product> fagito = new ArrayList<>(4);

    public static ArrayList<Product> etc = new ArrayList<>(6);


    public ProductList() {
        etc.add(new Product("decaf", 0));
        etc.add(new Product("γάλα", 0.50));
        etc.add(new Product("μαύρη", 0.50));
        etc.add(new Product("ζαχαρίνη", 0));
        etc.add(new Product("κανέλα", 0));
        etc.add(new Product("παγάκια", 0));

        anthrakouxa.add(new Product("Lux Πορτοκαλάδα με Ανθρ.", 2));
        anthrakouxa.add(new Product("Lux Πορτοκαλάδα χωρίς Ανθρ.", 2));
        anthrakouxa.add(new Product("Lux Gazoza/ Sprite", 2));
        anthrakouxa.add(new Product("Lux Βυσινάδα", 2));

        anthrakouxa.add(new Product("Coca Cola", 2));
        anthrakouxa.add(new Product("Hell", 2));
        anthrakouxa.add(new Product("Red Bull", 2));
        anthrakouxa.add(new Product("Perrier", 3));

        espresso.add(new Product("Freddo Espresso Σκέτο", 2));
        espresso.add(new Product("Freddo Espresso Mέτριο", 2));
        espresso.add(new Product("Freddo Espresso Γλυκό", 2));
        espresso.add(new Product("Freddo Espresso ΠΡΟΣ Γλυκό", 2));
        espresso.add(new Product("Freddo Espresso ΠΡΟΣ Σκέτο", 2));

        espresso.add(new Product("Freddo Cappuccino Σκέτο", 2));
        espresso.add(new Product("Freddo Cappuccino Mέτριο", 2));
        espresso.add(new Product("Freddo Cappuccino Γλυκό", 2));
        espresso.add(new Product("Freddo Cappuccino ΠΡΟΣ Γλυκό", 2));
        espresso.add(new Product("Freddo Cappuccino ΠΡΟΣ Σκέτο", 2));

        espresso.add(new Product("Espresso ΜΟΝΟ Σκέτο", 2));
        espresso.add(new Product("Espresso ΜΟΝΟ Mέτριο", 2));
        espresso.add(new Product("Espresso ΜΟΝΟ Γλυκό", 2));
        espresso.add(new Product("Espresso ΜΟΝΟ ΠΡΟΣ Γλυκό", 2));
        espresso.add(new Product("Espresso ΜΟΝΟ ΠΡΟΣ Σκέτο", 2));

        espresso.add(new Product("Espresso ΔΙΠΛΟ Σκέτο", 2));
        espresso.add(new Product("Espresso ΔΙΠΛΟ Mέτριο", 2));
        espresso.add(new Product("Espresso ΔΙΠΛΟ Γλυκό", 2));
        espresso.add(new Product("Espresso ΔΙΠΛΟ ΠΡΟΣ Γλυκό", 2));
        espresso.add(new Product("Espresso ΔΙΠΛΟ ΠΡΟΣ Σκέτο", 2));

        espresso.add(new Product("Cappuccino ΜΟΝΟ Σκέτο", 2));
        espresso.add(new Product("Cappuccino ΜΟΝΟ Mέτριο", 2));
        espresso.add(new Product("Cappuccino ΜΟΝΟ Γλυκό", 2));
        espresso.add(new Product("Cappuccino ΜΟΝΟ ΠΡΟΣ Γλυκό", 2));
        espresso.add(new Product("Cappuccino ΜΟΝΟ ΠΡΟΣ Σκέτο", 2));

        espresso.add(new Product("Cappuccino ΔΙΠΛΟ Σκέτο", 2));
        espresso.add(new Product("Cappuccino ΔΙΠΛΟ Mέτριο", 2));
        espresso.add(new Product("Cappuccino ΔΙΠΛΟ Γλυκό", 2));
        espresso.add(new Product("Cappuccino ΔΙΠΛΟ ΠΡΟΣ Γλυκό", 2));
        espresso.add(new Product("Cappuccino ΔΙΠΛΟ ΠΡΟΣ Σκέτο", 2));

        espresso.add(new Product("Americano ΜΟΝΟ Σκέτο", 2));
        espresso.add(new Product("Americano ΜΟΝΟ Mέτριο", 2));
        espresso.add(new Product("Americano ΜΟΝΟ Γλυκό", 2));
        espresso.add(new Product("Americano ΜΟΝΟ ΠΡΟΣ Γλυκό", 2));
        espresso.add(new Product("Americano ΜΟΝΟ ΠΡΟΣ Σκέτο", 2));

        espresso.add(new Product("Americano ΔΙΠΛΟ Σκέτο", 2));
        espresso.add(new Product("Americano ΔΙΠΛΟ Mέτριο", 2));
        espresso.add(new Product("Americano ΔΙΠΛΟ Γλυκό", 2));
        espresso.add(new Product("Americano ΔΙΠΛΟ ΠΡΟΣ Γλυκό", 2));
        espresso.add(new Product("Americano ΔΙΠΛΟ ΠΡΟΣ Σκέτο", 2));

        nescafe.add(new Product("Φραππέ Σκέτο", 2));
        nescafe.add(new Product("Φραππέ Mέτριο", 2));
        nescafe.add(new Product("Φραππέ Γλυκό", 2));
        nescafe.add(new Product("Φραππέ ΠΡΟΣ Γλυκό", 2));
        nescafe.add(new Product("Φραππέ ΠΡΟΣ Σκέτο", 2));

        tea.add(new Product("Τσάι Πράσινο", 2));
        tea.add(new Product("Τσάι Ροδάκινο", 2));
        tea.add(new Product("Τσάι Λεμόνι", 2));

        beers.add(new Product("Μπύρα Corona", 2));
        beers.add(new Product("Μπύρα Amstel", 2));
        beers.add(new Product("Μπύρα Alpha", 2));

        ximoi.add(new Product("Motion", 2));
        ximoi.add(new Product("Lux ΧΥΜΟΣ Πορτοκάλι", 2));

        toast.add(new Product("Τοστ γαλοπουλα-τυρι", 2));
        toast.add(new Product("Τοστ τυρι μονο", 2));
        toast.add(new Product("Τοστ ζαμπον-τυρι", 2));

        nero.add(new Product("Νερό Μικρό", 0.50));
        nero.add(new Product("Νερό Μεγάλο", 1.50));

        fagito.add(new Product("Μεριδα γυρο", 2));
        fagito.add(new Product("Σουβλάκι Χοιρινό", 2));
        fagito.add(new Product("Σουβλάκι Κοτόπουλο", 2));
        fagito.add(new Product("Πατάτες", 2));
        }
}
