import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class GamePanel extends JPanel implements ActionListener {
    private static final int PANEL_WIDTH = 500;
    private static final int PANEL_HEIGHT = 500;
    private int appleX;
    private int appleY;
    private static final int UNIT_SIZE = 20;
    private static final int UNITS_AMOUNT = (PANEL_HEIGHT * PANEL_WIDTH) / UNIT_SIZE;
    private int score;
    private static int DELAY = 150;
    private Timer timer;
    private int[] xCord = new int[UNITS_AMOUNT];
    private int[] yCord = new int[UNITS_AMOUNT];
    private int bodyParts=3;
    private char direction = 'R';
    private boolean running = false;
    private Random random;
    private ArrayList<Clip> voices=new ArrayList<>();
    private String[] spritesPath=new String[14];
    private ImageIcon[] imgSprites= new ImageIcon[spritesPath.length];
    private JButton restartButton;
    private boolean sfx=true;
    private boolean music=true;
    private boolean response=false;
    private static JButton menuButton;

    public GamePanel() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        random = new Random();
        loadImages();
        loadUpPanel();
        loadManu();
        addRestartButton();
        addMenuButton();


    }

    public void startGame() throws UnsupportedAudioFileException, LineUnavailableException, IOException {

        //repaint();
        running = true;
        appleCoordinates();
        timer = new Timer(DELAY, this);
        timer.start();
        music("src\\music\\HadesOutOfTartarus.wav",20);

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(response)
            draw(g);

    }
    public void draw(Graphics g) {
        if(running) {
            /*
            for (int i = 0; i < PANEL_HEIGHT / UNIT_SIZE; i++)
                g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, PANEL_HEIGHT);
            for (int i = 0; i < PANEL_WIDTH / UNIT_SIZE; i++)
                g.drawLine(0, i * UNIT_SIZE, PANEL_WIDTH, i * UNIT_SIZE);

            //g.setColor(Color.RED);

            //g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

             */
            g.drawImage(imgSprites[10].getImage(), appleX, appleY, 25, 25, null, null);

            for (int i = 0; i < bodyParts-2; i++) {
                if (i == 0) {
                    if (direction == 'U')
                        g.drawImage(imgSprites[0].getImage(), xCord[0], yCord[0], 25, 25, null, null);
                    if (direction == 'D')
                        g.drawImage(imgSprites[1].getImage(), xCord[0], yCord[0], 25, 25, null, null);

                    if (direction == 'R')
                        g.drawImage(imgSprites[2].getImage(), xCord[0], yCord[0], 25, 25, null, null);
                    if (direction == 'L')
                        g.drawImage(imgSprites[3].getImage(), xCord[0], yCord[0], 25, 25, null, null);


                } else {
                    if (xCord[i] == xCord[i + 2]) {
                        g.drawImage(imgSprites[5].getImage(), xCord[i], yCord[i], 25, 25, null, null);
                        g.drawImage(imgSprites[5].getImage(), xCord[i + 1], yCord[i + 1], 25, 25, null, null);
                        g.drawImage(imgSprites[5].getImage(), xCord[i + 2], yCord[i + 2], 25, 25, null, null);

                    } else if (yCord[i] == yCord[i + 2]) {
                        g.drawImage(imgSprites[4].getImage(), xCord[i], yCord[i], 25, 25, null, null);
                        g.drawImage(imgSprites[4].getImage(), xCord[i + 1], yCord[i + 1], 25, 25, null, null);
                        g.drawImage(imgSprites[4].getImage(), xCord[i + 2], yCord[i + 2], 25, 25, null, null);


                    } else if (xCord[i] > xCord[i + 2] && yCord[i] < yCord[i + 2]) {
                        if (yCord[i] == yCord[i + 1]) {
                            g.drawImage(imgSprites[4].getImage(), xCord[i], yCord[i], 25, 25, null, null);
                            g.drawImage(imgSprites[6].getImage(), xCord[i + 1], yCord[i + 1], 25, 25, null, null);
                            g.drawImage(imgSprites[5].getImage(), xCord[i + 2], yCord[i + 2], 25, 25, null, null);
                            i += 1;
                        } else {
                            g.drawImage(imgSprites[5].getImage(), xCord[i], yCord[i], 25, 25, null, null);
                            g.drawImage(imgSprites[8].getImage(), xCord[i + 1], yCord[i + 1], 25, 25, null, null);
                            g.drawImage(imgSprites[4].getImage(), xCord[i + 2], yCord[i + 2], 25, 25, null, null);
                            i += 1;
                        }
                    } else if (xCord[i] > xCord[i + 2] && yCord[i] > yCord[i + 2]) {
                        if (yCord[i] == yCord[i + 1]) {
                            g.drawImage(imgSprites[4].getImage(), xCord[i], yCord[i], 25, 25, null, null);
                            g.drawImage(imgSprites[9].getImage(), xCord[i + 1], yCord[i + 1], 25, 25, null, null);
                            g.drawImage(imgSprites[5].getImage(), xCord[i + 2], yCord[i + 2], 25, 25, null, null);
                            i += 1;
                        } else {
                            g.drawImage(imgSprites[5].getImage(), xCord[i], yCord[i], 25, 25, null, null);
                            g.drawImage(imgSprites[7].getImage(), xCord[i + 1], yCord[i + 1], 25, 25, null, null);
                            g.drawImage(imgSprites[4].getImage(), xCord[i + 2], yCord[i + 2], 25, 25, null, null);
                            i += 1;
                        }
                    } else if (xCord[i] < xCord[i + 2] && yCord[i] < yCord[i + 2]) {
                        if (xCord[i] < xCord[i + 1]) {
                            g.drawImage(imgSprites[4].getImage(), xCord[i], yCord[i], 25, 25, null, null);
                            g.drawImage(imgSprites[7].getImage(), xCord[i + 1], yCord[i + 1], 25, 25, null, null);
                            g.drawImage(imgSprites[5].getImage(), xCord[i + 2], yCord[i + 2], 25, 25, null, null);
                            i += 1;
                        } else {
                            g.drawImage(imgSprites[5].getImage(), xCord[i], yCord[i], 25, 25, null, null);
                            g.drawImage(imgSprites[9].getImage(), xCord[i + 1], yCord[i + 1], 25, 25, null, null);
                            g.drawImage(imgSprites[4].getImage(), xCord[i + 2], yCord[i + 2], 25, 25, null, null);
                            i += 1;
                        }
                    } else if (xCord[i] < xCord[i + 2] && yCord[i] > yCord[i + 2]) {
                        if (xCord[i] == xCord[i + 1]) {
                            g.drawImage(imgSprites[5].getImage(), xCord[i], yCord[i], 25, 25, null, null);
                            g.drawImage(imgSprites[6].getImage(), xCord[i + 1], yCord[i + 1], 25, 25, null, null);
                            g.drawImage(imgSprites[4].getImage(), xCord[i + 2], yCord[i + 2], 25, 25, null, null);
                            i += 1;

                        } else {
                            g.drawImage(imgSprites[4].getImage(), xCord[i], yCord[i], 25, 25, null, null);
                            g.drawImage(imgSprites[8].getImage(), xCord[i + 1], yCord[i + 1], 25, 25, null, null);
                            g.drawImage(imgSprites[5].getImage(), xCord[i + 2], yCord[i + 2], 25, 25, null, null);
                            i += 1;
                        }
                    }
                }
                if (i == bodyParts - 1) {
                    if (xCord[i] == xCord[i - 1])
                        g.drawImage(imgSprites[5].getImage(), xCord[i], yCord[i], 25, 25, null, null);
                    if (yCord[i] == yCord[i - 1])
                        g.drawImage(imgSprites[4].getImage(), xCord[i], yCord[i], 25, 25, null, null);
                }


                g.setColor(Color.red);
                g.setFont(new Font("Ink Free", Font.BOLD, 30));
                FontMetrics metrics = getFontMetrics(g.getFont());
                g.drawString("Score: " + score, (PANEL_WIDTH - metrics.stringWidth("Score")) / 2, g.getFont().getSize());
            }
        }
        else{
            gameOver(g);
        }





    }
    public void appleCoordinates() {
        appleX = random.nextInt((int) PANEL_WIDTH / UNIT_SIZE) * UNIT_SIZE;
        appleY = random.nextInt((int) PANEL_HEIGHT / UNIT_SIZE) * UNIT_SIZE;
    }
    public void checkApple() throws InterruptedException, UnsupportedAudioFileException, LineUnavailableException, IOException {

        if (xCord[0] == appleX && yCord[0] == appleY) {

            music("src\\music\\Apple Crunch.wav",0);
            Thread.sleep(20);
            score++;
            if (direction=='D') {
                if(xCord[bodyParts-1]!=0) {

                    xCord[bodyParts] = xCord[bodyParts-1];
                    yCord[bodyParts] = yCord[bodyParts-1]-UNIT_SIZE;
                    bodyParts++;
                }
            }
            else if(direction=='U'){
                if(xCord[bodyParts-1]!=PANEL_HEIGHT) {

                    xCord[bodyParts] = xCord[bodyParts-1];
                    yCord[bodyParts] = yCord[bodyParts-1]+UNIT_SIZE;
                    bodyParts++;
                }

            }
            else if(direction=='L'){
                if(xCord[bodyParts-1]!=PANEL_HEIGHT) {

                    xCord[bodyParts] = xCord[bodyParts-1] +UNIT_SIZE;
                    yCord[bodyParts] = yCord[bodyParts-1];
                    bodyParts++;
                }

            }
            if(DELAY>60) {
                timer.setDelay(DELAY - 2);
                DELAY -= 2;
            }
            boolean isInPlace=false;
            int x=0;
            int y=0;
            if(!isInPlace){
                x= random.nextInt((int) PANEL_WIDTH / UNIT_SIZE) * UNIT_SIZE;
                y = random.nextInt((int) PANEL_HEIGHT / UNIT_SIZE) * UNIT_SIZE;
                for(int i=0;i<bodyParts;i++){
                    if(xCord[i]!=x ||yCord[i]!=y)
                        isInPlace=true;
                }
            }


            appleX=x;
            appleY=y;

        }




    }
    public void checkCollision() {

            if (xCord[0] < 0)
                running = false;
            if (xCord[0] == PANEL_WIDTH)
                running = false;
            if (yCord[0] < 0)
                running = false;
            if (yCord[0] == PANEL_HEIGHT)
                running = false;


       for(int i=1;i<bodyParts;i++){
           if(xCord[0]==xCord[i] &&yCord[0]==yCord[i]){
               running=false;
           }

        }
       if(!running)
           timer.stop();


    }
    public void gameOver(Graphics g) {
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free",Font.BOLD,75));
        FontMetrics metrics1=getFontMetrics(g.getFont());
        g.drawString("Game Over",(PANEL_WIDTH-metrics1.stringWidth("Game Over"))/2,PANEL_HEIGHT/2);

        g.setFont(new Font("Ink Free",Font.BOLD,30));
        FontMetrics metrics2=getFontMetrics(g.getFont());
        g.drawString("Score: "+score,(PANEL_WIDTH-metrics2.stringWidth("Score"))/2-20,g.getFont().getSize());







        restartButton.setVisible(true);
        menuButton.setVisible(true);
        restartButton.enableInputMethods(true);
        menuButton.enableInputMethods(true);



    }
    public void move() {
        for (int i =bodyParts-1; i > 0; i--) {
            xCord[i] = xCord[i - 1];
            yCord[i] = yCord[i - 1];
        }
        if (direction == 'R')
            xCord[0] += UNIT_SIZE;
        if (direction == 'L')
            xCord[0] -= UNIT_SIZE;
        if (direction == 'U')
            yCord[0] -= UNIT_SIZE;
        if (direction == 'D')
            yCord[0] += UNIT_SIZE;


    }
    @Override
    public void actionPerformed(ActionEvent e) {

        if(running){
            move();
            try {
                checkApple();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            } catch (UnsupportedAudioFileException unsupportedAudioFileException) {
                unsupportedAudioFileException.printStackTrace();
            } catch (LineUnavailableException lineUnavailableException) {
                lineUnavailableException.printStackTrace();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            checkCollision();
        }
        if(!running){
            for(int i=0;i<voices.size();i++){
                if(voices.get(i)!=null){
                    if(voices.get(i).isActive())
                        voices.get(i).stop();
                }
            }
        }
        repaint();





    }
    public void loadImages(){
        spritesPath[0]="src\\images\\snake up.png";
        spritesPath[1]="src\\images\\snake down.png";
        spritesPath[2]="src\\images\\snake right.png";
        spritesPath[3]="src\\images\\snake left.png";
        spritesPath[4]="src\\images\\snake body.png";
        spritesPath[5]="src\\images\\snake body up-down.png";
        spritesPath[6]="src\\images\\turn right or down.png";
        spritesPath[7]="src\\images\\turn left or down.png";
        spritesPath[8]="src\\images\\turn left or up.png";
        spritesPath[9]="src\\images\\turn right or up.png";
        spritesPath[10]="src\\images\\apple .png";
        spritesPath[11]="src\\images\\settings.png";
        spritesPath[12]="src\\images\\music settings.png";
        spritesPath[12]="src\\images\\start game.png";
        for(int i=0;i<spritesPath.length;i++){
            imgSprites[i]=new ImageIcon(spritesPath[i]);
        }
    }
    public void loadUpPanel(){
        this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));

        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
    }
    public void loadManu(){
        ImageIcon imgStartGame=new ImageIcon("src\\images\\start game.png");
        JButton startGameButton=new JButton(imgStartGame);
        startGameButton.setBorder(BorderFactory.createEmptyBorder());
        startGameButton.setBounds(150,300,215,40);
        startGameButton.setVisible(true);


        ImageIcon imgSettings=new ImageIcon("src\\images\\settings.png");
        JButton settings=new JButton(imgSettings);
        settings.setBorder(BorderFactory.createEmptyBorder());
        settings.setBounds(90,100,30,30);
        settings.setVisible(true);



        ImageIcon imgMusicSettings=new ImageIcon("src\\images\\music settings.png");
        JButton musicSettings=new JButton(imgMusicSettings);
        musicSettings.setBorder(BorderFactory.createEmptyBorder());
        musicSettings.setBounds(410,100,30,30);
        musicSettings.setVisible(true);

        startGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                settings.setVisible(false);
                musicSettings.setVisible(false);
                startGameButton.setVisible(false);
                response=true;
                try {
                    startGame();
                    repaint();
                } catch (UnsupportedAudioFileException ex) {
                    ex.printStackTrace();
                } catch (LineUnavailableException ex) {
                    ex.printStackTrace();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        setLayout(null);
        add(startGameButton);
        add(settings);
        add(musicSettings);repaint();
        settings.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuButton.setVisible(true);
                settings.setVisible(false);
                musicSettings.setVisible(false);
                startGameButton.setVisible(false);

            }
        });

        musicSettings.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuButton.setVisible(true);
                settings.setVisible(false);
                musicSettings.setVisible(false);
                startGameButton.setVisible(false);
            }
        });

    }
    public void music(String path,float soundLevel) throws UnsupportedAudioFileException, IOException, LineUnavailableException {

        Clip clip;

        String filePath=path;
        AudioInputStream audioInputStream =
                AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
        clip=AudioSystem.getClip();

        clip.open(audioInputStream);
        FloatControl gainControl =
                (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(-soundLevel); // Reduce volume by 10 decibels.
        clip.start();
        voices.add(clip);


    }
    private void addRestartButton() {
        //String buttonText = "New Game?";

        ImageIcon imgEndGame1=new ImageIcon("src\\images\\New Game.png");
        restartButton = new JButton(imgEndGame1);



        restartButton.setBorder(BorderFactory.createEmptyBorder());



        restartButton.setBounds(150,300,200,40);
        restartButton.setVisible(false);

        add(restartButton);


        restartButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    restart();
                } catch (UnsupportedAudioFileException ex) {
                    ex.printStackTrace();
                } catch (LineUnavailableException ex) {
                    ex.printStackTrace();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

        });






    }
    private void addMenuButton(){
        ImageIcon menuImg=new ImageIcon("src\\images\\menu.png");
        menuButton=new JButton(menuImg);
        menuButton.setBorder(BorderFactory.createEmptyBorder());
        menuButton.setBounds(200,400,100,40);
        menuButton.setVisible(false);
        add(menuButton);

        menuButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    new GameFrame();
                } catch (UnsupportedAudioFileException ex) {
                    ex.printStackTrace();
                } catch (LineUnavailableException ex) {
                    ex.printStackTrace();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

        });
    }
    public void loadSettings(){
        JRadioButton classicGame=new JRadioButton("Classic");
    }
    public void restart() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
         bodyParts=3;
         direction = 'R';
         score=0;
         DELAY=150;
         restartButton.setVisible(false);

         for(int i=0;i<this.xCord.length;i++){
             xCord[i]=0;
             yCord[i]=0;
         }
        menuButton.setVisible(false);
        revalidate();
         startGame();

         repaint();





    }
    public class MyKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){
            switch(e.getKeyCode()){
                case KeyEvent.VK_LEFT:{
                    if(direction!='R')
                        direction='L';
                    break;
                }
                case KeyEvent.VK_RIGHT:{
                    if(direction!='L')
                        direction='R';
                    break;
                }
                case KeyEvent.VK_UP:{
                    if(direction!='D')
                        direction='U';
                    break;
                }
                case KeyEvent.VK_DOWN:{
                    if(direction!='U')
                        direction='D';
                    break;
                }


            }
            try {
                Thread.sleep(1);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }

        }

    }

    }


