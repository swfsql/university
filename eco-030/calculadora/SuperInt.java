public class SuperInt {
	private int x[], l;

	public SuperInt(String v) {
		x = new int[100];
		l = v.length();
		int i = -1;
		while(++i < l) x[l - i - 1] = (int) v.charAt(i) - 48;
	}

	private SuperInt(int v[], int n) {
		x = v;
		numDigitos = n;
	}

	public String toString() {

		byte temp[] = new byte[l];
		int i = -1;
		while(++i < l) temp[l - i - 1] = (byte) (x[i] + 48);
		String s = new String(temp);
		return s;
	}

	public SuperInt soma(SuperInt si) {
		int aux, vaiUm = 0;
		int maiorTam = (l > si.l) ? l : si.l;
		int menorTam = (l < si.l) ? l : si.l;
		int res[] = new int[100];

		int i = -1;
		while(++i < maiorTam) {
			if (i < menorTam)
				aux = x[i] + si.x[i] + vaiUm;
			else if (maiorTam == l)
				aux = x[i] + vaiUm;
			else
				aux = si.x[i] + vaiUm;

			if (aux < 10){
				vaiUm = 0;
				res[i] = aux;
			} else {
				vaiUm = 1;
				res[i] = aux - 10;
			}
		}
		res[maiorTam] = vaiUm;
		SuperInt r = new SuperInt(res, maiorTam + vaiUm);
		return r;
	}
}
