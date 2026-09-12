package ficsit.library.content;

import mindustry.world.Block;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import ficsit.library.blocks.HubBlock;
import ficsit.library.blocks.CapsuleBlock;
import ficsit.library.blocks.ChargeStation;
import ficsit.library.world.SecretCore;
import mindustry.content.Items;

public class FicsitBlocks {
    public static Block secretCore;
    public static Block hub;
    public static Block capsule;
    public static Block chargeStation;

    public static void load() {
        secretCore = new SecretCore("secret-core");
        
        capsule = new CapsuleBlock("ficsit-capsule") {{
            localizedName = "Cápsula de Descenso";
            description = "El punto de llegada inicial. Mantiene funciones básicas de supervivencia y recarga tu traje hasta que construyas un HUB completo.";
        }};
        
        hub = new HubBlock("ficsit-hub") {{
            requirements(Category.effect, ItemStack.with(Items.copper, 100, Items.lead, 100)); // Costo real ahora
            alwaysUnlocked = true;
            isFirstTier = true;
            unitType = FicsitUnits.engineer;
            health = 5000;
            itemCapacity = 4000;
            size = 4;
            armor = 5f;
            localizedName = "FICSIT HUB";
            description = "El centro de operaciones principal. Procesa minerales básicos a mano y recarga el traje del ingeniero de forma inalámbrica.";
        }};

        chargeStation = new ChargeStation("charge-station") {{
            requirements(Category.effect, ItemStack.with(Items.copper, 20, Items.lead, 10)); // Mover a logistica/effect
            health = 100;
            size = 1;
            localizedName = "Recargador de Inventario";
            description = "Estación de logística remota. Toca para empaquetar y enviar su inventario local directamente a tu bolsillo orbital.";
        }};
    }
}
