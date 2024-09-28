package Entities;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Palo extends JPanel {

    private int x;
    private int y;
    private ImageIcon paloIcon;
    private ImageIcon horizontalIcon;
    private Timer timer;
    private double angle;
    private int velocidad;
    private int maxFallHeight;
    private double initialAngle;
    private double targetAngle;
    private boolean animationComplete;
    public boolean derrumbado;

    public Palo(int x, int y, String imagePath, String tipoPalo) {
        this.x = x;
        this.y = y;
        this.animationComplete = false;

        this.paloIcon = new ImageIcon(imagePath);
        this.horizontalIcon = new ImageIcon("./resources/plvh.png");

        if ("V".equals(tipoPalo)) {
            this.timer = new Timer(1, e -> updateV());
        } else if ("H".equals(tipoPalo)) {
            this.timer = new Timer(1, e -> updateH());
        }

        this.angle = initialAngle;
        this.velocidad = 2;
        this.maxFallHeight = 700 - horizontalIcon.getIconHeight();
        setOpaque(false);
        derrumbado = false;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.translate(x, y);
        System.out.println("El palo se movio poquito");
        Image paloImage = paloIcon.getImage();
        g2d.drawImage(paloImage, 0, 0, null);
        g2d.dispose();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(paloIcon.getIconWidth(), paloIcon.getIconHeight());
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, paloIcon.getIconWidth(), paloIcon.getIconHeight());
    }

    public void fall(int velocidad) {
        System.out.println("Se va a caer el palo");
        this.velocidad = velocidad;
        derrumbado = true;
        timer.start();
    }

    public void fallHorizontal(int velocidad) {
        if (!timer.isRunning()) {
            this.velocidad = velocidad;
            reset();
            timer.start();
        }
    }

    public void updateV() {
        if (y < maxFallHeight) {
            y += velocidad;
        }

        // Verificar si el palo ha llegado al suelo
        if (y <= maxFallHeight) {
            y = maxFallHeight;
            angle = targetAngle; // Establecer el ángulo objetivo directamente
            animationComplete = true;
            paloIcon = horizontalIcon; // Cambiar la imagen a la versión horizontal
            derrumbado = true;
            timer.stop();
            System.out.println("Se supone que ya cayo el palo");
            reset();
        }

        repaint();
    }

    public void updateH() {
        if (y < maxFallHeight) {
            y += velocidad;

            if (y <= maxFallHeight) {
                y = maxFallHeight;
                angle = targetAngle;
                animationComplete = true;
                derrumbado = true;
                timer.stop();
                System.out.println("Se supone que ya cayo el palo");
                reset();
            }
        }

        repaint();
    }
    
    public void setDerrumbado(boolean value){
        derrumbado = value;
    }

    private void reset() {
        angle = initialAngle;
        animationComplete = false;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public ImageIcon getPaloIcon() {
        return paloIcon;
    }
}
