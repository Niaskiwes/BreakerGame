import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BrickBreakerLogin extends JFrame {
    private JButton startButton;
    private JButton exitButton;

    public BrickBreakerLogin() {
        super("Login");

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon bg = new ImageIcon("background.jpg"); // Путь к изображению фона
                g.drawImage(bg.getImage(), 0, 0, getWidth(), getHeight(), null);
            }
        };
        panel.setLayout(new GridBagLayout());
        panel.setBackground(new Color(0, 0, 0, 150)); // Настройка прозрачного фона

        JLabel titleLabel = new JLabel("Brick Breaker Game");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36)); // Увеличение размера надписи
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        startButton = new JButton("Start Game");
        startButton.setBackground(new Color(50, 150, 200));
        startButton.setForeground(Color.WHITE);
        startButton.setPreferredSize(new Dimension(200, 60)); // Увеличение размера кнопки
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose(); // Закрыть окно входа после нажатия кнопки
                startGame(); // Запуск игры
            }
        });

        exitButton = new JButton("Exit");
        exitButton.setBackground(new Color(200, 50, 50));
        exitButton.setForeground(Color.WHITE);
        exitButton.setPreferredSize(new Dimension(200, 60)); // Увеличение размера кнопки
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // Закрыть приложение
            }
        });

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);

        panel.add(titleLabel, gbc);
        gbc.gridy++;
        panel.add(startButton, gbc);
        gbc.gridy++;
        panel.add(exitButton, gbc);

        add(panel, BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true); // Убрать рамку окна
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Открыть в полноэкранном режиме
        setVisible(true);
    }

    private void startGame() {
        BrickBreaker game = new BrickBreaker();
        JFrame frame = new JFrame("Brick Breaker Game");
        frame.setUndecorated(true); // Убрать рамку окна
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Открыть в полноэкранном режиме
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(game);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new BrickBreakerLogin();
            }
        });
    }
}