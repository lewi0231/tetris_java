package tetris;

import java.awt.*;
import java.util.Random;

public class TetrisBlock {
    private int[][] shape;
    private Color color;
    private int x, y;
    private int[][][] shapes;
    private int rotation;
    private Color[] colors = new Color[]{Color.CYAN, Color.RED, Color.BLACK, Color.YELLOW, Color.GREEN};

    public TetrisBlock() {
        rotation = 0;
        shapes = new int[][][]{{{1, 0}, {1, 0}, {1, 1}}, {{1, 1, 1}, {1, 0, 0}},
                {{1, 1}, {0, 1}, {0, 1}}, {{0, 0, 1}, {1, 1, 1}}};
        this.shape = shapes[0];
    }

    public void spawn(int gridWidth) {
        Random random = new Random();

        rotation = random.nextInt(shapes.length);
        shape = shapes[rotation];
        y = -getHeight();
        x = random.nextInt(gridWidth - getWidth());
        color = colors[random.nextInt(colors.length)];
    }

    public int getBottomEdge() {
        return y + getHeight();
    }

    public int[][] getShape() {
        return shape;
    }

    public Color getColor() {
        return color;
    }

    public int getHeight() {
        return shape.length;
    }

    public int getWidth() {
        return shape[0].length;
    }


    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }


    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void moveDown() {
        y++;
    }

    public void moveLeft() {
        x--;
    }

    public void moveRight() {
        x++;
    }

    public void rotate() {
        rotation = ++rotation % 4;
        shape = shapes[rotation];
    }

    public int getLeftEdge() {
        return getX();
    }

    public int getRightEdge() {
        return getX() + getWidth();
    }
}
