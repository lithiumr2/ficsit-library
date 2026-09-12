package ficsit.library.world;

import mindustry.type.SectorPreset;
import mindustry.type.Planet;
import mindustry.content.Planets;

public class FicsitSectorPreset extends SectorPreset {
    public FicsitSectorPreset(String name, Planet planet, int sector) {
        super(name, planet, sector);
        
        // Configuraciones de un sector de inicio (Supervivencia normal)
        captureWave = 1000; // Básicamente infinito
        difficulty = 5;
        alwaysUnlocked = true; // Para que lo puedas jugar sin desbloquear nada
        addStartingItems = true;
    }
}
