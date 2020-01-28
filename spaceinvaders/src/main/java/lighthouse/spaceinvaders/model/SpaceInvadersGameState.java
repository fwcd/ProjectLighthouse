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
import lighthouse.util.DoubleRect;
import lighthouse.util.DoubleVec;
import lighthouse.util.Flag;
import lighthouse.util.IntVec;
import lighthouse.util.LighthouseConstants;

public class SpaceInvadersGameState extends BaseGameState {
    private final int boardWidth;
    private final int boardHeight;

    private List<Projectile> flyingProjectiles = new ArrayList<>();
    private List<Shield> shields = new ArrayList<>();
    private AlienSwarm swarm;
    private Cannon cannon;
    
    public SpaceInvadersGameState() {
        this(LighthouseConstants.COLS, LighthouseConstants.ROWS);
    }
    
    public SpaceInvadersGameState(int boardWidth, int boardHeight) {
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        
        int maxSteps = 4;
        int spacing = 2;
        swarm = new AlienSwarm(new DoubleVec(1, 1), 2, (boardWidth - maxSteps) / spacing, spacing, maxSteps);
        
        int cannonWidth = 4;
        int cannonHeight = 2;
        cannon = new Cannon(new DoubleRect(boardWidth / 2 - cannonWidth / 2, boardHeight - 2 * cannonHeight, cannonWidth, cannonHeight));
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
        if (cannon.hitBy(projectile)) {
            // TODO: Handle player hits
            return true;
        }

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
    
    public void moveCannon(int dx) {
        cannon = cannon.movedBy(dx);
    }
    
    public AlienSwarm getSwarm() { return swarm; }
    
    public List<Projectile> getFlyingProjectiles() { return Collections.unmodifiableList(flyingProjectiles); }
    
    public List<Shield> getShields() { return Collections.unmodifiableList(shields); }
    
    public Cannon getCannon() { return cannon; }

    @Override
    public IntVec getGridSize() { return new IntVec(boardWidth, boardHeight); }
}
