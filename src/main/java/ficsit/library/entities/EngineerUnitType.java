package ficsit.library.entities;

import arc.graphics.Color;
import arc.scene.ui.layout.Table;
import arc.util.Time;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.gen.Unit;
import mindustry.type.UnitType;
import mindustry.type.Weapon;
import mindustry.ui.Bar;

/**
 * Definición de tipo para la unidad Ingeniero con soporte para batería manual.
 */
public class EngineerUnitType extends UnitType {
    /** Tasa de decaimiento de batería por fotograma multiplicada por Time.delta */
    public float batteryDecayRate = 0.05f;

    public EngineerUnitType(String name) {
        super(name);

        // Asignación directa del constructor manual sin procesador de anotaciones
        this.constructor = ManualBatteryUnit::new;

        // Configuración de movilidad y estadísticas
        speed = 4f;
        drag = 0.05f;
        flying = true;
        engineOffset = 6f;
        engineSize = 3f;

        health = 50f;
        armor = 0f;
        hitSize = 8f;
        buildSpeed = 4f;
        buildRange = 220f;
        mineSpeed = 6f;
        mineTier = 2;

        // Arma principal de la unidad
        weapons.add(new Weapon("ficsit-library-engineer-weapon") {{
            x = 0f;
            y = 0f;
            mirror = false;
            reload = 15f;
            shoot.shots = 1;
            bullet = new BasicBulletType(4f, 10) {{
                width = 5f;
                height = 7f;
                lifetime = 30f;
            }};
        }});
    }

    /**
     * Inyección de la barra de estado de batería personalizada en la UI nativa.
     */
    @Override
    public void display(Unit unit, Table table) {
        super.display(unit, table);

        if (unit instanceof ManualBatteryUnit) {
            ManualBatteryUnit bUnit = (ManualBatteryUnit) unit;

            table.row();
            table.add(new Bar(
                () -> "Batería: " + (int) bUnit.battery + " / " + (int) bUnit.maxBattery,
                () -> Color.valueOf("f4d142"),
                () -> bUnit.maxBattery <= 0f ? 0f : bUnit.battery / bUnit.maxBattery
            )).growX().height(18f).padTop(4f);
        }
    }

    /**
     * Lógica de actualización por frame para decaimiento de batería usando Time.delta.
     */
    @Override
    public void update(Unit unit) {
        super.update(unit);

        if (unit instanceof ManualBatteryUnit) {
            ManualBatteryUnit bUnit = (ManualBatteryUnit) unit;

            // Decaimiento continuo de batería regulado por delta time
            if (bUnit.battery > 0f) {
                bUnit.battery = Math.max(0f, bUnit.battery - batteryDecayRate * Time.delta);
            }
        }
    }
}
