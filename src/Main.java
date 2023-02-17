import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;

public class Main extends Canvas implements Runnable{
    private BufferStrategy bs;

    private boolean running = false;
    private Thread thread;
    private String out = "8765", num1, num2;
    private int operatorPosition;
    private int highlightX, highlightY;
    private boolean overSquere = false;


    Font myFont = new Font("Ink Free",1,30);

    public Main() {

        setSize(570,620);
        JFrame frame = new JFrame();
        frame.add(this);
        this.requestFocusInWindow();
        frame.addKeyListener(new MyKeyListener());
        this.addMouseMotionListener(new MyMouseMotionListener());
        this.addMouseListener(new MyMouseListener());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }


    public void render() {
        bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();

        // Rita ut den nya bilden
        draw(g);

        g.dispose();
        bs.show();
    }
    public void draw(Graphics g) {
        g.clearRect(0,0,getWidth(),getHeight());
        g.setFont(myFont);
        g.setColor(new Color(0));
        drawValues(g);
        drawScreen(g);
        drawButtons(highlightX,highlightY,overSquere,g);

    }
    private void drawValues(Graphics g){
        g.setColor(new Color(0));
        g.drawString("out="+out,20,60);
        g.drawString("num1="+num1,20,590);
        g.drawString("num2="+num2,200,590);
        g.drawString("op="+operatorPosition,450,590);
    }
    private void drawScreen(Graphics g){
        g.setColor(Color.black);
        g.fillRect(90,70,390,60);
        g.setColor(Color.darkGray);
        g.fillRect(95,75,380,50);
        g.setColor(Color.white);
        g.drawString(out, 120,110);
    }
    private void drawButtons(int highlightX, int highlightY,boolean overSquere, Graphics g){
        Color grey = Color.gray;
        Color black = Color.BLACK;
        for (int x = 0; x < 4; x++){
            for (int y = 0; y < 4; y++){
                g.setColor(grey);
                g.fillRect(x * 100 + 90,y * 100 + 160,90,90);
                g.setColor(black);
                g.drawRect(x * 100 + 90,y * 100 + 160, 90,90);
            }
        }
        g.setFont(new Font("Comic sans",1,40));
        g.setColor(black);
        g.drawString("7",125,219);
        g.drawString("8",225,219);
        g.drawString("9",325,219);
        g.drawString("C",421,219);

        g.drawString("4",125,319);
        g.drawString("5",225,319);
        g.drawString("6",325,319);
        g.fillOval(420,275,30,60);
        g.fillOval(405,290,60,30);

        g.drawString("1",125,419);
        g.drawString("2",225,419);
        g.drawString("3",325,419);

        g.drawString("0",125,519);
        g.drawString("CLR",295,519);
        g.fillRect(405,485,60,15);
        g.fillRect(405,510,60,15);
        if (overSquere){
            g.setColor(new Color(0x2F00FF));
            g.drawRect(highlightX * 100 + 90, highlightY * 100 + 160,90,90);
            g.drawRect(highlightX * 100 + 91, highlightY * 100 + 161,88,88);
        }
    }
    private void update() {

    }

    public static void main(String[] args) {
        Main minGrafik = new Main();
        minGrafik.start();
    }

    public synchronized void start() {
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    public synchronized void stop() {
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        double ns = 1000000000.0 / 25.0;
        double delta = 0;
        long lastTime = System.nanoTime();

        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;

            while(delta >= 1) {
                // Uppdatera koordinaterna
                update();
                // Rita ut bilden med updaterad data
                render();
                delta--;
            }
        }
        stop();
    }

    public class MyMouseMotionListener implements MouseMotionListener {

        @Override
        public void mouseDragged(MouseEvent e) {
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            if (e.getY() >= 160 && e.getY() <= 250){ // rad 1
                if (e.getX() >= 90 && e.getX() <= 180) {
                    highlightX = 0;
                    highlightY = 0;
                    overSquere = true;
                }else if (e.getX() >= 190 && e.getX() <= 280) {
                    highlightX = 1;
                    highlightY = 0;
                    overSquere = true;
                }else if (e.getX() >= 290 && e.getX() <= 380) {
                    highlightX = 2;
                    highlightY = 0;
                    overSquere = true;
                } else if (e.getX() >= 390 && e.getX() <= 480) {
                    highlightX = 3;
                    highlightY = 0;
                    overSquere = true;
                } else {
                    overSquere = false;
                }
            } else if (e.getY() >= 260 && e.getY() <= 350){ // rad 2
                if (e.getX() >= 90 && e.getX() <= 180) {
                    highlightX = 0;
                    highlightY = 1;
                    overSquere = true;
                }else if (e.getX() >= 190 && e.getX() <= 280) {
                    highlightX = 1;
                    highlightY = 1;
                    overSquere = true;
                }else if (e.getX() >= 290 && e.getX() <= 380) {
                    highlightX = 2;
                    highlightY = 1;
                    overSquere = true;
                }else if (e.getX() >= 390 && e.getX() <= 480) {
                    highlightX = 3;
                    highlightY = 1;
                    overSquere = true;
                }else {
                    overSquere = false;
                }
            }else if (e.getY() >= 360 && e.getY() <= 450){
                if (e.getX() >= 90 && e.getX() <= 180) {
                    highlightX = 0;
                    highlightY = 2;
                    overSquere = true;
                }else if (e.getX() >= 190 && e.getX() <= 280) {
                    highlightX = 1;
                    highlightY = 2;
                    overSquere = true;
                }else if (e.getX() >= 290 && e.getX() <= 380) {
                    highlightX = 2;
                    highlightY = 2;
                    overSquere = true;
                }else {
                    overSquere = false;
                }
            }else if (e.getY() >= 460 && e.getY() <= 550){
                if (e.getX() >= 90 && e.getX() <= 180) {
                    highlightX = 0;
                    highlightY = 3;
                    overSquere = true;
                }else if (e.getX() >= 290 && e.getX() <= 380) {
                    highlightX = 2;
                    highlightY = 3;
                    overSquere = true;
                }else if (e.getX() >= 390 && e.getX() <= 480) {
                    highlightX = 3;
                    highlightY = 3;
                    overSquere = true;
                }else {
                    overSquere = false;
                }
            }else {
                overSquere = false;
            }

        }
    }

    public class MyMouseListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {

            if (e.getY() >= 160 && e.getY() <= 250){
                if (e.getX() >= 90 && e.getX() <= 180) {
                    out += '7';
                }
                if (e.getX() >= 190 && e.getX() <= 280) {
                    out += '8';
                }
                if (e.getX() >= 290 && e.getX() <= 380) {
                    out += '9';
                }
                if (e.getX() >= 390 && e.getX() <= 480){
                    out = out.substring(0,out.length()-1);
                }
            } // rad 1

            if (e.getY() >= 260 && e.getY() <= 350){
                if (e.getX() >= 90 && e.getX() <= 180) {
                    out += '4';
                }
                if (e.getX() >= 190 && e.getX() <= 280) {
                    out += '5';
                }
                if (e.getX() >= 290 && e.getX() <= 380) {
                    out += '6';
                }
                if (e.getX() >= 390 && e.getX() <= 480) {
                    num1 = out;
                    operatorPosition = out.length();
                    out += '#';
                }
            } // rad 2

            if (e.getY() >= 360 && e.getY() <= 450){
                if (e.getX() >= 90 && e.getX() <= 180) {
                    out += '1';
                }
                if (e.getX() >= 190 && e.getX() <= 280) {
                    out += '2';
                }
                if (e.getX() >= 290 && e.getX() <= 380) {
                    out += '3';
                }
            } // rad 3

            if (e.getY() >= 460 && e.getY() <= 550){
                if (e.getX() >= 90 && e.getX() <= 180) {
                    out += '0';
                }
                if (e.getX() >= 290 && e.getX() <= 380) {
                    out = "";
                }
                if (e.getX() >= 390 && e.getX() <= 480) {
                    num2 = out.substring(operatorPosition + 1);
                    out = (Integer.parseInt(num1) + Integer.parseInt(num2)) + "";
                }
            } // rad 4

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
    }

    public class MyKeyListener implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
            System.out.println("key code = " + e.getKeyCode());
            if (e.getKeyCode() == 8) {
                out = out.substring(0,out.length()-1);
            }
            if (e.getKeyChar() == '1') {
                out += '1';
            }
            if (e.getKeyChar()=='2'){
                out += '2';
            }
            if (e.getKeyChar()=='3'){
                out += '3';
            }
            if (e.getKeyChar()=='4'){
                out += '4';
            }
            if (e.getKeyChar()=='5'){
                out += '5';
            }
            if (e.getKeyChar()=='6'){
                out += '6';
            }
            if (e.getKeyChar()=='7'){
                out += '7';
            }
            if (e.getKeyChar()=='8'){
                out += '8';
            }
            if (e.getKeyChar()=='9'){
                out += '9';
            }
            if (e.getKeyChar()=='0'){
                out += '0';
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
        }

    }
}