package lighthouse.spaceinvaders.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lighthouse.model.BaseGameState;
import lighthouse.util.DoubleRect;
import lighthouse.util.DoubleVec;
import lighthouse.util.Flag;
import lighthouse.util.IntVec;
import lighthouse.util.LighthouseConstants;

public class SpaceInvadersGameState extends BaseGameState {
    private static final Logger LOG = LoggerFactory.getLogger(SpaceInvadersGameState.class);

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
        
        int spacing = 2;
        int border = 2;
        swarm = new AlienSwarm(new DoubleVec(1, 1), border, (boardWidth - 4) / spacing, spacing, boardWidth - border);
        
        double cannonWidth = 4;
        double cannonHeight = 1;
        double cannonX = boardWidth / 2 - cannonWidth / 2;
        double cannonY = boardHeight - 2 * cannonHeight;
        cannon = new Cannon(new DoubleRect(cannonX, cannonY, cannonWidth, cannonHeight));
        
        int shieldCount = 4;
        double shieldWidth = 3;
        double shieldHeight = 2;
        double shieldOffset = 2;
        double shieldSpacing = boardWidth / shieldCount;
        double shieldY = cannonY - shieldHeight - 1;
        shields = IntStream.range(0, shieldCount)
            .mapToObj(i -> new Shield(new DoubleRect(shieldOffset + i * shieldSpacing, shieldY, shieldWidth, shieldHeight)))
            .collect(Collectors.toList());
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
        if (!projectile.doesHitAliens() && projectile.collidesWith(cannon)) {
            // TODO: Handle player hits
            LOG.info("Hit player");
            return true;
        }

        Set<Alien> removed = new HashSet<>();
        Flag collided = new Flag(false);

        for (Alien alien : swarm) {
            if (projectile.doesHitAliens() && projectile.collidesWith(alien)) {
                removed.add(alien);
                LOG.info("Hit alien at {}", alien.getBoundingBox());
                collided.set(true);
            }
        }

        swarm = swarm.removingAll(removed);
        shields = shields.stream().filter(shield -> !shield.isDestroyed()).map(shield -> {
            if (projectile.collidesWith(shield)) {
                LOG.info("Hit shield at {}", shield.getBoundingBox());
                collided.set(true);
                return shield.damage();
            } else {
                return shield;
            }
        }).collect(Collectors.toList());
        
        return collided.isTrue();
    }
    
    public void moveCannon(int dx) {
        cannon = cannon.movedBy(dx);
    }
    
    public void fireCannon() {
        flyingProjectiles.add(cannon.shoot());
    }
    
    public AlienSwarm getSwarm() { return swarm; }
    
    public List<Projectile> getFlyingProjectiles() { return Collections.unmodifiableList(flyingProjectiles); }
    
    public List<Shield> getShields() { return Collections.unmodifiableList(shields); }
    
    public Cannon getCannon() { return cannon; }

    @Override
    public IntVec getGridSize() { return new IntVec(boardWidth, boardHeight); }
}
