package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.Random;

import javax.swing.*;

import app.dao.PartidaDAO;
import app.dao.PlayerDAO;
import app.model.Partida;
import app.model.Player;

public class GamePanel extends JPanel implements ActionListener {

    static final int WIDTH = 500;
    static final int HEIGHT = 500;
    static final int UNIT_SIZE = 20;
    static final int NUMBER_OF_UNITS = (WIDTH * HEIGHT) / (UNIT_SIZE * UNIT_SIZE);
    String nombreJugador;

    final int[] x = new int[NUMBER_OF_UNITS];
    final int[] y = new int[NUMBER_OF_UNITS];

    int length = 5;
    int foodEaten;
    int foodX, foodY;
    char direction = 'D';

    boolean running = false;
    boolean paused = false;
    boolean gameOver = false;

    Timer timer;
    Random random;

    GameFrame frame;

    public GamePanel(GameFrame frame, String nombreJugador) {
        this.frame = frame;
        this.nombreJugador = nombreJugador;
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.DARK_GRAY);
        setFocusable(true);
        setLayout(null);

        random = new Random();
        addKeyBindings();
        play();
    }

    private void addKeyBindings() {
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "togglePause");

        getActionMap().put("togglePause", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (gameOver) return;
                
                paused = !paused;
                
                if (paused) {
                    timer.stop();
                    frame.showButtons();
                } else {
                    timer.start();
                    frame.hideButtons();
                    requestFocusInWindow();
                }
                
                insertarPartida(nombreJugador,foodEaten,foodX, foodY, paused); 
                repaint();
            }
        });
    }

    public void play() {
        addFood();
        running = true;
        gameOver = false;
        paused = false;
        timer = new Timer(80, this);
        timer.start();
    }

    public void restartGame() {
        for (int i = 0; i < length; i++) {
            x[i] = 100 - i * UNIT_SIZE;
            y[i] = 100;
        }

        direction = 'D';
        length = 5;
        foodEaten = 0;
        running = true;
        paused = false;
        gameOver = false;

        addFood();

        if (timer != null) {
            timer.stop();
        }
        timer.start();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        if (running && !paused) {
            g.setColor(new Color(210, 115, 90));
            g.fillOval(foodX, foodY, UNIT_SIZE, UNIT_SIZE);

            g.setColor(Color.white);
            g.fillOval(x[0], y[0], UNIT_SIZE, UNIT_SIZE);
            for (int i = 1; i < length; i++) {
                g.setColor(new Color(40, 200, 150));
                g.fillOval(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
            }

        } else if (paused && !gameOver) {
            showPausedOverlay(g);
        }

        if (gameOver) {
            showGameOver(g);
        }
    }

    private void showPausedOverlay(Graphics g) {
        g.setColor(Color.white);
        g.setFont(new Font("Sans serif", Font.BOLD, 25));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Paused", (WIDTH - metrics.stringWidth("Paused")) / 2, HEIGHT / 2 - 40);

        g.setFont(new Font("Sans serif", Font.PLAIN, 20));
        g.drawString("Score: " + foodEaten, (WIDTH - metrics.stringWidth("Score: " + foodEaten)) / 2, HEIGHT / 2 - 10);
    }

    private void showGameOver(Graphics g) {
        g.setColor(Color.red);
        g.setFont(new Font("Sans serif", Font.BOLD, 50));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Game Over", (WIDTH - metrics.stringWidth("Game Over")) / 2, HEIGHT / 2 - 60);

        g.setColor(Color.white);
        g.setFont(new Font("Sans serif", Font.PLAIN, 25));
        g.drawString("Score: " + foodEaten, (WIDTH - metrics.stringWidth("Score: " + foodEaten)) / 2, HEIGHT / 2 - 20);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running && !paused) {
            move();
            checkCollisions();
            checkFood();
        }
        repaint();
    }

    private void move() {
        for (int i = length; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }

        switch (direction) {
            case 'L': x[0] -= UNIT_SIZE; break;
            case 'R': x[0] += UNIT_SIZE; break;
            case 'U': y[0] -= UNIT_SIZE; break;
            case 'D': y[0] += UNIT_SIZE; break;
        }

        x[0] = Math.floorMod(x[0], WIDTH);
        y[0] = Math.floorMod(y[0], HEIGHT);
    }

    private void checkFood() {
        if (x[0] == foodX && y[0] == foodY) {
            length++;
            foodEaten++;
            addFood();
        }
    }

    private void checkCollisions() {
        for (int i = length; i > 0; i--) {
            if (x[0] == x[i] && y[0] == y[i]) {
                running = false;
                break;
            }
        }

        if (!running && !gameOver) {
            gameOver = true;
            new Thread(() -> {
                insertarPartida(nombreJugador, 0, foodX, foodY, false);
            }).start();
            timer.stop();
            frame.showButtons();
        }
    }

    private void addFood() {
        foodX = random.nextInt(WIDTH / UNIT_SIZE) * UNIT_SIZE;
        foodY = random.nextInt(HEIGHT / UNIT_SIZE) * UNIT_SIZE;
    }

    @Override
    protected void processKeyEvent(KeyEvent evt) {
        if (evt.getID() == KeyEvent.KEY_PRESSED && !paused && running) {
            switch (evt.getKeyCode()) {
                case KeyEvent.VK_LEFT: if (direction != 'R') direction = 'L'; break;
                case KeyEvent.VK_RIGHT: if (direction != 'L') direction = 'R'; break;
                case KeyEvent.VK_UP: if (direction != 'D') direction = 'U'; break;
                case KeyEvent.VK_DOWN: if (direction != 'U') direction = 'D'; break;
            }
        }
        super.processKeyEvent(evt);
    }

    public boolean isGameOver() {
    return gameOver;
    }

    public void resumeGame() {
        if (paused && !gameOver) {
            paused = false;
            timer.start();
            frame.hideButtons();
            requestFocusInWindow();
        }
    }

    public int[] getSnakeX() {
    return java.util.Arrays.copyOf(x, length);
    }

    public int[] getSnakeY() {
        return java.util.Arrays.copyOf(y, length);
    }

    public int getScore() {
    return foodEaten;
    }

    public void insertarPartida(String nombreJugador, int puntos, int foodX, int foodY, boolean paused) {
        try {
            System.out.println("Insertando partida para el jugador: " + nombreJugador);
            PlayerDAO playerDAO = new PlayerDAO();
            PartidaDAO partidaDAO = new PartidaDAO();
            Player player = playerDAO.buscarPorNombre(nombreJugador);
            Partida partidaAnterior = partidaDAO.buscarPartidaPorJugador(player.getIdJugador());

            partidaDAO.actualizarPartida(partidaAnterior.getIdPartida(), puntos, paused);
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "❌ al insertar data: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
}
