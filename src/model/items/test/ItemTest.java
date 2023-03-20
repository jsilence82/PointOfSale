package model.items.test;

import static org.junit.Assert.*;

import model.items.Item;
import org.junit.Test;

public class ItemTest {

    @Test
    public void testGetDescription() {
        Item item = new Item(1, "Test Item", 10, 9.99);
        assertEquals("Test Item", item.getDescription());
    }

    @Test
    public void testSetDescription() {
        Item item = new Item(1, "Test Item", 10, 9.99);
        item.setDescription("New Description");
        assertEquals("New Description", item.getDescription());
    }

    @Test
    public void testGetLagerAnzahl() {
        Item item = new Item(1, "Test Item", 10, 9.99);
        assertEquals(10, item.getLagerAnzahl());
    }

    @Test
    public void testSetLagerAnzahl() {
        Item item = new Item(1, "Test Item", 10, 9.99);
        item.setLagerAnzahl(20);
        assertEquals(20, item.getLagerAnzahl());
    }

    @Test
    public void testGetPreis() {
        Item item = new Item(1, "Test Item", 10, 9.99);
        assertEquals(9.99, item.getPreis(), 0.001);
    }

    @Test
    public void testSetPreis() {
        Item item = new Item(1, "Test Item", 10, 9.99);
        item.setPreis(19.99);
        assertEquals(19.99, item.getPreis(), 0.001);
    }

    @Test
    public void testGetItemID() {
        Item item = new Item(1, "Test Item", 10, 9.99);
        assertEquals(1, item.getItemID());
    }

    @Test
    public void testSetItemID() {
        Item item = new Item(1, "Test Item", 10, 9.99);
        item.setItemID(2);
        assertEquals(2, item.getItemID());
    }

    @Test
    public void testCompareTo() {
        Item item1 = new Item(1, "Test Item 1", 10, 9.99);
        Item item2 = new Item(2, "Test Item 2", 20, 19.99);
        assertTrue(item1.compareTo(item2) < 0);
        assertTrue(item2.compareTo(item1) > 0);
        assertEquals(0, item1.compareTo(item1));
    }
}

