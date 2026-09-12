package ficsit.library.entities;

import mindustry.gen.UnitEntity;
import mindustry.gen.Building;
import mindustry.gen.EntityMapping;
import arc.util.Time;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.Color;

public class EngineerUnitEntity extends UnitEntity {
    public float battery = 100f;
    public float maxBattery = 100f;
    public float radioDeRecarga = 400f;

    // Registra la entidad personalizada dentro del mapa interno de Mindustry
    public static final int classId = EntityMapping.register("EngineerUnitEntity", EngineerUnitEntity::new);

    @Override
    public int classId() {
        return classId;
    }

    @Override
    public void update() {
        super.update();

        if(!isAdded() || dead) return;

        Building nucleo = closestCore();
        boolean cercaDeNucleo = (nucleo != null && within(nucleo, radioDeRecarga));

        if(cercaDeNucleo) {
            if(battery < maxBattery) {
                battery = Math.min(battery + 2f, maxBattery);
            }
        } else {
            if(battery > 0 && (int)Time.time % 60 == 0) {
                battery = Math.max(battery - 0.833f, 0f);
            }
        }

        if(battery <= 0) {
            damage(0.05f);
        }

        if(shield < battery) {
            battery = Math.max(0f, shield);
        } else {
            shield = Math.max(0f, battery);
        }
    }

    @Override
    public void draw() {
        super.draw();

        if(dead || !isAdded()) return;

        // Renderizado visual en la capa UI sobre la unidad
        float barWidth = 16f;
        float barHeight = 3f;
        float yOffset = hitSize() + 6f;
        float pct = Math.max(0f, Math.min(1f, battery / maxBattery));

        Draw.z(110f); // Capa z alta para dibujar sobre la unidad y evitar oclusión

        // Fondo oscuro de la barra
        Draw.color(Color.black, 0.8f);
        Fill.rect(x, y + yOffset, barWidth + 1.5f, barHeight + 1.5f);

        // Barra de energía (Amarillo eléctrico)
        Draw.color(Color.valueOf("f4d142"));
        Fill.rect(x - (barWidth * (1f - pct)) / 2f, y + yOffset, barWidth * pct, barHeight);

        Draw.reset();
    }
}
