package lighthouse.ai;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lighthouse.model.Board;
import lighthouse.model.Brick;
import lighthouse.model.Direction;
import lighthouse.model.Level;

public class AIMain {
    private static final Logger LOG = LoggerFactory.getLogger(AIMain.class);
    
    private List<Model> population;
    private int size;
    private Random r = new Random();
    private List<Board> forbidden = new ArrayList<>();

    public AIMain(int pop){
        size = pop;
        this.population = new ArrayList<>(pop);
        for (int i = 0; i < pop; i++){
            Model m = new Model();
            m.addConv(3, 3);
            m.addConv(2, 1);
            m.addConv(1, 2);
            m.addDense(-1);
            m.addDense(6);
            m.addDense(3);
            m.addDense(1);
            population.add(m);
        }
    }

    public void train(Level level){
        Board start = level.getStart();
        Board goal = level.getGoal();

        for (Model m : population){
            forbidden.clear();
            m.fitness = 0;
            Board current = start.copy();
            int i = 0;
            while(!current.equals(goal) && i < 200){
                if(forbidden.contains(current)){                    
                    m.fitness += forbidden.size();
                    break;
                }
                forbidden.add(current);
                i += 1;
                current = nextTurn(m, current, goal);
                //LOG.trace("Currently in round {}", i);
            }
            m.fitness += current.equals(goal) ? 200 - i: 0;
        }

        population.sort(null);
        LOG.info("Fitnesses: {}", population.get(size-1));

        for (int i = 0; i < size/2; i++){
            List<Double> m = population.get(i + size/2).getWeights();
            List<Double> f = population.get(i + size/2 - 1 ).getWeights();
            List<Double> c = new ArrayList<Double>();

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

    private Board nextTurn(Model m, Board b, Board g){
        Collection<Brick> bricks =  b.getBricks();
        double max = -1;
        Board best = null;
        for (Brick brick : bricks){
            Map<Direction, Integer> limits = b.getLimitsFor(brick);
            for (Direction dir : Direction.values()){
                if(limits.get(dir) > 0){
                    Board c = b.copy();
                    c.replace(brick, brick.movedInto(dir));
                    double rating = m.feed(c.encode2D(), g.encode2D());
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
