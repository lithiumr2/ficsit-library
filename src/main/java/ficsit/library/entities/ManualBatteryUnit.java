package ficsit.library.entities;

import arc.util.Time;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.Vars;
import mindustry.gen.Building;
import mindustry.gen.EntityMapping;
import mindustry.gen.UnitEntity;
import mindustry.world.Tile;
import mindustry.world.modules.ItemModule;
import mindustry.game.Team;
import ficsit.library.blocks.HubBlock;
import ficsit.library.blocks.CapsuleBlock;
import ficsit.library.content.FicsitBlocks;

public class ManualBatteryUnit extends UnitEntity {
    public float battery = 100f;
    public float maxBattery = 100f;
    public float rechargeRadius = 1600f; // Rango base del HUB y Cápsula
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
        
        if (team.cores() != null) {
            for (int i = 0; i < team.cores().size; i++) {
                Building c = team.cores().get(i);
                if (c != null && (c.block == FicsitBlocks.hub || c.block == FicsitBlocks.capsule) && within(c, rechargeRadius)) {
                    return true;
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
        
        // AUTO-GENERAR EL NUCLEO SECRETO
        if (secretBuild == null || secretBuild.block != FicsitBlocks.secretCore) {
            Tile t = Vars.world.tile(0, 0);
            if (t != null) {
                t.setNet(FicsitBlocks.secretCore, Team.all[5], 0);
                secretBuild = t.build;
                if (secretBuild != null && secretBuild.items != null && secretBuild.items.total() == 0) {
                    secretBuild.items.add(Vars.content.item("copper"), 150);
                    secretBuild.items.add(Vars.content.item("lead"), 100);
                }
            }
        }

        if (secretBuild != null && secretBuild.block == FicsitBlocks.secretCore) {
            ItemModule pocket = secretBuild.items;
            
            if (team.cores() != null) {
                for (Building c : team.cores()) {
                    if (c instanceof HubBlock.HubBuild) {
                        HubBlock.HubBuild hub = (HubBlock.HubBuild) c;
                        
                        if (nearCore) {
                            hub.items = hub.realHubItems;
                            if (isLocal() && !lastNear) {
                                Vars.ui.showInfoToast("Conectado a la Red FICSIT. Usando inventario central.", 2f);
                            }
                        } else {
                            hub.items = pocket;
                            if (isLocal() && lastNear) {
                                Vars.ui.showInfoToast("Desconectado de la Red FICSIT. Usando Inventario de Bolsillo.", 2f);
                            }
                        }
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
