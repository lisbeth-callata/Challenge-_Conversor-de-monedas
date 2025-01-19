package com.xyzbank;

import com.xyzbank.logica.CurrencyConverterModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class CurrencyConverterGUI {
    private CurrencyConverterModel converterModel;
    private JFrame frame;
    private JComboBox<String> fromCurrencyComboBox;
    private JComboBox<String> toCurrencyComboBox;
    private JTextField amountTextField;
    private JButton convertButton;
    private JButton closeButton;
    private JLabel resultLabel;

    // Mapa de abreviaciones de monedas a nombres completos
    private static final Map<String, String> currencyNames = new HashMap<>();
    static {
        currencyNames.put("USD", "Dólar estadounidense");
        currencyNames.put("ARS", "Peso argentino");
        currencyNames.put("BRL", "Real brasileño");
        currencyNames.put("COP", "Peso colombiano");
    }

    // Mapa de nombres completos a abreviaciones de monedas
    private static final Map<String, String> currencyAbbreviations = new HashMap<>();
    static {
        currencyAbbreviations.put("Dólar estadounidense", "USD");
        currencyAbbreviations.put("Peso argentino", "ARS");
        currencyAbbreviations.put("Real brasileño", "BRL");
        currencyAbbreviations.put("Peso colombiano", "COP");
    }

    public CurrencyConverterGUI() {
        converterModel = new CurrencyConverterModel(); // Modelo para manejar las conversiones

        // Crear la ventana de la interfaz gráfica
        frame = new JFrame("Conversor de Monedas");
        frame.setLayout(new BorderLayout());
        frame.setSize(500, 400);
        frame.setLocationRelativeTo(null); // Centrar la ventana en la pantalla
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Crear el panel superior con el título
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("Conversor de Monedas", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titlePanel.add(titleLabel);
        frame.add(titlePanel, BorderLayout.NORTH);

        // Crear el panel de entrada de datos
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Crear los elementos de la interfaz con los nombres completos de las monedas
        fromCurrencyComboBox = new JComboBox<>(new String[] {"Dólar estadounidense", "Peso argentino", "Real brasileño", "Peso colombiano"});
        toCurrencyComboBox = new JComboBox<>(new String[] {"Dólar estadounidense", "Peso argentino", "Real brasileño", "Peso colombiano"});
        amountTextField = new JTextField(10);
        convertButton = new JButton("Convertir");
        closeButton = new JButton("Cerrar");
        resultLabel = new JLabel(" ", JLabel.CENTER);
        resultLabel.setFont(new Font("Arial", Font.BOLD, 16));

        // Estilo para el botón Convertir
        convertButton.setForeground(Color.WHITE);
        convertButton.setBackground(new Color(0, 123, 255));
        convertButton.setFocusPainted(false);
        convertButton.setFont(new Font("Arial", Font.PLAIN, 14));

        // Estilo para el botón Cerrar
        closeButton.setForeground(Color.WHITE);
        closeButton.setBackground(new Color(220, 53, 69));
        closeButton.setFocusPainted(false);
        closeButton.setFont(new Font("Arial", Font.PLAIN, 14));

        // Estilo para las cajas de texto
        amountTextField.setFont(new Font("Arial", Font.PLAIN, 14));
        amountTextField.setBackground(Color.WHITE);

        // Agregar los elementos al panel de entrada con GridBagLayout
        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(new JLabel("De:", JLabel.RIGHT), gbc);

        gbc.gridx = 1;
        inputPanel.add(fromCurrencyComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(new JLabel("A:", JLabel.RIGHT), gbc);

        gbc.gridx = 1;
        inputPanel.add(toCurrencyComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(new JLabel("Cantidad:", JLabel.RIGHT), gbc);

        gbc.gridx = 1;
        inputPanel.add(amountTextField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        inputPanel.add(convertButton, gbc);

        gbc.gridx = 1;
        inputPanel.add(closeButton, gbc);

        // Agregar el panel de entrada al marco
        frame.add(inputPanel, BorderLayout.CENTER);

        // Crear un panel para el resultado
        JPanel resultPanel = new JPanel();
        resultPanel.setBackground(new Color(245, 245, 245));
        resultPanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        resultPanel.setPreferredSize(new Dimension(400, 100));
        resultPanel.add(resultLabel);
        frame.add(resultPanel, BorderLayout.SOUTH);

        // Evento del botón Convertir
        convertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                convertCurrency();
            }
        });

        // Evento del botón Cerrar
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // Cerrar la aplicación
            }
        });

        frame.setVisible(true);
    }

    // Método para realizar la conversión
    private void convertCurrency() {
        try {
            String fromCurrencyName = (String) fromCurrencyComboBox.getSelectedItem();
            String toCurrencyName = (String) toCurrencyComboBox.getSelectedItem();
            double amount = Double.parseDouble(amountTextField.getText());

            String fromCurrencyCode = currencyAbbreviations.get(fromCurrencyName);
            String toCurrencyCode = currencyAbbreviations.get(toCurrencyName);

            double result = converterModel.convert(fromCurrencyCode, toCurrencyCode, amount);

            String exchangeRate = converterModel.getExchangeRate(fromCurrencyCode, toCurrencyCode);

            String resultText = String.format("<html><b>Resultado: %.2f %s</b><br>Tipo de cambio: 1 %s = %.2f %s</html>",
                    result, toCurrencyCode, fromCurrencyCode, Double.parseDouble(exchangeRate), toCurrencyCode);
            resultLabel.setText(resultText);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Por favor, ingresa un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CurrencyConverterGUI());
    }
}
