import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.security.SecureRandom;

// Interface for Polymorphism
interface PasswordStrategy {
    String generate(int length);
}

class BasicPassword implements PasswordStrategy {
    public String generate(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        return PasswordUtils.generateFromChars(chars, length);
    }
}

class StrongPassword implements PasswordStrategy {
    public String generate(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()";
        return PasswordUtils.generateFromChars(chars, length);
    }
}

class PasswordUtils {
    public static String generateFromChars(String chars, int length) {
        StringBuilder sb = new StringBuilder();
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(chars.length());
            sb.append(chars.charAt(index));
        }
        return sb.toString();
    }
}

public class PasswordGeneratorApp extends JFrame implements ActionListener {
    private JTextField lengthField;
    private JTextArea outputArea;
    private JButton generateBtn;
    private JComboBox<String> typeBox;

    public PasswordGeneratorApp() {
        setTitle("Password Generator");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Password Length:"));
        lengthField = new JTextField(5);
        topPanel.add(lengthField);

        typeBox = new JComboBox<>(new String[] {"Basic", "Strong"});
        topPanel.add(new JLabel("Type:"));
        topPanel.add(typeBox);

        generateBtn = new JButton("Generate Password");
        generateBtn.addActionListener(this);
        topPanel.add(generateBtn);

        add(topPanel, BorderLayout.NORTH);

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        add(new JScrollPane(outputArea), BorderLayout.CENTER);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String lengthText = lengthField.getText();

        try {
            int length = Integer.parseInt(lengthText);
            if (length < 1) {
                outputArea.setText("Please enter a number greater than 0.");
                return;
            }

            outputArea.setText("Generating password...");

            PasswordStrategy strategy;
            if (typeBox.getSelectedItem().equals("Basic")) {
                strategy = new BasicPassword();
            } else {
                strategy = new StrongPassword();
            }

            String password = strategy.generate(length);
            outputArea.setText("Generated Password:\n" + password);

        } catch (NumberFormatException ex) {
            outputArea.setText("Please enter a valid number.");
        }
    }

    public static void main(String[] args) {
        new PasswordGeneratorApp();
    }
}
