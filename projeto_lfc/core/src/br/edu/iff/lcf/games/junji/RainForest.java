package br.edu.iff.lcf.games.junji;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.swing.JTextField;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.sun.scenario.effect.Blend;

import java.awt.*;



public class RainForest extends ApplicationAdapter {
	private OrthographicCamera camera;
	SpriteBatch batch;
	Texture gameBG, i0, i1, i2, i3, i4, i5, i6, i7;
	Music trilha;
	private Sound somItem, somCombo;
	Tabuleiro tabuleiroLogica;
	int xMax = 5, yMax = 7;
	Rectangle[][] tabuleiroDesenho;
	Map<Integer, Texture> itens;
	int proximoItem = Tabuleiro.sorteiaNovoItem();
	BitmapFont pontos;
	
	/*config.title = "Drop";
	config.width = 800;
	config.height = 480;
	*/
/*public static void main(String[] args) {
	LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
	config.title = "Drop";
	config.width = 800;
	config.height = 480;
	new LwjglApplication(new Drop(), config);
	}*/
	@Override
	public void create() {
	    /*JTextField usernameField = new JTextField(8);  
	    usernameField.setSize(100, 30);*/  
		//setSize(500, 200); 
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 480, 800);
		batch = new SpriteBatch();		
		gameBG = new Texture("bg1.png");
		i0 = new Texture("0.png");
		i1 = new Texture("1.png");
		i2 = new Texture("2.png");
		i3 = new Texture("3.png");
		i4 = new Texture("4.png");
		i5 = new Texture("5.png");
		i6 = new Texture("6.png");
		i7 = new Texture("7.png");
		itens = new HashMap<Integer, Texture>();
		itens.put(0, i0);
		itens.put(1, i1);
		itens.put(2, i2);
		itens.put(3, i3);
		itens.put(4, i4);
		itens.put(5, i5);
		itens.put(6, i6);
		itens.put(7, i7);

	    somItem = Gdx.audio.newSound(Gdx.files.internal("Woosh-Mark_DiAngelo-4778593.mp3"));
	    somCombo = Gdx.audio.newSound(Gdx.files.internal("Blop-Mark_DiAngelo-79054334.mp3"));
	    //somCombo = Gdx.audio.newSound(Gdx.files.internal("Tick-DeepFrozenApps-397275646.mp3"));
		trilha = Gdx.audio.newMusic(Gdx.files.internal("Carefree.mp3"));
		trilha.setLooping(true);
		trilha.play();
		
		pontos = new BitmapFont();

		try {
			tabuleiroLogica = new Tabuleiro(xMax, yMax);
			tabuleiroDesenho = new Rectangle[xMax][yMax];
			for (int i = 0; i < xMax; i++) {
				for (int j = 0; j < yMax; j++) {
					tabuleiroDesenho[i][j] = new Rectangle();
				}
			}
			tabuleiroDesenho[0][0].x = 0;
		} catch (EspacoInvalidoException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0.3f, 0.15f, 0.0f, 1);
	    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	    camera.update();
	      
		int larguraTela = Gdx.graphics.getWidth();
		int alturaTela = Gdx.graphics.getHeight();
		
		//Gdx.gl.glClearColor(0, 0, 0, 0);//1001
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(gameBG, 0, 0, larguraTela, alturaTela);//tamanho do tabuleiro****
		int tamanho = Math.round(larguraTela/7.38f);
		int x0 = (larguraTela - (tamanho * tabuleiroLogica.X)) / 2;
		int y0 = Math.round(alturaTela/1.6f);
		int y = y0;

		for (int i = 0; i < tabuleiroLogica.Y; i++) {
			int x = x0;
			for (int j = 0; j < tabuleiroLogica.X; j++) {
				Espaco espaco = tabuleiroLogica.Espacos.get(Espaco.whathaHash(j, i));
				int item = espaco.item;
				tabuleiroDesenho[j][i].x = x;
				tabuleiroDesenho[j][i].y = y;
				batch.draw(itens.get(item), x, y);
				x += tamanho;
			}
			y -= tamanho;
		}
		
		batch.draw(itens.get(proximoItem), x0 + (2 * tamanho), y0
				+ (2.6f * tamanho)); // proximo item sorteado
		if (Gdx.input.isTouched()) {
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);// conjunto de posições no eixo x e y

			//camera.unproject(touchPos);
			
			int xToc = (int) (touchPos.x-x0)/tamanho; // varia de 0 a X
			int yToc = (int) (touchPos.y-(alturaTela-y0-tamanho))/tamanho; // varia de 0 a Y
			try {
				if(tabuleiroLogica.insereNovoItem(xToc, yToc, proximoItem)) {
					// TODO tocar o som da combinação
					somCombo.play();
					// TODO movimentar elementos da combinação
					int i = 1;
					for (; i <= tabuleiroLogica.combinacao.size(); i++) {//
						Set<Espaco> espacosPraMover = tabuleiroLogica.combinacao.get(i);
						//espacosPraMover
						
					}
					i--;
					for (int j = i; j >= 1; j--) {
						tabuleiroLogica.combinacao.remove(i);
					}
					
					// TODO executar a animação da combinação
				} else {
					somItem.play();
				}
				proximoItem = Tabuleiro.sorteiaNovoItem();//sorteio
			} catch (EspacoInvalidoException e) {
			}
			
			
			
			System.out.println("Umidade: " + tabuleiroLogica.umidade);
			
		}
		//Color vermelhoEscuro = new Color(235,50,50); 
		pontos.setColor(1.0f, 1.0f, 1.0f, 1.0f);//cor da pontuação
		pontos.draw(batch, tabuleiroLogica.pontuacao+"", 25, 780);//, 100, 1, true);//posição da pontuação na tela
		//yourBitmapFontName.draw(batch, tab.pontuacao+"", 25, ); 

		batch.end();
	}

}
