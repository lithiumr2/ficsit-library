package ficsit.library.core;

import arc.Events;
import arc.struct.Seq;
import mindustry.Vars;
import mindustry.game.EventType;
import mindustry.gen.Player;

/**
 * Gestor del Mundo Abierto Masivo (10000x10000 simulado).
 * Utiliza el método de "Cinta de Correr" (Treadmill): 
 * El mapa físico de Mindustry siempre es de 500x500 (compuesto por 4 chunks de 250x250).
 * Cuando el jugador cruza un límite, todo el mapa se desplaza para cargar el nuevo chunk.
 */
public class VirtualWorldManager {
    public static final int CHUNK_SIZE = 250;
    public static final int VISIBLE_CHUNKS = 2; // 2x2 chunks (500x500)
    
    // Coordenadas globales del jugador en el mundo de 10000x10000
    public static int globalChunkX = 0;
    public static int globalChunkY = 0;

    public static void init() {
        Events.run(EventType.Trigger.update, VirtualWorldManager::update);
    }

    private static void update() {
        if(Vars.state.isPaused() || Vars.state.isMenu() || Vars.player == null) return;
        
        Player p = Vars.player;
        
        // Coordenadas locales en el mapa físico de Mindustry
        int tileX = Math.round(p.x / Vars.tilesize);
        int tileY = Math.round(p.y / Vars.tilesize);
        
        // Lógica de transición suave (Treadmill)
        // Si el jugador se acerca al borde del chunk (ej. pasa el tile 375 de 500)
        // se activa el "Shift" (Desplazamiento)
        
        if (tileX > CHUNK_SIZE + (CHUNK_SIZE / 2)) {
            shiftWorld(1, 0); // Mover hacia la derecha
        } else if (tileX < (CHUNK_SIZE / 2)) {
            shiftWorld(-1, 0); // Mover hacia la izquierda
        }
        
        if (tileY > CHUNK_SIZE + (CHUNK_SIZE / 2)) {
            shiftWorld(0, 1); // Mover hacia arriba
        } else if (tileY < (CHUNK_SIZE / 2)) {
            shiftWorld(0, -1); // Mover hacia abajo
        }
    }

    /**
     * Mueve el mundo físico de Mindustry.
     * @param dirX Dirección en Chunks (ej. 1 = un chunk a la derecha)
     * @param dirY Dirección en Chunks
     */
    private static void shiftWorld(int dirX, int dirY) {
        // 1. Pausar el juego lógicamente
        // 2. Guardar los bloques de los chunks que van a desaparecer en un archivo/base de datos (Cold Storage).
        // 3. Desplazar todos los bloques del mapa físico 250 tiles en dirección contraria.
        // 4. Leer los archivos/base de datos del nuevo chunk que debe aparecer.
        // 5. Teletransportar al jugador 250 tiles para compensar el movimiento y que la cámara no note el salto brusco.
        
        globalChunkX += dirX;
        globalChunkY += dirY;
        
        // Compensación de cámara y posición (Ilusión de movimiento fluido)
        if (Vars.player.unit() != null) {
            Vars.player.unit().x -= (dirX * CHUNK_SIZE * Vars.tilesize);
            Vars.player.unit().y -= (dirY * CHUNK_SIZE * Vars.tilesize);
        }
        
        // AQUI IRIAN LAS MATEMATICAS DE GUARDADO Y LECTURA
        if(Vars.player != null) {
            Vars.player.sendMessage("[yellow]Cargando nuevo cuadrante global: " + globalChunkX + ", " + globalChunkY);
        }
    }
}
