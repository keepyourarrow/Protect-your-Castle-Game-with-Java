import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {

    Game game;

    public boolean keys[] = new boolean[4];
    // key 0 = true  move right
    // key 1 = true move left
    // key 2 = true move up
    // key 3 = true move down

    public KeyInput(Game game) {
        this.game = game;
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if(key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) { keys[0] = true; }
        if(key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) { keys[1] = true; }
        if(key == KeyEvent.VK_W || key == KeyEvent.VK_UP) { keys[2] = true; }
        if(key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN) { keys[3] = true; }


        // If Esc is pressed
        if (key == KeyEvent.VK_ESCAPE) {
            game.gameState = Game.STATE.Menu;
        }

    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if(key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) { keys[0] = false; }
        if(key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) { keys[1] = false; }
        if(key == KeyEvent.VK_W || key == KeyEvent.VK_UP) { keys[2] = false; }
        if(key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN) { keys[3] = false; }

    }
}
