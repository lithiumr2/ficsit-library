package ficsit.library.entities;

import arc.graphics.g2d.Draw;
import arc.math.Mathf;
import arc.util.Time;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.Vars;
import mindustry.gen.Building;
import mindustry.gen.EntityMapping;
import mindustry.gen.UnitEntity;
import mindustry.world.Tile;
import ficsit.library.content.FicsitBlocks;
import mindustry.graphics.Layer;

public class DropPodEntity extends UnitEntity {
    public float fallTimer = 5f * 60f; // 5 seconds to land
    private boolean landed = false;

    public static int classId = -1;

    public static void register() {
        classId = EntityMapping.register("DropPodEntity", DropPodEntity::new);
    }

    public DropPodEntity() {
        super();
    }

    @Override
    public int classId() {
        return classId;
    }

    @Override
    public void update() {
        super.update();
        
        if (!isAdded() || dead || landed) return;

        fallTimer -= Time.delta;

        if (isShooting() || fallTimer <= 0f) {
            land();
        }
    }

    private void land() {
        if (landed) return;
        landed = true;

        Tile tile = Vars.world.tileWorld(x, y);
        if (tile != null && team != null && team.cores() != null) {
            // Remove the secret core so we don't have multiple cores or game over logic problems
            for (Building core : team.cores().copy()) {
                if (core.block == FicsitBlocks.secretCore) {
                    core.tile.setNet(Vars.content.block("air"), team, 0);
                }
            }
            
            // Place HUB
            if (FicsitBlocks.hub != null) {
                tile.setNet(FicsitBlocks.hub, team, 0);
            }
        }
        
        // Remove the drop pod
        kill();
    }

    @Override
    public void draw() {
        float progress = 1f - Mathf.clamp(fallTimer / (5f * 60f));
        float scale = Mathf.lerp(8f, 1f, progress);
        
        Draw.scl(scale);
        super.draw();
        Draw.scl(1f);
        
        // Sombra opcional debajo de la nave
        Draw.z(Layer.space);
        // ... (we can leave shadow for later)
    }

    @Override
    public void write(Writes write) {
        super.write(write);
        write.f(fallTimer);
    }

    @Override
    public void read(Reads read) {
        super.read(read);
        fallTimer = read.f();
    }
}
