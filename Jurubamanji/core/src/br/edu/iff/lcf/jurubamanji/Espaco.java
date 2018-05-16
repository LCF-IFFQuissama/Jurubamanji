package br.edu.iff.lcf.jurubamanji;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Espaco {

	// final static int LENHADOR = -100;

	// final static int PEDRA = -10;

	final static int VAZIO = 0;
	final static int SEMENTE = 1;
	final static int MUDA = 2;
	final static int ARBUSTO = 3;
	final static int ARVORE = 4;
	final static int BOSQUE = 5;
	final static int FLORESTA = 6;

	final static Map<Integer, String> ITENS = new HashMap<Integer, String>();
	static {
		ITENS.put(0, "Vazio");
		ITENS.put(1, "Semente");
		ITENS.put(2, "Muda");
		ITENS.put(3, "Arbusto");
		ITENS.put(4, "Árvore");
		ITENS.put(5, "Bosque");
		ITENS.put(6, "Floresta");
	}

	public int X, Y;
	public int item;
	private Map<Integer, Espaco> adjacentes = new HashMap<Integer, Espaco>();

	public Espaco(int maxX, int maxY, int x, int y, int item)
			throws EspacoInvalidoException {
		if (x > maxX || y > maxY || x < 0 || y < 0) {
			throw new EspacoInvalidoException();
		} else {
			this.X = x;
			this.Y = y;
			this.item = item;
		}
	}

	public boolean equals(Object outro) {
		if (outro == null)
			return false;
		if (outro == this)
			return true;
		if (!(outro instanceof Espaco))
			return false;
		Espaco outroEspaco = (Espaco) outro;
		if (this.X == outroEspaco.X && this.Y == outroEspaco.Y)
			return true;
		else
			return false;
	}

	public int hashCode() {
		return 37 * this.X + 5 * this.Y;
	}

	public static int whathaHash(int x, int y) {
		return 37 * x + 5 * y;
	}

	public Collection<Espaco> getAdjacentes() {
		return this.adjacentes.values();
	}

	public Set<Espaco> getAdjacentesCombinantes(int item, int nivel) {
		Set<Espaco> combinantes = new HashSet<Espaco>();

		if (nivel > 0) {
			Set<Integer> keys = adjacentes.keySet();
			for (Iterator<Integer> it = keys.iterator(); it.hasNext();) {
				Espaco espaco = adjacentes.get(it.next());
				if (espaco.item == item) {
					combinantes.add(espaco);
				}
			}
			nivel--;
			if (!combinantes.isEmpty()) {
				Set<Espaco> maisCombinantes = new HashSet<Espaco>();
				for (Iterator<Espaco> it = combinantes.iterator(); it.hasNext();) {
					Espaco combinante = it.next();
					maisCombinantes.addAll(combinante.getAdjacentesCombinantes(
							item, nivel));
				}
				combinantes.addAll(maisCombinantes);
			}
		}
		return combinantes;
	}

	public void adicionaAdjacente(Espaco adjacente) {
		if (adjacente != null) {
			System.err.println("Adicionando " + adjacente.hashCode()
					+ " adjacente a " + this.hashCode());
			this.adjacentes.put(adjacente.hashCode(), adjacente);
		}
	}
}
