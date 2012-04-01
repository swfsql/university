public class SuperInt {
	private int x[], // int array  // TODO - extra: use bits istead of ints or bytes - java.util.BitSet.
				l; // length.
	public int positive = 1; // 0 = negative; 1 = positive

	public SuperInt(String v) {
		x = new int[100];
		l = v.length();
		int i = -1;
		while(++i < l) x[l - i - 1] = (int) v.charAt(i) - 48;
	}
	// overload
	private SuperInt(int v[], int n) {
		init(v, n);
	}
	// overload
	private SuperInt(int v[], int n, int positive) {
		this.positive = positive;
		init(v, n);
	}
	private void init(int v[], int n) {
		x = v;
		l = n;
	}

	public String toString() {
		byte temp[] = new byte[l + 1 - positive];
		int i = -1;
		while(++i < l) temp[l - i - positive] = (byte) (x[i] + 48);
		if(positive == 0) temp[0] = '-';
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
			if (aux < 10) vaiUm = 0; 
			else {
				vaiUm = 1;
				res[i] -= 10;
			}
		}
		res[maiorTam] = vaiUm;
		return new SuperInt(res, maiorTam + vaiUm);
	}

	public SuperInt subtrai(SuperInt si) {
		
		SuperInt[] ints = {this, si};
		int aux, foiDez = 0,
			maior = (si.l > l) ? 1 : 0, // índice do número mais longo.
			maiorTam = ints[maior].l,
			menorTam = ints[1 - maior].l,
			res[] = new int[100],
			i = -1, l2 = maiorTam - menorTam,
			positive = 1;

		//  procurar o númeoro maior
		int j = 0;
		while(++i < maiorTam) {
			int a = ints[maior].x[maiorTam - i - 1], b;
			if (i < l2) {
				if (a == 0) {
					++j; // o quanto o maior tamanho será reduzido
					continue; 
				}
				break;
			}
			b = ints[1 - maior].x[maiorTam - i - 1];
			if (a == b) continue;
			if (b > a) { 
				positive = 0; // vemos aonde o numero é negativo
				maior = 1 - maior;
				break;
			}
			break;
		}
		maiorTam -= j;
		if (maior + positive == 2) positive = 0;

		i = -1;
		while(++i < maiorTam) {
			res[i] = aux = (i < menorTam) ? ints[maior].x[i] - ints[1 - maior].x[i] - foiDez : 
				ints[maior].x[i] - foiDez;
			if (aux < 0){
				foiDez = 1;
				res[i] += 10;
			} else foiDez = 0; 
		}
		res[maiorTam] -= foiDez;
		return new SuperInt(res, maiorTam, positive);

	}
}
