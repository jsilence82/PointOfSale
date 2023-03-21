package tests.testDatabase;

import controller.ItemsDBController;
import model.items.Item;

import java.util.List;

public class TestPull {
    public static void main(String[] args) {
        ItemsDBController pull = new ItemsDBController();
        List<Item> foods = pull.getFoodList();
        for(Item food : foods){
            System.out.println(food.getItemID() + " " + food.getDescription() + " " + food.getPrice() + " " + food.getQuantityInDB());
        }

        List<Item> drinks = pull.getDrinksList();
        for(Item drink : drinks){
            System.out.println(drink.getItemID() + " " + drink.getDescription() + " " + drink.getPrice() + " " + drink.getQuantityInDB());
        }
    }
}
