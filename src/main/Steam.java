package main;

import java.io.File;
import java.nio.file.Path;

import basic.Loggger;
import minecraft.TextureFolderMover;
import periphery.Periphery;
import periphery.SourceGame;
import periphery.TextureOptions;
import periphery.TexturePack;

public class Steam {

	private static final String STEAM_NAME = "Steam";
	private static final String STEAM_NAME_UNIX = ".steam" + File.separator + "steam";

	public static final String STEAM_GAME_PATH() {
		if (Main.isUnix()) {
			return "steamapps" + File.separator + "common";
		}
		return "SteamApps" + File.separator + "common";
	};

	public static final String STEAM_SDK_PATH = "sourcesdk_content";
	public static final String STEAM_MAP_SRC_PATH = "mapsrc";

	private static final String[] POTENTIAL_STEAM_PATH_GRAND_PARENTS = { "C:", "D:", "E:", File.separator };
	private static final String[] POTENTIAL_STEAM_PATH_PARENTS = { "Program Files", "Program Files (x86)", "Programs", "Programme" };

	public static File getSteamPath() {
		File steamPath = Periphery.CONFIG.getSteamPath();
		if (!isSteamPath(steamPath)) {
			File potentialPath = guessSteamPath();
			if (potentialPath != null) {
				Loggger.log("guessed steam path: " + potentialPath);
				steamPath = potentialPath;
			}
			Periphery.CONFIG.setSteamPath(steamPath);
		}
		return steamPath;
	}

	private static File guessSteamPath() {
		if (Main.isUnix()) {
			File potentialPath = new File(System.getProperty("user.home") + File.separator + STEAM_NAME_UNIX);
			if (isSteamPath(potentialPath)) {
				return potentialPath;
			}
		}
		for (String first : POTENTIAL_STEAM_PATH_GRAND_PARENTS) {
			for (String second : POTENTIAL_STEAM_PATH_PARENTS) {
				File potentialPath = new File(first + File.separator + second + File.separator + STEAM_NAME);
				if (isSteamPath(potentialPath)) {
					return potentialPath;
				}
			}
		}
		return null;
	}

	public static boolean isSteamPath(File path) {
		if (path == null) {
			return false;
		}
		if (!path.exists() || !path.isDirectory() || !path.getName()
				.toLowerCase()
				.equals(STEAM_NAME.toLowerCase())) {
			return false;
		}
		File gamePath = new File(path.toString() + File.separator + STEAM_GAME_PATH());
		if (!gamePath.exists() || !gamePath.isDirectory()) {
			return false;
		}
		return true;
	}

	public static boolean isGameInstalled(SourceGame game) {
		File hammer = new File(getHammerPath(game));
		return hammer.exists();
	}

	public static String getGamePathString(SourceGame game) {
		if (game == null) {
			game = Periphery.CONFIG.getGames()
					.get(0);
		}
		return game.getGamePath();
	}

	public static String getHammerPath(SourceGame game) {
		return game.getGameHammerPath(Periphery.CONFIG);
	}

	public static final String TEXTURES_NOT_OK = "";
	public static final String TEXTURES_OK = "Textures are in the right folder.";

	public static boolean areTexturesUpToDate(SourceGame game, TexturePack pack) {
		File materiaPath = game.getMatriealPath(pack);
		if (materiaPath == null) {
			return false;
		}
		Loggger.log("checking textures in " + materiaPath.toString());
		if (materiaPath == null || materiaPath.getParentFile()
				.exists() == false) {
			Loggger.warn("Cannot find material path.");
			return false;
		}
		Path optionsGamePath = new File(materiaPath.toString() + File.separator + TexturePack.TEXTURE_OPTIONS_FILE).toPath();
		TextureOptions toGame = TexturePack.readTextureOptions(new TextureOptions(), optionsGamePath);
		TextureOptions toSourcecraft = pack.getTextureOptions();
		return TextureOptions.isUpToDate(toGame, toSourcecraft);
	}

	public static void updateTextures(TexturePack pack, SourceGame game) {
		File srcFolder = pack.getFolder();
		File materiaPath = game.getMatriealPath(pack);
		TextureFolderMover.copyFolder(srcFolder, materiaPath);
	}
}
