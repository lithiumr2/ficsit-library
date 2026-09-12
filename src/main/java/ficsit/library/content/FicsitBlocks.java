package ficsit.library.content;

import mindustry.world.Block;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import ficsit.library.blocks.HubBlock;
import ficsit.library.blocks.ChargeStation;
import mindustry.content.Items;

public class FicsitBlocks {
    public static Block hub;
    public static Block chargeStation;

    public static void load() {
        hub = new HubBlock("ficsit-hub") {{
            requirements(Category.effect, ItemStack.with());
            alwaysUnlocked = true;
            isFirstTier = true;
            unitType = FicsitUnits.engineer;
            health = 5000;
            itemCapacity = 4000;
            size = 4;
            armor = 5f;
            localizedName = "Cápsula FICSIT (HUB)";
            description = "El centro de operaciones principal. Procesa minerales básicos a mano y recarga el traje del ingeniero de forma inalámbrica.";
        }};

        chargeStation = new ChargeStation("charge-station") {{
            requirements(Category.power, ItemStack.with(Items.copper, 20, Items.lead, 10));
            health = 100;
            laserRange = 10;
            maxNodes = 10;
            localizedName = "Estación de Carga";
            description = "Extiende la red de energía del traje y permite transferir ítems manualmente entre tu inventario y el núcleo.";
        }};
    }
}
