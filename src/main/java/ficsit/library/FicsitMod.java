package ficsit.library;

import arc.Events;
import mindustry.Vars;
import mindustry.content.Blocks;
import mindustry.game.EventType;
import mindustry.game.Rules;
import mindustry.game.Team;
import mindustry.mod.Mod;
import ficsit.library.content.FicsitBlocks;
import ficsit.library.content.FicsitPlanets;
import ficsit.library.content.FicsitUnits;
import ficsit.library.core.ChunkManager;
import ficsit.library.core.FicsitGenerator;
import ficsit.library.core.VirtualWorldManager;

public class FicsitMod extends Mod {

    public FicsitMod() {
        Events.on(EventType.ClientLoadEvent.class, e -> {
            if (Vars.ui != null && Vars.ui.menufrag != null) {
                Vars.ui.menufrag.addButton("Mundo FICSIT (500x500)", () -> {
                    Vars.ui.loadAnd(() -> {
                        Vars.logic.reset();
                        Vars.world.loadGenerator(500, 500, tiles -> {
                            FicsitGenerator.generateChunk(tiles, 0, 0, 0, 0, 500, 500);
                            tiles.getc(250, 250).setBlock(Blocks.coreShard, Team.sharded);
                        });
                        Vars.state.rules = new Rules();
                        Vars.state.rules.sector = null;
                        Vars.state.rules.editor = false;
                        Vars.state.rules.canGameOver = false;
                        Vars.state.rules.infiniteResources = true;
                        Vars.logic.play();
                        Events.fire(EventType.Trigger.newGame);
                    });
                });
            }
        });
    }

    @Override
    public void init() {
        super.init();
        ChunkManager.init();
        VirtualWorldManager.init();
    }

    @Override
    public void loadContent() {
        FicsitUnits.load();
        FicsitBlocks.load();
        FicsitPlanets.load();
    }
}
