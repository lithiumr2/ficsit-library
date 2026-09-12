package ficsit.library.world;

import mindustry.maps.generators.PlanetGenerator;
import mindustry.content.Blocks;
import arc.math.geom.Vec3;
import arc.graphics.Color;
import ficsit.library.core.FicsitGenerator;
import mindustry.game.Team;
import mindustry.world.Tiles;
import mindustry.type.Sector;

public class FicsitPlanetGenerator extends PlanetGenerator {

    @Override
    public float getHeight(Vec3 position) {
        return 0;
    }

    @Override
    public Color getColor(Vec3 position) {
        return Color.valueOf("4a9937");
    }

    @Override
    public int getSectorSize(Sector sector) {
        return 500;
    }

    @Override
    public void generate(Tiles tiles, Sector sec, int seed) {
        this.tiles = tiles;
        this.sector = sec;
        this.width = tiles.width;
        this.height = tiles.height;
        
        FicsitGenerator.generateChunk(tiles, 0, 0, 0, 0, width, height);
        tiles.getc(width / 2, height / 2).setBlock(Blocks.coreShard, Team.sharded);
    }

    @Override
    protected void generate() {
        if (tiles != null) {
            FicsitGenerator.generateChunk(tiles, 0, 0, 0, 0, width, height);
            tiles.getc(width / 2, height / 2).setBlock(Blocks.coreShard, Team.sharded);
        }
    }
}
