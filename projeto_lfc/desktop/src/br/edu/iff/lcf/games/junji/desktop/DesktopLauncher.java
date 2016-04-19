package br.edu.iff.lcf.games.junji.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import br.edu.iff.lcf.games.junji.RainForest;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Jurubamanji";
		config.width = 480;
		config.height = 800;
		config.resizable = false;
		config.allowSoftwareMode = true;
		config.backgroundFPS = -1;
		
		
		/*int width  = getWidth();
	    int height = getHeight();
	    
	    System.out.printf("largura %d",width);
	    System.out.printf("altura %d",height);*/
		    
	    /*Toolkit tk = Toolkit.getDefaultToolkit();  
		    Dimension d = tk.getScreenSize();  
		    System.out.println("Screen width = " + d.width);  
		    System.out.println("Screen height = " + d.height);*/  
		    
		new LwjglApplication(new RainForest(), config);
		
	}

	private static int getWidth() {
		// TODO Auto-generated method stub
		return 0;
	}

	private static int getHeight() {
		// TODO Auto-generated method stub
		return 0;
	}
}
