package model;

import controller.ItemsDBController;
import controller.UserSessionController;
import model.items.Item;

import javax.swing.*;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class ShoppingCart {

    private final HashMap<Item, Integer> cart;
    private final NumberFormat euroFormat;
    private final String orderId;
    private final int userID;

    public ShoppingCart() {
        cart = new HashMap<>();
        euroFormat = NumberFormat.getCurrencyInstance(Locale.GERMANY);
        orderId = generateOrderID();
       // For Unit tests:
       // userID = 1;
        userID = UserSessionController.getUserId();
       // Comment out UserSessionController
    }

    private String generateOrderID() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMddHHmm");
        Random random = new Random();
        Date now = new Date();
        String formattedDate = dateFormat.format(now);
        int randomNumber = random.nextInt(90) + 10;
        return formattedDate + randomNumber;
    }

    public void addItem(Item item, int amount) {
        try {
            if (cart.containsKey(item)) {
                int currentAmount = cart.get(item);
                int newAmount = currentAmount + amount;
                if (newAmount > item.getQuantityInDB()) {
                    throw new IllegalArgumentException();
                } else {
                    cart.put(item, newAmount);
                }
            } else {
                if (amount > item.getQuantityInDB()) {
                    throw new IllegalArgumentException();
                } else {
                    cart.put(item, amount);
                }
            }
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, "Die Menge übersteigt den Bestand!");
        }
    }

    public void removeItem(Item item) {
        if (cart.get(item) == 1) {
            cart.remove(item);
        } else {
            cart.put(item, cart.get(item) - 1);
        }
    }

    public void clearCart() {
        cart.clear();
    }

    public HashMap<Item, Integer> getCart() {
        return cart;
    }

    public String getFormattedTotal() {
        return euroFormat.format(getTotal());
    }

    public double getTotal() {
        double total = 0.00;
        for (Map.Entry<Item, Integer> entry : cart.entrySet()) {
            Item item = entry.getKey();
            double price = item.getPrice();
            total += (price * entry.getValue());
        }
        return total;
    }

    public void completeOrder() {
        ItemsDBController.insertOrder(orderId, userID, BigDecimal.valueOf(getTotal()));
        for (Map.Entry<Item, Integer> entry : cart.entrySet()) {
            Item item = entry.getKey();
            int quantity = entry.getValue();
            ItemsDBController.insertItemSold(orderId, item, quantity);
            ItemsDBController.updateDBItemQuantity(item, quantity);
        }
        clearCart();
    }

    public String getOrderId() {
        return orderId;
    }
}
