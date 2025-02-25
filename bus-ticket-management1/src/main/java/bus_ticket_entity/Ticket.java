package bus_ticket_entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "tickets")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ticketId;

    @ManyToOne
    private User user;

    @ManyToOne
    private Bus bus;

    private double price;
    private Date bookingDate;
    private String status; // 'booked' or 'cancelled'

    // Default constructor
    public Ticket() {
    }

    // Constructor with all fields
    public Ticket(User user, Bus bus, double price, Date bookingDate, String status) {
        this.user = user;
        this.bus = bus;
        this.price = price;
        this.bookingDate = bookingDate;
        this.status = status;
    }

    // Getters and Setters
    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Bus getBus() {
        return bus;
    }

    public void setBus(Bus bus) {
        this.bus = bus;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Ticket [ticketId=" + ticketId + ", user=" + user.getUsername() + ", bus=" + bus.getBusName() + ", price=" + price + ", bookingDate=" + bookingDate + ", status=" + status + "]";
    }

	public String getPassengerName() {
		// TODO Auto-generated method stub
		return null;
	}
}
