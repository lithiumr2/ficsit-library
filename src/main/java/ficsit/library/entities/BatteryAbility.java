package ficsit.library.entities;

import arc.scene.ui.layout.Table;
import mindustry.entities.abilities.ForceFieldAbility;
import mindustry.gen.Unit;
import mindustry.graphics.Pal;
import mindustry.ui.Bar;

/**
 * Habilidad de batería para el Ingeniero compatible con el HUD nativo de Mindustry.
 *
 * Al heredar de ForceFieldAbility:
 * 1. En HudFragment.java, el motor busca:
 *    Structs.find(player.unit().abilities, a -> a instanceof ForceFieldAbility)
 *    Al detectar esta habilidad, calcula:
 *    shieldFrac[0] = player.unit().shield / ff.max;
 *    Y renderiza el SideBar de escudo/batería en color Pal.accent a la izquierda
 *    de la vida del jugador, idéntico a cómo se muestra en el Oct y en el Quasar.
 * 2. En el menú de información de la unidad, añade la barra 'Batería' con el estilo nativo.
 */
public class BatteryAbility extends ForceFieldAbility {

    public BatteryAbility() {
        this(100f);
    }

    public BatteryAbility(float max) {
        // radius = 0f, regen = 0f, max, cooldown = 0f
        // radius = 0f asegura que no absorba proyectiles ni dibuje cúpulas físicas
        super(0f, 0f, max, 0f);
        this.display = true;
    }

    @Override
    public void update(Unit unit) {
        if (unit instanceof ManualBatteryUnit) {
            ManualBatteryUnit b = (ManualBatteryUnit) unit;
            this.max = b.maxBattery;
            // Sincronizar el campo nativo unit.shield con el valor real de batería
            // HudFragment lee shield / ff.max para pintar la barra Pal.accent
            unit.shield = Math.max(0f, b.battery);
        }
    }

    @Override
    public void draw(Unit unit) {
        // No dibujar domo poligonal en el mapa
    }

    @Override
    public void death(Unit unit) {
        // Evitar sonido o efecto de ruptura de escudo gigante
    }

    @Override
    public void displayBars(Unit unit, Table bars) {
        if (unit instanceof ManualBatteryUnit) {
            ManualBatteryUnit b = (ManualBatteryUnit) unit;
            bars.add(new Bar(
                () -> "Batería: " + (int) b.battery + " / " + (int) b.maxBattery,
                () -> Pal.accent,
                () -> b.maxBattery <= 0f ? 0f : b.battery / b.maxBattery
            )).row();
        }
    }
}
