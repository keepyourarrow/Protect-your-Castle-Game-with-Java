import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Wizard extends GameObject {
    // acceleration decceleration
    private float acc = 1f;
    private float dcc = 0.5f;

    private KeyInput keyInput;
    private Handler handler;
    private Game game;
    private BufferedImage[] wizard_image = new BufferedImage[3];

    private Animation anim;

    public Wizard(int x, int y, ID id, KeyInput keyInput,Handler handler, Game game, SpriteSheet ss) {
        super(x, y, id, ss);
        this.keyInput = keyInput;
        this.handler = handler;
        this.game = game;

        wizard_image[0] = ss.grabImage(1,1,32,48);
        wizard_image[1] = ss.grabImage(2,1,32,48);
        wizard_image[2] = ss.grabImage(3,1,32,48);

        anim = new Animation(3,wizard_image[0],wizard_image[1],wizard_image[2]);
    }

    public void tick() {

    x += velX;
    y += velY;

    collision();

    // Horizontal movement
    // key 0 = true  move right
    // key  1 = true move left
        if (keyInput.keys[0]) velX += acc;
        else if (keyInput.keys[1]) velX -= acc;
        else if (!keyInput.keys[0] && !keyInput.keys[1]) {
        if (velX > 0) velX -= dcc;
        else if (velX < 0) velX += dcc;
    }

    // Vertical movement
    // key 2 = true move up
    // key 3 = true  move down
        if (keyInput.keys[2]) velY -= acc;
        else if (keyInput.keys[3]) velY += acc;
        else if (!keyInput.keys[2] && !keyInput.keys[3]) {
        if (velY > 0) velY -= dcc;
        else if (velY < 0) velY += dcc;

        //animation
            anim.runAnimation();

    }

    // makes sure our velocity has a max and min value (otherwise it would get crazy speed)
    velX = clamp(velX, 7, -5);
    velY = clamp(velY, 7, -5);
}

    private float clamp(float value, float max, float min) {
        if (value > max) value = max;
        else if (value <= min) value = min;

        return value;
    }

    private void collision() {
        for (int i = 0; i < handler.object.size(); i++) {
            GameObject tempObject = handler.object.get(i);

            if (tempObject.getId() == ID.Block) { // if we find a block

                // If our Wizard rectangle intersects Block object
                if (getBounds().intersects(tempObject.getBounds())) {
                    x += velX * -2;
                    y += velY * -2;
                }
            }

            if (tempObject.getId() == ID.Crate) { // if we find a crate

                // If our Wizard intersects the crate we add 10 bullets and remove crate
                if (getBounds().intersects(tempObject.getBounds())) {
                    game.ammo += 10;
                    handler.removeObject(tempObject);
                }
            }

            if (tempObject.getId() == ID.Enemy) { // if we find a crate

                // If our Wizard intersects the crate we add 10 bullets and remove crate
                if (getBounds().intersects(tempObject.getBounds())) {
                    game.hp--;
                }
            }

        }
    }

    public void render(Graphics g) {
        if (velX == 0 && velY == 0)
        g.drawImage(wizard_image[0],x,y,null);
        else
            anim.drawAnimation(g,x,y,0);
    }

    public Rectangle getBounds() {
        return new Rectangle((int)x,(int)y,32,48);
    }
}
