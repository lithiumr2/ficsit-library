package ficsit.library.core;

import arc.util.noise.Simplex;
import mindustry.Vars;
import mindustry.content.Blocks;
import mindustry.world.Tile;
import mindustry.world.Tiles;
import mindustry.world.blocks.environment.Floor;

public class FicsitGenerator {
    
    public static int seed = 12345;

    public static void generateChunk(Tiles tiles, int physicalStartX, int physicalStartY, int globalStartX, int globalStartY, int sizeX, int sizeY) {
        int totalWidth = tiles != null ? tiles.width : (Vars.world != null ? Vars.world.width() : sizeX);
        int totalHeight = tiles != null ? tiles.height : (Vars.world != null ? Vars.world.height() : sizeY);

        for (int x = 0; x < sizeX; x++) {
            for (int y = 0; y < sizeY; y++) {
                
                int globalX = globalStartX + x;
                int globalY = globalStartY + y;
                int px = physicalStartX + x;
                int py = physicalStartY + y;
                
                // Bordes del mapa en agua profunda
                if (px < 10 || py < 10 || px > totalWidth - 10 || py > totalHeight - 10) {
                    Floor floorToPlace = (Floor)Blocks.darksandWater;
                    if (tiles != null) {
                        tiles.set(px, py, new Tile(px, py, floorToPlace, (Floor)Blocks.air, Blocks.air));
                    } else if (Vars.world != null) {
                        Tile t = Vars.world.tile(px, py);
                        if (t != null) {
                            t.setFloor(floorToPlace);
                            t.setOverlay((Floor)Blocks.air);
                            t.setAir();
                        }
                    }
                    continue;
                }

                float elevation = Simplex.noise2d(seed, 3, 0.5f, 1f/100f, globalX, globalY);
                float moisture = Simplex.noise2d(seed + 1, 3, 0.5f, 1f/100f, globalX, globalY);
                float oreNoise = Simplex.noise2d(seed + 2, 2, 0.5f, 1f/25f, globalX, globalY);

                Floor floorToPlace = (Floor)Blocks.stone;
                Floor oreToPlace = (Floor)Blocks.air;
                
                if (elevation < 0.25f) {
                    floorToPlace = (Floor)Blocks.water;
                } else if (elevation < 0.38f) {
                    floorToPlace = (Floor)Blocks.sandWater;
                } else {
                    if (moisture > 0.55f) {
                        floorToPlace = (Floor)Blocks.grass;
                    } else if (moisture < 0.3f) {
                        floorToPlace = (Floor)Blocks.sand;
                    } else {
                        floorToPlace = (Floor)Blocks.stone;
                    }
                }
                
                // Distribución abundante de minerales FICSIT
                if (elevation > 0.4f) {
                    if (oreNoise > 0.72f) {
                        oreToPlace = (Floor)Blocks.oreCopper;
                    } else if (oreNoise < -0.72f) {
                        oreToPlace = (Floor)Blocks.oreLead;
                    } else if (oreNoise > 0.62f && oreNoise < 0.68f) {
                        oreToPlace = (Floor)Blocks.oreCoal;
                    } else if (oreNoise < -0.62f && oreNoise > -0.68f) {
                        oreToPlace = (Floor)Blocks.oreTitanium;
                    }
                }
                
                if (tiles != null) {
                    tiles.set(px, py, new Tile(px, py, floorToPlace, oreToPlace, Blocks.air));
                } else if (Vars.world != null) {
                    Tile t = Vars.world.tile(px, py);
                    if (t != null) {
                        t.setFloor(floorToPlace);
                        t.setOverlay(oreToPlace);
                        t.setAir();
                    }
                }
            }
        }
    }
}

