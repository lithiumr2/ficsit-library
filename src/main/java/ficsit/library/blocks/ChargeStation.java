package ficsit.library.blocks;

import mindustry.world.Block;
import mindustry.gen.Building;
import mindustry.ui.dialogs.BaseDialog;
import arc.scene.ui.layout.Table;
import mindustry.Vars;
import mindustry.gen.Icon;
import mindustry.ui.Styles;
import mindustry.type.Item;
import mindustry.world.blocks.power.PowerNode;
import mindustry.world.meta.BlockGroup;

public class ChargeStation extends PowerNode {

    public ChargeStation(String name) {
        super(name);
        update = true;
        solid = true;
        configurable = true;
        hasPower = true;
        consumesPower = true;
        group = BlockGroup.power;
        // Make it look different and have a UI
    }

    public class ChargeStationBuild extends PowerNodeBuild {
        @Override
        public void buildConfiguration(Table table) {
            super.buildConfiguration(table);
            table.button(Icon.upOpen, Styles.cleari, () -> {
                showLoadoutMenu(this);
            }).size(40f).tooltip("Cargar Inventario");
        }
        
        @Override
        public boolean onConfigureBuildTapped(Building other){
            if(this == other){
                deselect();
                showLoadoutMenu(this);
                return false;
            }
            return super.onConfigureBuildTapped(other);
        }
    }

    public static void showLoadoutMenu(Building station) {
        BaseDialog dialog = new BaseDialog("Estación de Carga");
        dialog.addCloseButton();
        Table t = dialog.cont;
        
        t.add("Carga ítems a tu traje desde la red del núcleo.").pad(10f).row();
        
        if (station.team.cores().isEmpty()) {
            t.add("No hay un HUB conectado.").row();
            dialog.show();
            return;
        }

        Building core = station.team.cores().first();

        t.pane(p -> {
            for (Item item : Vars.content.items()) {
                if (core.items.has(item)) {
                    p.button(b -> {
                        b.image(item.uiIcon).size(32f).padRight(10f);
                        b.add("Tomar " + item.localizedName).left();
                    }, () -> {
                        mindustry.gen.Unit playerUnit = Vars.player.unit();
                        if (playerUnit != null) {
                            int amountToTake = Math.min(core.items.get(item), playerUnit.itemCapacity() - playerUnit.stack.amount);
                            if (playerUnit.stack.item != item && playerUnit.stack.amount > 0) {
                                Vars.ui.showInfoToast("Ya llevas otro tipo de ítem. Vacía tu inventario primero.", 2f);
                                return;
                            }
                            if (amountToTake > 0) {
                                core.items.remove(item, amountToTake);
                                playerUnit.addItem(item, amountToTake);
                                Vars.ui.showInfoToast("Has cargado " + amountToTake + " " + item.localizedName, 2f);
                            } else {
                                Vars.ui.showInfoToast("No puedes cargar más ítems.", 2f);
                            }
                        }
                    }).size(300f, 50f).pad(4f).row();
                }
            }
            
            p.button(b -> {
                b.image(Icon.downOpen).size(32f).padRight(10f);
                b.add("Vaciar inventario al núcleo").left();
            }, () -> {
                mindustry.gen.Unit playerUnit = Vars.player.unit();
                if (playerUnit != null && playerUnit.stack.amount > 0) {
                    Item item = playerUnit.stack.item;
                    int amount = playerUnit.stack.amount;
                    core.items.add(item, amount);
                    playerUnit.clearItem();
                    Vars.ui.showInfoToast("Has vaciado " + amount + " " + item.localizedName + " al núcleo", 2f);
                } else {
                    Vars.ui.showInfoToast("Tu inventario está vacío.", 2f);
                }
            }).size(300f, 50f).pad(4f).marginTop(20f).row();
        }).growX().maxHeight(400f);
        
        dialog.show();
    }
}
