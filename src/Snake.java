import java.util.ArrayList;
import java.util.List;

public class Snake {
    private Direction direction;

    public Snake(int StartX, int StartY){
        body.add(new Position(StartX,StartY));
        direction = Direction.UP;

    }


    private List<Position> body = new ArrayList<>();
    public void move(Direction newDirection, boolean ateFood) {
        direction = newDirection;

        int dx = 0, dy = 0;
        if (direction == Direction.RIGHT) dx = 1;
        else if (direction == Direction.LEFT) dx = -1;
        else if (direction == Direction.UP) dy = -1;
        else if (direction == Direction.DOWN) dy = 1;

        Position newHead = body.get(0).moveBy(dx, dy);
        body.add(0, newHead);
        if(!ateFood)
            body.remove(body.size() - 1);

    }
    boolean checkSelfCollision(){
        Position head = body.get(0);
        for(int i=1;i< body.size();i++){
            if (head.equals(body.get(i)))
                return true;
        }
        return false;
    }

    public List<Position> getBody() {
        return new ArrayList<>(body);
    }
    public Position getNextHeadPosition(Direction dir) {

        int dx = 0, dy = 0;
        if (dir == Direction.RIGHT) dx = 1;
        else if (dir == Direction.LEFT) dx = -1;
        else if (dir == Direction.UP) dy = -1;
        else if (dir == Direction.DOWN) dy = 1;
        return new Position( body.get(0).getX()+ dx, body.get(0).getY() + dy);


    }
}


