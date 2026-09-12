package ficsit.library.entities;

import mindustry.type.UnitType;

public class DropPodUnitType extends UnitType {
    public DropPodUnitType(String name) {
        super(name);
        
        this.constructor = DropPodEntity::new;
        
        speed = 8f;
        accel = 0.5f;
        drag = 0.05f;
        flying = true;
        health = 5000f;
        armor = 10f;
        hitSize = 16f;
        
        drawCell = false;
        drawMinimap = false;
        isEnemy = false;
        hidden = true;
    }
}
