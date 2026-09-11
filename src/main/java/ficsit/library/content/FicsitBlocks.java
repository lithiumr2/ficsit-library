package ficsit.library.content;

import mindustry.world.Block;
import ficsit.library.world.SecretCore;

public class FicsitBlocks {
    public static Block secretCore;

    public static void load() {
        // Inicializamos el núcleo secreto que creamos antes
        secretCore = new SecretCore("secret-core");
    }
}
