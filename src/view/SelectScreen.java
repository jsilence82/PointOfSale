package view;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import controller.ItemsDBController;
import controller.UserSessionController;
import controller.ShoppingCart;
import model.items.Item;
import view.custom.PanelFadeTransition;
import view.custom.RoundedButton;
import view.custom.RoundedPanel;
import view.custom.SystemColors;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class SelectScreen extends JFrame {

    private final PanelFadeTransition contentPane;
    private final RoundedButton logout;
    private JPanel cartPanel;
    private RoundedPanel userInfoPanel;
    private JPanel centerButtonPanel;
    private JPanel tally;
    private JLabel cartTotal;
    private ItemsDBController menu;
    private List<Item> foodList;
    private List<Item> drinkList;
    private ShoppingCart shoppingCart;
    private NumberFormat euroFormat;

    public SelectScreen() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 1680, 1080);
         setUndecorated(true);
         setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(true);
        setLocationRelativeTo(null);

        JPanel screenPane = new JPanel(new BorderLayout());
        screenPane.setBackground(SystemColors.BACKGROUND.getColorCode());
        setContentPane(screenPane);

        contentPane = new PanelFadeTransition();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new GridBagLayout());
        screenPane.add(contentPane, BorderLayout.CENTER);

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        titlePanel.setBackground(SystemColors.BACKGROUND.getColorCode());
        titlePanel.setPreferredSize(new Dimension(100, 50));
        JSeparator titleSeparator = new JSeparator(JSeparator.VERTICAL);
        titleSeparator.setForeground(SystemColors.BACKGROUND.getColorCode());
        titleSeparator.setBackground(SystemColors.BACKGROUND.getColorCode());
        titleSeparator.setPreferredSize(new Dimension(1, 15));

        logout = new RoundedButton("Ausloggen");
        logout.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        logout.setOpaque(false);
        logout.setForeground(SystemColors.SCHRIFTHELL.getColorCode());
        logout.setBackground(SystemColors.BUTTONS.getColorCode());
        logout.setVisible(false);
        logout.addActionListener(e -> logout());
        JPanel logoutPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        logoutPanel.setBackground(SystemColors.BACKGROUND.getColorCode());
        logoutPanel.add(logout);
        titlePanel.add(logoutPanel, BorderLayout.EAST);
        screenPane.add(titlePanel, BorderLayout.NORTH);

        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        logoPanel.setBackground(SystemColors.BACKGROUND.getColorCode());
        JLabel logo = new JLabel("");
        logo.setIcon(new ImageIcon("resources/raum5_2.png"));
        logoPanel.add(logo);
        screenPane.add(logoPanel, BorderLayout.SOUTH);

        JSeparator horizontalSeparator_east = new JSeparator(JSeparator.HORIZONTAL);
        horizontalSeparator_east.setForeground(SystemColors.BACKGROUND.getColorCode());
        horizontalSeparator_east.setBackground(SystemColors.BACKGROUND.getColorCode());
        horizontalSeparator_east.setPreferredSize(new Dimension(20, 500));
        screenPane.add(horizontalSeparator_east, BorderLayout.EAST);

        JSeparator horizontalSeparator_west = new JSeparator(JSeparator.HORIZONTAL);
        horizontalSeparator_west.setForeground(SystemColors.BACKGROUND.getColorCode());
        horizontalSeparator_west.setBackground(SystemColors.BACKGROUND.getColorCode());
        horizontalSeparator_west.setPreferredSize(new Dimension(20, 500));
        screenPane.add(horizontalSeparator_west, BorderLayout.WEST);

        setVisible(true);
    }

    public void startScreen() {
        menu = new ItemsDBController();
        foodList = menu.getFoodList();
        drinkList = menu.getDrinksList();
        shoppingCart = new ShoppingCart();
        euroFormat = NumberFormat.getCurrencyInstance(Locale.GERMANY);
        initializeScreen();
        contentPane.fade(!contentPane.isVisible());
    }

    private void viewUserTotals() {
        new UserTotals(this);
    }

    private void logout() {
        if (shoppingCart.getCart().isEmpty()) {
            dispose();
        } else {
            int confirmed = JOptionPane.showConfirmDialog(this,
                    "Warenkorb ist nicht leer. Wirklich ausloggen?", "Item in Warenkorb",
                    JOptionPane.YES_NO_OPTION);
            if (confirmed == JOptionPane.YES_OPTION) {
                dispose();
            }
        }
    }

    public void addToTally() {
        tally.removeAll();
        HashMap<Item, Integer> cart = shoppingCart.getCart();
        for (Map.Entry<Item, Integer> entry : cart.entrySet()) {
            Item item = entry.getKey();
            Integer quantity = entry.getValue();
            JPanel rowPanel = new JPanel(new GridLayout(1, 3, 75, 10));
            rowPanel.setBackground(SystemColors.BLACKBG.getColorCode());
            JLabel quantityLabel = new JLabel(quantity + "x");
            quantityLabel.setForeground(Color.WHITE);
            quantityLabel.setPreferredSize(new Dimension(20, 20));
            quantityLabel.setHorizontalAlignment(SwingConstants.LEFT);
            JLabel desc = new JLabel(item.getDescription());
            desc.setForeground(Color.WHITE);
            desc.setPreferredSize(new Dimension(75, 20));
            JLabel price = new JLabel(euroFormat.format(item.getPrice() * quantity));
            price.setForeground(Color.WHITE);
            price.setPreferredSize(new Dimension(20, 20));
            JButton delete = new RoundedButton("X");
            delete.setBackground(SystemColors.XBUTTON.getColorCode());
            delete.setHorizontalAlignment(SwingConstants.RIGHT);
            delete.setForeground(Color.WHITE);
            delete.setPreferredSize(new Dimension(3, 5));
            delete.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            delete.setOpaque(false);
            delete.addActionListener(e -> {
                shoppingCart.removeItem(item);
                addToTally();
            });

            rowPanel.add(quantityLabel);
            rowPanel.add(desc);
            rowPanel.add(price);
            rowPanel.add(delete);
            tally.add(rowPanel);
        }
        tally.revalidate();
        tally.repaint();
        updateTotals();
    }

    private void updateTotals() {
        cartTotal.setText(shoppingCart.getFormattedTotal());
    }

    private void resetCart() {
        if (!shoppingCart.getCart().isEmpty()) {
            shoppingCart.clearCart();
            updateScreen();
        }
    }

    private void updateScreen() {
        this.shoppingCart = new ShoppingCart();
        contentPane.removeAll();
        contentPane.revalidate();
        foodList.clear();
        drinkList.clear();
        menu.refreshDatabase();
        foodList = menu.getFoodList();
        drinkList = menu.getDrinksList();
        contentPane.repaint();
        initializeScreen();
    }

    private void getPaymentProcessor() {
		/*
		Methode zum Zahlungsabwickler, die hier hinzugefügt werden muss.
		 */
        JOptionPane.showMessageDialog(null, "Temp: Zahlungsabwickler Menu", "Bestellung bestätigt", JOptionPane.INFORMATION_MESSAGE);
    }

    /* ----------------------------------------------------------------
    GUI LAYOUT AND FORMATTING
     ----------------------------------------------------------------- */
    public void initializeScreen() {
        logout.setVisible(true);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.gridheight = GridBagConstraints.RELATIVE;
        gbc.weighty = 2.0;
        gbc.gridx = 0;
        gbc.gridy = 0;

        JPanel menuPanel = new RoundedPanel(new GridLayout(1, 2), 10);
        menuPanel.setBackground(SystemColors.WARENCONTAINER.getColorCode());
        menuPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        menuPanel.setMinimumSize(new Dimension(1000, 450));
        menuPanel.setOpaque(false);


        JPanel foodPanel = new JPanel();
        foodPanel.setLayout(new BoxLayout(foodPanel, BoxLayout.Y_AXIS));
        formatMenuPanel(foodPanel, foodList, "Speise");
        menuPanel.add(foodPanel);

        JPanel drinkPanel = new JPanel();
        drinkPanel.setLayout(new BoxLayout(drinkPanel, BoxLayout.Y_AXIS));
        formatMenuPanel(drinkPanel, drinkList, "Getränke");
        menuPanel.add(drinkPanel);
        contentPane.add(menuPanel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.gridheight = GridBagConstraints.REMAINDER;
        gbc.weighty = 0.0;
        gbc.weightx = 0.0;

        JPanel bottomContentRow = new JPanel(new GridLayout(1, 3));
        bottomContentRow.setBackground(SystemColors.BACKGROUND.getColorCode());
        cartPanel = new JPanel();
        formatCartPanel();
        JPanel cartContainer = new JPanel(new BorderLayout());
        cartContainer.setBackground(SystemColors.BACKGROUND.getColorCode());
        cartContainer.add(Box.createVerticalStrut(30), BorderLayout.NORTH);
        cartContainer.add(cartPanel, BorderLayout.CENTER);
        bottomContentRow.add(cartContainer);

        centerButtonPanel = new JPanel();
        formatCenterButtonPanel();
        JPanel centerContainer = new JPanel(new BorderLayout());
        centerContainer.setBackground(SystemColors.BACKGROUND.getColorCode());
        centerContainer.add(Box.createVerticalStrut(35), BorderLayout.NORTH);
        centerContainer.add(centerButtonPanel, BorderLayout.CENTER);
        bottomContentRow.add(centerContainer);

        JPanel userContainer = new JPanel(new BorderLayout());
        userContainer.setBackground(SystemColors.BACKGROUND.getColorCode());

        userContainer.add(Box.createVerticalStrut(30), BorderLayout.NORTH);
        userInfoPanel = new RoundedPanel(new BorderLayout(), 20);
        userInfoPanel.setOpaque(false);
        userInfoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        formatUserInfoPanel();
        userContainer.add(userInfoPanel, BorderLayout.CENTER);
        bottomContentRow.add(userContainer);
        contentPane.add(bottomContentRow, gbc);
    }

    private void formatMenuPanel(JPanel panel, List<Item> itemsList, String panelTitle) {
        int paddingSize = 20;
        Border emptyBorder = BorderFactory.createEmptyBorder(paddingSize, paddingSize, paddingSize, paddingSize);
        TitledBorder titledBorder = BorderFactory.createTitledBorder(emptyBorder, panelTitle,
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new Font("Simple", Font.PLAIN, 18), new Color(30, 30, 30));
        panel.setBorder(titledBorder);
        panel.setBackground(SystemColors.WARENCONTAINER.getColorCode());
        fillItemRows(panel, itemsList);
    }


    private void fillTitleBar(JPanel panel) {
        JPanel tilePanel = new JPanel(new GridBagLayout());
        tilePanel.setBackground(SystemColors.WARENCONTAINER.getColorCode());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.weightx = 0.2;
        gbc.insets = new Insets(0, 0, 0, 5);
        JLabel lager = new JLabel("Best.");
        lager.setFont(new Font("Candera", Font.BOLD, 16));
        lager.setPreferredSize(new Dimension(40, 20));
        tilePanel.add(lager, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.weightx = 0.7;
        gbc.insets = new Insets(0, 0, 0, 5);
        JLabel label = new JLabel("Produkt");
        label.setFont(new Font("Candera", Font.BOLD, 16));
        label.setPreferredSize(new Dimension(75, 20));
        tilePanel.add(label, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.weightx = 0.1;
        gbc.insets = new Insets(0, 0, 0, 5);
        JLabel price = new JLabel("Preis");
        price.setFont(new Font("Candera", Font.BOLD, 16));
        price.setPreferredSize(new Dimension(50, 20));
        tilePanel.add(price, gbc);

        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.weightx = 0.1;
        gbc.insets = new Insets(0, 0, 0, 5);
        JLabel quantity = new JLabel("Menge");
        quantity.setFont(new Font("Candera", Font.BOLD, 16));
        quantity.setPreferredSize(new Dimension(55, 20));
        tilePanel.add(quantity, gbc);

        gbc.gridx = 4;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.weightx = 0.2;
        JLabel addToCart = new JLabel("Warenkorb");
        addToCart.setFont(new Font("Candera", Font.BOLD, 16));
        addToCart.setPreferredSize(new Dimension(75, 20));
        tilePanel.add(addToCart, gbc);

        panel.add(tilePanel, BorderLayout.NORTH);
    }


    private void fillItemRows(JPanel panel, List<Item> items) {
        fillTitleBar(panel);
        JPanel entries = new JPanel(new GridLayout(items.size(), 4));

        JScrollPane scrollPane = new JScrollPane(entries, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new Dimension(0, 600));

        panel.add(scrollPane, BorderLayout.CENTER);
        for (Item item : items) {
            JPanel rowPanel = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            rowPanel.setBackground(SystemColors.WARENCONTAINER.getColorCode());

            JPanel inStockPanel = new JPanel(new BorderLayout());
            inStockPanel.setBackground(SystemColors.WARENCONTAINER.getColorCode());
            JTextPane inStock = new JTextPane();
            inStock.setText(String.valueOf(item.getQuantityInDB()));
            inStock.setForeground(getInStockColor(item.getQuantityInDB()));
            inStock.setBackground(SystemColors.BLACKBG.getColorCode());
            inStock.setFont(new Font("Simple", Font.PLAIN, 16));
            inStock.setPreferredSize(new Dimension(30, 20));
            inStock.setEditable(false);
            inStock.setFocusable(false);
            StyledDocument style = inStock.getStyledDocument();
            SimpleAttributeSet center = new SimpleAttributeSet();
            StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
            style.setParagraphAttributes(0, style.getLength(), center, false);

            JPanel blankPanel = new JPanel();
            blankPanel.setBackground(SystemColors.WARENCONTAINER.getColorCode());
            inStockPanel.add(inStock, BorderLayout.WEST);
            inStockPanel.add(blankPanel, BorderLayout.EAST);

            JLabel label = new JLabel(item.getDescription());
            label.setFont(new Font("Candera", Font.BOLD, 16));
            JLabel price = new JLabel(euroFormat.format(item.getPrice()));
            price.setFont(new Font("Candera", Font.BOLD, 16));
            JComboBox<Integer> quantity = new JComboBox<>(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10});
            quantity.setSelectedIndex(0);
            JButton button = new RoundedButton("Hinzufügen");
            button.setOpaque(false);
            button.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            button.setBackground(SystemColors.BUTTONS.getColorCode());
            button.setForeground(SystemColors.SCHRIFTHELL.getColorCode());
            button.addActionListener(e -> {
                int amountOrdered = (int) quantity.getSelectedItem();
                shoppingCart.addItem(item, amountOrdered);
                addToTally();
            });

            gbc.insets = new Insets(5, 5, 5, 5);
            gbc.gridx = 0;
            gbc.weightx = 0.1;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.ipadx = 5;
            gbc.ipady = 5;
            inStockPanel.setPreferredSize(new Dimension(40, 20));
            rowPanel.add(inStockPanel, gbc);

            gbc.gridx = 1;
            gbc.weightx = 0.6;
            label.setPreferredSize(new Dimension(50, 20));
            rowPanel.add(label, gbc);

            gbc.gridx = 2;
            gbc.weightx = 0.1;
            price.setPreferredSize(new Dimension(30, 20));
            rowPanel.add(price, gbc);

            gbc.gridx = 3;
            gbc.weightx = 0.1;
            quantity.setPreferredSize(new Dimension(30, 20));
            rowPanel.add(quantity, gbc);

            gbc.gridx = 4;
            gbc.weightx = 0.15;
            button.setPreferredSize(new Dimension(75, 20));
            rowPanel.add(button, gbc);

            entries.add(rowPanel);
        }
    }

    private Color getInStockColor(int amount) {
        if (amount > 10) {
            return Color.GREEN;
        } else if (amount >= 5) {
            return Color.YELLOW;
        } else {
            return Color.RED;
        }
    }

    private void formatCartPanel() {
        cartPanel.setLayout(new BorderLayout());
        cartPanel.setBackground(SystemColors.BACKGROUND.getColorCode());
        JLabel cartLabel = new JLabel("Warenkorb");
        cartLabel.setFont(new Font("Simple", Font.PLAIN, 22));
        cartLabel.setHorizontalAlignment(SwingConstants.LEFT);
        cartPanel.add(cartLabel, BorderLayout.NORTH);

        tally = new JPanel();
        tally.setPreferredSize(new Dimension(100, 300));
        tally.setBackground(SystemColors.SCHRIFTDUNKEL.getColorCode());
        JScrollPane cartScroll = new JScrollPane(tally, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        cartScroll.setBackground(SystemColors.SCHRIFTDUNKEL.getColorCode());
        cartPanel.add(cartScroll, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setBackground(SystemColors.BACKGROUND.getColorCode());
        JButton sellButton = new RoundedButton("Kassieren");
        sellButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        sellButton.setOpaque(false);

        sellButton.setBackground(SystemColors.BUTTONS.getColorCode());
        sellButton.setForeground(SystemColors.SCHRIFTHELL.getColorCode());

        sellButton.addActionListener(e -> {
            if (!shoppingCart.getCart().isEmpty()) {
                shoppingCart.completeOrder();
                getPaymentProcessor();
                updateScreen();
            } else {
                JOptionPane.showMessageDialog(null, "Warenkorb ist leer", "Leere Warenkorb", JOptionPane.ERROR_MESSAGE);
            }
        });

        JPanel totalArea = new JPanel(new BorderLayout());
        totalArea.setBackground(SystemColors.BACKGROUND.getColorCode());
        JLabel totalLabel = new JLabel("Gesamt");
        totalLabel.setFont(new Font("Simple", Font.PLAIN, 22));
        totalLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        cartTotal = new JLabel();
        cartTotal.setHorizontalAlignment(SwingConstants.RIGHT);
        cartTotal.setForeground(Color.BLACK);
        cartTotal.setText("0,00€");
        cartTotal.setFont(new Font("Simple", Font.PLAIN, 22));
        totalArea.add(totalLabel, BorderLayout.NORTH);
        totalArea.add(cartTotal);
        buttonPanel.add(sellButton, BorderLayout.WEST);
        buttonPanel.add(totalArea, BorderLayout.EAST);
        this.cartPanel.add(buttonPanel, BorderLayout.SOUTH);
    }

    private void formatCenterButtonPanel() {

        JPanel buttons = new JPanel(new GridLayout(1, 3));
        buttons.setBackground(SystemColors.BACKGROUND.getColorCode());

        centerButtonPanel.setLayout(new BorderLayout());
        centerButtonPanel.setBackground(SystemColors.BACKGROUND.getColorCode());
        centerButtonPanel.add(buttons, BorderLayout.NORTH);

        RoundedButton resetButton = new RoundedButton("Zurücksetzen");
        resetButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        resetButton.setOpaque(false);
        resetButton.setForeground(SystemColors.SCHRIFTDUNKEL.getColorCode());
        resetButton.setBackground(SystemColors.XBUTTON.getColorCode());
        resetButton.addActionListener(e -> resetCart());

        JLabel cafeLogo = new JLabel();
        cafeLogo.setIcon(new ImageIcon("resources/Cafeteria-logo.png"));
        cafeLogo.setHorizontalAlignment(SwingConstants.CENTER);
        cafeLogo.setVerticalAlignment(SwingConstants.CENTER);
        centerButtonPanel.add(cafeLogo, BorderLayout.CENTER);

        RoundedButton userTotalButton = new RoundedButton("Kasse");
        userTotalButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        userTotalButton.setOpaque(false);
        userTotalButton.setForeground(SystemColors.SCHRIFTHELL.getColorCode());
        userTotalButton.setBackground(SystemColors.BUTTONS.getColorCode());
        userTotalButton.addActionListener(e -> viewUserTotals());

        for (int i = 0; i <= 3; i++) {
            JPanel cellPanel = new JPanel();
            cellPanel.setBackground(SystemColors.BACKGROUND.getColorCode());
            buttons.add(cellPanel);
            if (i == 0) {
                cellPanel.add(resetButton);
            }
            if (i == 3) {
                cellPanel.add(userTotalButton);
            }
        }
    }

    private JLabel getLabelFormat(String title) {
        JLabel label = new JLabel(title);
        label.setFont(new Font("Simple", Font.PLAIN, 25));
        return label;
    }

    private JLabel getInfoBoxFormat() {
        JLabel label = new JLabel();
        label.setForeground(SystemColors.SCHRIFTHELL.getColorCode());
        label.setFont(new Font("Simple", Font.PLAIN, 25));
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        label.setVerticalTextPosition(SwingConstants.CENTER);
        return label;
    }

    private RoundedPanel getRoundedPanel() {
        RoundedPanel panel = new RoundedPanel(new FlowLayout(FlowLayout.RIGHT), 25);
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(SystemColors.BLACKBG.getColorCode());
        return panel;
    }

    private void formatUserInfoPanel() {
        userInfoPanel.setBackground(SystemColors.BUTTONS.getColorCode());

        JPanel center = new JPanel(new GridLayout(8, 1));
        center.setBackground(SystemColors.BUTTONS.getColorCode());

        JLabel date = getLabelFormat("Datum");
        RoundedPanel datePanel = getRoundedPanel();
        JLabel datePrint = getInfoBoxFormat();
        datePrint.setText(String.valueOf(LocalDate.now()));
        datePanel.add(datePrint);

        JLabel orderIdLabel = getLabelFormat("Bestellung ID");
        RoundedPanel bestellungPanel = getRoundedPanel();
        JLabel orderId = getInfoBoxFormat();
        orderId.setText(String.valueOf(shoppingCart.getOrderId()));
        bestellungPanel.add(orderId);

        JLabel userIDLabel = getLabelFormat("Kassenpersonal ID");
        JLabel userId = getInfoBoxFormat();
        userId.setText(String.valueOf(UserSessionController.getUserId()));
        RoundedPanel mitarbeitIDPanel = getRoundedPanel();
        mitarbeitIDPanel.add(userId);

        JLabel nameLabel = getLabelFormat("Name");
        JLabel userName = getInfoBoxFormat();
        userName.setText(UserSessionController.getUserFirstName() + " " + UserSessionController.getUserLastName());
        RoundedPanel namePanel = getRoundedPanel();
        namePanel.add(userName);

        center.add(date);
        center.add(datePanel);
        center.add(orderIdLabel);
        center.add(bestellungPanel);
        center.add(userIDLabel);
        center.add(mitarbeitIDPanel);
        center.add(nameLabel);
        center.add(namePanel);
        userInfoPanel.add(center, BorderLayout.CENTER);
    }
}
