import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Arrays;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Calculator {
    int boardWidth = 360;
    int boardHeight = 540;

    Color customLightGray = new Color(212, 212, 210);
    Color customDarkGray = new Color(80, 80, 80);
    Color customBlackColor = new Color(28, 28, 28);
    Color customOrangeColor = new Color(255, 149, 0);

    String[] buttonValues = {
        "AC", "+/-", "%", "÷",
        "7", "8", "9", "×",
        "4", "5", "6", "-",
        "1", "2", "3", "+",
        "0", ".", "√", "="
    };
    String[] rightSymbols = {"÷", "×", "-", "+", "="};
    String[] topSymbols = {"AC", "+/-", "%"};

    JFrame frame = new JFrame("Calculator");
    JLabel displayJLabel = new JLabel();
    JPanel displayJPanel = new JPanel();
    JPanel buttonJPanel = new JPanel();

    String A = "0";
    String operator = null;
    String B = "0";

    Calculator() {
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        displayJLabel.setBackground(customBlackColor);
        displayJLabel.setForeground(Color.white);
        displayJLabel.setFont(new Font("Arial", Font.PLAIN, 80));
        displayJLabel.setHorizontalAlignment(JLabel.RIGHT);
        displayJLabel.setText("0");
        displayJLabel.setOpaque(true);

        displayJPanel.setLayout(new BorderLayout());
        displayJPanel.add(displayJLabel);
        frame.add(displayJPanel, BorderLayout.NORTH);

        buttonJPanel.setLayout(new GridLayout(5, 4));
        buttonJPanel.setBackground(customBlackColor);
        frame.add(buttonJPanel);

        for (int i = 0; i < buttonValues.length; i++) {
            JButton button = new JButton();
            String buttonValue = buttonValues[i];
            button.setFont(new Font("Arial", Font.PLAIN, 30));
            button.setText(buttonValue);
            button.setFocusable(false);

            if (Arrays.asList(topSymbols).contains(buttonValue)) {
                button.setBackground(customLightGray);
                button.setForeground(customBlackColor);
            } else if (Arrays.asList(rightSymbols).contains(buttonValue)) {
                button.setBackground(customOrangeColor);
                button.setForeground(Color.WHITE);
            } else {
                button.setBackground(customDarkGray);
                button.setForeground(Color.WHITE);
            }

            buttonJPanel.add(button);

            button.addActionListener(e -> {
                JButton clicked = (JButton) e.getSource();
                String value = clicked.getText();

                if (Arrays.asList(rightSymbols).contains(value)) {
                    if (value.equals("=")) {
                        if (operator != null) {
                            B = displayJLabel.getText();
                            double numA = Double.parseDouble(A);
                            double numB = Double.parseDouble(B);

                            if (operator.equals("+")) {
                                displayJLabel.setText(removeZeroDecimal(numA + numB));
                            } else if (operator.equals("-")) {
                                displayJLabel.setText(removeZeroDecimal(numA - numB));
                            } else if (operator.equals("×")) {
                                displayJLabel.setText(removeZeroDecimal(numA * numB));
                            } else if (operator.equals("÷")) {
                                displayJLabel.setText(removeZeroDecimal(numA / numB));
                            }
                            clearAll();
                        }
                    } else {
                        if (operator == null) {
                            A = displayJLabel.getText();
                            displayJLabel.setText("0");
                            B = "0";
                        }
                        operator = value;
                    }
                } else if (Arrays.asList(topSymbols).contains(value)) {
                    if (value.equals("AC")) {
                        clearAll();
                        displayJLabel.setText("0");
                    } else if (value.equals("+/-")) {
                        double numDisplay = Double.parseDouble(displayJLabel.getText());
                        numDisplay *= -1;
                        displayJLabel.setText(removeZeroDecimal(numDisplay));
                    } else if (value.equals("%")) {
                        double numDisplay = Double.parseDouble(displayJLabel.getText());
                        numDisplay /= 100;
                        displayJLabel.setText(removeZeroDecimal(numDisplay));
                    }
                } else {
                    if (value.equals(".")) {
                        if (!displayJLabel.getText().contains(".")) {
                            displayJLabel.setText(displayJLabel.getText() + ".");
                        }
                    } else if (value.equals("√")) {
                        double numDisplay = Double.parseDouble(displayJLabel.getText());
                        displayJLabel.setText(removeZeroDecimal(Math.sqrt(numDisplay)));
                    } else if ("0123456789".contains(value)) {
                        if (displayJLabel.getText().equals("0")) {
                            displayJLabel.setText(value);
                        } else {
                            displayJLabel.setText(displayJLabel.getText() + value);
                        }
                    }
                }
            });
        }

        frame.setVisible(true);
    }

    void clearAll() {
        A = "0";
        operator = null;
        B = "0";
    }

    String removeZeroDecimal(double numDisplay) {
        if (numDisplay % 1 == 0) {
            return Long.toString((long) numDisplay);
        }
        return Double.toString(numDisplay);
    }

    public static void main(String[] args) {
        new Calculator();
    }
}