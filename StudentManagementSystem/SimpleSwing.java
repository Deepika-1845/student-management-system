import javax.swing.*;

public class SimpleSwing {

    public static void main(String[] args) {

        JFrame frame = new JFrame("Simple Swing Demo");

        JLabel label = new JLabel("Hello Student!", JLabel.CENTER);

        JButton button = new JButton("Click Me");

        button.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, "Button Clicked!");
        });

        frame.setLayout(null);

        label.setBounds(100, 30, 200, 30);
        button.setBounds(120, 80, 150, 30);

        frame.add(label);
        frame.add(button);

        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}