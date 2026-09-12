package ficsit.library.entities;

import arc.util.io.Reads;
import arc.util.io.Writes;
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
    }

    @Override
    public int classId() {
        return classId;
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
    }
}
