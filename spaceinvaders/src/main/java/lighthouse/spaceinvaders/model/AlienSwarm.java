package lighthouse.spaceinvaders.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import lighthouse.util.IntVec;

public class AlienSwarm implements Iterable<Alien> {
    private final List<Alien> aliens;
    private final int direction; // 1 or -1
    private final int steps;
    private final int maxSteps;
    
    public AlienSwarm(IntVec topLeft, int rows, int cols, int spacing, int maxSteps) {
        aliens = IntStream.range(0, rows)
            .boxed()
            .flatMap(y -> IntStream.range(0, cols)
                .mapToObj(x -> new Alien(topLeft.add(x * spacing, y * spacing))))
            .collect(Collectors.toList());
        direction = 1;
        steps = 0;
        this.maxSteps = maxSteps;
    }
    
    private AlienSwarm(List<Alien> aliens, int direction, int steps, int maxSteps) {
        this.aliens = aliens;
        this.direction = direction;
        this.steps = steps;
        this.maxSteps = maxSteps;
    }
    
    public List<Alien> getAliens() { return aliens; }
    
    public AlienSwarm removingAll(Collection<Alien> removed) {
        List<Alien> nextAliens = aliens.stream().filter(a -> !removed.contains(a)).collect(Collectors.toList());
        return new AlienSwarm(nextAliens, direction, steps, maxSteps);
    }
    
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
        List<Alien> nextAliens = aliens.stream()
            .map(a -> a.movedBy(alienDelta))
            .collect(Collectors.toList());

        return new AlienSwarm(nextAliens, nextDirection, nextSteps, maxSteps);
    }
    
    public List<Projectile> launchProjectiles() {
        List<Projectile> projectiles = new ArrayList<>();
        Random random = ThreadLocalRandom.current();
        float projectileLaunchProbability = 0.3F;

        for (Alien alien : aliens) {
            if (random.nextFloat() < projectileLaunchProbability) {
                projectiles.add(alien.shoot());
            }
        }
        
        return projectiles;
    }
    
    @Override
    public Iterator<Alien> iterator() {
        return aliens.iterator();
    }
}
