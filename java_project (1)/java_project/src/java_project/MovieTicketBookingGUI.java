package java_project;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

class Movie {
    String name;
    int availableSeats;
    String showTimings;

    Movie(String name, int seats, String showTimings) {
        this.name = name;
        this.availableSeats = seats;
        this.showTimings = showTimings;
    }
}

class UserAccount {
    String email;
    ArrayList<String> bookings = new ArrayList<>();

    UserAccount(String email) {
        this.email = email;
    }

    void addBooking(String booking) {
        bookings.add(booking);
    }

    void clearBookings() {
        bookings.clear();
    }

    String getBookingHistory() {
        StringBuilder history = new StringBuilder();
        for (String booking : bookings) {
            history.append(booking).append("\n");
        }
        return history.toString();
    }
}

public class MovieTicketBookingGUI extends JFrame {
    private JComboBox<String> movieDropdown;
    private JTextField ticketField, emailField;
    private JTextArea showTimingsArea;
    private JComboBox<String> ticketTypeDropdown;
    private ArrayList<Movie> movies = new ArrayList<>();
    private UserAccount userAccount;

    public MovieTicketBookingGUI() {
        setTitle("Movie Ticket Booking System 🎬");
        setSize(700, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(0, 1));
        getContentPane().setBackground(new Color(240, 240, 240));

        movies.add(new Movie("Avengers: Endgame", 50, "10:00 AM, 2:00 PM, 6:00 PM"));
        movies.add(new Movie("Inception", 40, "11:00 AM, 3:00 PM, 7:00 PM"));
        movies.add(new Movie("The Dark Knight", 30, "12:00 PM, 4:00 PM, 8:00 PM"));
        movies.add(new Movie("Interstellar", 35, "1:00 PM, 5:00 PM, 9:00 PM"));
        movies.add(new Movie("Joker", 25, "2:00 PM, 6:00 PM, 10:00 PM"));

        JPanel moviePanel = new JPanel(new GridLayout(2, 1));
        moviePanel.setBorder(BorderFactory.createTitledBorder("Select Movie"));
        moviePanel.setBackground(new Color(173, 216, 230));

        JLabel selectMovieLabel = new JLabel("Select Movie:");
        selectMovieLabel.setFont(new Font("Arial", Font.BOLD, 16));
        movieDropdown = new JComboBox<>();
        updateDropdown();

        moviePanel.add(selectMovieLabel);
        moviePanel.add(movieDropdown);

        JPanel timingsPanel = new JPanel(new BorderLayout());
        timingsPanel.setBorder(BorderFactory.createTitledBorder("Show Timings"));
        timingsPanel.setBackground(new Color(245, 222, 179));

        showTimingsArea = new JTextArea(5, 20);
        showTimingsArea.setFont(new Font("Monospaced", Font.BOLD, 16));
        showTimingsArea.setBackground(new Color(255, 250, 240));
        showTimingsArea.setEditable(false);
        timingsPanel.add(new JScrollPane(showTimingsArea), BorderLayout.CENTER);

        movieDropdown.addActionListener(e -> updateShowTimings());

        JPanel bookingFormPanel = new JPanel(new GridLayout(4, 1));
        bookingFormPanel.setBorder(BorderFactory.createTitledBorder("Booking Form"));
        bookingFormPanel.setBackground(new Color(255, 182, 193));

        JLabel ticketLabel = new JLabel("Enter Number of Tickets:");
        ticketLabel.setFont(new Font("Arial", Font.BOLD, 16));
        ticketField = new JTextField();

        JLabel ticketTypeLabel = new JLabel("Select Ticket Type:");
        ticketTypeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        String[] ticketTypes = {"Adult (₹800)", "Child (₹500)", "Senior (₹600)"};
        ticketTypeDropdown = new JComboBox<>(ticketTypes);

        JLabel emailLabel = new JLabel("Enter Email for Ticket:");
        emailLabel.setFont(new Font("Arial", Font.BOLD, 16));
        emailField = new JTextField();

        bookingFormPanel.add(ticketLabel);
        bookingFormPanel.add(ticketField);
        bookingFormPanel.add(ticketTypeLabel);
        bookingFormPanel.add(ticketTypeDropdown);
        bookingFormPanel.add(emailLabel);
        bookingFormPanel.add(emailField);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 3));
        buttonPanel.setBackground(new Color(240, 240, 240));

        JButton bookButton = new JButton("Book Ticket");
        bookButton.addActionListener(e -> bookTicket());

        JButton cancelButton = new JButton("Cancel Booking");
        cancelButton.addActionListener(e -> cancelBooking());

        JButton accountButton = new JButton("My Account");
        accountButton.addActionListener(e -> viewAccount());

        buttonPanel.add(bookButton);
        buttonPanel.add(cancelButton);
        buttonPanel.add(accountButton);

        add(moviePanel);
        add(timingsPanel);
        add(bookingFormPanel);
        add(buttonPanel);

        setVisible(true);
    }

    private void bookTicket() {
        String email = emailField.getText();
        if (email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter an email!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (userAccount == null || !userAccount.email.equals(email)) {
            userAccount = new UserAccount(email);
        }
        String bookingInfo = "=======================\n"+
        		"Movie Hall: XYZ Cinemas\n" +
                "Movie: " + movieDropdown.getSelectedItem() + "\n" +
                "Tickets: " + ticketField.getText() + "\n" +
                "Type: " + ticketTypeDropdown.getSelectedItem() + "\n" +
                "Show Time: " + showTimingsArea.getText() + "\n" +
                "Email: " + email + "\n\n"+"=======================\n";
        userAccount.addBooking(bookingInfo);
        JOptionPane.showMessageDialog(this, "Booking Successful!\n\n" + bookingInfo, "Ticket Confirmation", JOptionPane.INFORMATION_MESSAGE);
    }

    private void cancelBooking() {
        if (userAccount == null || userAccount.bookings.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No bookings to cancel!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        userAccount.clearBookings();
        JOptionPane.showMessageDialog(this, "Booking Cancelled!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void viewAccount() {
        if (userAccount == null) {
            JOptionPane.showMessageDialog(this, "No account found!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        JOptionPane.showMessageDialog(this, "Email: " + userAccount.email + "\nBookings:\n" + userAccount.getBookingHistory(), "My Account", JOptionPane.INFORMATION_MESSAGE);
    }

    private void updateDropdown() {
        movieDropdown.removeAllItems();
        for (Movie movie : movies) {
            movieDropdown.addItem(movie.name + " (" + movie.availableSeats + " seats)");
        }
    }

    private void updateShowTimings() {
        int selectedIndex = movieDropdown.getSelectedIndex();
        if (selectedIndex >= 0) {
            showTimingsArea.setText(movies.get(selectedIndex).showTimings);
        }
    }

    public static void main(String[] args) {
        new MovieTicketBookingGUI();
    }
}
