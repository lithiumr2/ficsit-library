package ficsit.library.entities;

import arc.util.Time;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.Vars;
import mindustry.gen.Building;
import mindustry.gen.EntityMapping;
import mindustry.gen.UnitEntity;
import mindustry.world.blocks.power.PowerNode;
import mindustry.world.modules.ItemModule;
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
    private transient boolean lastNear = false;
    
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
        
        // Siempre buscamos en el equipo actual (Team.sharded)
        if (team.cores() != null) {
            for (int i = 0; i < team.cores().size; i++) {
                Building c = team.cores().get(i);
                if (c != null && c.block == ficsit.library.content.FicsitBlocks.hub && within(c, rechargeRadius)) {
                    return true;
                }
            }
        }

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
        
        // ============================================
        // Lógica de Swap de Inventario (Pocket vs Base)
        // ============================================
        Building secretBuild = Vars.world.build(0, 0);
        if (secretBuild != null && secretBuild.block == ficsit.library.content.FicsitBlocks.secretCore) {
            ItemModule pocket = secretBuild.items;
            
            // Suponiendo que el jugador esta en el mismo equipo que el HUB
            if (team.core() != null && team.core().block instanceof ficsit.library.blocks.HubBlock) {
                ficsit.library.blocks.HubBlock.HubBuild hub = (ficsit.library.blocks.HubBlock.HubBuild) team.core();
                
                if (nearCore) {
                    hub.items = hub.realHubItems;
                    if (isLocal() && !lastNear) {
                        Vars.ui.showInfoToast("Conectado a la Red FICSIT. Usando inventario del HUB.", 2f);
                    }
                } else {
                    hub.items = pocket;
                    if (isLocal() && lastNear) {
                        Vars.ui.showInfoToast("Desconectado de la Red FICSIT. Usando Inventario de Bolsillo.", 2f);
                    }
                }
            }
        }
        
        lastNear = nearCore;
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
