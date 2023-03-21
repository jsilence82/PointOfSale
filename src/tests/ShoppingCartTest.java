package tests;

import org.junit.Before;
import org.junit.Test;
import java.util.HashMap;
import static org.junit.Assert.assertEquals;
import model.ShoppingCart;
import model.items.Item;


public class ShoppingCartTest {

    private ShoppingCart shoppingCart;
    private Item item1;
    private Item item2;

    @Before
    public void setUp() {
        shoppingCart = new ShoppingCart();
        item1 = new Item(1, "Item 1", 5, 10.00);
        item2 = new Item(2, "Item 2", 10, 5.00);
    }


    @Test
    public void testAddItem() {
        Item item = new Item(1, "test item", 10, 5.99);
        shoppingCart.addItem(item, 1);
        HashMap<Item, Integer> cart = shoppingCart.getCart();
        assertEquals(1, cart.size());
        assertEquals(1, (int) cart.get(item));
    }

    @Test
    public void testRemoveItem() {
        shoppingCart.addItem(item1, 2);
        shoppingCart.addItem(item2, 5);
        shoppingCart.removeItem(item2);
        HashMap<Item, Integer> expectedCart = new HashMap<>();
        expectedCart.put(item1, 2);
        expectedCart.put(item2, 4);
        assertEquals(expectedCart, shoppingCart.getCart());
    }

    @Test
    public void testClearCart() {
        shoppingCart.addItem(item1, 2);
        shoppingCart.addItem(item2, 5);
        shoppingCart.clearCart();
        HashMap<Item, Integer> expectedCart = new HashMap<>();
        assertEquals(expectedCart, shoppingCart.getCart());
    }

    @Test
    public void testGetTotal() {
        shoppingCart.addItem(item1, 2);
        shoppingCart.addItem(item2, 5);
        assertEquals(45, shoppingCart.getTotal(), 0.01);
    }

}
