import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;




public class BrickBreaker extends JPanel implements KeyListener, ActionListener {
    private static final int WIDTH = 2570;
    private static final int HEIGHT = 1080;
    private int playerX = WIDTH / 2 - 50;
    private int playerWidth = 100;
    private int ballX = WIDTH / 2 - 10;
    private int ballY = HEIGHT - 80;
    private int ballXDir = -3;
    private int ballYDir = -4;
    private boolean isPaused = true;
    private int[][] bricks = new int[8][29];
    private ScoreManager scoreManager;
    private int score;
    private int lives = 3;
    private Graphics g;
    private Timer timer;
    private JButton MenuButton;
    private JButton exitButton;
    private JButton changeColorsButton;
    private Color[] colors = {Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.ORANGE, Color.CYAN};
    private Color platformColor, ballColor, brickColor;

    public BrickBreaker() {
        score=0;
        scoreManager = new ScoreManager(score);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(8, this);
        timer.start();
        for (int i = 0; i < bricks.length; i++) {
            for (int j = 0; j < bricks[0].length; j++) {
                bricks[i][j] = 1;
            }

        }

        exitButton = new JButton("Exit");
        exitButton.setBounds(WIDTH - 150, HEIGHT - 50, 100, 30);
        exitButton.setBackground(new Color(200, 50, 50));
        exitButton.setForeground(Color.WHITE);
        exitButton.setBorder(BorderFactory.createLineBorder(Color.white));
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        MenuButton = new JButton("Menu");
        MenuButton.setBounds(WIDTH - 150, HEIGHT - 50, 100, 30);
        MenuButton.setBackground(new Color(50, 150, 200));
        MenuButton.setForeground(Color.WHITE);
        MenuButton.setBorder(BorderFactory.createLineBorder(Color.white));
        MenuButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MenuGame();
            }
        });

        changeColorsButton = new JButton("Change Colors");
        changeColorsButton.setBounds(WIDTH - 150, HEIGHT - 100, 150, 30);
        changeColorsButton.setBackground(new Color(0,150,0));
        changeColorsButton.setForeground(Color.WHITE);
        changeColorsButton.setBorder(BorderFactory.createLineBorder(Color.white));
        changeColorsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                changeColors();
            }
        });

        add(exitButton);
        add(changeColorsButton);

        changeColors();
    }

    public void changeColors() {
        Random rand = new Random();
        platformColor = colors[rand.nextInt(colors.length)];
        ballColor = colors[rand.nextInt(colors.length)];
        brickColor = colors[rand.nextInt(colors.length)];
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        exitButton.setBounds(WIDTH - 300, HEIGHT - 100, 300, 100);

        add(exitButton);

        changeColorsButton.setBounds(WIDTH - 300, HEIGHT - 200, 300, 100);

        add(changeColorsButton);

        MenuButton.setBounds(WIDTH - 300, HEIGHT - 300, 300, 100);

        add(MenuButton);

        // Отрисовка платформы
        g.setColor(platformColor);
        g.fillRect(playerX, HEIGHT - 50, playerWidth, 10);

        // Отрисовка мяча
        g.setColor(ballColor);
        g.fillOval(ballX, ballY, 20, 20);

        // Отрисовка кирпичей
        for (int i = 0; i < bricks.length; i++) {
            for (int j = 0; j < bricks[0].length; j++) {
                if (bricks[i][j] > 0) {
                    g.setColor(brickColor);
                    g.fillRect(j * 75 + 30, i * 20 + 50, 70, 15);
                }
            }
        }

        // Отрисовка счета и жизней
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 30));
        g.drawString("Score: " + score, 2350, 200);
        g.drawString("Lives: " + lives, 2350, 300);
        if (isPaused) {
            g.drawString("Press SPACE to start", WIDTH / 2 - 60, HEIGHT / 2);
        }
        g.drawLine(2270,1080,2270,0);
        g.setColor(Color.WHITE);

    }

    // Остальные методы класса

    public void actionPerformed(ActionEvent e) {
        timer.start();
        if (!isPaused) {
            ballX += ballXDir;
            ballY += ballYDir;
            if (ballX < 0 || ballX > WIDTH - 325) {
                ballXDir = -ballXDir;
            }
            if (ballY < 0) {
                ballYDir = -ballYDir;
            }
            if (ballY > HEIGHT - 70 && ballX > playerX && ballX < playerX + playerWidth) {
                ballYDir = -3
                ; // Фиксированное направление отскока при столкновении с платформой
            }
            if (ballY > HEIGHT) {
                lives--;
                if (lives == 0) {
                    Score();
                } else {
                    ballX = WIDTH / 2 - 10;
                    ballY = HEIGHT - 80;
                    ballXDir = -3;
                    ballYDir = -4;
                    playerX = WIDTH / 2 - 50;
                    isPaused = true;
                }
            }

            for (int i = 0; i < bricks.length; i++) {
                for (int j = 0; j < bricks[0].length; j++) {
                    if (bricks[i][j] > 0) {
                        int brickX = j * 75 + 30;
                        int brickY = i * 20 + 50;
                        int brickWidth = 70;
                        int brickHeight = 15;

                        Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
                        Rectangle ballRect = new Rectangle(ballX, ballY, 20, 20);
                        if (ballRect.intersects(rect)) {
                            bricks[i][j]--;
                            score += 10; // Увеличиваем счет на 10 при разрушении кирпича
                            if (bricks[i][j] == 0) {
                                // Проверяем, если все кирпичи разрушены, можно добавить дополнительные действия
                            }
                            ballYDir = -ballYDir;

                        }
                    }
                }

            }

        }

        repaint();
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (playerX >= WIDTH - playerWidth) {
                playerX = WIDTH - playerWidth;
            } else {
                moveRight();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (playerX <= 0) {
                playerX = 0;
            } else {
                moveLeft();
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            isPaused = !isPaused;
        }
    }

    public void moveRight() {
        isPaused = false;
        playerX += 20;
    }

    public void moveLeft() {
        isPaused = false;
        playerX -= 20;
    }
    private void MenuGame() {
        BrickBreakerLogin menu = new BrickBreakerLogin();
        JFrame frame = new JFrame("Brick Breaker Game");
        frame.setUndecorated(true); // Убрать рамку окна
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Открыть в полноэкранном режиме
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(menu);
        frame.setVisible(true);
    }
    public void updateScore(int newScore) {
        score = newScore;
        scoreManager.updateScore(score);
    }
    private void Score() {
        scoreManager.getScoreLabel().setText("Your Score: " + score);
        JFrame frame = new JFrame("Score");
        frame.setUndecorated(true);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(scoreManager);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Brick Breaker");
        BrickBreaker game = new BrickBreaker();

        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);

        frame.add(game);
        frame.setVisible(true);

    }
}