package ficsit.library.content;

import mindustry.content.Planets;
import ficsit.library.world.MassiveWorldPlanet;

public class FicsitPlanets {
    public static MassiveWorldPlanet massageage;

    public static void load() {
        // Añadimos el planeta a la vista estelar, orbitando el Sol (sun)
        massageage = new MassiveWorldPlanet("ficsit-planet", Planets.sun, 1.2f, 3);
    }
}
