import javax.swing.*;
import java.awt.*;

public class TemperatureConverter {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            JFrame frame = new JFrame("Temperature Converter");
            frame.setSize(520, 430);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.setResizable(false);

            // Main panel
            JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
            mainPanel.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));

            // ---------- HEADER ----------
            JLabel title = new JLabel(
                    "Temperature Converter",
                    SwingConstants.CENTER
            );
            title.setFont(new Font("Arial", Font.BOLD, 26));

            JLabel subtitle = new JLabel(
                    "Convert temperatures quickly and accurately",
                    SwingConstants.CENTER
            );
            subtitle.setFont(new Font("Arial", Font.PLAIN, 14));

            JPanel header = new JPanel(new GridLayout(2, 1, 0, 5));
            header.add(title);
            header.add(subtitle);

            // ---------- INPUT ----------
            JLabel inputLabel = new JLabel("Temperature:");

            JTextField inputField = new JTextField();
            inputField.setFont(new Font("Arial", Font.PLAIN, 16));

            // ---------- UNIT SELECTION ----------
            JLabel fromLabel = new JLabel("From:");

            JLabel toLabel = new JLabel("To:");

            String[] units = {
                    "Celsius",
                    "Fahrenheit",
                    "Kelvin"
            };

            JComboBox<String> fromBox = new JComboBox<>(units);
            JComboBox<String> toBox = new JComboBox<>(units);

            // ---------- BUTTONS ----------
            JButton convertButton = new JButton("Convert");
            JButton swapButton = new JButton("⇄ Swap");
            JButton clearButton = new JButton("Clear");

            // ---------- RESULT ----------
            JLabel resultLabel = new JLabel(
                    "Enter a temperature and click Convert",
                    SwingConstants.CENTER
            );

            resultLabel.setFont(
                    new Font("Arial", Font.BOLD, 18)
            );

            // ---------- FORM ----------
            JPanel formPanel = new JPanel(
                    new GridLayout(3, 2, 12, 12)
            );

            formPanel.add(inputLabel);
            formPanel.add(inputField);

            formPanel.add(fromLabel);
            formPanel.add(fromBox);

            formPanel.add(toLabel);
            formPanel.add(toBox);

            // ---------- BUTTON PANEL ----------
            JPanel buttonPanel = new JPanel(
                    new FlowLayout(FlowLayout.CENTER, 10, 10)
            );

            buttonPanel.add(convertButton);
            buttonPanel.add(swapButton);
            buttonPanel.add(clearButton);

            // ---------- CENTER PANEL ----------
            JPanel centerPanel = new JPanel(
                    new BorderLayout(10, 15)
            );

            centerPanel.add(formPanel, BorderLayout.NORTH);
            centerPanel.add(buttonPanel, BorderLayout.CENTER);
            centerPanel.add(resultLabel, BorderLayout.SOUTH);

            // ---------- CONVERT ----------
            convertButton.addActionListener(e -> {

                try {

                    String inputText = inputField.getText().trim();

                    if (inputText.isEmpty()) {
                        showError(
                                frame,
                                "Please enter a temperature."
                        );
                        return;
                    }

                    double temperature =
                            Double.parseDouble(inputText);

                    String from =
                            (String) fromBox.getSelectedItem();

                    String to =
                            (String) toBox.getSelectedItem();

                    // Absolute zero validation
                    if (from.equals("Celsius")
                            && temperature < -273.15) {

                        showError(
                                frame,
                                "Celsius cannot be below -273.15 °C."
                        );
                        return;
                    }

                    if (from.equals("Fahrenheit")
                            && temperature < -459.67) {

                        showError(
                                frame,
                                "Fahrenheit cannot be below -459.67 °F."
                        );
                        return;
                    }

                    if (from.equals("Kelvin")
                            && temperature < 0) {

                        showError(
                                frame,
                                "Kelvin cannot be below 0 K."
                        );
                        return;
                    }

                    // Convert input to Celsius
                    double celsius;

                    if (from.equals("Fahrenheit")) {

                        celsius =
                                (temperature - 32) * 5 / 9;

                    } else if (from.equals("Kelvin")) {

                        celsius =
                                temperature - 273.15;

                    } else {

                        celsius = temperature;
                    }

                    // Convert Celsius to target unit
                    double result;

                    if (to.equals("Fahrenheit")) {

                        result =
                                (celsius * 9 / 5) + 32;

                    } else if (to.equals("Kelvin")) {

                        result =
                                celsius + 273.15;

                    } else {

                        result = celsius;
                    }

                    resultLabel.setText(
                            String.format(
                                    "Result: %.2f °%s",
                                    result,
                                    getSymbol(to)
                            )
                    );

                } catch (NumberFormatException ex) {

                    showError(
                            frame,
                            "Please enter a valid number."
                    );
                }
            });

            // ---------- SWAP ----------
            swapButton.addActionListener(e -> {

                int fromIndex =
                        fromBox.getSelectedIndex();

                int toIndex =
                        toBox.getSelectedIndex();

                fromBox.setSelectedIndex(toIndex);
                toBox.setSelectedIndex(fromIndex);

                resultLabel.setText(
                        "Units swapped — enter a value and convert"
                );
            });

            // ---------- CLEAR ----------
            clearButton.addActionListener(e -> {

                inputField.setText("");

                fromBox.setSelectedIndex(0);

                toBox.setSelectedIndex(1);

                resultLabel.setText(
                        "Enter a temperature and click Convert"
                );

                inputField.requestFocus();
            });

            // ---------- ENTER KEY ----------
            inputField.addActionListener(e ->
                    convertButton.doClick()
            );

            // ---------- ADD TO FRAME ----------
            mainPanel.add(header, BorderLayout.NORTH);
            mainPanel.add(centerPanel, BorderLayout.CENTER);

            frame.add(mainPanel);

            frame.setVisible(true);

            inputField.requestFocus();
        });
    }

    // ---------- TEMPERATURE SYMBOL ----------
    private static String getSymbol(String unit) {

        switch (unit) {

            case "Celsius":
                return "C";

            case "Fahrenheit":
                return "F";

            case "Kelvin":
                return "K";

            default:
                return "";
        }
    }

    // ---------- ERROR MESSAGE ----------
    private static void showError(
            JFrame frame,
            String message) {

        JOptionPane.showMessageDialog(
                frame,
                message,
                "Invalid Input",
                JOptionPane.ERROR_MESSAGE
        );
    }
}