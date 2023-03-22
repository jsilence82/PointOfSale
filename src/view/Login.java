package view;

import controller.UserSessionController;
import model.database.UserDao;
import view.custom.RoundedButton;
import view.custom.RoundedPanel;
import view.custom.SystemColors;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
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
        setAlwaysOnTop(true);
        setBounds(0, 0, 480, 360);
        setLocationRelativeTo(startProgram);

        RoundedPanel contentPane = new RoundedPanel();
        contentPane.setBackground(SystemColors.BACKGROUND.getColorCode());
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setOpaque(false);
        contentPane.setLayout(new BorderLayout());
        setContentPane(contentPane);

        JLabel logo = new JLabel("");
        logo.setIcon(new ImageIcon("src\\view\\images\\raum5_2.png"));
        logo.setHorizontalAlignment(JLabel.RIGHT);
        userID = new JTextField();
        userID.setColumns(5);
        userID.setHorizontalAlignment(SwingConstants.CENTER);
        userID.setFont(new Font("Tahoma", Font.PLAIN, 16));
        JLabel mitarbeiterLabel = new JLabel("Mitarbeiter ID:");
        mitarbeiterLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mitarbeiterLabel.setFont(new Font("Tahoma", Font.PLAIN,20));
        JLabel passwordLabel = new JLabel("Passwort:");
        passwordLabel.setHorizontalAlignment(SwingConstants.CENTER);
        passwordLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));

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
        loginLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
        loginLabel.setHorizontalAlignment(SwingConstants.CENTER);

        passwordCheck = new JPasswordField();
        passwordCheck.setColumns(5);
        passwordCheck.setHorizontalAlignment(SwingConstants.CENTER);
        passwordCheck.setFont(new Font("Arial", Font.PLAIN, 16));

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

        JPanel titlePanel = new JPanel(new GridLayout(1,2));
        titlePanel.setBackground(SystemColors.BACKGROUND.getColorCode());
        titlePanel.add(cafeteriaLabel, 0);
        titlePanel.add(logo, 1);
        contentPane.add(titlePanel, BorderLayout.NORTH);

        JPanel blankEasePanel = new JPanel();
        blankEasePanel.setBackground(SystemColors.BACKGROUND.getColorCode());
        blankEasePanel.setPreferredSize(new Dimension(20,0));
        contentPane.add(blankEasePanel, BorderLayout.EAST);

        JPanel blankWest = new JPanel();
        blankWest.setBackground(SystemColors.BACKGROUND.getColorCode());
        blankWest.setPreferredSize(new Dimension(20,0));
        contentPane.add(blankWest, BorderLayout.WEST);

        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        southPanel.setBackground(SystemColors.BACKGROUND.getColorCode());
        southPanel.add(btnExit);
        contentPane.add(southPanel, BorderLayout.SOUTH);

        JPanel centerContentPanel = new JPanel(new BorderLayout());
        centerContentPanel.setBackground(SystemColors.BACKGROUND.getColorCode());
        centerContentPanel.add(loginLabel, BorderLayout.NORTH);

        JPanel inputFields = new JPanel(new GridLayout(4,1));
        inputFields.setBackground(SystemColors.BACKGROUND.getColorCode());
        inputFields.add(mitarbeiterLabel, 0);

        JPanel userInputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        userInputPanel.setBackground(SystemColors.BACKGROUND.getColorCode());
        userInputPanel.add(userID);
        inputFields.add(userInputPanel, 1);
        inputFields.add(passwordLabel, 2);

        JPanel passwordInputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        passwordInputPanel.setBackground(SystemColors.BACKGROUND.getColorCode());
        passwordInputPanel.add(passwordCheck);
        inputFields.add(passwordInputPanel, 3);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(SystemColors.BACKGROUND.getColorCode());
        buttonPanel.setPreferredSize(new Dimension(50,50));
        buttonPanel.add(btnLogin);
        buttonPanel.add(btnReset);
        centerContentPanel.add(buttonPanel, BorderLayout.SOUTH);
        centerContentPanel.add(inputFields, BorderLayout.CENTER);
        contentPane.add(centerContentPanel, BorderLayout.CENTER);

        contentPane.getRootPane().setDefaultButton(btnLogin);
        setVisible(true);
    }
}
