package Assignment;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;

public class Report extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable playerDetailsTable;
    private DefaultTableModel tableModel;

    /**
     * Launch the application.
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

    private void loadPlayerDetails() {
        tableModel.setRowCount(0);
        PlayerSql.loadPlayerDetails(tableModel);
    }

    /**
     * Create the frame.
     */
    public Report() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 658, 429);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null); // Set layout for proper arrangement
        setContentPane(contentPane);

        String[] columnNames = {"Id", "Name", "Level", "Score1", "Score2", "Score3", "Score4", "Score5"};
        tableModel = new DefaultTableModel(columnNames, 0);
        playerDetailsTable = new JTable(tableModel);

        JScrollPane tableScrollPane = new JScrollPane(playerDetailsTable);
        tableScrollPane.setBounds(10, 10, 620, 370); // Set size & position
        contentPane.add(tableScrollPane); // Add JScrollPane to contentPane

        loadPlayerDetails(); // Fixed syntax error
    }
}
