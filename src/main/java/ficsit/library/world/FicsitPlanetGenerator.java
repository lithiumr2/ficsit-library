package ficsit.library.world;

import mindustry.maps.generators.PlanetGenerator;
import mindustry.world.Tile;
import mindustry.world.Block;
import mindustry.content.Blocks;
import arc.math.geom.Vec3;
import arc.graphics.Color;
import ficsit.library.core.FicsitGenerator;
import mindustry.world.blocks.environment.Floor;

public class FicsitPlanetGenerator extends PlanetGenerator {

    @Override
    public float getHeight(Vec3 position) {
        return 0; // Usado para mallas 3D del planeta (visual estelar)
    }

    @Override
    public Color getColor(Vec3 position) {
        return Color.valueOf("4a9937"); // Color para mallas 3D (visual estelar)
    }
    
    // Este metodo es llamado por Mindustry AL INICIAR el sector
    @Override
    protected void generate() {
        // Inicializamos las coordenadas globales en 0,0 al caer
        int startGlobalX = 0;
        int startGlobalY = 0;
        
        // Llamamos a nuestro script de generación procedural (ruido)
        // para que llene todo el mapa de 500x500 (el tamaño por defecto)
        FicsitGenerator.generateChunk(0, 0, startGlobalX, startGlobalY, width); // width suele ser 500
        
        // Colocamos el nucleo del jugador (Core) justo en el centro del chunk inicial de 500x500
        tiles.getc(width / 2, height / 2).setBlock(Blocks.coreShard, team);
    }
}
