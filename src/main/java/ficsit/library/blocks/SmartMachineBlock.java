package ficsit.library.blocks;

import mindustry.world.blocks.production.GenericCrafter;
import ficsit.library.core.ChunkManager;
import mindustry.Vars;

public class SmartMachineBlock extends GenericCrafter {
    public SmartMachineBlock(String name) {
        super(name);
    }

    public class SmartMachineBuild extends GenericCrafterBuild {
        public boolean isSleeping = false;
        private float sleepTimer = 0f;

        @Override
        public void updateTile() {
            if (Vars.player != null && Vars.player.unit() != null) {
                float distSq = dst2(Vars.player.x, Vars.player.y);
                
                if (distSq > ChunkManager.ACTIVE_RADIUS_SQ) {
                    isSleeping = true;
                } else {
                    if (isSleeping) {
                        // El jugador volvió. Calcular producción abstracta por el tiempo dormido.
                        catchUpSimulation();
                        isSleeping = false;
                    }
                }
            }

            if (!isSleeping) {
                // Si el jugador está cerca, la máquina funciona de manera nativa frame a frame
                super.updateTile();
            } else {
                // Si la máquina está "lejos", acumula el tiempo y NO hace efectos visuales ni sonidos pesados
                sleepTimer += edelta();
            }
        }
        
        // Simulación matemática para cuando la máquina despierta
        public void catchUpSimulation() {
            if (sleepTimer <= 0) return;
            
            // Aquí se usaría el sleepTimer para calcular cuantas veces
            // podría haber crafteado el item usando MultiLib
            // y actualizar el inventario directamente con pura matemática
            // sin consumir frames por cada crafteo individual.
            
            sleepTimer = 0f; // Reiniciar
        }
    }
}
