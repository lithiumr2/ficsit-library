package ficsit.library.entities;

import arc.util.Time;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.Vars;
import mindustry.gen.Building;
import mindustry.gen.EntityMapping;
import mindustry.gen.UnitEntity;

/**
 * Entidad de unidad con sistema de almacenamiento de batería manual.
 * 
 * Implementada sin procesadores de anotaciones (@Ent) para compatibilidad
 * multiplataforma limpia (incluyendo compilación en Android/Termux).
 */
public class ManualBatteryUnit extends UnitEntity {
    public float battery = 100f;
    public float maxBattery = 100f;
    public float rechargeRadius = 1600f; // Mayor alcance para mundo abierto
    public float rechargeRate = 1.5f;
    public float decayRate = 0.01f; // Batería dura aprox 2.77 minutos

    private transient float asfixiaTimer = 0f;

    /** Identificador de clase asignado en el EntityMapping del motor */
    public static int classId = -1;

    /**
     * Registra esta entidad en el mapeo global de Mindustry.
     * DEBE llamarse antes de instanciar cualquier UnitType o cargar partidas.
     */
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

    @Override
    public void update() {
        super.update();

        if (!isAdded() || dead) return;

        // Comprobar recarga cerca de núcleo aliado (o red de energía aliada)
        Building core = closestCore();
        boolean nearCore = false;
        if (core != null && within(core, rechargeRadius)) {
            nearCore = true;
        } else if (team != null && team.cores() != null && team.cores().size > 0) {
            for (int i = 0; i < team.cores().size; i++) {
                Building c = team.cores().get(i);
                if (c != null && within(c, rechargeRadius)) {
                    nearCore = true;
                    break;
                }
            }
        }

        if (nearCore) {
            if (battery < maxBattery) {
                battery = Math.min(maxBattery, battery + rechargeRate * Time.delta);
            }
        } else {
            if (battery > 0f) {
                battery = Math.max(0f, battery - decayRate * Time.delta);
            }
        }

        // Si la batería se agota por completo, daño por asfixia/fallo del traje
        if (battery <= 0f) {
            asfixiaTimer += Time.delta;
            if (asfixiaTimer >= 60f) { // Acumula el daño durante un segundo (aprox 60 ticks)
                damage(1f); // 1 de daño por segundo (50s antes de morir)
                asfixiaTimer = 0f; // Reinicia el acumulador
            }
        } else {
            asfixiaTimer = 0f;
        }

        // Sincronizar con el escudo nativo (HudFragment lee unit.shield)
        shield = Math.max(0f, battery);
    }

    /**
     * Serialización de datos de la unidad a disco o red.
     */
    @Override
    public void write(Writes write) {
        super.write(write);
        write.f(battery);
        write.f(maxBattery);
    }

    /**
     * Deserialización de datos de la unidad desde disco o red.
     */
    @Override
    public void read(Reads read) {
        super.read(read);
        battery = read.f();
        maxBattery = read.f();
        shield = battery;
    }
}
