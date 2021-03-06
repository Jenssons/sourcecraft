package source.addable;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;

import basic.Loggger;
import cuboidFinder.CuboidFinder;
import minecraft.Position;
import minecraft.map.MinecraftMap;
import vmfWriter.Orientation;

public abstract class Addable {

	protected CuboidFinder cuboidFinder;
	protected MinecraftMap map;
	protected AddableManager manager;
	protected int[] materialUsedFor = {};
	private int[][] additionalMaterial = null;

	public Addable() {
	}

	public Iterable<Addable> getInstances() {
		List<Addable> list = new LinkedList<>();
		try {
			Addable a = (Addable) this.getClass()
					.getConstructors()[0].newInstance();
			list.add(a);
		} catch (InstantiationException ex) {
			Loggger.warn("Addable " + this.getClass()
					.getSimpleName() + " does not have a suitable constructor (InstantiationException)");
		} catch (IllegalAccessException ex) {
			Loggger.warn("Addable " + this.getClass()
					.getSimpleName() + " does not have a suitable constructor (IllegalAccessException)");
		} catch (IllegalArgumentException ex) {
			Loggger.warn("Addable " + this.getClass()
					.getSimpleName() + " does not have a suitable constructor (IllegalArgumentException)");
		} catch (InvocationTargetException ex) {
			Loggger.warn("Addable " + this.getClass()
					.getSimpleName() + " does not have a suitable constructor (InvocationTargetException)");
		}
		return list;
	}

	public String getName() {
		return this.getClass()
				.getSimpleName();
	}

	public void setAccess(CuboidFinder cuboidFinder, MinecraftMap map, AddableManager manager) {
		this.cuboidFinder = cuboidFinder;
		this.map = map;
		this.manager = manager;
	}

	/**
	 * Returns whether the added blocks are air.
	 */
	public boolean isAirBlock() {
		return true;
	}

	public boolean hasWall(Orientation orientation) {
		return false;
	}

	public int[] getMaterialUsedFor() {
		return this.materialUsedFor;
	}

	public void setMaterialUsedFor(int[] material) {
		this.materialUsedFor = material;
	}

	public void setMaterialUsedFor(int material) {
		this.materialUsedFor = new int[1];
		this.materialUsedFor[0] = material;
	}

	public void setAdditionalMaterial(int[][] material) {
		this.additionalMaterial = material;
	}

	public int[][] getAdditionalMaterial() {
		return this.additionalMaterial;
	}

	/**
	 * For a given position with given material, this method adds solids and
	 * entities to the resulting Source map.
	 *
	 * @param position
	 * @param material
	 */
	public abstract void add(Position position, int material);

}
