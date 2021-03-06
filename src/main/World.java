package main;

import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;

import javax.swing.ImageIcon;

import gui.panel.LabeledCoordinates;
import minecraft.Position;
import minecraft.reader.nbt.PlayerInLevelReader;
import periphery.Periphery;

public class World {

	public static final String PLAYER_POSITION = "Player position";

	private static final String WORLD_LEVEL_DAT_NAME = "level.dat";

	private static final String WORLD_ICON_NAME = "icon.png";

	private static final int ICON_SIZE = 64;

	private String name;

	private ImageIcon icon;

	private LabeledCoordinates playerPosition;
	private LabeledCoordinates worldSpawnPosition;

	public World(String name) throws IOException {
		this.name = name;
		File file = new File(Periphery.CONFIG.getWorldPath(this), WORLD_LEVEL_DAT_NAME);
		Position player = new Position();
		Position worldSpawn = new Position();
		DataInputStream stream = new DataInputStream(new GZIPInputStream(new FileInputStream(file)));
		PlayerInLevelReader reader = new PlayerInLevelReader(stream);
		reader.read();
		player = reader.getPlayerPosition();
		worldSpawn = reader.getWorldSpawn();
		this.playerPosition = new LabeledCoordinates(PLAYER_POSITION, player, player);
		this.worldSpawnPosition = new LabeledCoordinates("World spawn", worldSpawn, worldSpawn);
		this.playerPosition.enlarge(new Position(-20, -30, -20), new Position(20, 30, 20));
		this.worldSpawnPosition.enlarge(new Position(-20, -30, -20), new Position(20, 30, 20));
	}

	@Override
	public String toString() {
		return this.name;
	}

	@Override
	public boolean equals(Object another) {
		if (another instanceof World == false) {
			return false;
		}
		World anotherWorld = (World) another;
		return this.name.equals(anotherWorld.name);
	}

	public ImageIcon getIcon() {
		if (this.icon == null) {
			File path = new File(Periphery.CONFIG.getWorldPath(this), WORLD_ICON_NAME);
			ImageIcon icon;
			if (path.exists()) {
				icon = new ImageIcon(path.toString());
				this.icon = new ImageIcon(icon.getImage()
						.getScaledInstance(ICON_SIZE, ICON_SIZE, 0));
			} else {
				this.icon = new ImageIcon(new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB).getScaledInstance(ICON_SIZE, ICON_SIZE, 0));
			}

		}
		return this.icon;
	}

	public LabeledCoordinates getPlayerPosition() {
		return this.playerPosition;
	}

	public LabeledCoordinates getWorldSpawnPosition() {
		return this.worldSpawnPosition;
	}

	public String getName() {
		return this.name;
	}

}
