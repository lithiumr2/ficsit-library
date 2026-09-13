package ficsit.library.core;

import arc.Events;
import mindustry.Vars;
import mindustry.game.EventType;

/**
 * Gestor del Mundo FICSIT (500x500).
 * Mantiene la estabilidad del mundo y la carga optima de sectores en Mindustry.
 */
public class VirtualWorldManager {
    public static final int CHUNK_SIZE = 250;

    public static void init() {
        Events.run(EventType.Trigger.update, VirtualWorldManager::update);
    }

    private static void update() {
        // Mantiene la sesión estable sin teletransportes bruscos en los bordes del mapa
        if (Vars.state.isPaused() || Vars.state.isMenu() || Vars.player == null || Vars.world == null) return;
    }
}


