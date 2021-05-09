package sk.ukf.snake;

import java.util.ArrayList;

public class Snake {

    public class SnakePart {
        int x;
        int y;

        public SnakePart(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    private ArrayList<SnakePart> snake;
    private int direction = 1;

    public Snake() {
        snake = new ArrayList<>();
        snake.add(new SnakePart(Game.getGridX()*5, Game.getGridY()*10));
        snake.add(new SnakePart(Game.getGridX()*6, Game.getGridY()*10));
        snake.add(new SnakePart(Game.getGridX()*7, Game.getGridY()*10));
        snake.add(new SnakePart(Game.getGridX()*8, Game.getGridY()*10));
        snake.add(new SnakePart(Game.getGridX()*9, Game.getGridY()*10));
        snake.add(new SnakePart(Game.getGridX()*10, Game.getGridY()*10));
    }

    public void move() {
        int dx = 0, dy = 0;
        switch (direction) {
            case 1: dx = Game.getGridX(); break;
            case 2: dy = Game.getGridY(); break;
            case 3: dx = -Game.getGridX(); break;
            case 4: dy = -Game.getGridY(); break;
            case 5: dx = Game.getGridX(); dy = -Game.getGridY(); break;
            case 6: dx = Game.getGridX(); dy = Game.getGridY(); break;
            case 7: dx = -Game.getGridX(); dy = Game.getGridY(); break;
            case 8: dx = -Game.getGridX(); dy = -Game.getGridY(); break;
        }

        SnakePart head = snake.get(snake.size()-1);
        int x = head.x + dx;
        int y = head.y + dy;

        if (x < 0) x = Game.getScreen().x - Game.getGridX();
        else if (x >= Game.getScreen().x) x = 0;
        if (y < 0) y = Game.getScreen().y - Game.getGridY();
        else if (y >= Game.getScreen().y) y = 0;

        SnakePart newHead = new SnakePart(x, y);
        snake.add(newHead);
        snake.remove(0);
    }

    public int getDirection() { return direction; }
    public void setDirection(int direction) { this.direction = direction; }
    public ArrayList<SnakePart> getSnake() { return snake; }
    public SnakePart getHead() { return snake.get(snake.size()-1); }
}
