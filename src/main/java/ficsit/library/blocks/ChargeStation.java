package ficsit.library.blocks;

import mindustry.world.blocks.power.PowerNode;
import mindustry.gen.Building;
import mindustry.type.Item;
import mindustry.Vars;
import arc.util.Time;
import ficsit.library.content.FicsitBlocks;

public class ChargeStation extends PowerNode {
    public float transferInterval = 60f; 
    public int transferAmount = 10;

    public ChargeStation(String name) {
        super(name);
        hasItems = true;
        itemCapacity = 100;
        update = true;
    }

    public class ChargeStationBuild extends PowerNodeBuild {
        public float timer = 0f;

        @Override
        public boolean acceptItem(Building source, Item item) {
            return items.get(item) < getMaximumAccepted(item);
        }

        @Override
        public void updateTile() {
            super.updateTile();
            
            if (items.total() > 0) {
                timer += Time.delta;
                if (timer >= transferInterval) {
                    transferToSecretCore();
                    timer = 0f;
                }
            }
        }

        public void transferToSecretCore() {
            Building secretCore = Vars.world.build(0, 0);
            if (secretCore != null && secretCore.block == FicsitBlocks.secretCore) {
                for (Item item : Vars.content.items()) {
                    int amount = items.get(item);
                    if (amount > 0) {
                        int accepted = Math.min(amount, transferAmount);
                        int space = 20000 - secretCore.items.get(item);
                        int toTransfer = Math.min(accepted, space);
                        
                        if (toTransfer > 0) {
                            items.remove(item, toTransfer);
                            secretCore.items.add(item, toTransfer);
                        }
                    }
                }
            }
        }
    }
}
