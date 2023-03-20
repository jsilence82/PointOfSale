package model.items.test;

import controller.DBController;
import model.items.Item;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WarenTest {

    @Test
    void testGetEssenFromDatabase() {
        DBController waren = new DBController();
        List<Item> essenList = waren.getEssenList();

        asserList(essenList);
    }

    private void asserList(List<Item> essenList) {
        assertNotNull(essenList);
        assertFalse(essenList.isEmpty());

        for (Item essen : essenList) {
            assertNotNull(essen);
            assertNotNull(essen.getDescription());
            assertNotEquals("", essen.getDescription());
            assertTrue(essen.getItemID() > 0);
            assertTrue(essen.getLagerAnzahl() >= 0);
            assertTrue(essen.getPreis() >= 0);
        }
    }

    @Test
    void testGetGetraenkFromDatabase() {
        DBController waren = new DBController();
        List<Item> trinkList = waren.getTrinkList();

        asserList(trinkList);
    }
}
