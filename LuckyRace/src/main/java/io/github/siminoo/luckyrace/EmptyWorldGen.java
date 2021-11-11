package io.github.siminoo.luckyrace;

import java.util.Random;

import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.generator.ChunkGenerator;

public class EmptyWorldGen {
	public void CreateEmptyWorld(String worldname) {
		WorldCreator wc = new WorldCreator(worldname);
		wc.generator(new ChunkGenerator() {
		    public byte[] generate(World world, Random random, int x, int z) {
		        return new byte[32768];
		    }
		});
		World world = wc.createWorld();
	}
}
