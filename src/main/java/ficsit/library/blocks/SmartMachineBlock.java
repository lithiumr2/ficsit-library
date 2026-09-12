package ficsit.library.blocks;

import mindustry.world.blocks.production.GenericCrafter;
import ficsit.library.core.ChunkManager;
import mindustry.Vars;
import mindustry.type.Item;
import mindustry.gen.Building;

public class SmartMachineBlock extends GenericCrafter {
    public SmartMachineBlock(String name) {
        super(name);
    }

    public class SmartMachineBuild extends GenericCrafterBuild {
        // INICIA DORMIDA: Evita que mapas gigantescos crasheen el juego al cargar.
        public boolean isSleeping = true; 
        public float sleepTimer = 0f;
        
        // Búfer virtual para acumular ítems sin atascar las cintas transportadoras
        public int[] virtualInput = new int[Vars.content.items().size];
        public int[] virtualOutput = new int[Vars.content.items().size];

        @Override
        public boolean acceptItem(Building source, Item item) {
            if (isSleeping) {
                // Si la máquina duerme, "traga" los ítems en su búfer virtual
                // siempre y cuando la receta lo necesite.
                return consumesItem(item);
            }
            return super.acceptItem(source, item);
        }

        @Override
        public void handleItem(Building source, Item item) {
            if (isSleeping) {
                virtualInput[item.id]++;
            } else {
                super.handleItem(source, item);
            }
        }

        @Override
        public void updateTile() {
            if (Vars.player != null && Vars.player.unit() != null) {
                float distSq = dst2(Vars.player.x, Vars.player.y);
                
                if (distSq > ChunkManager.ACTIVE_RADIUS_SQ) {
                    isSleeping = true;
                } else {
                    if (isSleeping) {
                        catchUpSimulation();
                        isSleeping = false;
                    }
                }
            }

            if (!isSleeping) {
                super.updateTile(); // Producción normal animada
                
                // Escupir los ítems procesados virtualmente hacia la cinta de salida
                dumpVirtualOutputs();
            } else {
                sleepTimer += edelta();
            }
        }

        private void dumpVirtualOutputs() {
            for (Item item : Vars.content.items()) {
                if (virtualOutput[item.id] > 0) {
                    // Intenta sacar el ítem hacia la cinta transportadora conectada.
                    // No aparecerá de golpe, saldrá gradualmente por donde debe.
                    if (dump(item)) {
                        virtualOutput[item.id]--;
                    }
                }
            }
        }
        
        // Este método será sobreescrito por fábricas complejas (como la Fundidora Científica)
        public void catchUpSimulation() {
            if (sleepTimer <= 0) return;
            
            // Comportamiento base (si no es sobreescrito): 
            // Inyectar el input virtual al inventario real para que se procese
            for (Item item : Vars.content.items()) {
                if (virtualInput[item.id] > 0) {
                    int space = getMaximumAccepted(item) - items.get(item);
                    int toMove = Math.min(space, virtualInput[item.id]);
                    if (toMove > 0) {
                        items.add(item, toMove);
                        virtualInput[item.id] -= toMove;
                    }
                }
            }
            sleepTimer = 0f;
        }
    }
}
