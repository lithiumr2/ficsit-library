package ficsit.library.world;

import mindustry.world.blocks.storage.CoreBlock;
import mindustry.world.meta.BuildVisibility;
import mindustry.content.UnitTypes;
import ficsit.library.content.FicsitUnits;

public class SecretCore extends CoreBlock {
    public SecretCore(String name) {
        super(name);
        size = 1;
        health = 999999;
        unitType = FicsitUnits.dropPod != null ? FicsitUnits.dropPod : UnitTypes.alpha;
        itemCapacity = 1000;
        buildVisibility = BuildVisibility.hidden;
    }

    @Override
    public void init() {
        if (FicsitUnits.dropPod != null) {
            this.unitType = FicsitUnits.dropPod;
        }
        super.init();
    }

    public class SecretCoreBuild extends CoreBuild {
        @Override
        public void draw() {
            // Invisible: no dibuja nada en pantalla
        }
        @Override
        public void drawTeam() {
            // Oculta el indicador de color de equipo
        }
    }
}
