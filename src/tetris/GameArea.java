package tetris;

import javax.swing.*;
import java.awt.*;

public class GameArea extends JPanel {
    private final int gridRows;
    private final int gridColumns;
    private final int gridCellSize;
    TetrisBlock block;
    Color[][] background;

    public GameArea(int columns) {
        gridColumns = columns;

        setBounds(100, 100, 300, 300);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        gridCellSize = getWidth() / gridColumns;
        gridRows = getHeight() / gridCellSize;

        background = new Color[gridRows][gridColumns];
    }

    public void spawnBlock() {
        block = new TetrisBlock();
        block.spawn(gridColumns);
    }

    public boolean moveBlockDown() {
        if (checkFloor()) {
            return false;
        }
        block.moveDown();
        repaint();
        return true;
    }

    public boolean checkFloor() {
        if (block.getBottomEdge() >= gridRows) {
            return true;
        }
        int[][] shape = block.getShape();
        int w = block.getWidth();
        int h = block.getHeight();

        for (int col = 0; col < w; col++) {
            for (int row = h - 1; row >= 0; row--) {
                if (shape[row][col] != 0) {
                    int x = col + block.getX();
                    int y = row + block.getY() + 1;

                    if (y < 0) break;
                    if (background[y][x] != null) {
                        return true;
                    }
                    break;
                }
            }
        }

        return false;
    }

    public boolean checkLeftBackground() {
        if (block.getLeftEdge() == 0) {
            return true;
        }
        int[][] shape = block.getShape();
        int w = block.getWidth();
        int h = block.getHeight();

        for (int row = 0; row < h; row++) {
            for (int col = 0; col < w; col++) {
                if (shape[row][col] != 0) {
                    int x = col + block.getX() - 1;
                    int y = row + block.getY();
                    if (y < 0) break;
                    if (background[y][x] != null) {
                        return true;
                    }
                    break;
                }
            }
        }

        return false;
    }

    private boolean checkRightBackground() {
        if (block.getRightEdge() == gridColumns) {
            return true;
        }
        int[][] shape = block.getShape();
        int w = block.getWidth();
        int h = block.getHeight();

        for (int row = 0; row < h; row++) {
            for (int col = w - 1; col >= 0; col--) {
                if (shape[row][col] != 0) {
                    int x = col + block.getX() + 1;
                    int y = row + block.getY();
                    if (y < 0) break;
                    if (background[y][x] != null) {
                        return true;
                    }
                    break;
                }
            }
        }

        return false;
    }

    public boolean isBlockOutOfBounds() {
        if (block.getY() < 0) {
            block = null;
            return true;
        }
        return false;
    }

    public int clearLines() {
        int linesCleared = 0;
        boolean lineFilled;
        for (int r = gridRows - 1; r >= 0; r--) {
            lineFilled = true;
            for (int c = 0; c < gridColumns; c++) {
                if (background[r][c] == null) {
                    lineFilled = false;
                    break;
                }
            }
            if (lineFilled) {
                linesCleared++;
                clearLine(r);
                shiftDown(r);
                clearLine(0);

                r++;

                repaint();
            }
        }
        return linesCleared;
    }

    private void shiftDown(int r) {
        for (int row = r; row > 0; row--) {
            for (int col = 0; col < gridColumns; col++) {
                background[row][col] = background[row - 1][col];
            }
        }
    }

    private void clearLine(int r) {
        for (int i = 0; i < gridColumns; i++) {
            background[r][i] = null;
        }
    }

    public void moveBlockLeft() {
        if (block == null) return;
        if (!checkLeftBackground()) {
            block.moveLeft();
            repaint();
        }
    }

    public void moveBlockRight() {
        if (block == null) return;
        if (!checkRightBackground()) {
            block.moveRight();
            repaint();
        }
    }


    public void dropBlock() {
        if (block == null) return;
        while (!checkFloor()) {
            block.moveDown();
        }
        repaint();
    }

    public void rotateBlock() {
        if (block == null) return;
        if (!checkFloor()) {
            block.rotate();
            repaint();
        }
    }

    private void drawBlock(Graphics g) {
        int h = block.getHeight();
        int w = block.getWidth();
        Color c = block.getColor();
        int[][] shape = block.getShape();

        for (int row = 0; row < h; row++) {
            for (int col = 0; col < w; col++) {
                if (shape[row][col] == 1) {
                    int x = (block.getX() + col) * gridCellSize;
                    int y = (block.getY() + row) * gridCellSize;

                    drawGridSquare(x, y, c, g);
                }
            }
        }
    }

    public void moveBlockToBackground() {
        int[][] shape = block.getShape();
        int h = block.getHeight();
        int w = block.getWidth();

        int xPos = block.getX();
        int yPos = block.getY();

        Color color = block.getColor();

        for (int r = 0; r < h; r++) {
            for (int c = 0; c < w; c++) {
                if (shape[r][c] == 1) {
                    background[r + yPos][c + xPos] = color;
                }
            }
        }
    }

    private void drawBackground(Graphics g) {
        Color color;
        for (int row = 0; row < gridRows; row++) {
            for (int col = 0; col < gridColumns; col++) {
                color = background[row][col];
                int x = col * gridCellSize;
                int y = row * gridCellSize;

                if (color != null) {
                    drawGridSquare(x, y, color, g);
                }
            }
        }
    }

    public void drawGridSquare(int x, int y, Color color, Graphics g) {
        g.setColor(color);
        g.fillRect(x, y, gridCellSize, gridCellSize);
        g.setColor(Color.BLACK);
        g.drawRect(x, y, gridCellSize, gridCellSize);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        drawBackground(g);
        drawBlock(g);
    }
}
