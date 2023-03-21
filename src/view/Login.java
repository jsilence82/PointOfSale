package view;

import controller.UserSessionController;
import model.database.UserDao;
import view.custom.RoundedButton;
import view.custom.RoundedPanel;
import view.custom.SystemColors;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class Login extends JFrame {

    private final JTextField userID;
    private JPasswordField passwordCheck;

    public Login(SelectScreen startProgram) {
        startProgram.addComponentListener(new ComponentAdapter() {
            public void componentMoved(ComponentEvent e) {
                setLocationRelativeTo(startProgram);
            }
        });
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setAlwaysOnTop(true);
        setBounds(100, 100, 520, 320);
        setLocationRelativeTo(startProgram);

        RoundedPanel contentPane = new RoundedPanel();
        contentPane.setBackground(SystemColors.BACKGROUND.getColorCode());
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setOpaque(false);
        setContentPane(contentPane);

        RoundedPanel mainPanel = new RoundedPanel();
        mainPanel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        mainPanel.setOpaque(false);

        JLabel logo = new JLabel("");
        logo.setIcon(new ImageIcon("src\\view\\images\\raum5_2.png"));
        userID = new JTextField();
        userID.setColumns(5);
        JLabel mitarbeiterLabel = new JLabel("Mitarbeiter ID:");
        JLabel passwordLabel = new JLabel("Passwort:");

        JButton btnLogin = new RoundedButton("Login");
        btnLogin.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        btnLogin.setOpaque(false);
        btnLogin.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnLogin.setForeground(SystemColors.SCHRIFTHELL.getColorCode());
        btnLogin.setBackground(SystemColors.BUTTONS.getColorCode());
        btnLogin.addActionListener(e -> {
            try {
                UserDao authorize = new UserDao();
                int username = Integer.parseInt(userID.getText());
                String password = String.valueOf(passwordCheck.getPassword());
                if (authorize.authorizeUser(username, password)) {
                    dispose();
                    new UserSessionController(username, startProgram);
                } else {
                    JOptionPane.showMessageDialog(this, "Username/Passwort ist falsch", "Error", JOptionPane.ERROR_MESSAGE);
                    userID.setText("");
                    passwordCheck.setText("");
                }
            } catch (NumberFormatException d) {
                JOptionPane.showMessageDialog(this, "Username ist falsch", "Error", JOptionPane.ERROR_MESSAGE);
                userID.setText("");
                passwordCheck.setText("");
            }
        });

        JLabel cafeteriaLabel = new JLabel("Cafeteria 123");
        cafeteriaLabel.setFont(new Font("Candara", Font.BOLD, 30));

        JLabel loginLabel = new JLabel("Login");
        loginLabel.setFont(new Font("Tahoma", Font.BOLD, 18));

        passwordCheck = new JPasswordField();
        passwordCheck.setColumns(5);

        JButton btnReset = new RoundedButton("Reset");
        btnReset.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        btnReset.setOpaque(false);
        btnReset.addActionListener(e -> {
            userID.setText("");
            passwordCheck.setText("");
        });

        RoundedButton btnExit = new RoundedButton("Power Off");
        btnExit.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        btnExit.setOpaque(false);
        btnExit.addActionListener(e -> {
            UserDao.closeConnection();
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
                gl_contentPane.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addGap(25)
                                .addComponent(cafeteriaLabel, GroupLayout.PREFERRED_SIZE, 194, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(ComponentPlacement.RELATED, 128, Short.MAX_VALUE)
                                .addComponent(logo))
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addGap(178)
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
                                        .addGroup(gl_contentPane.createSequentialGroup()
                                                .addComponent(btnLogin, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(btnReset, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE))
                                        .addComponent(userID, GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                                        .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
                                                .addComponent(passwordLabel)
                                                .addComponent(passwordCheck, GroupLayout.PREFERRED_SIZE, 160, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(gl_contentPane.createSequentialGroup()
                                                .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
                                                        .addComponent(mitarbeiterLabel)
                                                        .addComponent(loginLabel))
                                                .addPreferredGap(ComponentPlacement.RELATED)))
                                .addGap(176))
                        .addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
                                .addContainerGap(427, Short.MAX_VALUE)
                                .addComponent(btnExit, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );
        gl_contentPane.setVerticalGroup(
                gl_contentPane.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
                                        .addComponent(logo)
                                        .addGroup(gl_contentPane.createSequentialGroup()
                                                .addGap(19)
                                                .addComponent(cafeteriaLabel)))
                                .addGap(18)
                                .addComponent(loginLabel)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(mitarbeiterLabel)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(userID, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(ComponentPlacement.UNRELATED)
                                .addComponent(passwordLabel)
                                .addGap(3)
                                .addComponent(passwordCheck, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
                                .addGap(18)
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
                                        .addComponent(btnLogin, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnReset, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                                .addComponent(btnExit, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );
        contentPane.setLayout(gl_contentPane);
        contentPane.getRootPane().setDefaultButton(btnLogin);
    }
}
