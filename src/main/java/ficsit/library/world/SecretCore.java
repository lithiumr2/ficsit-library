package ficsit.library.world;

import mindustry.world.blocks.storage.CoreBlock;
import mindustry.world.meta.BuildVisibility;
import mindustry.content.UnitTypes;

public class SecretCore extends CoreBlock {
    public SecretCore(String name) {
        super(name);
        size = 1;
        health = 999999;
        unitType = UnitTypes.alpha;
        itemCapacity = 1000;
        buildVisibility = BuildVisibility.hidden;
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
