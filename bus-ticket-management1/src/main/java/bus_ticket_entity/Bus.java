package bus_ticket_entity;

import javax.persistence.*;

@Entity
public class Bus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int busId;

    @Column(nullable = false)
    private String busName;

    @Column(nullable = false)
    private String route;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private String date;

    // Default Constructor
    public Bus() {
    }

    // Parameterized Constructor
    public Bus(String busName, String route, double price, String date) {
        this.busName = busName;
        this.route = route;
        this.price = price;
        this.date = date;
    }

    // Getters and Setters
    public int getBusId() {
        return busId;
    }

    public String getBusName() {
        return busName;
    }

    public void setBusName(String busName) {
        this.busName = busName;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
