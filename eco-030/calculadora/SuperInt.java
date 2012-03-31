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

		SuperInt[] ints = {this, si}; // facilita o acesso
		int aux, vaiUm = 0,
			maior = (si.l > l) ? 1 : 0, // 0 = this; 1 = si;
			maiorTam = ints[maior].l,
			menorTam = ints[1-maior].l,
			res[] = new int[100],
			i = -1;

		while(++i < maiorTam) {
			res[i] = aux = (i < menorTam) ? x[i] + si.x[i] + vaiUm : 
				ints[maior].x[i] + vaiUm;
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

		//        casos:				|	lê da esquerda pra direita
		//		  2-2; 0-0;				|	
		//        2-1; 1-0; 11-09;		|	11-90
		//        1-2; 0-1; 09-11;		|	90-11

		SuperInt[] ints = {this, si};
		int aux, vaiUm = 0, vaiDez = 0,
			maior = (si.l > l) ? 1 : 0, // número mais longo. // 0 = this; 1 = si;
			maiorTam = ints[maior].l,
			menorTam = ints[1 - maior].l,
			res[] = new int[100],
			i = -1, l2;

		// cuida da conta enquanto há um número mais 'longo' que o outro
		l2 = maiorTam - menorTam;
		while(++i < l2) res[maiorTam - i - 1] = ints[maior].x[maiorTam - i - 1]; // 11111-99 ---> 111xx
		--i;
		if (res[maiorTam - i - 1] > 0) { // 11111-99 ---> 1110(x+10)x 
			--res[maiorTam - i - 1];
			vaiDez = 10;
		}

		// agora tem que cuidar da conta até o fim dos números
		l2 = menorTam;
		i = -1;
		maior = (si.x[menorTam - 1] > x[menorTam - 1]) ? 1 : 0; // número realmente maior. TODO@ parei aqui



		// ir diminuindo
		while(++i < maiorTam) {


			aux = (i < menorTam) ? x[i] - si.x[i] + vaiUm + vaiDez : 
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

		while(++i < maiorTam) {
			res[i] = aux = (i < menorTam) ? x[i] + si.x[i] + vaiUm : 
				  (maiorTam == l) ? x[i] + vaiUm :
				  si.x[i] + vaiUm;
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
