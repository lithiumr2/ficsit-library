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
import mindustry.world.modules.ItemModule;
import arc.util.io.Reads;
import arc.util.io.Writes;

public class HubBlock extends CoreBlock {
    public HubBlock(String name) {
        super(name);
        configurable = true;
    }

    public class HubBuild extends CoreBuild {
        // Inventario fisico real del HUB
        public ItemModule realHubItems = new ItemModule();

        @Override
        public void buildConfiguration(Table table) {
            super.buildConfiguration(table);
            
            table.button(Icon.pencil, Styles.cleari, () -> {
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

        @Override
        public boolean acceptItem(Building source, Item item){
            return realHubItems.get(item) < getMaximumAccepted(item);
        }

        @Override
        public void handleItem(Building source, Item item){
            realHubItems.add(item, 1);
        }

        @Override
        public void write(Writes write) {
            ItemModule temp = this.items;
            this.items = realHubItems;
            super.write(write);
            this.items = temp;
        }

        @Override
        public void read(Reads read, byte revision) {
            super.read(read, revision);
            realHubItems.clear();
            realHubItems.add(this.items);
        }
    }

    public static void showCraftingMenu(HubBuild core) {
        BaseDialog dialog = new BaseDialog("Banco de Crafteo Manual (HUB)");
        dialog.addCloseButton();
        
        Table t = dialog.cont;
        t.add("Fabricación Básica. Selecciona una receta:").pad(10f).row();
        
        t.button(b -> {
            b.image(FicsitItems.copperIngot.uiIcon).size(32f).padRight(10f);
            b.add("Fabricar Lingote de Cobre\nCosto: 1 Cobre").left();
        }, () -> {
            if (core.realHubItems.has(Items.copper, 1)) {
                core.realHubItems.remove(Items.copper, 1);
                core.realHubItems.add(FicsitItems.copperIngot, 1);
            } else {
                Vars.ui.showInfoToast("No hay suficiente cobre en el núcleo", 2f);
            }
        }).size(350f, 60f).pad(4f).row();

        t.button(b -> {
            b.image(FicsitItems.leadPlate.uiIcon).size(32f).padRight(10f);
            b.add("Fabricar Placa de Plomo\nCosto: 1 Plomo").left();
        }, () -> {
            if (core.realHubItems.has(Items.lead, 1)) {
                core.realHubItems.remove(Items.lead, 1);
                core.realHubItems.add(FicsitItems.leadPlate, 1);
            } else {
                Vars.ui.showInfoToast("No hay suficiente plomo en el núcleo", 2f);
            }
        }).size(350f, 60f).pad(4f).row();

        dialog.show();
    }
}
