package source.addable.addable.stairs;

import java.util.LinkedList;

import minecraft.Position;
import minecraft.SubBlockPosition;
import source.Material;
import source.addable.Addable;
import vmfWriter.Orientation;

/**
 *
 *
 */
public class StairsHighSouth extends Addable {

	private int materialReplacement;

	public StairsHighSouth() {
		int[] temp = { Material.OAK_STAIRS_HIGH_SOUTH, Material.COBBLESTONE_STAIRS_HIGH_SOUTH, Material.BRICK_STAIRS_HIGH_SOUTH,
				Material.STONE_BRICK_STAIRS_HIGH_SOUTH, Material.NETHER_BRICK_STAIRS_HIGH_SOUTH, Material.SANDSTONE_STAIRS_HIGH_SOUTH, };
		super.setMaterialUsedFor(temp);
	}

	public StairsHighSouth(int material, int materialReplacement) {
		int[] temp = { material };
		super.setMaterialUsedFor(temp);
		this.materialReplacement = materialReplacement;
	}

	@Override
	public Iterable<Addable> getInstances() {
		LinkedList<Addable> list = new LinkedList<Addable>();
		list.add(new StairsHighSouth(Material.OAK_STAIRS_HIGH_SOUTH, Material.OAK_SLAB));
		list.add(new StairsHighSouth(Material.COBBLESTONE_STAIRS_HIGH_SOUTH, Material.COBBLESTONE_SLAB));
		list.add(new StairsHighSouth(Material.BRICK_STAIRS_HIGH_SOUTH, Material.BRICK_SLAB));
		list.add(new StairsHighSouth(Material.STONE_BRICK_STAIRS_HIGH_SOUTH, Material.STONE_BRICK_SLAB));
		list.add(new StairsHighSouth(Material.SANDSTONE_STAIRS_HIGH_SOUTH, Material.SANDSTONE_SLAB));
		return list;
	}

	@Override
	public boolean hasWall(Orientation orientation) {
		return orientation != Orientation.SOUTH;
	}

	@Override
	public void add(Position p, int material) {
		this.map.markAsConverted(p);
		int x = p.getX();
		int y = p.getY();
		int z = p.getZ();
		if (this.map.hasOrHadMaterial(new Position(x + 1, y, z), this.getMaterialUsedFor())) {
			this.map.addSubBlock(p, SubBlockPosition.BOTTOM_EAST_SOUTH, this.materialReplacement);
		} else {
			if (this.map.hasOrHadMaterial(new Position(x, y, z - 1), new StairsHighEast().getMaterialUsedFor())) { // negative
				// corner
				this.map.addSubBlock(p, SubBlockPosition.BOTTOM_EAST_SOUTH, this.materialReplacement);
				this.map.addSubBlock(p, SubBlockPosition.BOTTOM_EAST_NORTH, this.materialReplacement);
			} else if (!this.map.hasOrHadMaterial(new Position(x, y, z + 1), new StairsHighWest().getMaterialUsedFor())) {
				this.map.addSubBlock(p, SubBlockPosition.BOTTOM_EAST_SOUTH, this.materialReplacement);
			}
		}
		if (this.map.hasOrHadMaterial(new Position(x - 1, y, z), this.getMaterialUsedFor())) {
			this.map.addSubBlock(p, SubBlockPosition.BOTTOM_WEST_SOUTH, this.materialReplacement);
		} else {
			if (this.map.hasOrHadMaterial(new Position(x, y, z - 1), new StairsHighWest().getMaterialUsedFor())) { // negative
				// corner
				this.map.addSubBlock(p, SubBlockPosition.BOTTOM_WEST_SOUTH, this.materialReplacement);
				this.map.addSubBlock(p, SubBlockPosition.BOTTOM_WEST_NORTH, this.materialReplacement);
			} else if (!this.map.hasOrHadMaterial(new Position(x, y, z + 1), new StairsHighEast().getMaterialUsedFor())) {
				this.map.addSubBlock(p, SubBlockPosition.BOTTOM_WEST_SOUTH, this.materialReplacement);
			}
		}
		this.map.addSubBlock(p, SubBlockPosition.TOP_EAST_SOUTH, this.materialReplacement);
		this.map.addSubBlock(p, SubBlockPosition.TOP_EAST_NORTH, this.materialReplacement);
		this.map.addSubBlock(p, SubBlockPosition.TOP_WEST_SOUTH, this.materialReplacement);
		this.map.addSubBlock(p, SubBlockPosition.TOP_WEST_NORTH, this.materialReplacement);
	}
}
