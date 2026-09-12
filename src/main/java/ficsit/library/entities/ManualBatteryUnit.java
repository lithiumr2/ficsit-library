package ficsit.library.entities;

import arc.util.Time;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.Vars;
import mindustry.gen.Building;
import mindustry.gen.EntityMapping;
import mindustry.gen.UnitEntity;
import mindustry.world.blocks.power.PowerNode;
import mindustry.world.blocks.storage.CoreBlock;
import mindustry.game.Team;
import arc.struct.Seq;

public class ManualBatteryUnit extends UnitEntity {
    public float battery = 100f;
    public float maxBattery = 100f;
    public float rechargeRadius = 1600f; // Rango base del HUB
    public float nodeRechargeRadius = 400f; // Rango de los postes eléctricos
    public float rechargeRate = 1.5f;
    public float decayRate = 0.01f; 
    private transient float asfixiaTimer = 0f;

    public static int classId = -1;

    public static void register() {
        classId = EntityMapping.register("ManualBatteryUnit", ManualBatteryUnit::new);
    }

    public ManualBatteryUnit() {
        super();
        this.battery = 100f;
        this.maxBattery = 100f;
        this.shield = 100f;
    }

    @Override
    public int classId() {
        return classId;
    }

    public boolean isNearCore() {
        if (team == null || Vars.state.isMenu()) return false;
        
        // 1. Check near cores
        if (team.cores() != null) {
            for (int i = 0; i < team.cores().size; i++) {
                Building c = team.cores().get(i);
                if (c != null && within(c, rechargeRadius)) {
                    return true;
                }
            }
        }

        // 2. Check near power nodes (Estaciones de carga / Postes)
        // Recorremos los edificios para encontrar nodos de energía
        if (Vars.indexer != null) {
            Seq<Building> buildings = team.data().buildings;
            for (int i = 0; i < buildings.size; i++) {
                Building b = buildings.get(i);
                if (b != null && b.block instanceof PowerNode) {
                    if (within(b, nodeRechargeRadius)) {
                        return true;
                    }
                }
            }
        }
        
        return false;
    }

    @Override
    public void update() {
        super.update();
        if (!isAdded() || dead) return;

        boolean nearCore = isNearCore();

        if (nearCore) {
            if (battery < maxBattery) {
                battery = Math.min(maxBattery, battery + rechargeRate * Time.delta);
            }
        } else {
            if (battery > 0f) {
                battery = Math.max(0f, battery - decayRate * Time.delta);
            }
        }

        if (battery <= 0f) {
            asfixiaTimer += Time.delta;
            if (asfixiaTimer >= 60f) { 
                damage(1f); 
                asfixiaTimer = 0f; 
            }
        } else {
            asfixiaTimer = 0f;
        }

        shield = Math.max(0f, battery);
        
        // Restricción de Inventario Independiente:
        // Si el jugador intenta construir lejos de la red, bloqueamos los planes de construcción mágicos.
        // Para construir lejos, tiene que usar los ítems físicos de su inventario, lo cual es muy difícil en vanilla.
        // Para simular la "creación independiente": cancelamos planes si no está en rango.
        if (!nearCore && plans.size > 0 && !Vars.state.rules.infiniteResources) {
            // Permitimos la construcción SI la unidad tiene un ítem en su inventario que coincide con algún requisito del bloque.
            // Para simplificar, si está desconectado de la red de energía/HUB, no puede construir desde cero 
            // mágicamente desde el núcleo.
            
            // Evaluamos el primer plan
            mindustry.entities.units.BuildPlan plan = plans.first();
            if(plan != null && plan.block != null) {
                // Si la unidad lleva un item y el bloque lo requiere, lo dejamos intentar (Mindustry usará su inventario)
                if(stack.amount > 0 && plan.block.requirements != null) {
                    boolean requiresCarriedItem = false;
                    for(mindustry.type.ItemStack req : plan.block.requirements) {
                        if(req.item == stack.item) {
                            requiresCarriedItem = true;
                            break;
                        }
                    }
                    if(!requiresCarriedItem) {
                        plans.removeIndex(0);
                        if(isLocal()) {
                            Vars.ui.showInfoToast("Fuera de la red eléctrica. Carga ítems en tu inventario para construir.", 1f);
                        }
                    }
                } else {
                    // No lleva ítems útiles, cancelamos el plan.
                    plans.removeIndex(0);
                    if(isLocal()) {
                        Vars.ui.showInfoToast("Sin conexión al HUB. Construye Postes Eléctricos para extender la red.", 1f);
                    }
                }
            }
        }
    }

    @Override
    public void write(Writes write) {
        super.write(write);
        write.f(battery);
        write.f(maxBattery);
    }

    @Override
    public void read(Reads read) {
        super.read(read);
        battery = read.f();
        maxBattery = read.f();
        shield = battery;
    }
}
