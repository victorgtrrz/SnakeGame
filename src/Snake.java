
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Toolkit;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.image.BufferedImage;

public class Snake extends JFrame {

    int width = 640;
    int height = 500;
    int widthPoint = 10;
    int heightPoint = 10;
    int direccion = KeyEvent.VK_LEFT;

    Point snake;
    Point score;

    String points = "Score: 0";
    int pointsvalue = 0;
    String timer = "Time: 0";
    int timervalue = 0;

    ArrayList<Point> body;
    ArrayList<Point> rocks;

    ImagenSnake imagenSnake; 

    Random rand = new Random();  

    Color color2 = Color.BLUE;
    
    static boolean gameOver = false;

    public static void tableScore(String scoretext) {

        JFrame frame = new JFrame("Score table");
        frame.setSize(300,300);
        frame.getContentPane().setBackground(Color.WHITE);
        
        String points = scoretext; 

        JTextArea ta = new JTextArea();
        ta.append(points);
        ta.setBounds(100, 100, 100, 100);
        ta.setBackground(Color.WHITE);
        
        frame.add(ta);
        frame.setLayout(null);
        frame.setVisible(true);

    }

    public Snake() {

        setTitle("Snake game by Victoret");
        setSize(width, height);
        getContentPane().setBackground(Color.YELLOW);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2 - width/2, dim.height/2 - height/2);

        Keyboard keys = new Keyboard();
        this.addKeyListener(keys);

        startGame();

        imagenSnake = new ImagenSnake();

        this.getContentPane().add(imagenSnake);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setVisible(true);

        Momento momento = new Momento();
        Thread trid = new Thread(momento);
        trid.start();

    }

    public static void main(String[] args) throws Exception {
        tableScore("Score: 0");
        new Snake();
        //System.out.println(KeyEvent.VK_LEFT);
        AudioInputStream Stream = AudioSystem.getAudioInputStream(new File("src/BSO.wav").getAbsoluteFile());
        Clip clip = AudioSystem.getClip();
        clip.open(Stream);
        clip.start();
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void startGame() {
        snake = new Point(width/2, height/2);
        body = new ArrayList<>();
        rocks = new ArrayList<>();
        body.add(snake);
        score = new Point(200,200);
    }

    public void updatePoints () {
        pointsvalue += 10;
        points = "Score: " + pointsvalue; 
        repaint();
    }

    public void updateTimer () {
        timervalue += 1;
        timer = "Time: " + timervalue; 
        repaint();
    }

    public void newPoint() {
        int w = rand.nextInt(550) + 40;
        int h = rand.nextInt(370) + 40;
        if (w%10 != 0) {
            w -= w%10;
        }
        if (h%10 != 0) {
            h -= h%10;
        }
        score = new Point(w,h);
    }

    public void newRock() {
        int w = rand.nextInt(550) + 40;
        int h = rand.nextInt(370) + 40;
        if (w%10 != 0) {
            w -= w%10;
        }
        if (h%10 != 0) {
            h -= h%10;
        }
        Point rock = new Point(w,h);
        rocks.add(rock);
    }

    public class ImagenSnake extends JPanel {
        public void paintComponent(Graphics g) {
            BufferedImage img = getImage("image.png");
            super.paintComponent(g);
            g.setColor(new Color (30,30,30));
            g.fillRect(0,0,width,height);
            g.drawImage(img, 0, 0, null);
            for (int i = 0; i<body.size(); i++) {
                g.setColor(new Color (0,0,255));
                Point bodyunit = body.get(i);
                g.fillRect(bodyunit.x, bodyunit.y, widthPoint, heightPoint);
            }
            
            g.setColor(new Color (255,0,0));
            g.fillRect(score.x, score.y, widthPoint, heightPoint);

            for (int i = 0; i<rocks.size(); i++)  {
                Point rock = rocks.get(i);
                g.setColor(new Color (60,60,60));
                g.fillRect(rock.x, rock.y, widthPoint, heightPoint);
            }

            if (gameOver) {
                g.setColor(Color.BLACK);
                g.setFont(new Font("VT323", Font.PLAIN, 50)); 
                FontMetrics fm = g.getFontMetrics();
                Rectangle2D rect = fm.getStringBounds("GAME OVER :(", g);
                g.drawString("GAME OVER :(", (int) (width/2 - rect.getWidth()/2), height/2);
                
            }

            g.setColor(Color.WHITE);
            g.setFont(new Font("VT323", Font.PLAIN, 25)); 
            FontMetrics fm = g.getFontMetrics();
            Rectangle2D rect = fm.getStringBounds(points, g);
            g.drawString(points, (int) (width - 130 - rect.getWidth()/2), 450);

            FontMetrics fm2 = g.getFontMetrics();
            Rectangle2D rect2 = fm2.getStringBounds(timer, g);
            g.drawString(timer, (int) (50 + rect2.getWidth()/2), 450);


        }
    }
    
    public class Momento extends Thread {
    
        long last = 0;
        long lastRocks = 0;
        long lastTimer = 0;
        long frecuencia = 50;  //miliSeconds
        long frecRocks = 30000;
        long frecTimer = 1000;

        
            public void run() {
    
                while(!gameOver) {
                    if ((java.lang.System.currentTimeMillis() - last) > frecuencia) {
                        if (direccion == KeyEvent.VK_UP) {
                            snake.y -= heightPoint;
                        }
                        else if (direccion == KeyEvent.VK_DOWN) {
                            snake.y += heightPoint;
                        }
                        else if (direccion == KeyEvent.VK_RIGHT) {
                            snake.x += widthPoint;
                        }
                        else if (direccion == KeyEvent.VK_LEFT) {
                            snake.x -= widthPoint;
                        }
                        try {
                            actualizar();
                        } catch (LineUnavailableException e) {
                            e.printStackTrace();
                        }
                    last = java.lang.System.currentTimeMillis();
                    }
                
                    if (java.lang.System.currentTimeMillis() - lastRocks > frecRocks) {
                        newRock();
                        lastRocks = java.lang.System.currentTimeMillis();
                    }

                    if (java.lang.System.currentTimeMillis() - lastTimer > frecTimer) {
                        updateTimer();
                        lastTimer = java.lang.System.currentTimeMillis();
                    }

                }
            }
        }

    public void actualizar() throws LineUnavailableException {
        imagenSnake.repaint();
        Sound sound = new Sound();

        body.add(0,new Point(snake.x, snake.y));
        body.remove(body.size()-1);

        if ((snake.x < 40) || (snake.x > 590) || (snake.y < 40) || (snake.y > 400)) {
            gameOver=true;
            sound.gameover();
        }

        for (int i = 1; i<body.size(); i++) {
            Point pcheck = body.get(i);
            if ((pcheck.x == snake.x) && (pcheck.y == snake.y)) {
                gameOver=true;
                sound.gameover();
            }
        }

        for (int i = 0; i<rocks.size(); i++) {
            Point rock = rocks.get(i);
            if ((rock.x == snake.x) && (rock.y == snake.y)) {
                gameOver=true;
                sound.gameover();
            }
        }

        if ((snake.x == score.x) && (snake.y == score.y)) {
            newPoint();
            body.add(0,new Point(snake.x, snake.y));
            sound.soundpoint();
            updatePoints();
        }

    }

    public class Keyboard extends KeyAdapter { 
        public void keyPressed(KeyEvent e) {
            if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                System.exit(0);
            } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                if (direccion != KeyEvent.VK_DOWN) {
                    direccion = KeyEvent.VK_UP;
                }
            } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                if (direccion != KeyEvent.VK_UP) {
                    direccion = KeyEvent.VK_DOWN;
                }
            } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                if (direccion != KeyEvent.VK_RIGHT) {
                    direccion = KeyEvent.VK_LEFT;
                }
            } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                if (direccion != KeyEvent.VK_LEFT) {
                    direccion = KeyEvent.VK_RIGHT;
                }
            }
        }
    }

    private BufferedImage getImage(String filename) {
        try {                  
            InputStream in = getClass().getResourceAsStream(filename);
            return ImageIO.read(in);
        } catch (IOException e) {
            System.out.println("The image was not loaded.");
        }
        return null;
    }
}

