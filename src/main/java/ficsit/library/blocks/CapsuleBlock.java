package ficsit.library.blocks;

import mindustry.world.meta.BuildVisibility;
import arc.scene.ui.layout.Table;

public class CapsuleBlock extends HubBlock {
    public CapsuleBlock(String name) {
        super(name);
        size = 3; // Ligeramente más pequeña que el HUB
        health = 2000;
        itemCapacity = 1500;
        alwaysUnlocked = true;
        // Solo para spawn inicial, no se puede construir manualmente en el menú de usuario
        buildVisibility = BuildVisibility.debugOnly;
    }
    
    // Hereda las capacidades de crafteo y almacenamiento (HubBuild)
}
