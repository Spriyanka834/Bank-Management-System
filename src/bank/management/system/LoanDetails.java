package bank.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LoanDetails extends JFrame implements ActionListener {
    JButton exitButton;
    Connn dbConnection;

    LoanDetails(String pin, String loanAmount, String loanTenure, String interestRate, String loanType) {
        dbConnection = new Connn(); // Initialize the database connection

        setTitle("Loan Details Confirmation");
        setLayout(null);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/atm2.jpg"));
        Image i2 = i1.getImage().getScaledInstance(600, 600, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel background = new JLabel(i3);
        background.setBounds(0, 0, 600, 600);
        add(background);

        double principal = Double.parseDouble(loanAmount);
        double tenureYears = Double.parseDouble(loanTenure);
        double interestRatePercent = Double.parseDouble(interestRate);

        double totalPayable = principal * (1 + (interestRatePercent * tenureYears) / 100);
        double monthlyPayable = totalPayable / (tenureYears * 12);
        double overduePayable = totalPayable / 2;  // Modify this calculation as needed

        DecimalFormat df = new DecimalFormat("0.00");

        JLabel l1 = new JLabel("Loan Details");
        l1.setFont(new Font("System", Font.BOLD, 22));
        l1.setForeground(Color.WHITE);
        l1.setBounds(200, 30, 200, 30);
        background.add(l1);

        JLabel l2 = new JLabel("Loan Amount: Rs. " + loanAmount);
        l2.setFont(new Font("System", Font.PLAIN, 16));
        l2.setForeground(Color.WHITE);
        l2.setBounds(150, 100, 300, 30);
        background.add(l2);

        JLabel l3 = new JLabel("Loan Tenure (Years): " + loanTenure);
        l3.setFont(new Font("System", Font.PLAIN, 16));
        l3.setForeground(Color.WHITE);
        l3.setBounds(150, 150, 300, 30);
        background.add(l3);

        JLabel l4 = new JLabel("Interest Rate: " + interestRate + "%");
        l4.setFont(new Font("System", Font.PLAIN, 16));
        l4.setForeground(Color.WHITE);
        l4.setBounds(150, 200, 300, 30);
        background.add(l4);

        JLabel l5 = new JLabel("Loan Type: " + loanType);
        l5.setFont(new Font("System", Font.PLAIN, 16));
        l5.setForeground(Color.WHITE);
        l5.setBounds(150, 250, 300, 30);
        background.add(l5);

        JLabel l6 = new JLabel("Total Payable Amount: Rs. " + df.format(totalPayable));
        l6.setFont(new Font("System", Font.PLAIN, 16));
        l6.setForeground(Color.WHITE);
        l6.setBounds(150, 300, 300, 30);
        background.add(l6);

        JLabel l7 = new JLabel("Monthly Payable Amount: Rs. " + df.format(monthlyPayable));
        l7.setFont(new Font("System", Font.PLAIN, 16));
        l7.setForeground(Color.WHITE);
        l7.setBounds(150, 350, 300, 30);
        background.add(l7);

        JLabel l8 = new JLabel("Overdue Payable Amount: Rs. " + df.format(overduePayable));
        l8.setFont(new Font("System", Font.PLAIN, 16));
        l8.setForeground(Color.WHITE);
        l8.setBounds(150, 400, 300, 30);
        background.add(l8);

        exitButton = new JButton("Exit");
        exitButton.setBounds(450, 500, 100, 30);
        exitButton.setBackground(new Color(5, 98, 237));
        exitButton.setForeground(Color.WHITE);
        exitButton.addActionListener(this);
        background.add(exitButton);

        saveLoanDetails(pin, loanAmount, loanTenure, interestRate, loanType, totalPayable, monthlyPayable, overduePayable);

        setSize(600, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void saveLoanDetails(String pin, String loanAmount, String loanTenure, String interestRate, String loanType, double totalPayable, double monthlyPayable, double overduePayable) {
        PreparedStatement ps = null;

        try {

            String sql = "INSERT INTO loan_applications (pin, loan_amount, loan_tenure, interest_rate, loan_type, total_payable, monthly_payable, overdue_payable) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            ps = dbConnection.connection.prepareStatement(sql);
            ps.setString(1, pin);
            ps.setDouble(2, Double.parseDouble(loanAmount));
            ps.setInt(3, Integer.parseInt(loanTenure));
            ps.setDouble(4, Double.parseDouble(interestRate));
            ps.setString(5, loanType);
            ps.setDouble(6, totalPayable); // Insert the total payable amount
            ps.setDouble(7, monthlyPayable); // Insert the monthly payable amount
            ps.setDouble(8, overduePayable); // Insert the overdue payable amount
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error saving loan details: " + ex.getMessage());
        } finally {

            try {
                if (ps != null) ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == exitButton) {
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new LoanDetails("", "500000", "5", "7.5", "Home Loan");
    }
}
