package ficsit.library.content;

import mindustry.type.UnitType;
import ficsit.library.entities.EngineerUnitType;
import ficsit.library.entities.DropPodUnitType;

public class FicsitUnits {
    public static UnitType engineer;
    public static UnitType dropPod;

    public static void load() {
        engineer = new EngineerUnitType("engineer-unit");
        dropPod = new DropPodUnitType("drop-pod");
    }
}
