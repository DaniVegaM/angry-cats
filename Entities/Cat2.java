package Entities;

import frames.Level2;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Cat2 extends JPanel implements Level2.GatoPositionListener {

    int x;
    int y;

    private ImageIcon imagen;
    private double angle;
    private int gravity = 2;
    private Level2 level2; //Declaro una instancia level1

    public Cat2(String imagePath, Level2 level2) { //Constructor Gato
        this.imagen = new ImageIcon(imagePath);
        angle = Math.PI / 8;
        this.level2 = level2;
        x = 0;
        y = 0;

    }

    //Obtiene coordenadas del gato en tiempo 
    @Override
    public void onGatoPositionChanged(int x, int y) {
        this.x = x;
        this.y = y;
        if (level2.numGato == 1) {
            System.out.println("X NUEVOOO = " + x + "Y NUEVOOO = " + y);
        }
        System.out.println("Coordenada del Gato en X= " + x);
        System.out.println("Coordenada del Gato en Y= " + y);
    }

    //Establece area y cooredenadas de mi gato
    public Rectangle getBounds() {
        if (level2.numGato == 1) {
            System.out.println("2  X NUEVOOO = " + x + "Y NUEVOOO = " + y);
        }

        return new Rectangle(x, y, imagen.getIconWidth(), imagen.getIconHeight());
    }

    //getters
    public ImageIcon getCatIcon() {
        return imagen;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.translate(x, y);
        g2d.rotate(angle, imagen.getIconWidth() / 2, imagen.getIconHeight() / 2);
        Image gatoImage = imagen.getImage();
        g2d.drawImage(gatoImage, 0, 0, null);
        g2d.dispose();

        // Incrementar las coordenadas y en cada llamada para simular la caída
        y += gravity;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(imagen.getIconWidth(), imagen.getIconHeight());
    }

    public void choco(int velocidad) {
        System.out.println("Ya estamos en choco");
        level2.setDetenerParabolaSlingshot(true);
        level2.rebote(-(level2.vX), (level2.vY));
    }

    public void changeIcon(String path) {
        Image nuevaImagen = new ImageIcon(path).getImage();
        imagen.setImage(nuevaImagen);
    }
}
