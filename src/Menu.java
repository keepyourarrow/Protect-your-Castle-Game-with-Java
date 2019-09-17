import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Menu  extends MouseAdapter {

    private Game game;
    private Handler handler;
    private KeyInput kInput;

    public Menu (Game game, Handler handler, KeyInput kInput) {
        this.game = game;
        this.handler = handler;
        this.kInput = kInput;
    }

    public void mousePressed(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();

        if (game.gameState == Game.STATE.Menu) {

            // Play button
            if (mouseOver(mx,my,380,190,200,64)) {
                game.gameState = Game.STATE.Game;
            }
            // Help button
            if (mouseOver(mx,my,380,290,200,64)) {
                game.gameState = Game.STATE.Help;
            }
            // Quit button
            if (mouseOver(mx,my,380,390,200,64)) {
                System.exit(1);
            }

        }



    }

    public void mouseReleased(MouseEvent e) {

    }

    private boolean mouseOver(int mx, int my, int x, int y, int width, int height) {
        if (mx > x && mx < x + width) {
            if (my > y && my < y + height)
                return true;
            else return false;
        } else return false;
    }

    public void tick() {

    }
    public void render(Graphics g) {
        if (game.gameState == Game.STATE.Menu) {
            g.setColor(Color.black);
            g.fillRect(0,0,Game.WIDTH,Game.HEIGHT);
            Font f = new Font("arial", 1, 50);
            Font f2 = new Font("arial", 1, 30);

            g.setFont(f);
            g.setColor(Color.white);
            g.drawString("Menu", 415, 135);

            g.setFont(f2);
            g.setColor(Color.white);
            g.drawString("Play", 450, 235);
            g.setColor(Color.white);
            g.drawRect(380, 190, 200, 64);

            g.setFont(f2);
            g.setColor(Color.white);
            g.drawString("Help", 450, 335);
            g.setColor(Color.white);
            g.drawRect(380, 290, 200, 64);

            g.setFont(f2);
            g.setColor(Color.white);
            g.drawString("Quit", 450, 435);
            g.setColor(Color.white);
            g.drawRect(380, 390, 200, 64);


        } else if (game.gameState == Game.STATE.Help) {
            help(g);
        }
    }

    private void help(Graphics g) {
        Font f = new Font("arial", 1, 50);
        Font f2 = new Font("arial", 1, 24);

        g.setColor(Color.black);
        g.fillRect(0,0,Game.WIDTH,Game.HEIGHT);

        g.setFont(f);
        g.setColor(Color.white);
        g.drawString("Help", 415, 135);

        g.setFont(f2);
        g.drawString("Use WASD or Arrow keys to move around. Use Mouse to shoot", 215, 195);
        g.drawString("You are a Wizard, protect your castle from foregn invaders", 215, 265);
        g.drawString("Clear all enemies to win and regain your peace", 215, 335);
        g.drawString("Beware of dodgy enemies and ammo duration", 215, 405);
        g.drawString("You will get a crate of ammo for every 5th enemy killed", 215, 465);
        g.drawString("Press ESC to go back", 25, 535);


    }

}
