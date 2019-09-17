import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseInput extends MouseAdapter {

    private Handler handler;
    private Camera camera;
    private GameObject tempPlayer = null;
    private Game game;
    private SpriteSheet ss;

    public MouseInput(Handler handler, Camera camera,Game game, SpriteSheet ss) {
        this.handler = handler;
        this.camera = camera;
        this.game = game;
        this.ss = ss;
    }

    public void findPlayer() {
        for (int i = 0; i < handler.object.size(); i++) {
            if (handler.object.get(i).getId() == ID.Wizard) {
                tempPlayer = handler.object.get(i);
                break;
            }
        }
    }

    public void mousePressed(MouseEvent e) {

        int mx = e.getX() + (int) camera.getX();
        int my = e.getY() + (int) camera.getY();

        for (int i = 0; i < handler.object.size(); i++) {

            GameObject tempObject = handler.object.get(i);

            if (tempObject.getId() == ID.Wizard && game.ammo >= 1) {
                handler.addObject(new Bullet(tempObject.getX() + 16,
                        tempObject.getY() + 24, ID.Bullet, handler, mx, my, ss));
                game.ammo--;
            }
        }

    }
}
