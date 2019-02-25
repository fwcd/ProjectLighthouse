package lighthouse.model;


public class Game{

    Board start;
    Board goal;
    Board current;

    boolean won = false;

    public Game(Board start, Board goal){
        this.start = start;
        this.goal = goal;
        current = start.copy();
    }

    public void tick(){
        if(current.equals(goal)) won = true;
    }
}
