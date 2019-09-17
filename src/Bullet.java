import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Bullet extends GameObject {

    private Handler handler;

    public Bullet(int x, int y, ID id, Handler handler, int mx, int my, SpriteSheet ss) {

        super(x, y, id, ss);
        this.handler = handler;

        // number 10 is the speed at which bullet is travelling.
        velX = (mx - x) / 10;
        velY = (my - y) / 10;
    }

    public void tick() {
        x += velX;
        y += velY;

        for (int i = 0; i <  handler.object.size(); i++) {
            GameObject tempObject  = handler.object.get(i);

            if (tempObject.getId() == ID.Block) {
                if (getBounds().intersects((tempObject.getBounds()))) {
                    handler.removeObject(this);
                }
            }

        }

    }

    public void render(Graphics g) {
        g.setColor(Color.green);
        g.fillOval(x,y,14,14);
    }

    public Rectangle getBounds() {
        return new Rectangle(x,y,14,14);
    }
}
