package ficsit.library.entities;

import arc.graphics.Color;
import mindustry.gen.Iconc;
import mindustry.gen.Unit;
import mindustry.graphics.Pal;
import mindustry.type.AmmoType;

/**
 * Tipo de munición que representa la Batería del Ingeniero.
 * 
 * Permite que el HUD nativo de Mindustry en HudFragment dibuje la barra
 * de batería con la curvatura y estética exacta de la barra de munición/escudo
 * en color Pal.accent (amarillo/dorado FICSIT).
 */
public class BatteryAmmoType implements AmmoType {

    @Override
    public String icon() {
        return Iconc.power + "";
    }

    @Override
    public Color color() {
        return Pal.accent;
    }

    @Override
    public Color barColor() {
        return Pal.accent;
    }

    @Override
    public void resupply(Unit unit) {
        // La recarga se gestiona autónomamente por proximidad al núcleo/cables en ManualBatteryUnit
    }
}
