package br.edu.iff.lcf.jurubamanji.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import br.edu.iff.lcf.jurubamanji.JurubamanjiGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Jurubamanji";
		config.width = 480;
		config.height = 800;
		config.resizable = false;
		config.allowSoftwareMode = true;
		config.backgroundFPS = -1;
		new LwjglApplication(new JurubamanjiGame(), config);
	}
}
