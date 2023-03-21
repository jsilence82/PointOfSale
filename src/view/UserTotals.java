package view;

import controller.UserSessionController;
import view.custom.RoundedButton;
import view.custom.RoundedPanel;
import view.custom.SystemColors;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class UserTotals extends JDialog {

    private JTable allDayOrders;
    private DefaultTableModel ordersModel, detailsModel;
    private double totalSalesEuro;
    private int totalEntries;
    private JLabel totalOrders;

    public UserTotals(JFrame selectScreen) {
        RoundedPanel main = new RoundedPanel();
        main.setBackground(SystemColors.BACKGROUND.getColorCode());

        int mitarbeiterID = UserSessionController.getUserId();
        ResultSet tagesUmsatz = UserSessionController.getUsersDailyOrders();

        selectScreen.addComponentListener(new ComponentAdapter() {
            public void componentMoved(ComponentEvent e) {
                setLocationRelativeTo(selectScreen);
            }
        });
        setUndecorated(true);
        JPanel titleBar = new JPanel();
        titleBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        JLabel titleLabel = new JLabel("Tageusumsatz");
        titleBar.add(titleLabel);
        getContentPane().add(titleBar, BorderLayout.NORTH);
        getContentPane().setBackground(SystemColors.BACKGROUND.getColorCode());

        setVisible(true);
        setAlwaysOnTop(true);
        setBounds(100, 100, 1000, 600);
        setLocationRelativeTo(selectScreen);
        setContentPane(main);

        JLayeredPane layeredPane = selectScreen.getRootPane().getLayeredPane();
        JPanel blurPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(255, 255, 255, 200));
                g2.fillRect(0, 0, getWidth(), getHeight());
                super.paintComponent(g);
            }
        };
        blurPanel.setBounds(0, 0, selectScreen.getWidth(), selectScreen.getHeight());
        blurPanel.setOpaque(false);
        layeredPane.add(blurPanel, JLayeredPane.POPUP_LAYER);

        try {
            ordersModel = new DefaultTableModel();
            allDayOrders = new JTable(ordersModel);
            ordersModel.addColumn("Bestellung ID");
            ordersModel.addColumn("Gesamtpreis");

            while (tagesUmsatz.next()) {
                Object[] row = {tagesUmsatz.getString("BestellungID"), tagesUmsatz.getBigDecimal("betrag")};
                totalSalesEuro += tagesUmsatz.getDouble("betrag");
                ordersModel.addRow(row);
            }

            detailsModel = new DefaultTableModel();
            JTable bestellungDetails = new JTable(detailsModel);
            detailsModel.addColumn("Waren");
            detailsModel.addColumn("Menge");
            detailsModel.addColumn("Preis");

            allDayOrders.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    detailsModel.setRowCount(0);
                    int row = allDayOrders.getSelectedRow();
                    String bestellungId = (String) ordersModel.getValueAt(row, 0);

                    ResultSet bestellung = UserSessionController.getOrderInfo(bestellungId);
                    try {
                        while (bestellung.next()) {
                            Object[] row2 = {bestellung.getString("Item"), bestellung.getInt("Menge"), bestellung.getDouble("Preis")};
                            detailsModel.addRow(row2);
                            totalEntries++;
                        }
                        totalOrders.setText("Bestellungen: " + totalEntries);
                        totalEntries = 0;
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            });

            allDayOrders.setBackground(SystemColors.WARENCONTAINER.getColorCode());
            bestellungDetails.setBackground(SystemColors.WARENCONTAINER.getColorCode());

            JScrollPane scrollPane1 = new JScrollPane(allDayOrders);
            JScrollPane scrollPane2 = new JScrollPane(bestellungDetails);
            JPanel panel1 = new JPanel(new BorderLayout());
            panel1.setBackground(SystemColors.BACKGROUND.getColorCode());
            panel1.add(scrollPane1, BorderLayout.SOUTH);
            JLabel gesamtUmsatz = new JLabel();
            gesamtUmsatz.setFont(new Font("Simple", Font.PLAIN, 18));
            gesamtUmsatz.setText("Gesamt: " + totalSalesEuro + "€");
            JPanel gesamtPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            gesamtPanel.setBackground(SystemColors.BACKGROUND.getColorCode());
            gesamtPanel.add(gesamtUmsatz);
            panel1.add(gesamtPanel, BorderLayout.NORTH);

            JPanel panel2 = new JPanel(new BorderLayout());
            panel2.setBackground(SystemColors.BACKGROUND.getColorCode());
            totalOrders = new JLabel();
            totalOrders.setFont(new Font("Simple", Font.PLAIN, 18));
            totalOrders.setText("Bestellungen: ");
            JPanel total = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            total.setBackground(SystemColors.BACKGROUND.getColorCode());
            total.add(totalOrders);
            panel2.add(total, BorderLayout.NORTH);
            panel2.add(scrollPane2, BorderLayout.SOUTH);

            JPanel panel3 = new JPanel();
            panel3.setBackground(SystemColors.BACKGROUND.getColorCode());
            JLabel label = new JLabel("Tageusmsatz für Mitarbeiter " + mitarbeiterID + " am " + LocalDate.now());
            label.setFont(new Font("Simple", Font.BOLD, 30));
            panel3.add(label);
            RoundedButton close = new RoundedButton("Close");
            close.setBackground(SystemColors.BUTTONS.getColorCode());
            close.setForeground(SystemColors.SCHRIFTHELL.getColorCode());
            close.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
            close.setOpaque(false);
            close.addActionListener(e-> {
                blurPanel.setVisible(false);
                this.dispose();
            });

            JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            panel.setBackground(SystemColors.BACKGROUND.getColorCode());
            panel.add(close);
            main.add(panel3, "North");
            main.add(panel1, "West");
            main.add(panel2, "East");
            main.add(panel, "South");

            setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            setVisible(true);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }
}