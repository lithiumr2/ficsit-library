package ficsit.library.blocks;

import mindustry.world.Block;
import mindustry.gen.Building;
import mindustry.gen.Icon;
import mindustry.ui.Styles;
import arc.scene.ui.layout.Table;
import mindustry.type.Item;
import mindustry.Vars;
import ficsit.library.content.FicsitBlocks;
import ficsit.library.content.FicsitFx;

public class ChargeStation extends Block {

    public ChargeStation(String name) {
        super(name);
        hasItems = true;
        itemCapacity = 100;
        update = true;
        configurable = true;
        destructible = true;
        solid = true;
    }

    public class ChargeStationBuild extends Building {
        @Override
        public boolean acceptItem(Building source, Item item) {
            return items.get(item) < getMaximumAccepted(item);
        }

        @Override
        public void buildConfiguration(Table table) {
            super.buildConfiguration(table);
            
            // Botón estilo almacén Erekir
            table.button(Icon.upOpen, Styles.cleari, () -> {
                transferToSecretCore();
            }).size(40f);
        }
        
        @Override
        public boolean onConfigureBuildTapped(Building other){
            if(this == other){
                deselect();
                // Activar transferencia directa si se vuelve a tocar
                transferToSecretCore();
                return false;
            }
            return super.onConfigureBuildTapped(other);
        }

        public void transferToSecretCore() {
            Building secretCore = Vars.world.build(0, 0);
            if (secretCore != null && secretCore.block == FicsitBlocks.secretCore) {
                boolean hasTransferred = false;
                
                for (Item item : Vars.content.items()) {
                    int amount = items.get(item);
                    if (amount > 0) {
                        int space = 1000 - secretCore.items.get(item); // 1000 es el límite
                        int toTransfer = Math.min(amount, space);
                        
                        if (toTransfer > 0) {
                            items.remove(item, toTransfer);
                            secretCore.items.add(item, toTransfer);
                            hasTransferred = true;
                        }
                    }
                }
                
                if (hasTransferred) {
                    FicsitFx.cargoLaunch.at(x, y);
                    if (isLocal()) {
                        Vars.ui.showInfoToast("Enviando cargamento al bolsillo orbital...", 2f);
                    }
                } else if (items.total() > 0) {
                    if (isLocal()) {
                        Vars.ui.showInfoToast("¡Inventario de bolsillo lleno (Límite 1000)!", 2f);
                    }
                }
            }
        }
    }
}
