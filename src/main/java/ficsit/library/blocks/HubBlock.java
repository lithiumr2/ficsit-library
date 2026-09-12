package ficsit.library.blocks;

import mindustry.world.blocks.storage.CoreBlock;
import mindustry.gen.Building;
import mindustry.gen.Icon;
import mindustry.ui.Styles;
import mindustry.ui.dialogs.BaseDialog;
import arc.scene.ui.layout.Table;
import mindustry.Vars;
import mindustry.content.Items;
import ficsit.library.content.FicsitItems;
import mindustry.type.Item;

public class HubBlock extends CoreBlock {
    public HubBlock(String name) {
        super(name);
        configurable = true;
    }

    public class HubBuild extends CoreBuild {
        @Override
        public void buildConfiguration(Table table) {
            super.buildConfiguration(table);
            
            // Botón en la configuración del bloque (al hacer clic en el HUB)
            table.button(Icon.hammer, Styles.cleari, () -> {
                showCraftingMenu(this);
            }).size(40f);
        }
        
        @Override
        public boolean onConfigureBuildTapped(Building other){
            if(this == other){
                deselect();
                showCraftingMenu(this);
                return false;
            }
            return super.onConfigureBuildTapped(other);
        }
    }

    public static void showCraftingMenu(CoreBuild core) {
        BaseDialog dialog = new BaseDialog("Banco de Crafteo Manual (HUB)");
        dialog.addCloseButton();
        
        Table t = dialog.cont;
        t.add("Fabricación Básica. Selecciona una receta:").pad(10f).row();
        
        // --- Receta 1: Lingote de Cobre ---
        t.button(b -> {
            b.image(FicsitItems.copperIngot.uiIcon).size(32f).padRight(10f);
            b.add("Fabricar Lingote de Cobre\nCosto: 1 Cobre").left();
        }, () -> {
            if (core.items.has(Items.copper, 1)) {
                core.items.remove(Items.copper, 1);
                core.items.add(FicsitItems.copperIngot, 1);
            } else {
                Vars.ui.showInfoToast("No hay suficiente cobre en el núcleo", 2f);
            }
        }).size(350f, 60f).pad(4f).row();

        // --- Receta 2: Placa de Plomo ---
        t.button(b -> {
            b.image(FicsitItems.leadPlate.uiIcon).size(32f).padRight(10f);
            b.add("Fabricar Placa de Plomo\nCosto: 1 Plomo").left();
        }, () -> {
            if (core.items.has(Items.lead, 1)) {
                core.items.remove(Items.lead, 1);
                core.items.add(FicsitItems.leadPlate, 1);
            } else {
                Vars.ui.showInfoToast("No hay suficiente plomo en el núcleo", 2f);
            }
        }).size(350f, 60f).pad(4f).row();

        dialog.show();
    }
}
