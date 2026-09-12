package ficsit.library.core;

import arc.util.noise.Simplex;
import mindustry.Vars;
import mindustry.content.Blocks;
import mindustry.world.Tile;
import mindustry.world.blocks.environment.Floor;

public class FicsitGenerator {
    
    // Semilla para el mundo, para que siempre se genere igual si usas la misma
    public static int seed = 12345;

    /**
     * Rellena o actualiza una sección del mapa físico usando ruido procedural (Simplex).
     * @param physicalStartX Coordenada X local en el mapa de 500x500
     * @param physicalStartY Coordenada Y local en el mapa de 500x500
     * @param globalStartX Coordenada X real en el mundo de 10000x10000
     * @param globalStartY Coordenada Y real en el mundo de 10000x10000
     * @param size El tamaño de la cuadrícula a generar (250)
     */
    public static void generateChunk(int physicalStartX, int physicalStartY, int globalStartX, int globalStartY, int size) {
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                
                int globalX = globalStartX + x;
                int globalY = globalStartY + y;
                
                // Usamos ruido Simplex para calcular la elevación (montañas vs tierra)
                // Usamos las coordenadas globales para que el terreno sea continuo
                float elevation = Simplex.noise2d(seed, 2, 0.5f, 1f/100f, globalX, globalY);
                
                // Usamos otro ruido para el clima / humedad (arena vs pasto)
                float moisture = Simplex.noise2d(seed + 1, 2, 0.5f, 1f/100f, globalX, globalY);

                Floor floorToPlace = (Floor)Blocks.stone;
                Floor oreToPlace = (Floor)Blocks.air;
                
                // --- REGLAS SIMPLES DE GENERACION (BIOMAS) ---
                if (elevation < 0.2f) {
                    floorToPlace = (Floor)Blocks.water;
                } else if (elevation < 0.4f) {
                    floorToPlace = (Floor)Blocks.sandWater;
                } else {
                    if (moisture > 0.5f) {
                        floorToPlace = (Floor)Blocks.grass;
                    } else {
                        floorToPlace = (Floor)Blocks.stone; // o arena
                    }
                }
                
                // Generar menas (ej: cobre natural)
                if (elevation > 0.5f && Simplex.noise2d(seed + 2, 1, 1f, 1f/20f, globalX, globalY) > 0.7f) {
                    oreToPlace = (Floor)Blocks.oreCopper;
                }
                
                // Actualizar el Tile físico en la RAM de Mindustry
                Tile t = Vars.world.tile(physicalStartX + x, physicalStartY + y);
                if (t != null) {
                    t.setFloor(floorToPlace);
                    t.setOverlay(oreToPlace);
                    // IMPORTANTE: Al generar terreno, borramos los bloques anteriores 
                    // (las fábricas se deben cargar DESPUES de esto desde el archivo de guardado)
                    t.setNet(Blocks.air, t.team(), 0);
                }
            }
        }
    }
}
