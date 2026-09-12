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

        // Capacidad de munición / batería (utilizado por el SideBar nativo del HUD)
        ammoCapacity = 100;
        ammoType = new BatteryAmmoType();

        // Desactivar el dibujo de círculo de daño de escudo sobre el sprite (igual que en Oct)
        drawShields = false;

        // Habilidad de batería para el inspector / panel de estadísticas de la unidad
        abilities.add(new BatteryAbility(100f));

        // Arma principal de la unidad (useAmmo = false para que disparar use su propio balance o no consuma la batería de soporte vital)
        weapons.add(new Weapon("ficsit-library-engineer-weapon") {{
            x = 0f;
            y = 0f;
            mirror = false;
            reload = 15f;
            shoot.shots = 1;
            useAmmo = false;
            bullet = new BasicBulletType(4f, 10) {{
                width = 5f;
                height = 7f;
                lifetime = 30f;
            }};
        }});
    }
}
