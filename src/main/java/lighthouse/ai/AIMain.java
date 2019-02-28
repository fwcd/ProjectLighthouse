package lighthouse.ai;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lighthouse.model.Board;
import lighthouse.model.Brick;
import lighthouse.model.Direction;
import lighthouse.model.Level;
import lighthouse.ui.board.controller.BoardPlayController;

public class AIMain {
    private static final Logger LOG = LoggerFactory.getLogger(AIMain.class);
    
    ArrayList<Model> population;
    BoardPlayController controller;
    int size;
    Random r = new Random();

    public AIMain(int pop){
        size = pop;
        this.population = new ArrayList<>(pop);
        for (int i = 0; i < pop; i++){
            Model m = new Model();
            m.addConv(3, 3);
            m.addConv(2, 1);
            m.addConv(1, 2);
            m.addDense(3);
            m.addDense(2);
            m.addDense(1);
            population.add(m);
        }
    }

    public void train(Level level){
        Board start = level.getStart();
        Board goal = level.getGoal();

        controller = new BoardPlayController(start, () -> {});

        for (Model m : population){
            Board current = start.copy();
            int i = 0;
            while(!current.equals(goal) && i < 200){
                i += 1;
                current = nextTurn(m, current);
                LOG.trace("Currently in round {}", i);
            }
            m.fitness += current.equals(goal) ? 1 - i/200 : -1;
        }

        population.sort(null);
        LOG.info("Fitnesses: {}", population);

        for (int i = 0; i < size/2; i++){
            ArrayList<Double> m = population.get(i + size/2).getWeights();
            ArrayList<Double> f = population.get(i + size/2 - 1 ).getWeights();
            ArrayList<Double> c = new ArrayList<Double>();

            for (int o = 0; o < m.size(); o++){
                if (r.nextDouble() > 0.5){
                    c.add(m.get(o));
                }else{
                    c.add(f.get(o));
                }
            }

            Model n = new Model(c);
            n.mutateWeights();
            population.set(i, n);
        }
    }

    private Board nextTurn(Model m, Board b){
        controller.setCurrentBoard(b);
        Collection<Brick> bricks =  b.getBricks();
        double max = -1;
        Board best = null;
        for (Brick brick : bricks){
            controller.setCurrentBrick(brick);
            Map<Direction, Integer> limits = controller.getCurrentLimits();
            for (Direction dir : Direction.values()){
                if(limits.get(dir) > 0){
                    Board c = b.copy();
                    c.replace(brick, brick.movedInto(dir));
                    double rating = m.feed(c.encode2D());
                    if (rating > max){
                        best = c;
                        max = rating;
                    }
                }
            }
        }
        return best;
    }

    
}
