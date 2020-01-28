package lighthouse.spaceinvaders.model;

import java.util.Arrays;
import java.util.stream.IntStream;

import lighthouse.util.IntVec;

public class AlienSwarm {
    private final Alien[] aliens;
    private final int direction; // 1 or -1
    private final int steps;
    private final int maxSteps;
    
    public AlienSwarm(IntVec topLeft, int rows, int cols, int spacing, int maxSteps) {
        aliens = IntStream.range(0, rows)
            .boxed()
            .flatMap(y -> IntStream.range(0, cols)
                .mapToObj(x -> new Alien(topLeft.add(x * spacing, y * spacing))))
            .toArray(Alien[]::new);
        direction = 1;
        steps = 0;
        this.maxSteps = maxSteps;
    }
    
    private AlienSwarm(Alien[] aliens, int direction, int steps, int maxSteps) {
        this.aliens = aliens;
        this.direction = direction;
        this.steps = steps;
        this.maxSteps = maxSteps;
    }
    
    public Alien[] getAliens() { return aliens; }
    
    public AlienSwarm flipDirection() { return new AlienSwarm(aliens, -direction, steps, maxSteps); }
    
    public AlienSwarm step() {
        int nextSteps = steps + 1;
        int nextDirection = direction;
        IntVec delta = new IntVec(direction, 0);

        if (nextSteps > maxSteps) {
            nextDirection = -direction;
            nextSteps = 0;
            delta = new IntVec(0, 1);
        }
        
        IntVec alienDelta = delta;
        Alien[] nextAliens = Arrays.stream(aliens)
            .map(a -> a.movedBy(alienDelta))
            .toArray(Alien[]::new);

        return new AlienSwarm(nextAliens, nextDirection, nextSteps, maxSteps);
    }
}
