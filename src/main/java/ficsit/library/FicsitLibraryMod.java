package ficsit.library;

import mindustry.mod.Mod;
import ficsit.library.content.FicsitBlocks;
import ficsit.library.content.FicsitUnits;
import ficsit.library.content.FicsitItems;
import ficsit.library.content.FicsitPlanets;
import ficsit.library.entities.ManualBatteryUnit;

/**
 * Clase base de carga de contenido de Ficsit Library.
 */
public class FicsitLibraryMod extends Mod {

    public FicsitLibraryMod() {
        super();
    }

    public void load() {
        // PASO 1: Registro de EntityMapping (Pre-requisito absoluto)
        ManualBatteryUnit.register();
        
        // PASO 2: Carga de Ítems
        FicsitItems.load();

        // PASO 3: Carga de Unidades
        FicsitUnits.load();

        // PASO 4: Carga de Bloques (HUB, etc)
        FicsitBlocks.load();

        // PASO 5: Carga del Planeta MASSAGE-2(AB)b / FICSIT
        FicsitPlanets.load();
    }

    @Override
    public void loadContent() {
        load();
    }
}
