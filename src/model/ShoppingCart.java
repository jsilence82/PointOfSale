package model;

import controller.DBController;
import controller.UserSessionController;
import model.items.Item;
import view.SelectScreen;

import javax.swing.*;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class ShoppingCart {

    private final SelectScreen screen;
    private final HashMap<Item, Integer> cart;
    private final NumberFormat euroFormat;
    private final String bestellungID;
    private final int mitarbeiterID;

    public ShoppingCart(SelectScreen screen) {
        this.screen = screen;
        cart = new HashMap<>();
        euroFormat = NumberFormat.getCurrencyInstance(Locale.GERMANY);
        bestellungID = generateBestellungID();
        mitarbeiterID = UserSessionController.getMitarbeiterID();
    }

    private String generateBestellungID() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMddHHmm");
        Random random = new Random();
        Date now = new Date();
        String formattedDate = dateFormat.format(now);
        int randomNumber = random.nextInt(90) + 10;
        return formattedDate + randomNumber;
    }

//    public void addItem(Item item, int amount) {
//        if(cart.containsKey(item)){
//            cart.put(item, cart.get(item) + amount);
//        } else {
//            cart.put(item, amount);
//        }
//        screen.addToTally();
//    }
    public void addItem(Item item, int amount) {
        if(cart.containsKey(item)){
            int currentAmount = cart.get(item);
            int newAmount = currentAmount + amount;
            if(newAmount > item.getLagerAnzahl()) {
                JOptionPane.showMessageDialog(null, "Amount exceeds the stock!");
            } else {
                cart.put(item, newAmount);
            }
        } else {
            if(amount > item.getLagerAnzahl()) {
                JOptionPane.showMessageDialog(null, "Amount exceeds the stock!");
            } else {
                cart.put(item, amount);
            }
        }
        screen.addToTally();
    }


    public void removeItem(Item item){
        if(cart.get(item) == 1){
            cart.remove(item);
        } else {
            cart.put(item, cart.get(item) - 1);
        }
        screen.addToTally();
    }

    public void clearCart(){
        cart.clear();
    }

    public HashMap<Item, Integer> getCart() {
        return cart;
    }

    public String getFormattedTotal(){
        return euroFormat.format(getTotal());
    }

    public double getTotal(){
        double total = 0.00;
        for(Map.Entry<Item, Integer> entry : cart.entrySet()){
            Item item = entry.getKey();
            double price = item.getPreis();
            total += (price * entry.getValue());
        }
        return total;
    }

    public void buchungSchliessen() {
            DBController.insertBestellung(bestellungID, mitarbeiterID, BigDecimal.valueOf(getTotal()));
            for (Map.Entry<Item, Integer> entry : cart.entrySet()) {
                Item item = entry.getKey();
                int quantity = entry.getValue();
                DBController.insertItemSold(bestellungID, item, quantity);
                DBController.updateLagerBestand(item, quantity);
            }
            clearCart();
    }

    public String getBestellungID() {
        return bestellungID;
    }
}
