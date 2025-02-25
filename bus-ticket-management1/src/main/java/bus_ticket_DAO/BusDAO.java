package bus_ticket_DAO;

import bus_ticket_entity.Bus;
import bus_ticket_util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class BusDAO {

    // Save Bus
    public void saveBus(Bus bus) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(bus);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    // Get Bus by ID
    public Bus getBusById(int busId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Bus.class, busId);
        }
    }

    // Get All Buses
    public List<Bus> getAllBuses() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Bus> query = session.createQuery("from Bus", Bus.class);
            return query.list();
        }
    }

    // Delete Bus by ID
    public void deleteBus(int busId) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Bus bus = session.get(Bus.class, busId);
            if (bus != null) {
                session.delete(bus);
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
