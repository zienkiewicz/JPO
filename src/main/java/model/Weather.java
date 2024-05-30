package model;

public class Weather {
    public Location location;
    public Current current;

    // Getters and setters
    public Location getLocation() { return location; }
    public void setLocation(Location location) { this.location = location; }
    public Current getCurrent() { return current; }
    public void setCurrent(Current current) { this.current = current; }
}
