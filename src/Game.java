import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Game extends Canvas implements Runnable {
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 600;
    public static final String TITLE ="Protect Your Castle";

    private Thread thread;
    private boolean isRunning = false;

    private Menu menu;

    // Instances
    private Handler handler;
    private KeyInput kInput;
    private Camera camera;
    private SpriteSheet ss;


    private BufferedImage level = null;
    private BufferedImage sprite_sheet = null;
    private BufferedImage floor = null;

    public int ammo = 35;
    public int hp = 100;


    public enum STATE {
        Menu,
        Help,
        Game
    };
    public STATE gameState = STATE.Menu;


    public Game() {
        new Window(WIDTH,HEIGHT,TITLE,this);
        start();
        init();
    }

    public void init() {
        handler = new Handler();
        kInput = new KeyInput(this);
        camera = new Camera(0,0);
        menu = new Menu(this,handler,kInput);

        this.addKeyListener(kInput);
        this.addMouseListener(menu);


        BufferedImageLoader loader = new BufferedImageLoader();
        level = loader.loadImage("/wizard_level.png");
        sprite_sheet = loader.loadImage("sprite_sheet.png");


        ss = new SpriteSheet(sprite_sheet);

        floor = ss.grabImage(4,2,32,32);

        this.addMouseListener(new MouseInput(handler,camera,this,ss));

        loadLevel(level);

    }

     private synchronized void start() {
        // safety belt to avoid a mistake of running the game when the game is already running
        if (isRunning) return;

        thread = new Thread(this);
        thread.start();
        isRunning = true;
    }

    private synchronized void stop() {
        // safety belt to avoid a mistake of stopping the game when the game is already stopped
        if (!isRunning) return;

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        isRunning = false;

    }

    //Game Loop
    public void run() {
        this.requestFocus();
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double nanosecondConversion = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while (isRunning) {
            long now = System.nanoTime();
            delta += (now - lastTime) /nanosecondConversion;
            lastTime = now;
            while (delta >= 1) {
                tick();
                delta--;
            }
            render();
            frames++;

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                frames = 0;
            }
        }
        stop();

    }

    private void tick() {

        if (gameState == STATE.Game) {
            handler.tick();
            for (int i = 0; i < handler.object.size(); i++) {
                if (handler.object.get(i).getId() == ID.Wizard) { // find player
                    camera.tick(handler.object.get(i));  // gives wizard object to camera
                }
            }
            isGameOver();
        } else if (gameState == STATE.Menu || gameState == STATE.Help) {
            menu.tick();
        }
    }

    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();
        Graphics2D g2d = (Graphics2D) g;


        if (gameState == STATE.Game) {
            g2d.translate(-camera.getX(), -camera.getY());

            for (int xx = 0; xx < 30 * 72; xx += 32) {
                for (int yy = 0; yy < 30 * 72; yy += 32) {
                    g.drawImage(floor, xx, yy, null);
                }
            }
            handler.render(g);
            g2d.translate(camera.getX(), camera.getY());

            g.setColor(Color.gray);
            g.fillRect(5, 5, 200, 32);
            g.setColor(Color.green);
            g.fillRect(5, 5, hp * 2, 32);
            g.setColor(Color.black);
            g.drawRect(5, 5, 200, 32);


            g.setColor(Color.white);
            g.drawString("Ammo: " + ammo, 5, 50);
        } else if (gameState == STATE.Menu || gameState == STATE.Help) {
            menu.render(g);
        }

        bs.show();
        g.dispose();
    }

    // Loading level
    private void loadLevel(BufferedImage image) {
        int w = image.getWidth();
        int h = image.getHeight();

        for (int xx = 0; xx < w; xx++) {
            for (int yy = 0; yy < h; yy++) {
                int pixel = image.getRGB(xx, yy);
                int red = (pixel >> 16) & 0xff;
                int green = (pixel >> 8 ) & 0xff;
                int blue = (pixel) & 0xff;

                if (red == 255)
                    handler.addObject(new Block(xx*32, yy*32, ID.Block, ss));

                if (blue == 255 && green == 0)
                    handler.addObject(new Wizard(xx*32,yy*32,ID.Wizard, kInput, handler,this, ss));

                if (green == 255 && blue == 0)
                    handler.addObject(new Enemy(xx*32,yy*32,ID.Enemy, handler, ss));

                if (green == 255 && blue == 255)
                    handler.addObject(new Crate(xx*32,yy*32,ID.Crate, ss));
            }
        }
    }
    // method to determine if the game is over
    public void isGameOver() {
        if (hp <= 0) {
            System.exit(1);
        }
    }


    public static void main(String[] args) {
        new Game();
    }
}
