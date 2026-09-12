package ficsit.library.content;

import mindustry.type.UnitType;
import ficsit.library.entities.EngineerUnitType;

public class FicsitUnits {
    public static UnitType engineer;

    public static void load() {
        engineer = new EngineerUnitType("engineer-unit");
    }
}
