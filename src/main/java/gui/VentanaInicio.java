
package gui;

import app.dao.PlayerDAO;
import app.model.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaInicio extends JFrame {

    private JTextField txtUsuario;

    public VentanaInicio() {
        setTitle("Snake Game - Inicio");
        setSize(500, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Panel de fondo con degradado azul
        JPanel fondo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, new Color(0, 0, 139),
                        getWidth(), getHeight(), new Color(173, 216, 230));
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        fondo.setLayout(null);
        setContentPane(fondo);

        JLabel lblTitulo = new JLabel("🐍 SNAKE GAME 🐍", SwingConstants.CENTER);
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Consolas", Font.BOLD, 30));
        lblTitulo.setBounds(100, 20, 300, 40);
        fondo.add(lblTitulo);

        txtUsuario = new JTextField("Usuario...");
        txtUsuario.setBounds(150, 80, 200, 30);
        fondo.add(txtUsuario);

        // Cuadro con serpiente y comida
        JPanel cuadroJuego = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                setBackground(new Color(255, 255, 255, 180));

                // Dibujar serpiente (bloques verdes)
                g.setColor(Color.GREEN);
                g.fillRect(50, 60, 20, 20);
                g.fillRect(70, 60, 20, 20);
                g.fillRect(90, 60, 20, 20);
                g.fillRect(90, 80, 20, 20);

                // Dibujar comida (círculo rojo)
                g.setColor(Color.RED);
                g.fillOval(130, 70, 15, 15);
            }
        };
        cuadroJuego.setBounds(120, 130, 250, 120);
        cuadroJuego.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
        fondo.add(cuadroJuego);

        NeonButton btnStart = new NeonButton("START", new Color(0, 204, 255), new Color(0, 102, 255));
        btnStart.setBounds(130, 270, 100, 35);
        btnStart.setBackground(new Color(0, 204, 255));
        btnStart.setForeground(Color.BLACK);
        fondo.add(btnStart);

        NeonButton btnExit = new NeonButton("EXIT", new Color(204, 0, 255), new Color(102, 0, 204));
        btnExit.setBounds(260, 270, 100, 35);
        btnExit.setBackground(new Color(204, 0, 255));
        btnExit.setForeground(Color.WHITE);
        fondo.add(btnExit);

        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = txtUsuario.getText().trim();

                if (nombre.isEmpty() || nombre.equalsIgnoreCase("Usuario...")) {
                    JOptionPane.showMessageDialog(null, "Por favor ingresa un nombre válido.", "⚠️ Advertencia", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                try {
                    Player jugador = new Player();
                    jugador.setNombre(nombre);

                    PlayerDAO dao = new PlayerDAO();
                    dao.insertar(jugador);

                    JOptionPane.showMessageDialog(null, "✅ Jugador registrado: " + nombre);

                    new GameFrame().setVisible(true);
                    dispose();

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "❌ Error al registrar jugador: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        });

        btnExit.addActionListener(e -> System.exit(0));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VentanaInicio().setVisible(true));
    }
}