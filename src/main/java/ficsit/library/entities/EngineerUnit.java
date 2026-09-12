package ficsit.library.entities;

import mindustry.type.UnitType;
import mindustry.type.Weapon;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.gen.Unit;
import arc.graphics.Color;
import arc.scene.ui.layout.Table;
import mindustry.ui.Bar;

public class EngineerUnit extends UnitType {

    public EngineerUnit(String name) {
        super(name);
        
        constructor = EngineerUnitEntity::create;
        
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

        drawShields = false;
        abilities.add(new BatteryAbility(100f));

        Weapon mainWeapon = new Weapon("ficsit-library-engineer-weapon") {{
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
        }};
        
        weapons.add(mainWeapon);
    }

    @Override
    public void display(Unit unit, Table table) {
        super.display(unit, table);

        if(unit instanceof EngineerUnitEntity) {
            EngineerUnitEntity eng = (EngineerUnitEntity) unit;
            
            table.row();
            table.add(new Bar(
                () -> "Batería",
                () -> Color.valueOf("f4d142"),
                () -> eng.battery / eng.maxBattery
            )).growX().height(18f).padTop(4f);
        }
    }
}
