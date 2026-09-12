package ficsit.library;

import mindustry.mod.Mod;
import ficsit.library.content.FicsitBlocks;
import ficsit.library.content.FicsitUnits;
import ficsit.library.entities.ManualBatteryUnit;
import ficsit.library.entities.DropPodEntity;

/**
 * Clase principal del mod Ficsit Library.
 * 
 * Gestiona el ciclo de vida, registro manual de entidades y carga de contenidos.
 */
public class FicsitLibraryMod extends Mod {

    public FicsitLibraryMod() {
        super();
    }

    public void load() {
        // PASO 1: Registro de EntityMapping (Pre-requisito absoluto)
        ManualBatteryUnit.register();
        DropPodEntity.register();

        // PASO 2: Carga de Unidades (que usan ManualBatteryUnit::new)
        FicsitUnits.load();

        // PASO 3: Carga de Bloques (que pueden vincular FicsitUnits.engineer al núcleo)
        FicsitBlocks.load();
    }

    @Override
    public void loadContent() {
        load();
    }
}
