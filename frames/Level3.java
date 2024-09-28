package frames;

import Entities.Cat3;
import Entities.Palo;
import Entities.Pez;
import Physics.LogicaColisiones;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;

public class Level3 extends JPanel implements KeyListener {

    @Override
    public void keyPressed(KeyEvent e) { //Cuando presiono la "R"
        if (e.getKeyCode() == KeyEvent.VK_R) { //Cuando se presiona la tecla R
            // Llamar al método que deseas cuando se presione la tecla "R"
            numGato += 1;
            cerrarFrame();
            System.out.println("Se agrega otro gato");

            switch (numGato) { //Cambia la imagen del gato
                case 1:
                case 3:
                    gato.changeIcon("./resources/cat2.png");
                    break;
                case 2:
                case 4:
                    gato.changeIcon("./resources/cat3.png");
                    break;
            }

            gatoX = 100;
            gatoY = 500;
            setDetenerParabolaSlingshot(false);
            detenerRebote(false);
            repaint();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // No es necesario implementar este método
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // No es necesario implementar este método
    }

    //Cerrando FRAME
    private void cerrarFrame() {
        Timer timer = new Timer(5, new ActionListener() {
            private double time = 0.0;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (puntaje >= 2400) {
                    //Mensaje ganador
                    JPanel panel = new JPanel();
                    panel.setBackground(Color.WHITE);
                    JLabel label = new JLabel("Ganaste");
                    label.setFont(new Font("Arial", Font.BOLD, 24));
                    panel.add(label);
                    JOptionPane.showMessageDialog(Level3.this, panel, "¡Felicitaciones!", JOptionPane.PLAIN_MESSAGE);
                    System.out.println("Ya se va a cerrar el frame");
                    clip1.stop();
                    clip1.close();
                    clip2.stop();
                    clip2.close();
                    Window window = SwingUtilities.getWindowAncestor(Level3.this);
                    window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
                    ((Timer) e.getSource()).stop(); // Detener el timer
                    
                } else if (puntaje < 2400 && numGato == 3) {
                    //Mensaje perdedor
                    JPanel panel1 = new JPanel();
                    panel1.setBackground(Color.WHITE);
                    JLabel label1 = new JLabel("Perdiste");
                    label1.setFont(new Font("Arial", Font.BOLD, 24));
                    panel1.add(label1);
                    JOptionPane.showMessageDialog(Level3.this, panel1, "¡No le sabes!", JOptionPane.PLAIN_MESSAGE);
                    System.out.println("Ya se va a cerrar el frame");
                    clip1.stop();
                    clip1.close();
                    clip2.stop();
                    clip2.close();
                    Window window = SwingUtilities.getWindowAncestor(Level3.this);
                    window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
                    ((Timer) e.getSource()).stop(); // Detener el timer
                }
            }

        });

        timer.start();
    }

    public interface GatoPositionListener { //Interfaz para ver posicion del gato

        void onGatoPositionChanged(int x, int y);
    }
    private GatoPositionListener positionListener;

    public void setGatoPositionListener(GatoPositionListener listener) {
        this.positionListener = listener;
    }

    //Variables
    private Clip clip1;
    private Clip clip2;
    private JFrame frame;
    public int numGato = 0;
    public Cat3 gato;
    private Pez pescado[];
    private Palo palo[]; //Arreglo de palos para construir estructuras
    private int slingshotX = 100; // Posición X de la resortera
    private int slingshotY = 500; // Posición Y de la resortera
    private int gatoX = 100; // Posición X del gato
    private int gatoY = 500; // Posición Y del gato
    private int mouseX, mouseY; // Coordenadas del ratón al hacer clic
    private boolean dragging; // Indica si se está arrastrando el gato
    private int radio = 80; // Radio límite de movimiento del gato
    private double velocidad = 3.8; // Esta es nuestra velocidad inicial
    private ImageIcon resortera;
    LogicaColisiones logicaColisiones = new LogicaColisiones();
    private boolean detenerParabolaSlingshot = false;
    private boolean detenerRebote = false;
    public double vX, vY;
    private double gravedad;
    private BufferedImage backgroundImage; // Imagen de fondo
    int numNiveles = 2;
    int numPaloPorNivel = 3;
    int numPeces = 4;
    int puntaje = 0;

    //CONSTRUCTOR
    public Level3() {
        // Registrar la instancia de Level1 como el KeyListener
        this.frame = frame;
        addKeyListener(this);
        setFocusable(true);
        // Configurar el panel principal
        setPreferredSize(new Dimension(1280, 720));
        setLayout(null); //lo pongo nulo para acomodar elementos manualmente
        reproducirSonido("./resources/opcion3.wav");
        // Cargamos las imagenes
        palo = new Palo[numNiveles * numPaloPorNivel * 2];
        gato = new Cat3("./resources/cat1.png", this);
        pescado = new Pez[numPeces];

//Generador de estructuras de Palos y pescados
        int index = 0;
        int numA;
        Random random = new Random();
        for (int nivel = 0; nivel < numNiveles; nivel++) {
            int nivelOffset = nivel * numPaloPorNivel;

            palo[index] = new Palo(950, 520 - nivelOffset * 32, "./resources/plvertical.png", "V");
            palo[index + 1] = new Palo(1110, 520 - nivelOffset * 32, "./resources/plvertical.png", "V");
            palo[index + 2] = new Palo(950, 540 - nivelOffset * 32, "./resources/plhorizontal.png", "H");
            index += numPaloPorNivel;
            palo[index] = new Palo(750, 520 - nivelOffset * 32, "./resources/plvertical.png", "V");
            palo[index + 1] = new Palo(910, 520 - nivelOffset * 32, "./resources/plvertical.png", "V");
            palo[index + 2] = new Palo(750, 540 - nivelOffset * 32, "./resources/plhorizontal.png", "H");
            index += numPaloPorNivel;
        }
        for (int i = 0; i < numPeces; i++) {
            numA = random.nextInt(3) + 1;
            if (numA == 1 || numA == 2) {
                pescado[i] = new Pez(1010, 500 - (3 * i) * 32, "./resources/fish" + (numA) + ".png", numA);
                i+=1;
                pescado[i] = new Pez(800, 500 - (3 * (i-1)) * 32, "./resources/fish" + (numA) + ".png", numA);
            } else {
                pescado[i] = new Pez(1000, 480 - (3 * (i-1)) * 32, "./resources/bigfish.png", numA);
                i+=1;
                pescado[i] = new Pez(800, 480 - (3 * (i-1)) * 32, "./resources/bigfish.png", numA);
            }
        }
        resortera = new ImageIcon(("./resources/slingshot.png"));

        // Cargamos la imagen de fondo
        try {
            backgroundImage = ImageIO.read(new File("./resources/game_background.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Agregar listeners de ratón
        addMouseListener(
                new MouseAdapter() { //Evento de presionar y soltar boton del mouse
            @Override
            public void mousePressed(MouseEvent e) {
                System.out.println("Aqui es MOUSE PRESSED");
                //Obtiene poisicion de donde se presiona con el mouse
                mouseX = e.getX();
                mouseY = e.getY();

                if (mouseX >= gatoX && mouseX <= gatoX + 50
                        && mouseY >= gatoY && mouseY <= gatoY + 40) { //El +100 y el + 80 indican los limites de la imagen del gato
                    dragging = true; //indica que se esta arrastrando el gato
                }
            }

            // El evento de soltar el ratón genera el disparo del gato
            @Override
            public void mouseReleased(MouseEvent e) { //Realiza calculo con Pitagoras y componentes de velocidad
                if (dragging) {
                    dragging = false; //reincia el valor
                    double distanceX = slingshotX - gatoX; //calcula distancias entre resortera y gato
                    double distanceY = slingshotY - gatoY;
                    int distance = (int) Math.sqrt(Math.pow(distanceX, 2) + Math.pow(distanceY, 2)); //pitagoraso 
                    double speed = (velocidad * distance) / 15; //obtiene una velocidad

                    // Calculamos el ángulo de disparo
                    double x = 0;
                    double y = getHeight();

                    double angle = Math.atan2(distanceY, distanceX);

                    System.out.println("Distancia entre el gato y la resortera: " + distance);
                    System.out.println("Distancia Y" + distanceY);
                    System.out.println("Distancia X" + distanceX);
                    System.out.println("Velocidad V" + speed);

                    System.out.println("El angulo en grados" + Math.toDegrees(-1 * angle));
                    // Calculamos las componentes de la velocidad
                    double vx = speed * Math.cos(angle);
                    double vy = speed * Math.sin(angle);
                    if (mouseY > slingshotY) { //Para detectar si se apunta hacia abajo
                        vy *= -1; //La componente de la velocidad la hace negativa para que se dispare hacia abajo
                    }

                    // Disparar el gato
                    setGravedad(9.8);
                    System.out.println("X = " + vX + "Y= " + vY);
                    maullar("./resources/miau.wav");
                    dispararGato(vx, vy); //Le pasa componentes de la velocidad
                    vX = vx;
                    vY = vy;
                }
            }
        }
        );

        addMouseMotionListener(
                new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e
            ) { //simplemente permite ver al gato moviendose
                if (dragging) {                      //cuando estamos calculando el angulo del disparo
                    // Calculamos la distancia entre dos puntos
                    int distance = (int) Math.sqrt(Math.pow(e.getX() - slingshotX, 2) + Math.pow(e.getY() - slingshotY, 2));
                    if (distance <= radio) {
                        gatoX = e.getX();
                        gatoY = e.getY();
                    } else {
                        // Obtenemos el valor del ángulo. arctan(y/x)
                        double angle = Math.atan2(e.getY() - slingshotY, e.getX() - slingshotX);

                        gatoX = (int) (slingshotX + Math.cos(angle) * radio);
                        gatoY = (int) (slingshotY + Math.sin(angle) * radio);
                    }

                    repaint(); //Actualiza imagen del gato

                }
            }
        }
        );
    }

    //SETTERS
    public void setDetenerParabolaSlingshot(boolean value) {
        detenerParabolaSlingshot = value;
    }

    public void detenerRebote(boolean value) {
        detenerRebote = value;
    }

    public void setGravedad(double g) {
        gravedad = g;
    }

    private void reproducirSonido(String archivo) {
        try {
            // Carga el archivo de sonido
            File soundFile = new File(archivo);
            clip1 = AudioSystem.getClip();
            clip1.open(AudioSystem.getAudioInputStream(soundFile));

            // Reproduce el sonido en bucle
            clip1.loop(Clip.LOOP_CONTINUOUSLY);

            // Agrega un LineListener para detectar el final de la reproducción
            clip1.addLineListener(new LineListener() {
                public void update(LineEvent event) {
                    if (event.getType() == LineEvent.Type.STOP) {
                        clip1.close();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void maullar(String archivo) {
        try {
            // Carga el archivo de sonido
            File soundFile = new File(archivo);
            clip2 = AudioSystem.getClip();
            clip2.open(AudioSystem.getAudioInputStream(soundFile));

            // Reproduce el sonido una vez
            clip2.start();
            // Agrega un LineListener para detectar el final de la reproducción
            clip2.addLineListener(new LineListener() {
                public void update(LineEvent event) {
                    if (event.getType() == LineEvent.Type.STOP) {
                        clip2.close();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //DETECTOR DE COLISIONES 

    private void detectarColisiones() {
        for (int i = 0; i < palo.length; i++) {
            if (logicaColisiones.loChocaron(gato, palo[i])) {
                if (numGato == 1) {
                    System.out.println("GATO NUEVO CHOOCOOOOOOOOO");
                }
                System.out.println("¡Colisión detectada!");
                System.out.println("Ya chocaron con palo " + i);
                if (palo[i].derrumbado != true) {
                    logicaColisiones.chocoConMadera(gato, palo[i], 4);
                    logicaColisiones.fisicaDerrumbar(gato, palo[i], 4);
                }
                logicaColisiones.fisicaAplicada = true; // Realizar la animación específica para el palo actual
            }
            //Aqui a la i le sumo 2, porque el orden del arreglo es
            //2 palos de base y uno arriba de esos y asi
//            for (int j = i + 1; j < palo.length; j++) { //Aqui a la i le sumo 2, porque el orden del arreglo es
//                //2 palos de base y uno arriba de esos y asi
//                if ((palo[i].derrumbado && palo[j].derrumbado) == true) {
//                    System.out.println("Se derrumbaron dos palos de base, toca derrumbar los de arriba");
//                    logicaColisiones.fisicaDerrumbar(gato, palo[j], 4);
//                    logicaColisiones.fisicaDerrumbar(gato, palo[i], 4);
//                }
//            }

        }
        //Para derribar al pescado
        for (int i = 0; i < pescado.length; i++) {
            if (logicaColisiones.chocaronAlPez(gato, pescado[i], 4)) {
                if (numGato == 1) {
                    System.out.println("GATO NUEVO CHOOCOOOOOOOOO");
                }
                System.out.println("¡Colisión detectada!");
                System.out.println("Ya chocaron con palo " + i);
                if (pescado[i].derrumbado != true) {
                    logicaColisiones.chocoConMadera(gato, palo[i], 4);
                    logicaColisiones.matarAlPez(pescado[i], 4);
                    puntaje += 300;
                }
                logicaColisiones.fisicaAplicada = true; // Realizar la animación específica para el palo actual
            }
            //Aqui a la i le sumo 2, porque el orden del arreglo es
            //2 palos de base y uno arriba de esos y asi
//            for (int j = i + 1; j < palo.length; j++) { //Aqui a la i le sumo 2, porque el orden del arreglo es
//                //2 palos de base y uno arriba de esos y asi
//                if ((palo[i].derrumbado && palo[j].derrumbado) == true) {
//                    System.out.println("Se derrumbaron dos palos de base, toca derrumbar los de arriba");
//                    logicaColisiones.matarAlPez(pescado[i], 4);
//                    logicaColisiones.matarAlPez(pescado[i], 4);
//                }
//            }

        }
    }

    public void dispararGato(double vx, double vy) { //Animacion de gato en tiro parabolico
        System.out.println("DISPARO");
        if (numGato == 1) {
            System.out.println("OTRO DISPARO");
        }
        System.out.println("La velocidad en x" + vx);
        System.out.println("La velocidad en y" + vy);

        /* El propósito de este temporizador es controlar la animación del gato en movimiento parabólico 
        después de que se dispara. El retardo de 10 milisegundos proporciona una animación fluida y 
        se ajusta a la velocidad de actualización de la interfaz gráfica.*/
        Timer timer = new Timer(3, new ActionListener() {
            private double time = 0.0;

            @Override
            public void actionPerformed(ActionEvent e) {
                //Detecta si ya se debe de detener o no
                if (detenerParabolaSlingshot) {
                    ((Timer) e.getSource()).stop(); // Detener el timer
                }
                // Calcular las coordenadas basadas en el tiempo y las componenetes de la velocidad
                //Son las formulas de tiro parabolico
                double nuevaX = gatoX + vx * time;
                double nuevaY = gatoY - vy * time + (0.5 * gravedad * time * time);

                // Actualizamos las coordenadas
                gatoX = (int) nuevaX;
                gatoY = (int) nuevaY;
                if (positionListener != null) {
                    positionListener.onGatoPositionChanged(gatoX, gatoY);
                }

                System.out.println("Nueva X: " + gatoX);
                System.out.println("Nueva y:" + gatoY);

                detectarColisiones();
                // Incrementamos el tiempo
                time += 0.1;
                repaint(); //Se va actualizando el dibujo del gato

                // En caso de que el gato salga de los bordes
                if (gatoX <= 0 || gatoX >= 1260 || gatoY >= 700 || gatoY <= 0) {
                    detenerRebote = true;
                    setDetenerParabolaSlingshot(true);
                    ((Timer) e.getSource()).stop();
                }

            }
        });

        timer.start();
    }

    public void rebote(double vx, double vy) {
        System.out.println("Gato rebotando");
        Timer timer = new Timer(10, new ActionListener() {
            private double time = 0.0;

            @Override
            public void actionPerformed(ActionEvent e) {
                //Detecta si ya se debe de detener o no
                if (detenerRebote) {
                    ((Timer) e.getSource()).stop(); // Detener el timer
                }
                // Calcular las coordenadas basadas en el tiempo y las componenetes de la velocidad
                //Son las formulas de tiro parabolico
                double nuevaX = gatoX + (vx * time);
                double nuevaY = gatoY + (vy * time) + (0.5 * gravedad * time * time);

                // Actualizamos las coordenadas
                gatoX = (int) nuevaX;
                gatoY = (int) nuevaY;
                if (positionListener != null) {
                    if (numGato == 1) {
                        System.out.println("Si se actualizan coord nuevo " + gatoX + " " + gatoY);
                    }
                    positionListener.onGatoPositionChanged(gatoX, gatoY);
                }

                System.out.println("Nueva X: " + gatoX);
                System.out.println("Nueva y:" + gatoY);
                if (numGato == 1) {
                    System.out.println("GATO NUEVO EN REBOTE");
                }
                detectarColisiones();
                // Incrementamos el tiempo
                time += 0.1;
                repaint(); //Se va actualizando el dibujo del gato

                // En caso de que el gato salga de los bordes
                if (gatoX <= 0 || gatoX >= 1260 || gatoY >= 700 || gatoY <= 0) {
                    detenerRebote = true;
                    setDetenerParabolaSlingshot(true);
                    ((Timer) e.getSource()).stop();
                }

            }
        });

        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        // Dibujar la imagen de fondo
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
        }
        if (gatoX + gato.getCatIcon().getIconWidth() > getWidth()) {
            gatoX = getWidth() - gato.getCatIcon().getIconWidth(); // Ajusta la posición del gato en el eje X
        }

        if (gatoY + gato.getCatIcon().getIconHeight() > getHeight()) {
            gatoY = getHeight() - gato.getCatIcon().getIconHeight(); // Ajusta la posición del gato en el eje Y
        }
        // Dibujar el puntaje
        String texto = "Puntaje = " + puntaje;
        Font font = new Font("Showcard Gothic", Font.PLAIN, 26);
        g2d.setFont(font);
        g2d.setColor(Color.BLACK);
        g2d.drawString(texto, 50, 50);

        // Dibujamos la imagen del gato y de la resortera
        g2d.drawImage(gato.getCatIcon().getImage(), gatoX, gatoY, null);
        System.out.println("Ruta de la imagen del gato: " + gato.getCatIcon().toString());
        g2d.drawImage(resortera.getImage(), slingshotX, slingshotY, null); //Le pasa donde se posiciona la resortera
        //Aqui tambien iria un for que pintaria todos los palosv y todos los palosh
        for (int i = 0; i < palo.length; i++) {
            g2d.drawImage(palo[i].getPaloIcon().getImage(), palo[i].getX(), palo[i].getY(), null);
        }
        for (int i = 0; i < pescado.length; i++) {
            g2d.drawImage(pescado[i].getPezIcon().getImage(), pescado[i].getX(), pescado[i].getY(), null);
        }
    }

    public static void main(String[] args) {
        Level3 level1 = new Level3();

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame("AngryCats");
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                frame.add(level1);

                // Establecer level1 como el GatoPositionListener de gato
                level1.setGatoPositionListener(level1.gato);

                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });

        // Crear una instancia de LogicaColisiones
        // Iniciar un hilo de ejecución para verificar colisiones y aplicar la física
        Thread collisionThread = new Thread(() -> {
            while (true) {
                //System.out.println("Evaluando si chocan");
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //Detectar colisiones
                //level1.detectarColisiones();
            }
        });

        // Iniciar la ejecución del hilo
        collisionThread.start();
    }

}
