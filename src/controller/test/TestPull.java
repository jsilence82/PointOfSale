package controller.test;

import controller.DBController;
import model.items.Item;

import java.util.List;

public class TestPull {
    public static void main(String[] args) {
        DBController pull = new DBController();
        List<Item> foods = pull.getEssenList();
        for(Item food : foods){
            System.out.println(food.getItemID() + " " + food.getDescription() + " " + food.getPreis() + " " + food.getLagerAnzahl());
        }

        List<Item> drinks = pull.getTrinkList();
        for(Item drink : drinks){
            System.out.println(drink.getItemID() + " " + drink.getDescription() + " " + drink.getPreis() + " " + drink.getLagerAnzahl());
        }
    }
}
