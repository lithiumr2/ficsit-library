package ficsit.library.core;

import arc.util.noise.Simplex;
import mindustry.Vars;
import mindustry.content.Blocks;
import mindustry.world.Tile;
import mindustry.world.Tiles;
import mindustry.world.blocks.environment.Floor;

public class FicsitGenerator {
    
    // Semilla para el mundo, para que siempre se genere igual si usas la misma
    public static int seed = 12345;

    public static void generateChunk(Tiles tiles, int physicalStartX, int physicalStartY, int globalStartX, int globalStartY, int sizeX, int sizeY) {
        for (int x = 0; x < sizeX; x++) {
            for (int y = 0; y < sizeY; y++) {
                
                int globalX = globalStartX + x;
                int globalY = globalStartY + y;
                
                float elevation = Simplex.noise2d(seed, 2, 0.5f, 1f/100f, globalX, globalY);
                float moisture = Simplex.noise2d(seed + 1, 2, 0.5f, 1f/100f, globalX, globalY);

                Floor floorToPlace = (Floor)Blocks.stone;
                Floor oreToPlace = (Floor)Blocks.air;
                
                if (elevation < 0.2f) {
                    floorToPlace = (Floor)Blocks.water;
                } else if (elevation < 0.4f) {
                    floorToPlace = (Floor)Blocks.sandWater;
                } else {
                    if (moisture > 0.5f) {
                        floorToPlace = (Floor)Blocks.grass;
                    } else {
                        floorToPlace = (Floor)Blocks.stone;
                    }
                }
                
                if (elevation > 0.5f && Simplex.noise2d(seed + 2, 1, 1f, 1f/20f, globalX, globalY) > 0.7f) {
                    oreToPlace = (Floor)Blocks.oreCopper;
                }
                
                Tile t = null;
                if(tiles != null) {
                    t = tiles.getc(physicalStartX + x, physicalStartY + y);
                } else if(Vars.world != null) {
                    t = Vars.world.tile(physicalStartX + x, physicalStartY + y);
                }

                if (t != null) {
                    t.setFloor(floorToPlace);
                    t.setOverlay(oreToPlace);
                    t.setNet(Blocks.air, t.team(), 0);
                }
            }
        }
    }
}
