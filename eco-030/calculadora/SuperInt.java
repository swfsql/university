public class SuperInt {
	private int x[], // int array  // TODO - extra: use bits istead of ints or bytes - java.util.BitSet.
				l; // length.
	public boolean positive = true; // if the SuperInt is positive or negative.

	public SuperInt(String v) {
		init(v);
	}
	// overload
	public SuperInt(String v, boolean positive) {
		this.positive = positive;
		init(v);
	}
	// overload
	private SuperInt(int v[], int n) {
		x = v;
		l = n;
	}

	private void init(String v) {
		x = new int[100];
		l = v.length();
		int i = -1;
		while(++i < l) x[l - i - 1] = (int) v.charAt(i) - 48;
	}

	public String toString() {
		byte temp[] = new byte[l];
		int i = -1;
		while(++i < l) temp[l - i - 1] = (byte) (x[i] + 48);
		return new String(temp);
	}

	public SuperInt soma(SuperInt si) {

		// por enquanto considera-se 'positivo + positivo' apenas

		int aux, vaiUm = 0,
			maiorTam = (l > si.l) ? l : si.l,
			menorTam = (l < si.l) ? l : si.l,
			res[] = new int[100],
			i = -1;

		while(++i < maiorTam) {
			aux = (i < menorTam) ? x[i] + si.x[i] + vaiUm : 
				  (maiorTam == l) ? x[i] + vaiUm :
				  si.x[i] + vaiUm;
			res[i] = aux;
			if (aux < 10){
				vaiUm = 0;
			} else {
				vaiUm = 1;
				res[i] -= 10;
			}
		}
		res[maiorTam] = vaiUm;
		return new SuperInt(res, maiorTam + vaiUm);
	}

	public SuperInt subtrai(SuperInt si) {
		
		// TODO - extra: '-1 - 1 -> -2' '-1 - -1 -> 0'
		/*if (!positive) {
			if (si.positive) {} // soma
			else {} // subtração
		}*/

		// por enquanto considera-se 'positivo - positivo'

		// casos: 2-2; 0-0;
		//        2-1; 1-0; 11-9;
		//        1-2; 0-1; 9-11;

		int aux, vaiUm = 0,
			maiorTam = (l > si.l) ? l : si.l,
			menorTam = (l < si.l) ? l : si.l,
			res[] = new int[100],
			i = -1;



		while(++i < maiorTam) {
			aux = (i < menorTam) ? x[i] + si.x[i] + vaiUm : 
				  (maiorTam == l) ? x[i] + vaiUm :
				  si.x[i] + vaiUm;
			res[i] = aux;
			if (aux < 10){
				vaiUm = 0;
			} else {
				vaiUm = 1;
				res[i] -= 10;
			}
		}
		res[maiorTam] = vaiUm;
		return new SuperInt(res, maiorTam + vaiUm);
	}
}
