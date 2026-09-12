package ficsit.library.content;

import mindustry.type.Item;
import arc.graphics.Color;

public class FicsitItems {
    public static Item copperIngot, leadPlate;

    public static void load() {
        copperIngot = new Item("copper-ingot", Color.valueOf("d99d73")) {{
            hardness = 1;
            cost = 1f;
            localizedName = "Lingote de Cobre";
            description = "Cobre fundido en un lingote estandarizado. Usado para cableado y construcción básica.";
        }};

        leadPlate = new Item("lead-plate", Color.valueOf("8c7fa9")) {{
            hardness = 1;
            cost = 1f;
            localizedName = "Placa de Plomo";
            description = "Plomo prensado en placas resistentes. Usado para blindaje y contenedores.";
        }};
    }
}
