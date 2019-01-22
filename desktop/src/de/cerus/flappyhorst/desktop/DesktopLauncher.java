package de.cerus.flappyhorst.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import de.cerus.flappyhorst.FlappyHorst;

import javax.swing.*;
import java.io.File;

public class DesktopLauncher {
	public static void main (String[] arg) {
        File installationPath;
		try {
			installationPath = new File(System.getenv("ProgramFiles(X86)")+"\\Cerus\\Flappy Horst");
			installationPath.exists();
		} catch (NullPointerException ignored){
			installationPath = new File(System.getenv("ProgramFiles")+"\\Cerus\\Flappy Horst");
		}
        if (!installationPath.exists()) {
			JOptionPane.showMessageDialog(null, "Directory could not be found, please check your installation.", "Installation directory not found", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
			return;
		}
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.resizable = false;
		config.vSyncEnabled = true;
		config.height = 480;
		config.width = 960;
		config.addIcon(installationPath.getPath()+"\\icon.png", Files.FileType.Absolute);
		new LwjglApplication(new FlappyHorst(installationPath), config);
	}
}
