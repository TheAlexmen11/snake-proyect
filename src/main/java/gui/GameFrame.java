package gui;

import java.awt.Dimension;
import javax.swing.JFrame;

public class GameFrame extends javax.swing.JFrame {

    static final int WIDTH = 515;
    static final int HEIGHT = 535;
    GameFondo fondo;
    GamePanel panel;

    public GameFrame() {

        //panel = new GamePanel();
        this.add(panel);
        panel.setBounds(0, 0, 500, 500);
        panel.setOpaque(false);
        fondo = new GameFondo();
        this.add(fondo);
        fondo.setBounds(0, 0, 500, 500);

        //.setOpaque(false);
        this.setTitle("snake");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT + 5));
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        //this.setLocationRelativeTo(null);

        initComponents();

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 708, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 497, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_formKeyPressed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
