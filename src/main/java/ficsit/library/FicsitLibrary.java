package ficsit.library;

import arc.Events;
import mindustry.Vars;
import mindustry.content.Blocks;
import mindustry.content.Items;
import mindustry.game.EventType;
import mindustry.game.EventType.WorldLoadEvent;
import mindustry.game.Rules;
import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.world.Tile;
import ficsit.library.core.ChunkManager;
import ficsit.library.core.FicsitGenerator;
import ficsit.library.core.VirtualWorldManager;

import static mindustry.Vars.*;

public class FicsitLibrary extends FicsitLibraryMod {

    public FicsitLibrary() {
        super();

        // Botones en el menu principal para acceso directo
        Events.on(EventType.ClientLoadEvent.class, e -> {
            if (Vars.ui != null && Vars.ui.menufrag != null) {
                // Boton directo para abrir la vista de planetas en Campaña
                Vars.ui.menufrag.addButton("Planetas / Campaña", () -> {
                    if (Vars.ui.planet != null) {
                        Vars.ui.planet.show();
                    }
                });

                // Boton directo para iniciar el Mundo Abierto FICSIT con 1 clic directo
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

        // Configuracion de HUB al cargar mundo
        Events.on(WorldLoadEvent.class, e -> {
            spawnHubCore();
        });
    }

    @Override
    public void init() {
        super.init();
        ChunkManager.init();
        VirtualWorldManager.init();
    }

    private void spawnHubCore() {
        Tile secretTile = world.tile(0, 0);
        if (secretTile != null) {
            secretTile.setNet(ficsit.library.content.FicsitBlocks.secretCore, Team.blue, 0);
            if (Team.blue.core() != null && Team.blue.core().items != null) {
                Team.blue.core().items.add(Items.copper, 150);
                Team.blue.core().items.add(Items.lead, 100);
            }
        }

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
