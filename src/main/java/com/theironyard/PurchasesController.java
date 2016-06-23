package com.theironyard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by will on 6/22/16.
 */
@Controller
public class PurchasesController {

    @Autowired
    CustomerRepository customers;

    @Autowired
    PurchaseRepository purchases;

    @PostConstruct
    public void init() throws FileNotFoundException {
        if (customers.count() == 0) {
            File c = new File("customers.csv");
            Scanner customerScanner = new Scanner(c);
            customerScanner.nextLine();
            while (customerScanner.hasNext()) {
                String line = customerScanner.nextLine();
                String[] columns = line.split(",");
                Customer customer = new Customer(columns[0], columns[1]);
                customers.save(customer);
            }
        }
        if (purchases.count() == 0) {
            File p = new File("purchases.csv");

            Scanner purchaseScanner = new Scanner(p);
            purchaseScanner.nextLine();
            while (purchaseScanner.hasNext()) {
                String line = purchaseScanner.nextLine();
                String[] columns = line.split(",");
                Purchase purchase = new Purchase(columns[1], columns[2], Integer.valueOf(columns[3]), columns[4], customers.findOne(Integer.valueOf(columns[0])));
                purchases.save(purchase);
            }
        }
    }

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String home(Model model, String category) {
        Iterable<Purchase> purcs;

        if (category != null) {
            purcs = purchases.findByCategory(category);
        }
        else {
            purcs = purchases.findAll();
        }

        model.addAttribute("purchases", purcs);
        return "home";
    }

}
