package ficsit.library.world;

import mindustry.world.Block;
import mindustry.world.meta.BuildVisibility;
import mindustry.gen.Building;

public class SecretCore extends Block {
    public SecretCore(String name) {
        super(name);
        size = 1;
        health = 999999;
        itemCapacity = 1000; // Capacidad inicial
        hasItems = true;
        buildVisibility = BuildVisibility.hidden;
        solid = false;
        destructible = false; // Indestructible
        update = true;
    }

    public class SecretCoreBuild extends Building {
        @Override
        public void draw() {
        }
        @Override
        public void drawTeam() {
        }
    }
}
