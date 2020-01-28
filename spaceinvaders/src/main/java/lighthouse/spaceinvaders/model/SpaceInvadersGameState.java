package lighthouse.spaceinvaders.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lighthouse.model.BaseGameState;
import lighthouse.util.Flag;
import lighthouse.util.IntVec;
import lighthouse.util.LighthouseConstants;

public class SpaceInvadersGameState extends BaseGameState {
    private final int boardWidth;
    private final int boardHeight;

    private List<Projectile> flyingProjectiles = new ArrayList<>();
    private List<Shield> shields = new ArrayList<>();
    private AlienSwarm swarm;
    
    public SpaceInvadersGameState() {
        this(LighthouseConstants.COLS, LighthouseConstants.ROWS);
    }
    
    public SpaceInvadersGameState(int boardWidth, int boardHeight) {
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        
        int maxSteps = 4;
        int spacing = 2;
        swarm = new AlienSwarm(new IntVec(1, 1), (boardWidth - maxSteps) / spacing, (boardHeight - maxSteps) / spacing, spacing, maxSteps);
    }
    
    public void advance() {
        swarm = swarm.step();

        flyingProjectiles = Stream.concat(swarm.launchProjectiles().stream(), flyingProjectiles.stream())
            .map(p -> p.fly())
            .filter(p -> !p.isOutOfBounds(boardWidth, boardHeight))
            .collect(Collectors.toCollection(ArrayList::new));
        
        handleCollisions();
    }
    
    private void handleCollisions() {
        Iterator<Projectile> it = flyingProjectiles.iterator();
        
        while (it.hasNext()) {
            Projectile projectile = it.next();
            boolean shouldRemove = handleCollisionsFor(projectile);
            if (shouldRemove) {
                it.remove();
            }
        }
    }
    
    private boolean handleCollisionsFor(Projectile projectile) {
        Set<Alien> removed = new HashSet<>();
        Flag collided = new Flag(false);

        for (Alien alien : swarm) {
            if (alien.hitBy(projectile)) {
                removed.add(alien);
                collided.set(true);
            }
        }
        swarm = swarm.removingAll(removed);
        shields = shields.stream().map(s -> {
            collided.set(true);
            return s.hitBy(projectile) ? s.damage() : s;
        }).collect(Collectors.toList());
        
        return collided.isTrue();
    }
    
    public AlienSwarm getSwarm() { return swarm; }
    
    public List<Projectile> getFlyingProjectiles() { return Collections.unmodifiableList(flyingProjectiles); }

    @Override
    public IntVec getGridSize() { return new IntVec(boardWidth, boardHeight); }
}
