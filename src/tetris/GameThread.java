package tetris;

public class GameThread extends Thread {
    GameArea gameArea;
    GameForm gameForm;
    private int level = 1;
    int score = 0;
    private int scorePerLevel = 3;
    private int speedUpPerLevel = 100;
    private int pause = 1000;

    public GameThread(GameArea gameArea, GameForm gameForm) {
        this.gameArea = gameArea;
        this.gameForm = gameForm;
    }


    public void run() {

        while (true) {

            gameArea.spawnBlock();

            while (gameArea.moveBlockDown()) {
                try {
                    Thread.sleep(pause);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            if (gameArea.isBlockOutOfBounds()) {
                System.out.println("Game Over");
                break;
            }

            gameArea.moveBlockToBackground();
            score += gameArea.clearLines();
            gameForm.updateScore(score);

            int lvl = score / scorePerLevel + 1;

            if (lvl > level) {
                level = lvl;
                gameForm.updateLevel(level);
                pause -= speedUpPerLevel;
            }
        }
    }
}
