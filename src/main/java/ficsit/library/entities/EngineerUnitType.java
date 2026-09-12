package ficsit.library.entities;

import mindustry.entities.bullet.BasicBulletType;
import mindustry.type.UnitType;
import mindustry.type.Weapon;

/**
 * Definición de tipo para la unidad Ingeniero con soporte para batería manual.
 */
public class EngineerUnitType extends UnitType {

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

        // Desactivar el dibujo de círculo de daño de escudo sobre el sprite (igual que en Oct)
        drawShields = false;

        // Añadir la habilidad de batería que hereda de ForceFieldAbility
        // Esto hace que Mindustry dibuje automáticamente la barra de batería (Pal.accent)
        // a la izquierda de la barra de vida en el HUD del jugador, exactamente como en el Oct y Quasar.
        abilities.add(new BatteryAbility(100f));

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
}
