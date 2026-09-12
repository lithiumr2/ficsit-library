package ficsit.library;

import arc.Events;
import mindustry.game.EventType.WorldLoadEvent;
import mindustry.game.Team;
import mindustry.world.Tile;
import mindustry.gen.Building;
import mindustry.content.Items;
import static mindustry.Vars.*;

public class FicsitLibrary extends FicsitLibraryMod {
    public FicsitLibrary() {
        super();
        Events.on(WorldLoadEvent.class, e -> {
            spawnHubCore();
        });
    }

    private void spawnHubCore() {
        // Spawn the secretCore at (0, 0) on Team.blue (the independent inventory team)
        Tile secretTile = world.tile(0, 0);
        if (secretTile != null) {
            secretTile.setNet(ficsit.library.content.FicsitBlocks.secretCore, Team.blue, 0);
            if (Team.blue.core() != null && Team.blue.core().items != null) {
                Team.blue.core().items.add(Items.copper, 150);
                Team.blue.core().items.add(Items.lead, 100);
            }
        }

        // If there is already a core on the map, replace it with the FICSIT HUB so the player can use it
        if (Team.sharded.core() != null) {
            Building existingCore = Team.sharded.core();
            Tile tile = existingCore.tile;
            if (tile != null && existingCore.block != ficsit.library.content.FicsitBlocks.hub) {
                tile.setNet(ficsit.library.content.FicsitBlocks.hub, Team.sharded, 0);
            }
        } else {
            Tile hubTile = world.tile(10, 10);
            if (hubTile != null) {
                hubTile.setNet(ficsit.library.content.FicsitBlocks.hub, Team.sharded, 0);
            }
        }

        if (Team.sharded.core() != null && Team.sharded.core().items != null) {
            Team.sharded.core().items.add(Items.copper, 300);
            Team.sharded.core().items.add(Items.lead, 200);
        }
    }
}
