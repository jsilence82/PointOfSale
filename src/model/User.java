package model;

public class User {

    private static User instance;
    private int mitarbeiterID;
    private String vorname;
    private String nachname;

    private User() {}

    public static synchronized User getInstance() {
        if (instance == null) {
            instance = new User();
        }
        return instance;
    }

    public int getMitarbeiterID() {
        return mitarbeiterID;
    }

    public void setMitarbeiterID(int mitarbeiterID) {
        this.mitarbeiterID = mitarbeiterID;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }
}

