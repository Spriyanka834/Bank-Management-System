package bank.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Pin extends JFrame implements ActionListener {
    JButton b1,b2;
    JPasswordField p1,p2;
    String pin;
    Pin(String pin){
        this.pin =pin;

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/atm2.jpg"));
        Image i2 = i1.getImage().getScaledInstance(1550,830,Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel l3 = new JLabel(i3);
        l3.setBounds(0,0,1550,830);
        add(l3);

        JLabel label1 = new JLabel("CHANGE YOUR PIN");
        label1.setForeground(Color.WHITE);
        label1.setFont(new Font("System", Font.BOLD, 16));
        label1.setBounds(430,180,400,35);
        l3.add(label1);


        JLabel label2 = new JLabel("New PIN: ");
        label2.setForeground(Color.WHITE);
        label2.setFont(new Font("System", Font.BOLD, 16));
        label2.setBounds(430,220,150,35);
        l3.add(label2);

        p1 = new JPasswordField();
        p1.setBackground(new Color(180, 181, 182));
        p1.setForeground(Color.BLACK);
        p1.setBounds(600,220,180,25);
        p1.setFont(new Font("Raleway", Font.BOLD,22));
        l3.add(p1);

        JLabel label3 = new JLabel("Re-Enter New PIN: ");
        label3.setForeground(Color.WHITE);
        label3.setFont(new Font("System", Font.BOLD, 16));
        label3.setBounds(430,250,400,35);
        l3.add(label3);

        p2 = new JPasswordField();
        p2.setBackground(new Color(180, 181, 182));
        p2.setForeground(Color.BLACK);
        p2.setBounds(600,255,180,25);
        p2.setFont(new Font("Raleway", Font.BOLD,22));
        l3.add(p2);

        b1 = new JButton("CHANGE");
        b1.setBounds(735,362,100,30);
        b1.setBackground(new Color(5, 98, 237));
        b1.setForeground(Color.WHITE);
        b1.addActionListener(this);
        l3.add(b1);

        b2 = new JButton("BACK");
        b2.setBounds(735,406,100,30);
        b2.setBackground(new Color(5, 98, 237));
        b2.setForeground(Color.WHITE);
        b2.addActionListener(this);
        l3.add(b2);



        setSize(1550,1080);
        setLayout(null);
        setLocation(0,0);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            String pin1 = p1.getText();
            String pin2 = p2.getText();

            if (e.getSource() == b2) {  // Back Button Logic
                new main_Class(pin);  // Return to main_Class page
                setVisible(false);
                return;  // Exit method to bypass PIN validation
            }

            // Validate if the entered PINs match
            if (!pin1.equals(pin2)) {
                JOptionPane.showMessageDialog(null, "Entered PIN does not match");
                return;
            }

            // Validate if the PIN is 4 digits long
            if (pin1.length() != 4) {
                JOptionPane.showMessageDialog(null, "PIN must be exactly 4 digits");
                return;
            }

            // Check if the PIN fields are empty
            if (pin1.equals("")) {
                JOptionPane.showMessageDialog(null, "Enter New PIN");
                return;
            }
            if (pin2.equals("")) {
                JOptionPane.showMessageDialog(null, "Re-Enter New PIN");
                return;
            }

            // Proceed to update the PIN in the database
            if (e.getSource() == b1) {  // Change Button Logic
                Connn c = new Connn();

                String q1 = "UPDATE bank SET pin = '" + pin1 + "' WHERE pin = '" + pin + "'";
                String q2 = "UPDATE login SET pin = '" + pin1 + "' WHERE pin = '" + pin + "'";
                String q3 = "UPDATE signupthree SET pin = '" + pin1 + "' WHERE pin = '" + pin + "'";

                c.statement.executeUpdate(q1);
                c.statement.executeUpdate(q2);
                c.statement.executeUpdate(q3);

                JOptionPane.showMessageDialog(null, "PIN changed successfully");
                setVisible(false);
                new main_Class(pin1);  // Pass updated PIN to main_Class
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Pin("");
    }
}
