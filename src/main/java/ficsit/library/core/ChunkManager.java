package ficsit.library.core;

import arc.Events;
import arc.struct.Seq;
import mindustry.Vars;
import mindustry.game.EventType;
import mindustry.gen.Building;

public class ChunkManager {
    public static final float ACTIVE_RADIUS = 2000f; // Distancia de carga del jugador
    public static final float ACTIVE_RADIUS_SQ = ACTIVE_RADIUS * ACTIVE_RADIUS;
    
    // Lista de edificios que nosotros controlaremos que se "duerman"
    private static Seq<Building> sleepingBuildings = new Seq<>();
    
    // Frame counter para no comprobar distancias los 60 frames por segundo (optimización)
    private static int updateTimer = 0;

    public static void init() {
        Events.run(EventType.Trigger.update, ChunkManager::update);
    }

    private static void update() {
        if(Vars.state.isPaused() || Vars.state.isMenu() || Vars.player == null || Vars.player.unit() == null) return;
        
        updateTimer++;
        // Solo verificamos chunks 2 veces por segundo (cada 30 frames) en lugar de 60 veces
        if(updateTimer >= 30) {
            updateTimer = 0;
            
            float px = Vars.player.x;
            float py = Vars.player.y;
            
            // Aqui recorreríamos las estructuras complejas (ej: fundidoras, ensambladoras)
            // Para "dormirlas" o pasarlas a un estado matemático si están fuera del ACTIVE_RADIUS_SQ
            // Y "despertarlas" si el jugador entra en su rango.
        }
    }
}
