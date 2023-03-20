package model.items;

public class Item implements Comparable<Item> {

    private int itemID;
    private String description;
    private int lagerAnzahl;
    private double preis;

    public Item(int itemID,String description, int lagerAnzahl, double preis) {
        this.itemID = itemID;
        this.description = description;
        this.lagerAnzahl = lagerAnzahl;
        this.preis = preis;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getLagerAnzahl() {
        return lagerAnzahl;
    }

    public void setLagerAnzahl(int lagerAnzahl) {
        this.lagerAnzahl = lagerAnzahl;
    }

    public double getPreis() {
        return preis;
    }

    public void setPreis(double preis) {
        this.preis = preis;
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
