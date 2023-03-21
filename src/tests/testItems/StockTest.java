package tests.testItems;

import controller.ItemsDBController;
import model.items.Item;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StockTest {

    @Test
    void testGetFoodFromDB() {
        ItemsDBController waren = new ItemsDBController();
        List<Item> foodList = waren.getFoodList();
        asserList(foodList);
    }

    private void asserList(List<Item> items) {
        assertNotNull(items);
        assertFalse(items.isEmpty());

        for (Item item : items) {
            assertNotNull(item);
            assertNotNull(item.getDescription());
            assertNotEquals("", item.getDescription());
            assertTrue(item.getItemID() > 0);
            assertTrue(item.getQuantityInDB() >= 0);
            assertTrue(item.getPrice() >= 0);
        }
    }

    @Test
    void testGetDrinksFromDB() {
        ItemsDBController controller = new ItemsDBController();
        List<Item> drinksList = controller.getDrinksList();

        asserList(drinksList);
    }
}
