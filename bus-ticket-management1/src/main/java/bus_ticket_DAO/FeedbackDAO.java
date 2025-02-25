package bus_ticket_DAO;

import bus_ticket_entity.Feedback;
import bus_ticket_util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class FeedbackDAO {

    // Save Feedback
    public void saveFeedback(Feedback feedback) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(feedback);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    // Get Feedback by ID
    public Feedback getFeedbackById(int feedbackId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Feedback.class, feedbackId);
        }
    }

    // Get All Feedbacks
    public List<Feedback> getAllFeedbacks() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Feedback> query = session.createQuery("from Feedback", Feedback.class);
            return query.list();
        }
    }

    // Delete Feedback by ID
    public void deleteFeedback(int feedbackId) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Feedback feedback = session.get(Feedback.class, feedbackId);
            if (feedback != null) {
                session.delete(feedback);
                transaction.commit();
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
