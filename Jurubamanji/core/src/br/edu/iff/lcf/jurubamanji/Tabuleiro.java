package br.edu.iff.lcf.jurubamanji;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Tabuleiro {

	Map<Integer, Espaco> Espacos = new HashMap<Integer, Espaco>();
	public int X;
	public int Y;
	public int umidade = 0;
	public int pontuacao = 0;
	public Map<Integer, Set<Espaco>> combinacao = new HashMap();
	boolean comboAnimado = true;

	private final int multiplicadorPontos = 10;

	public Tabuleiro(int x, int y) throws EspacoInvalidoException {
		if (x < 2 || y < 2) {
			throw new EspacoInvalidoException();
		} else {
			this.X = x;
			this.Y = y;
			for (int i = 0; i < x; i++) {
				for (int j = 0; j < y; j++) {
					try {
						int item = sorteiaItemInicial();
						Espaco espaco = new Espaco(x, y, i, j, item);
						Espacos.put(espaco.hashCode(), espaco);
					} catch (EspacoInvalidoException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

			for (int i = 0; i < x; i++) {
				for (int j = 0; j < y; j++) {
					Espaco cadaEspaco = Espacos.get(Espaco.whathaHash(i, j));
					cadaEspaco.adicionaAdjacente(Espacos.get(Espaco.whathaHash(i - 1, j)));
					cadaEspaco.adicionaAdjacente(Espacos.get(Espaco.whathaHash(i + 1, j)));
					cadaEspaco.adicionaAdjacente(Espacos.get(Espaco.whathaHash(i, j - 1)));
					cadaEspaco.adicionaAdjacente(Espacos.get(Espaco.whathaHash(i, j + 1)));
				}
			}
		}
	}

	public boolean insereNovoItem(int x, int y, int item)
			throws EspacoInvalidoException {
		/*
		 * o Nível de Busca não pode ser menor que 2, mas depois podemos testar
		 * valores maiores...
		 */
		int nivelDeBusca = 2;
		boolean combinou = false;

		Espaco espacoClicado = Espacos.get(Espaco.whathaHash(x, y));
		if (espacoClicado == null || espacoClicado.item != 0) {
			throw new EspacoInvalidoException();
		} else {
			// Aqui ele busca recursivamente os espaços adjacentes por itens combinantes
			Set<Espaco> espacosCombinantes = espacoClicado
					.getAdjacentesCombinantes(item, nivelDeBusca);
			//System.out.println("Adjacentes combinantes = " + espacosCombinantes.size());
			if (espacosCombinantes.size() >= 2) {
				int ordem = this.combinacao.size()+1;
				this.combinacao.put(ordem, espacosCombinantes);
				for (Espaco espaco : espacosCombinantes) {
					espaco.item = Espaco.VAZIO;
				}
				// Atualização extra da umidade como bonificação da combinação
				this.atualizaUmidade();
				this.pontuacao += item*item*multiplicadorPontos;
				this.insereNovoItem(x, y, ++item);
				combinou = true;
			} else {
				espacoClicado.item = item;
			}
		}
		// Atualização da umidade a cada rodada
		this.atualizaUmidade();
		return combinou;
	}

	private static int sorteiaItemInicial() {
		int sorte = BigDecimal.valueOf(100 * Math.random()).intValue();
		int item = 0;

		if (sorte > 97) {
			item = 4;
		} else if(sorte > 90) {
			item = 3;
		} else if (sorte > 82) {
			item = 2;
		} else if (sorte > 77) {
			item = 1;
		}

		return item;
	}

	public static int sorteiaNovoItem() {
		int sorte = BigDecimal.valueOf(100 * Math.random()).intValue();
		int item = 1;

		if (sorte > 95) {
			item = 3;
		} else if (sorte > 82) {
			item = 2;
		}

		return item;
	}

	public void atualizaUmidade() {
		int bonus = 0;
		for (Espaco espaco : Espacos.values()) {
			int item = espaco.item;
			bonus += item*item;
		}
		this.umidade += bonus;
	}

}
