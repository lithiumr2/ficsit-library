package ficsit.library;

import arc.Events;
import mindustry.game.EventType.WorldLoadEvent;
import mindustry.mod.Mod;
import mindustry.game.Team;
import mindustry.world.Tile;
import mindustry.content.Items;
import static mindustry.Vars.*;

public class FicsitLibrary extends Mod {

    public FicsitLibrary() {
        Events.on(WorldLoadEvent.class, e -> {
            spawnSecretCore();
        });
    }

    private void spawnSecretCore() {
        Tile targetTile = world.tile(0, 0); 
        if (targetTile != null) {
            targetTile.setNet(ficsit.library.content.FicsitBlocks.secretCore, Team.sharded, 0);
            Team.sharded.core().items.add(Items.copper, 150);
            Team.sharded.core().items.add(Items.lead, 100);
        }
    }

    @Override
    public void loadContent() {
        ficsit.library.content.FicsitBlocks.load();
        ficsit.library.content.FicsitUnits.load();
    }
}
