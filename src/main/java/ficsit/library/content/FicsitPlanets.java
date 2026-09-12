package ficsit.library.content;

import mindustry.content.Planets;
import ficsit.library.world.MassiveWorldPlanet;
import ficsit.library.world.FicsitSectorPreset;

public class FicsitPlanets {
    public static MassiveWorldPlanet massageage;
    public static FicsitSectorPreset ficsitStart;

    public static void load() {
        // Añadimos el planeta a la vista estelar, orbitando el Sol (sun)
        massageage = new MassiveWorldPlanet("ficsit-planet", Planets.sun, 1.2f, 3);
        
        // Creamos un "Sector" jugable que los jugadores pueden clickear
        ficsitStart = new FicsitSectorPreset("ficsit-start", massageage, 0);
    }
}
