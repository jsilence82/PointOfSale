package model.items;

public class Item implements Comparable<Item> {

    private int itemID;
    private String description;
    private int quantityInDB;
    private double price;

    public Item(int itemID,String description, int lagerAnzahl, double preis) {
        this.itemID = itemID;
        this.description = description;
        this.quantityInDB = lagerAnzahl;
        this.price = preis;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantityInDB() {
        return quantityInDB;
    }

    public void setQuantityInDB(int quantityInDB) {
        this.quantityInDB = quantityInDB;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    @Override
    public int compareTo(Item o) {
        return Integer.compare(this.itemID, o.itemID);
    }
}
