package ficsit.library.content;

import mindustry.type.UnitType;
import ficsit.library.entities.EngineerUnit;

public class FicsitUnits {
    public static UnitType engineer;

    public static void load() {
        // Inicializamos la unidad con batería de la Fase 1
        engineer = new EngineerUnit("engineer-unit");
    }
}
