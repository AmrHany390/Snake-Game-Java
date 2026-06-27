import java.util.ArrayList;
import java.util.List;
import java.util.Random;
public class GameBoard {
    Snake snake;
    Position food;
    private final int width=20;
    private final int height=20;
    private int score;
    private boolean gameOver;
    private boolean ateFood;
    Random random = new Random();


    public boolean isGameOver() {
        return gameOver;
    }

    public GameBoard(Snake snake, Position food) {
        this.snake = snake;
        this.food = food;
    }

    public int getScore() {
        return score;
    }

    public Position getFood() {
        return food;
    }

    public List<Position> getSnakeBody() {
        return snake.getBody();
    }

    public void update(Direction x){

        Position newHead = snake.getNextHeadPosition(x);

        if (newHead.equals(food)){
            ateFood=true;
            snake.move(x,ateFood);
            ++score;
            Position candidate;
            do {
                int randomX = random.nextInt(width);
                int randomY = random.nextInt(height);
                candidate = new Position(randomX, randomY);
            } while (isOccupied(candidate) );

            food = candidate;



        }
        else{
            ateFood=false;
            snake.move(x,ateFood);
        }
        if (  (snake.checkSelfCollision() || wallCollision(newHead)))
            gameOver=true;






    }
    public boolean wallCollision(Position pos){

        if (pos.getX()<0 || pos.getY()>height-1 || pos.getX()>width-1 || pos.getY()<0)
            return true;
        else
            return false;

    }
    public boolean isOccupied(Position pos){
        for (int i=0;i<snake.getBody().size();i++){
            if (pos.equals(snake.getBody().get(i)))
                return true;
        }
        return false;
    }


}
