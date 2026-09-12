package ficsit.library.entities;

import arc.util.Time;
import arc.util.io.Reads;
import arc.util.io.Writes;
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
    public float rechargeRadius = 300f;
    public float rechargeRate = 1.5f;
    public float decayRate = 0.05f;

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

        // Comprobar recarga cerca de núcleo aliado
        Building core = closestCore();
        boolean nearCore = (core != null && within(core, rechargeRadius));

        if (nearCore) {
            if (battery < maxBattery) {
                battery = Math.min(maxBattery, battery + rechargeRate * Time.delta);
            }
        } else {
            if (battery > 0f) {
                battery = Math.max(0f, battery - decayRate * Time.delta);
            }
        }

        // Si la batería se agota, daño paulatino al traje/unidad
        if (battery <= 0f) {
            damage(0.05f * Time.delta);
        }

        // Si la unidad recibió daño, el escudo absorbe el golpe antes que la vida
        if (shield < battery) {
            battery = Math.max(0f, shield);
        } else {
            shield = Math.max(0f, battery);
        }
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
