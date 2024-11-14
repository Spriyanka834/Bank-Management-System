package bank.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;

public class main_Class extends JFrame implements ActionListener {
    JButton b1, b2, b3, b4, b5, b6, b7, b8, b9; // Declare new delete button (b9)
    String pin;

    main_Class(String pin) {
        this.pin = pin;
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/atm2.jpg"));
        Image i2 = i1.getImage().getScaledInstance(1550, 830, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel l3 = new JLabel(i3);
        l3.setBounds(0, 0, 1550, 830);
        add(l3);

        JLabel label = new JLabel("Please Select Your Transaction");
        label.setBounds(430, 180, 700, 35);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("System", Font.BOLD, 28));
        l3.add(label);

        b1 = new JButton("DEPOSIT");
        b1.setForeground(Color.WHITE);
        b1.setBackground(new Color(5, 98, 237));
        b1.setBounds(410, 274, 150, 35);
        b1.addActionListener(this);
        l3.add(b1);

        b2 = new JButton("CASH WITHDRAWL");
        b2.setForeground(Color.WHITE);
        b2.setBackground(new Color(5, 98, 237));
        b2.setBounds(700, 274, 150, 35);
        b2.addActionListener(this);
        l3.add(b2);

        b3 = new JButton("FAST CASH");
        b3.setForeground(Color.WHITE);
        b3.setBackground(new Color(5, 98, 237));
        b3.setBounds(410, 318, 150, 35);
        b3.addActionListener(this);
        l3.add(b3);

        b4 = new JButton("MINI STATEMENT");
        b4.setForeground(Color.WHITE);
        b4.setBackground(new Color(5, 98, 237));
        b4.setBounds(700, 318, 150, 35);
        b4.addActionListener(this);
        l3.add(b4);

        b5 = new JButton("PIN CHANGE");
        b5.setForeground(Color.WHITE);
        b5.setBackground(new Color(5, 98, 237));
        b5.setBounds(410, 362, 150, 35);
        b5.addActionListener(this);
        l3.add(b5);

        b6 = new JButton("BALANCE ENQUIRY");
        b6.setForeground(Color.WHITE);
        b6.setBackground(new Color(5, 98, 237));
        b6.setBounds(700, 362, 150, 35);
        b6.addActionListener(this);
        l3.add(b6);

        b7 = new JButton("EXIT");
        b7.setForeground(Color.WHITE);
        b7.setBackground(new Color(5, 98, 237));
        b7.setBounds(725, 440, 100, 30);
        b7.addActionListener(this);
        l3.add(b7);

        b8 = new JButton("NEXT");
        b8.setForeground(Color.WHITE);
        b8.setBackground(new Color(5, 98, 237));
        b8.setBounds(435, 440, 100, 30);
        b8.addActionListener(this);
        l3.add(b8);

        // New Delete Account button
        b9 = new JButton("DELETE ACCOUNT");
        b9.setForeground(Color.WHITE);
        b9.setBackground(new Color(255, 0, 0)); // Red color for emphasis
        b9.setBounds(560, 500, 150, 35); // Adjust position as needed
        b9.addActionListener(this);
        l3.add(b9);

        setLayout(null);
        setSize(1550, 1080);
        setLocation(0, 0);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == b1) {
            new Deposit(pin);
            setVisible(false);
        } else if (e.getSource() == b2) {
            new Withdrawl(pin);
            setVisible(false);
        } else if (e.getSource() == b3) {
            new FastCash(pin);
            setVisible(false);
        } else if (e.getSource() == b4) {
            new mini(pin);
        } else if (e.getSource() == b5) {
            new Pin(pin);
            setVisible(false);
        } else if (e.getSource() == b6) {
            new BalanceEnquriy(pin);
            setVisible(false);
        } else if (e.getSource() == b7) {
            System.exit(0);
        } else if (e.getSource() == b8) {  // Next Button Logic
            new LoanForm(pin);  // Redirect to LoanForm page
            setVisible(false);
        } else if (e.getSource() == b9) {  // Delete Account Logic
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to delete your account? This action cannot be undone.",
                    "Confirm Deletion", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    Connn c = new Connn();

                    // Call the stored procedure to delete the account
                    String callProcedure = "{ CALL delete_account(?) }";
                    CallableStatement stmt = c.connection.prepareCall(callProcedure);
                    stmt.setString(1, pin);  // Set the pin parameter
                    stmt.executeUpdate();

                    JOptionPane.showMessageDialog(null, "Account deleted successfully.");
                    setVisible(false);
                    new main_Class(""); // Redirect to main screen or login

                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error deleting account. Please try again.");
                }
            }
        }
    }

    public static void main(String[] args) {
        new main_Class("");
    }
}
