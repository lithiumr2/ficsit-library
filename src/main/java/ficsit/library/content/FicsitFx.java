package ficsit.library.content;

import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Lines;
import mindustry.entities.Effect;
import mindustry.graphics.Pal;
import arc.math.Interp;

public class FicsitFx {
    public static final Effect cargoLaunch = new Effect(90f, e -> {
        // Simulación de un carguero/cápsula despegando hacia el cielo
        float rise = e.finpow() * 80f; // Sube 80 píxeles
        float size = (1f - e.fin()) * 6f; // Se hace pequeño a medida que sube
        float alpha = 1f - e.fin();

        Draw.color(Pal.accent, Pal.lightOrange, e.fin());
        Draw.alpha(alpha);
        
        // Dibuja el cuerpo de la nave/cápsula
        Fill.rect(e.x, e.y + rise, size, size * 1.5f);
        
        // Estela del motor
        Draw.color(Pal.techCrits);
        Lines.stroke(size / 2f * alpha);
        Lines.line(e.x, e.y + rise - size, e.x, e.y + rise - size - 10f * e.fout());
    });
}
