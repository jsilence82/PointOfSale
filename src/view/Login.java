package view;

import controller.DBController;
import controller.UserSessionController;
import model.database.UserDao;
import view.custom.RoundedButton;
import view.custom.RoundedPanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class Login extends JFrame {

    private final JTextField mitarbeiterID;
    private JPasswordField passwordCheck;

    public Login(SelectScreen startProgram) {
        startProgram.addComponentListener(new ComponentAdapter() {
            public void componentMoved(ComponentEvent e) {
                setLocationRelativeTo(startProgram);
            }
        });
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setAlwaysOnTop(true);
        setBounds(100, 100, 540, 390);
        setLocationRelativeTo(startProgram);

        RoundedPanel contentPane = new RoundedPanel();
        contentPane.setBackground(SystemColors.BACKGROUND.getColorCode());
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setOpaque(false);
        setContentPane(contentPane);

        RoundedPanel mainPanel = new RoundedPanel();
        mainPanel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        mainPanel.setOpaque(false);


        JLabel lblNewLabel = new JLabel("");
        lblNewLabel.setIcon(new ImageIcon("src\\view\\images\\raum5_2.png"));
        mitarbeiterID = new JTextField();
        mitarbeiterID.setColumns(10);
        JLabel lblNewLabel_1 = new JLabel("Mitarbeiter ID:");
        JLabel lblNewLabel_2 = new JLabel("Passwort:");

        JButton btnLogin = new RoundedButton("Login");
        btnLogin.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        btnLogin.setOpaque(false);
        btnLogin.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnLogin.setForeground(SystemColors.SCHRIFTHELL.getColorCode());
        btnLogin.setBackground(SystemColors.BUTTONS.getColorCode());
        btnLogin.addActionListener(e -> {
            try {
                UserDao authorize = new UserDao();
                int username = Integer.parseInt(mitarbeiterID.getText());
                String password = String.valueOf(passwordCheck.getPassword());
                if (authorize.authenticateUser(username, password)) {
                    dispose();
                    new UserSessionController(username, startProgram);
                } else {
                    JOptionPane.showMessageDialog(this, "Passwort ist falsch", "Error", JOptionPane.ERROR_MESSAGE);
                    mitarbeiterID.setText("");
                    passwordCheck.setText("");
                }
            } catch (NumberFormatException d) {
                JOptionPane.showMessageDialog(this, "Username ist falsch", "Error", JOptionPane.ERROR_MESSAGE);
                mitarbeiterID.setText("");
                passwordCheck.setText("");
            }
        });

        JLabel lblNewLabel_3 = new JLabel("Cafeteria 123");
        lblNewLabel_3.setFont(new Font("Candara", Font.BOLD, 30));

        JLabel lblNewLabel_4 = new JLabel("Login");
        lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 18));

        passwordCheck = new JPasswordField();
        passwordCheck.setColumns(10);

        JButton btnReset = new RoundedButton("Reset");
        btnReset.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        btnReset.setOpaque(false);
        btnReset.addActionListener(e -> {
            mitarbeiterID.setText("");
            passwordCheck.setText("");
        });

        JButton btnExit = new RoundedButton("Power Off");
        btnExit.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        btnExit.setOpaque(false);
        btnExit.addActionListener(e -> {
            DBController.closeConnection();
            System.exit(0);
        });
        btnExit.setForeground(Color.BLACK);
        btnExit.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnExit.setBackground(SystemColors.XBUTTON.getColorCode());


        btnReset.setForeground(Color.WHITE);
        btnReset.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnReset.setBackground(SystemColors.BUTTONS.getColorCode());
        GroupLayout gl_contentPane = new GroupLayout(contentPane);
        gl_contentPane.setHorizontalGroup(
                gl_contentPane.createParallelGroup(Alignment.TRAILING)
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addGap(25)
                                .addComponent(lblNewLabel_3, GroupLayout.PREFERRED_SIZE, 194, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(ComponentPlacement.RELATED, 128, Short.MAX_VALUE)
                                .addComponent(lblNewLabel))
                        .addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
                                .addGap(178)
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
                                        .addGroup(gl_contentPane.createSequentialGroup()
                                                .addComponent(btnLogin, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(btnReset, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE))
                                        .addComponent(mitarbeiterID, GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE)
                                        .addGroup(Alignment.TRAILING, gl_contentPane.createParallelGroup(Alignment.LEADING)
                                                .addComponent(lblNewLabel_2)
                                                .addComponent(passwordCheck, GroupLayout.PREFERRED_SIZE, 158, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(gl_contentPane.createSequentialGroup()
                                                .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
                                                        .addComponent(lblNewLabel_1)
                                                        .addComponent(lblNewLabel_4))
                                                .addPreferredGap(ComponentPlacement.RELATED, 88, GroupLayout.PREFERRED_SIZE)))
                                .addGap(176))

        );
        gl_contentPane.setVerticalGroup(
                gl_contentPane.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
                                        .addComponent(lblNewLabel)
                                        .addGroup(gl_contentPane.createSequentialGroup()
                                                .addGap(19)
                                                .addComponent(lblNewLabel_3)))
                                .addGap(18)
                                .addComponent(lblNewLabel_4)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(lblNewLabel_1)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(mitarbeiterID, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(lblNewLabel_2)
                                .addGap(3)
                                .addComponent(passwordCheck, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
                                .addGap(18)
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
                                        .addComponent(btnLogin, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnReset, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(83, Short.MAX_VALUE))


        );
        contentPane.setLayout(gl_contentPane);
    }
}
