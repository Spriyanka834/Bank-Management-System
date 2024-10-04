package bank.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LoanForm extends JFrame implements ActionListener {
    JTextField loanAmountField, loanTenureField, interestRateField;
    JComboBox<String> loanTypeBox;
    JButton submitButton;
    String pin;

    Connn dbConnection;

    LoanForm(String pin) {
        this.pin = pin;

        dbConnection = new Connn();

        setTitle("Loan Application Form");
        setLayout(null);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/atm2.jpg"));
        Image i2 = i1.getImage().getScaledInstance(600, 500, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel background = new JLabel(i3);
        background.setBounds(0, 0, 600, 500);
        add(background);

        JLabel heading = new JLabel("Loan Application");
        heading.setFont(new Font("System", Font.BOLD, 28));
        heading.setForeground(Color.WHITE);
        heading.setBounds(200, 20, 300, 40);
        background.add(heading);

        JLabel l1 = new JLabel("Enter Loan Amount:");
        l1.setFont(new Font("System", Font.BOLD, 16));
        l1.setForeground(Color.WHITE);
        l1.setBounds(100, 100, 200, 30);
        background.add(l1);

        loanAmountField = new JTextField();
        loanAmountField.setBounds(300, 100, 150, 30);
        background.add(loanAmountField);

        JLabel l2 = new JLabel("Enter Loan Tenure:");
        l2.setFont(new Font("System", Font.BOLD, 16));
        l2.setForeground(Color.WHITE);
        l2.setBounds(100, 150, 200, 30);
        background.add(l2);

        loanTenureField = new JTextField();
        loanTenureField.setBounds(300, 150, 150, 30);
        background.add(loanTenureField);

        JLabel l2Years = new JLabel("(In Years)");
        l2Years.setFont(new Font("System", Font.PLAIN, 12));
        l2Years.setForeground(Color.WHITE);
        l2Years.setBounds(130, 170, 150, 20);
        background.add(l2Years);

        JLabel l3 = new JLabel("Enter Interest Rate (%):");
        l3.setFont(new Font("System", Font.BOLD, 16));
        l3.setForeground(Color.WHITE);
        l3.setBounds(100, 210, 200, 30);
        background.add(l3);

        interestRateField = new JTextField();
        interestRateField.setBounds(300, 210, 150, 30);
        background.add(interestRateField);

        JLabel l4 = new JLabel("Select Loan Type:");
        l4.setFont(new Font("System", Font.BOLD, 16));
        l4.setForeground(Color.WHITE);
        l4.setBounds(100, 260, 200, 30);
        background.add(l4);

        String[] loanTypes = {"Personal Loan", "Home Loan", "Car Loan", "Education Loan"};
        loanTypeBox = new JComboBox<>(loanTypes);
        loanTypeBox.setBounds(300, 260, 150, 30);
        background.add(loanTypeBox);

        submitButton = new JButton("Submit");
        submitButton.setBounds(200, 350, 150, 35);
        submitButton.setBackground(new Color(5, 98, 237));
        submitButton.setForeground(Color.WHITE);
        submitButton.addActionListener(this);
        background.add(submitButton);

        setSize(600, 500);
        setLocation(500, 200);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton) {
            String loanAmount = loanAmountField.getText();
            String loanTenure = loanTenureField.getText();
            String interestRate = interestRateField.getText();
            String loanType = (String) loanTypeBox.getSelectedItem();

            saveLoanApplication(pin, loanAmount, loanTenure, interestRate, loanType);

            new LoanDetails(pin, loanAmount, loanTenure, interestRate, loanType);
            setVisible(false);
        }
    }

    private void saveLoanApplication(String pin, String loanAmount, String loanTenure, String interestRate, String loanType) {
        PreparedStatement ps = null;

        try {
            String sql = "INSERT INTO loan_applications (pin, loan_amount, loan_tenure, interest_rate, loan_type) VALUES (?, ?, ?, ?, ?)";
            ps = dbConnection.connection.prepareStatement(sql);
            ps.setString(1, pin);
            ps.setDouble(2, Double.parseDouble(loanAmount));
            ps.setInt(3, Integer.parseInt(loanTenure));
            ps.setDouble(4, Double.parseDouble(interestRate));
            ps.setString(5, loanType);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Loan application submitted successfully!");
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error submitting loan application: " + ex.getMessage());
        } finally {
            try {
                if (ps != null) ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new LoanForm("");
    }
}
