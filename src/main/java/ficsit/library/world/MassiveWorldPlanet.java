package ficsit.library.world;

import mindustry.type.Planet;
import mindustry.graphics.g3d.HexMesh;
import mindustry.graphics.g3d.HexSkyMesh;
import mindustry.graphics.g3d.MultiMesh;
import mindustry.content.Planets;
import mindustry.content.Items;
import mindustry.content.Blocks;
import arc.graphics.Color;

public class MassiveWorldPlanet extends Planet {
    
    public MassiveWorldPlanet(String name, Planet parent, float radius, int sectorSize) {
        super(name, parent, radius, sectorSize);
        
        // Configuracion visual del planeta en la interfaz estelar
        meshLoader = () -> new HexMesh(this, 5);
        cloudMeshLoader = () -> new MultiMesh(
            new HexSkyMesh(this, 11, 0.15f, 0.13f, 5, Color.valueOf("608cff").mul(0.9f).a(0.75f), 2, 0.45f, 0.9f, 0.38f),
            new HexSkyMesh(this, 1, 0.6f, 0.16f, 5, Color.white.cpy().lerp(Color.valueOf("608cff"), 0.55f).a(0.75f), 2, 0.45f, 1f, 0.41f)
        );

        iconColor = Color.valueOf("7d983e");
        atmosphereColor = Color.valueOf("4a9937");
        atmosphereRadIn = 0.02f;
        atmosphereRadOut = 0.3f;
        startSector = 0;
        alwaysUnlocked = true;
        allowLaunchToNumbered = true;
        
        // Items base permitidos en este planeta
        hiddenItems.addAll(Items.serpuloItems).removeAll(Items.erekirItems);
        defaultCore = Blocks.coreShard;
        
        // Generador procedural nativo para el planeta
        generator = new FicsitPlanetGenerator();
    }
}
