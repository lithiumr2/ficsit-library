package ficsit.library.content;

import mindustry.world.Block;
import mindustry.world.blocks.storage.CoreBlock;
import mindustry.type.Category;
import ficsit.library.world.SecretCore;

public class FicsitBlocks {
    public static Block secretCore;
    public static CoreBlock hub;

    public static void load() {
        secretCore = new SecretCore("secret-core");
        
        hub = new CoreBlock("ficsit-hub") {{
            requirements(Category.effect, mindustry.type.ItemStack.empty);
            alwaysUnlocked = true;
            isFirstTier = true;
            unitType = FicsitUnits.engineer;
            health = 5000;
            itemCapacity = 4000;
            size = 4;
            armor = 5f;
            // Build visibility can be shown or hidden, usually shown for cores
            // We want it to be placed via the drop pod.
        }};
    }
}
