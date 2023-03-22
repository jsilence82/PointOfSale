package tests.testDatabase;

import controller.ItemsDBController;
import model.items.Item;
import org.junit.jupiter.api.Test;

import java.util.List;

public class TestPull {
    @Test
    public void main() {
        ItemsDBController pull = new ItemsDBController();
        List<Item> foods = pull.getFoodList();
        for(Item food : foods){
            System.out.println(food.getItemID() + " " + food.getDescription() + " " + food.getPrice() + " " + food.getQuantityInDB());
        }
// Test Output
//        1 K�sebretzel 1.99 8
//        2 Bretzel 1.49 11
//        3 Salami Sandwich 2.3 16
//        5 Berliner 1.0 8
//        6 Streusel 1.29 3

        List<Item> drinks = pull.getDrinksList();
        for(Item drink : drinks){
            System.out.println(drink.getItemID() + " " + drink.getDescription() + " " + drink.getPrice() + " " + drink.getQuantityInDB());
        }
// Test Output
//        1 Coca Cola 1.29 10
//        2 Fanta 1.29 4
//        3 Cola Light 1.29 5
//        4 Kaffee 1.0 4
//        5 Wasser 0.99 20

    }
}

