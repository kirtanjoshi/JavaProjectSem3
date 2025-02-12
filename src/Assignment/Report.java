package Assignment;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;

/**
 * The {@code Report} class represents a graphical user interface (GUI)
 * for displaying player details in a table format. It retrieves data
 * from a database and displays it using {@code JTable}.
 * 
 * <p>This class extends {@code JFrame} and uses {@code DefaultTableModel}
 * to manage the table data.
 * 
 * @author [Your Name]
 * @version 1.0
 * @since 2025
 */
public class Report extends JFrame {

    private static final long serialVersionUID = 1L;

    /** The main container panel for the GUI components. */
    private JPanel contentPane;

    /** The table used to display player details. */
    private JTable playerDetailsTable;

    /** The table model that manages data for {@code playerDetailsTable}. */
    private DefaultTableModel tableModel;

    /**
     * Launches the application.
     * The GUI is initialized and displayed in the Event Dispatch Thread.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Report frame = new Report();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Loads player details from the database and updates the table model.
     * This method clears existing table data before loading new data.
     */
    private void loadPlayerDetails() {
        tableModel.setRowCount(0); // Clear existing data
        PlayerSql.loadPlayerDetails(tableModel); // Load new data
    }

    /**
     * Constructs the {@code Report} frame.
     * Initializes the GUI components, sets up the table model,
     * and loads player details from the database.
     */
    public Report() {
        // Set default frame properties
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 658, 429);

        // Initialize content panel
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null); // Set layout for precise positioning
        setContentPane(contentPane);

        // Define table columns
        String[] columnNames = {"Id", "Name", "Level", "Score1", "Score2", "Score3", "Score4", "Score5"};
        tableModel = new DefaultTableModel(columnNames, 0); // Initialize table model
        playerDetailsTable = new JTable(tableModel); // Create table with model

        // Add scroll pane to hold the table
        JScrollPane tableScrollPane = new JScrollPane(playerDetailsTable);
        tableScrollPane.setBounds(10, 10, 620, 370); // Set size and position
        contentPane.add(tableScrollPane); // Add to content panel

        // Load player data into the table
        loadPlayerDetails();
    }
}
