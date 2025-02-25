package bus_ticket_entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "feedbacks")
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int feedbackId;

    @ManyToOne
    private User user;

    @ManyToOne
    private Bus bus;

    private String feedbackText;
    private int rating; // Rating from 1 to 5

    // Default constructor
    public Feedback() {
    }

    // Constructor with all fields
    public Feedback(User user, Bus bus, String feedbackText, int rating) {
        this.user = user;
        this.bus = bus;
        this.feedbackText = feedbackText;
        this.rating = rating;
    }

    // Getters and Setters
    public int getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(int feedbackId) {
        this.feedbackId = feedbackId;
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

    public String getFeedbackText() {
        return feedbackText;
    }

    public void setFeedbackText(String feedbackText) {
        this.feedbackText = feedbackText;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Feedback [feedbackId=" + feedbackId + ", user=" + user.getUsername() + ", bus=" + bus.getBusName() + ", feedbackText=" + feedbackText + ", rating=" + rating + "]";
    }
}
