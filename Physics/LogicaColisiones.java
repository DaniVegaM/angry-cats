package Physics;

import Entities.Cat1;
import Entities.Cat2;
import Entities.Cat3;
import Entities.Palo;
import Entities.Pez;
import java.awt.Rectangle;

public class LogicaColisiones {

    public boolean fisicaAplicada; // Variable para controlar si la física ya se aplicó

    public LogicaColisiones() {
        fisicaAplicada = false;
    }

    public boolean loChocaron(Cat2 cat, Palo palo) {
        Rectangle thisBounds = palo.getBounds();
        Rectangle otherBounds = cat.getBounds();
        return thisBounds.intersects(otherBounds);
    }
     public boolean loChocaron(Cat1 cat, Palo palo) {
        Rectangle thisBounds = palo.getBounds();
        Rectangle otherBounds = cat.getBounds();
        return thisBounds.intersects(otherBounds);
    }
      public boolean loChocaron(Cat3 cat, Palo palo) {
        Rectangle thisBounds = palo.getBounds();
        Rectangle otherBounds = cat.getBounds();
        return thisBounds.intersects(otherBounds);
    }

    public void fisicaDerrumbar(Cat2 cat, Palo pl, int velocidad) {
        pl.fall(velocidad);
    }
        public void fisicaDerrumbar(Cat1 cat, Palo pl, int velocidad) {
        pl.fall(velocidad);
    }
            public void fisicaDerrumbar(Cat3 cat, Palo pl, int velocidad) {
        pl.fall(velocidad);
    }

    public void matarAlPez(Pez pez, int velocidad) {
        System.out.println("Muerte al pez");
        pez.fall(velocidad);
    }

    public void chocoConMadera(Cat2 cat, Palo pl, int velocidad) {
        System.out.println("Ya se va a hacer la accion del gato que choco");
        cat.choco(velocidad);
    }
     public void chocoConMadera(Cat1 cat, Palo pl, int velocidad) {
        System.out.println("Ya se va a hacer la accion del gato que choco");
        cat.choco(velocidad);
    }
      public void chocoConMadera(Cat3 cat, Palo pl, int velocidad) {
        System.out.println("Ya se va a hacer la accion del gato que choco");
        cat.choco(velocidad);
    }

    public boolean chocaronAlPez(Cat2 cat, Pez pez, int velocidad) {
        System.out.println("Ya se va a morir el pez");
        Rectangle thisBounds = pez.getBounds();
        Rectangle otherBounds = cat.getBounds();
        return thisBounds.intersects(otherBounds);
    }
    public boolean chocaronAlPez(Cat1 cat, Pez pez, int velocidad) {
        System.out.println("Ya se va a morir el pez");
        Rectangle thisBounds = pez.getBounds();
        Rectangle otherBounds = cat.getBounds();
        return thisBounds.intersects(otherBounds);
    }
    public boolean chocaronAlPez(Cat3 cat, Pez pez, int velocidad) {
        System.out.println("Ya se va a morir el pez");
        Rectangle thisBounds = pez.getBounds();
        Rectangle otherBounds = cat.getBounds();
        return thisBounds.intersects(otherBounds);
    }
}
