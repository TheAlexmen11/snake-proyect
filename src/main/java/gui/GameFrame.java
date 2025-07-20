package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;

public class GameFrame extends JFrame {

    static final int WIDTH = 515;
    static final int HEIGHT = 535;

    GameFondo fondo;
    GamePanel panel;

    NeonButton botonInicio;
    NeonButton botonRanking;
    NeonButton botonContinuar;

    public GameFrame() {
        super("Snake");

        setLayout(null); // Necesario para posicionar manualmente
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);

        // Panel del juego
        panel = new GamePanel(this);
        panel.setBounds(0, 0, 500, 500);
        panel.setOpaque(false);
        add(panel);

        // Fondo (detrás del panel)
        fondo = new GameFondo();
        fondo.setBounds(0, 0, 500, 500);
        add(fondo);

        // Botones encima usando LayeredPane
        initButtons();

        setVisible(true);
    }

    private void initButtons() {
        botonInicio = new NeonButton("START", new Color(0, 204, 255), new Color(0, 102, 255));
        botonInicio.setBounds(150, 260, 200, 40);
        botonInicio.setVisible(false);
        botonInicio.addActionListener(e -> {
            panel.restartGame();
            hideButtons();
        });

        // Botón CONTINUAR
        botonContinuar = new NeonButton("CONTINUE", new Color(0, 255, 102), new Color(0, 153, 51));
        botonContinuar.setBounds(150, 260, 200, 40);
        botonContinuar.setVisible(false);
        botonContinuar.addActionListener(e -> {
            panel.resumeGame();
            hideButtons();
        });


        botonRanking = new NeonButton("RANKING", new Color(0, 204, 255), new Color(0, 102, 255));
        botonRanking.setBounds(150, 310, 200, 40);
        botonRanking.setVisible(false);
        botonRanking.addActionListener(e -> {
                try {
                    new FrameRanking().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(FrameRanking.class.getName()).log(Level.SEVERE, null, ex);
                }
        });

        JLayeredPane layeredPane = getLayeredPane();
        layeredPane.add(botonInicio, JLayeredPane.POPUP_LAYER);
        layeredPane.add(botonContinuar, JLayeredPane.POPUP_LAYER);
        layeredPane.add(botonRanking, JLayeredPane.POPUP_LAYER);
    }

    public void showButtons() {
        if (panel.isGameOver()) {
            botonInicio.setVisible(true);
            botonContinuar.setVisible(false);
        } else {
            botonInicio.setVisible(false);
            botonContinuar.setVisible(true);
        }
        botonRanking.setVisible(true);
    }


    public void hideButtons() {
        botonInicio.setVisible(false);
        botonRanking.setVisible(false);
        botonContinuar.setVisible(false);
    }
}