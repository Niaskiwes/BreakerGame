import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ScoreManager extends JPanel {
    private JLabel scoreLabel;
    private JButton startButton;
    private JButton exitButton;

    public ScoreManager(int score) {
        setBackground(Color.BLACK);
        setLayout(new BorderLayout());

        startButton = new JButton("Replay");
        startButton.setBackground(new Color(50, 150, 200));
        startButton.setForeground(Color.black);
        startButton.setPreferredSize(new Dimension(400, 100)); // Увеличение размера кнопки
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startGame(); // Запуск игры
            }
        });

        exitButton = new JButton("Exit");
        exitButton.setBackground(new Color(200, 50, 50));
        exitButton.setForeground(Color.black);
        exitButton.setPreferredSize(new Dimension(400, 100)); // Увеличение размера кнопки
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // Закрыть приложение
            }
        });

        scoreLabel = new JLabel("Your Score: " + score);
        scoreLabel.setFont(new Font("Arial", Font.PLAIN, 40));
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER); // Выравнивание текста по центру

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(startButton);
        buttonPanel.add(exitButton);

        add(buttonPanel, BorderLayout.SOUTH); // Размещаем кнопки по центру
        add(scoreLabel, BorderLayout.CENTER); // Размещаем счет очков в центре
    }

    public JLabel getScoreLabel() {
        return scoreLabel;
    }

    public void updateScore(int newScore) {
        scoreLabel.setText("Your Score: " + newScore);
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
}