package lighthouse.spaceinvaders.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import lighthouse.util.DoubleVec;

public class AlienSwarm implements Iterable<Alien> {
    private final List<Alien> aliens;
    private final int direction; // 1 or -1
    private final double minX;
    private final double maxX;
    
    public AlienSwarm(DoubleVec topLeft, int rows, int cols, int spacing, double maxX) {
        aliens = IntStream.range(0, rows)
            .boxed()
            .flatMap(y -> IntStream.range(0, cols)
                .mapToObj(x -> new Alien(topLeft.add(x * spacing, y * spacing))))
            .collect(Collectors.toList());
        direction = 1;
        minX = topLeft.getX() - 0.01;
        this.maxX = maxX;
    }
    
    private AlienSwarm(List<Alien> aliens, int direction, double minX, double maxX) {
        this.aliens = aliens;
        this.direction = direction;
        this.minX = minX;
        this.maxX = maxX;
    }
    
    public List<Alien> getAliens() { return aliens; }
    
    public AlienSwarm removingAll(Collection<Alien> removed) {
        List<Alien> nextAliens = aliens.stream().filter(a -> !removed.contains(a)).collect(Collectors.toList());
        return new AlienSwarm(nextAliens, direction, minX, maxX);
    }
    
    private double getRightmostX() {
        return aliens.stream().map(Alien::getPosition).mapToDouble(DoubleVec::getX).max().orElse(Double.NEGATIVE_INFINITY);
    }
    
    private double getLeftmostX() {
        return aliens.stream().map(Alien::getPosition).mapToDouble(DoubleVec::getX).min().orElse(Double.POSITIVE_INFINITY);
    }
    
    public AlienSwarm step() {
        int nextDirection = direction;
        DoubleVec delta = new DoubleVec(direction * SpaceInvadersConstants.SWARM_SPEED, 0);

        if (getLeftmostX() < minX || getRightmostX() > maxX) {
            nextDirection = -direction;
            delta = new DoubleVec(SpaceInvadersConstants.SWARM_SPEED * nextDirection, 1.0);
        }
        
        DoubleVec alienDelta = delta;
        List<Alien> nextAliens = aliens.stream()
            .map(a -> a.movedBy(alienDelta))
            .collect(Collectors.toList());

        return new AlienSwarm(nextAliens, nextDirection, minX, maxX);
    }
    
    public List<Projectile> launchProjectiles() {
        List<Projectile> projectiles = new ArrayList<>();
        Random random = ThreadLocalRandom.current();

        for (Alien alien : aliens) {
            if (random.nextDouble() < SpaceInvadersConstants.PROJECTILE_PROBABILITY) {
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
