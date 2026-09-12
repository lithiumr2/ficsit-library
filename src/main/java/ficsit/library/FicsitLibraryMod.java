package ficsit.library;

import mindustry.mod.Mod;
import ficsit.library.content.FicsitBlocks;
import ficsit.library.content.FicsitUnits;
import ficsit.library.entities.ManualBatteryUnit;

/**
 * Clase principal del mod Ficsit Library.
 * 
 * Gestiona el ciclo de vida, registro manual de entidades y carga de contenidos.
 */
public class FicsitLibraryMod extends Mod {

    public FicsitLibraryMod() {
        super();
    }

    /**
     * Secuencia obligatoria de inicialización y carga de contenidos.
     *
     * ORDEN CRÍTICO DE EJECUCIÓN:
     * 1. ManualBatteryUnit.register(): Debe registrarse primero en el EntityMapping
     *    de Mindustry antes de que cualquier UnitType o bloque sea instanciado,
     *    o antes de que el motor intente deserializar entidades desde el guardado.
     * 2. Carga de bloques y contenidos dependientes (FicsitBlocks.load()).
     * 3. Carga de tipos de unidades (FicsitUnits.load()), donde el constructor ya
     *    referenciará una entidad con classId válido.
     */
    public void load() {
        // PASO 1: Registro de EntityMapping (Pre-requisito absoluto)
        ManualBatteryUnit.register();

        // PASO 2: Carga de contenido genérico (Bloques, Ítems, etc.)
        FicsitBlocks.load();

        // PASO 3: Carga de Unidades (que usan ManualBatteryUnit::new)
        FicsitUnits.load();
    }

    @Override
    public void loadContent() {
        load();
    }
}
