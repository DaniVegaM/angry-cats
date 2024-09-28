package Entities;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Pez extends JPanel {

    //Variables
    private int x;
    private int y;
    private ImageIcon pezIcono;
    private ImageIcon iconoPezMuerto;
    private Timer timer;
    private int velocidad;
    private int maxFallHeight;
    private double initialAngle;
    private double targetAngle;
    private boolean animationComplete;
    public boolean derrumbado;

    public Pez(int x, int y, String imagePath, int numero) { //Constructor
        this.x = x;
        this.y = y;
        this.animationComplete = false;
        this.pezIcono = new ImageIcon(imagePath);
        derrumbado = false;
        this.timer = new Timer(1, e -> updateV());
        if(numero == 1 || numero == 2){
            this.iconoPezMuerto = new ImageIcon("./resources/fishmuerto.png");
        }
        else{
            this.iconoPezMuerto = new ImageIcon("./resources/bigfishmuerto.png");
        }
        this.velocidad = 2;
        this.maxFallHeight = 700 - iconoPezMuerto.getIconHeight();
        setOpaque(false);
    }
    
    //Pintar
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.translate(x, y);
        System.out.println("El pez se movio poquito");
        Image paloImage = pezIcono.getImage();
        g2d.drawImage(paloImage, 0, 0, null);
        g2d.dispose();
    }
    
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(pezIcono.getIconWidth(), pezIcono.getIconHeight());
    }
    
    public void updateV() {
        if (y < maxFallHeight) {
            y += velocidad;
        }

        // Verificar si el palo ha llegado al suelo
        if (y <= maxFallHeight) {
            y = maxFallHeight;
            animationComplete = true;
            pezIcono = iconoPezMuerto; // Cambiar la imagen a la versión muerta
            derrumbado = true;
            timer.stop();
            System.out.println("Se supone que ya cayo el pez");
            reset();
        }

        repaint();
    }
    
    // getters
    public ImageIcon getPezIcon() {
        return this.pezIcono;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getPezCoordenadaX() {
        return this.x;
    }

    public Rectangle getBounds() {
        return new Rectangle(this.x, this.y, pezIcono.getIconWidth() + 10, pezIcono.getIconHeight() + 10);
    }

    public int getPezCoordenadaY() {
        return this.y;
    } // Fin de Getters

    public void fall(int velocidad) {
        System.out.println("Se va a caer el pez");
        this.velocidad = velocidad;
        derrumbado = true;
        timer.start();
    }

    public void setDerrumbado(boolean value) {
        derrumbado = value;
    }
      private void reset() {
        animationComplete = false;
    }
}
