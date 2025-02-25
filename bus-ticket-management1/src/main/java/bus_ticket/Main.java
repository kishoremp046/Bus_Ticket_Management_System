package bus_ticket;

import bus_ticket_entity.Bus;
import bus_ticket_entity.Feedback;
import bus_ticket_entity.Ticket;
import bus_ticket_entity.User;
import bus_ticket_DAO.UserDAO;
import bus_ticket_DAO.TicketDAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static SessionFactory sessionFactory;

    public static void main(String[] args) {
        // Set up Hibernate
        Configuration config = new Configuration().configure("hibernate.cfg.xml")
                .addAnnotatedClass(Bus.class)
                .addAnnotatedClass(Feedback.class)
                .addAnnotatedClass(Ticket.class)
                .addAnnotatedClass(User.class);
        sessionFactory = config.buildSessionFactory();

        Scanner sc = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n--- Bus Ticket Management System ---");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Select an option: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    registerUser();
                    break;
                case 2:
                    User loggedInUser = loginUser();
                    if (loggedInUser != null) {
                        if (loggedInUser.getRole().equals("admin")) {
                            adminMenu(loggedInUser);
                        } else {
                            userMenu(loggedInUser);
                        }
                    }
                    break;
                case 3:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice! Please select again.");
            }
        }
        sc.close();
        sessionFactory.close();
    }

    private static void registerUser() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n--- Register ---");
        System.out.print("Username: ");
        String username = sc.next();
        System.out.print("Password: ");
        String password = sc.next();
        System.out.print("Email: ");
        String email = sc.next();
        System.out.print("Role (user/admin): ");
        String role = sc.next();

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        User newUser = new User(username, password, email, role);
        session.save(newUser);

        transaction.commit();
        session.close();

        System.out.println("Registration successful! You can now log in.");
    }

    private static User loginUser() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n--- Login ---");
        System.out.print("Username: ");
        String username = sc.next();
        System.out.print("Password: ");
        String password = sc.next();

        Session session = sessionFactory.openSession();
        User user = session.createQuery("FROM User WHERE username = :username AND password = :password", User.class)
                .setParameter("username", username)
                .setParameter("password", password)
                .uniqueResult();
        session.close();

        if (user != null) {
            System.out.println("Login successful! Welcome, " + user.getUsername());
            return user;
        } else {
            System.out.println("Invalid credentials! Please try again.");
            return null;
        }
    }

    private static void adminMenu(User admin) {
        Scanner sc = new Scanner(System.in);
        boolean adminRunning = true;

        while (adminRunning) {
            System.out.println("\n--- Admin Menu ---");
            System.out.println("1. Add Bus");
            System.out.println("2. View All Buses");
            System.out.println("3. View All Tickets");
            System.out.println("4. View All Users");
            System.out.println("5. Delete User by ID");
            System.out.println("6. Delete Ticket by ID");
            System.out.println("7. Logout");
            System.out.print("Select an option: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    addBus();
                    break;
                case 2:
                    viewAllBuses();
                    break;
                case 3:
                    viewAllTickets();
                    break;
                case 4:
                    viewAllUsers();
                    break;
                case 5:
                    deleteUserById();
                    break;
                case 6:
                    deleteTicketById();
                    break;
                case 7:
                    adminRunning = false;
                    break;
                default:
                    System.out.println("Invalid choice! Please select again.");
            }
        }
    }

    private static void userMenu(User user) {
        Scanner sc = new Scanner(System.in);
        boolean userRunning = true;

        while (userRunning) {
            System.out.println("\n--- User Menu ---");
            System.out.println("1. View Available Buses");
            System.out.println("2. Book Ticket");
            System.out.println("3. Provide Feedback");
            System.out.println("4. Logout");
            System.out.print("Select an option: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    viewAllBuses();
                    break;
                case 2:
                    bookTicket(user);
                    break;
                case 3:
                    provideFeedback(user);
                    break;
                case 4:
                    userRunning = false;
                    break;
                default:
                    System.out.println("Invalid choice! Please select again.");
            }
        }
    }

    private static void addBus() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n--- Add Bus ---");
        System.out.print("Bus Name: ");
        String busName = sc.nextLine();
        System.out.print("Route: ");
        String route = sc.nextLine();
        System.out.print("Price: ");
        double price = sc.nextDouble();
        System.out.print("Date: ");
        String date = sc.next();

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Bus bus = new Bus(busName, route, price, date);
        session.save(bus);

        transaction.commit();
        session.close();

        System.out.println("Bus added successfully!");
    }

    private static void viewAllBuses() {
        Session session = sessionFactory.openSession();
        List<Bus> buses = session.createQuery("FROM Bus", Bus.class).list();
        session.close();

        System.out.println("\n--- Available Buses ---");
        for (Bus b : buses) {
            System.out.println(b.getBusId() + " - " + b.getBusName() + " | Route: " + b.getRoute() + " | Price: " + b.getPrice());
        }
    }

    private static void bookTicket(User user) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Bus ID to book: ");
        int busId = sc.nextInt();

        Session session = sessionFactory.openSession();
        Bus bus = session.get(Bus.class, busId);

        if (bus != null) {
            Transaction transaction = session.beginTransaction();
            Ticket ticket = new Ticket(user, bus, bus.getPrice(), new Date(), "booked");
            session.save(ticket);
            transaction.commit();
            System.out.println("Ticket booked successfully!");
        } else {
            System.out.println("Invalid Bus ID. Please try again.");
        }
        session.close();
    }

    private static void provideFeedback(User user) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Bus ID for feedback: ");
        int busId = sc.nextInt();
        sc.nextLine();
        System.out.print("Feedback: ");
        String feedbackText = sc.nextLine();
        System.out.print("Rating (1-5): ");
        int rating = sc.nextInt();

        Session session = sessionFactory.openSession();
        Bus bus = session.get(Bus.class, busId);

        if (bus != null) {
            Transaction transaction = session.beginTransaction();
            Feedback feedback = new Feedback(user, bus, feedbackText, rating);
            session.save(feedback);
            transaction.commit();
            System.out.println("Feedback submitted successfully!");
        } else {
            System.out.println("Invalid Bus ID. Please try again.");
        }
        session.close();
    }

    private static void viewAllTickets() {
        Session session = sessionFactory.openSession();
        List<Ticket> tickets = session.createQuery("FROM Ticket", Ticket.class).list();
        session.close();

        System.out.println("\n--- Booked Tickets ---");
        for (Ticket t : tickets) {
            System.out.println(t.getTicketId() + " - " + t.getUser().getUsername() + " | Bus: " + t.getBus().getBusName() + " | Price: " + t.getPrice());
        }
    }

    private static void viewAllUsers() {
        UserDAO userDAO = new UserDAO();
        List<User> users = userDAO.getAllUsers();

        System.out.println("\n--- Registered Users ---");
        for (User u : users) {
            System.out.println("ID: " + u.getUserId() + " | Username: " + u.getUsername() + " | Email: " + u.getEmail() + " | Role: " + u.getRole());
        }
    }

    private static void deleteUserById() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter User ID to delete: ");
        int userId = sc.nextInt();

        UserDAO userDAO = new UserDAO();
        userDAO.deleteUser(userId);

        System.out.println("User deleted successfully!");
    }

    private static void deleteTicketById() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Ticket ID to delete: ");
        int ticketId = sc.nextInt();

        TicketDAO ticketDAO = new TicketDAO();
        ticketDAO.deleteTicket(ticketId);

        System.out.println("Ticket deleted successfully!");
    }
}
