package models;

public class Ship {
    private String name;
    private int length;
    private int hits;

    public Ship(String name, int length) {
        this.name = name;
        this.length = length;
        this.hits = 0;
    }

    public boolean isDestroyed() {
        return hits >= length;
    }

    public void hit() {
        hits++;
    }

    public int getLength() {
        return length;
    }

    public String getName() {
        return name;
    }
}
