package tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class GameForm extends JFrame {
    GameArea gameArea;
    JLabel scoreDisplay = new JLabel("Score 0");
    JLabel levelDisplay = new JLabel("Level 1");

    public GameForm() {
        JPanel mainPanel = new JPanel();
        scoreDisplay.setSize(new Dimension(100, 50));
        scoreDisplay.setBounds(100, 50, 100, 50);
        levelDisplay.setSize(new Dimension(100, 50));
        levelDisplay.setBounds(350, 50, 100, 50);
        gameArea = new GameArea(10);
        mainPanel.setLayout(null);
        mainPanel.add(gameArea);
        mainPanel.add(scoreDisplay);
        mainPanel.add(levelDisplay);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(500, 500));
        getContentPane().add(mainPanel);
        pack();
        setVisible(true);

        initControls();
        startGame();
    }

    private void initControls() {
        InputMap im = getRootPane().getInputMap();
        ActionMap am = getRootPane().getActionMap();

        im.put(KeyStroke.getKeyStroke("RIGHT"), "right");
        im.put(KeyStroke.getKeyStroke("LEFT"), "left");
        im.put(KeyStroke.getKeyStroke("UP"), "up");
        im.put(KeyStroke.getKeyStroke("DOWN"), "down");

        am.put("right", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameArea.moveBlockRight();
            }
        });
        am.put("left", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameArea.moveBlockLeft();
            }
        });
        am.put("up", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameArea.rotateBlock();
            }
        });
        am.put("down", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameArea.dropBlock();
            }
        });
    }

    public void updateScore(int score) {
        scoreDisplay.setText("Score: " + score);
    }

    public void updateLevel(int level) {
        levelDisplay.setText("Level: " + level);
    }

    public void startGame() {
        new GameThread(gameArea, this).start();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GameForm().setVisible(true);
            }
        });
    }
}
