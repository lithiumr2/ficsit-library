package ficsit.library;

import mindustry.mod.Mod;
import ficsit.library.content.FicsitBlocks;
import ficsit.library.content.FicsitUnits;
import ficsit.library.core.ChunkManager;

public class FicsitMod extends Mod {

    public FicsitMod() {
        // Inicializar eventos globales aquí si es necesario
    }

    @Override
    public void init() {
        super.init();
        ChunkManager.init(); // Iniciar nuestro gestor de zonas
        ficsit.library.core.VirtualWorldManager.init();
    }

    @Override
    public void loadContent() {
        FicsitUnits.load();
        FicsitBlocks.load();
        ficsit.library.content.FicsitPlanets.load();
    }
}
