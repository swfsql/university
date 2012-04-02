public class SuperInt {
	private int 
		_x[], // TODO - extra: bits array - java.util.BitSet.
		_l, // length.
		_positive = 1; // 0 = negative; 1 = positive;

	public SuperInt(String v) {
		_x = new int[100];
		_l = v.length();
		int i = -1;
		while(++i < _l) _x[_l - i - 1] = (int) v.charAt(i) - 48;
	}
	// overload
	private SuperInt(int v[], int n) {
		_init(v, n);
	}
	// overload
	private SuperInt(int v[], int n, int positive) {
		_positive = positive;
		_init(v, n);
	}
	private void _init(int v[], int n) {
		_x = v;
		_l = n;
	}

	public String toString() {
		byte temp[] = new byte[_l + 1 - _positive];
		int i = -1;
		while(++i < _l) temp[_l - i - _positive] = (byte) (_x[i] + 48);
		if(_positive == 0) temp[0] = '-';
		return new String(temp);
	}

	public SuperInt plus (SuperInt si) {
		SuperInt[] ints = {this, si}; // easy reference.
		int bigger = (si._l > _l) ? 1 : 0, // bigger length. 0 = this; 1 = si;
			lBig = ints[bigger]._l, // big length.
			lSmall = ints[1-bigger]._l, // small length.
			res[] = new int[100];

		// calculate the sum.
		{
			int i = -1, plusOne = 0;
			while(++i < lBig) {
				res[i] = ints[bigger]._x[i] + ( i < lSmall ?  ints[1 - bigger]._x[i] : 0 ) + plusOne;
				if (res[i] < 10) plusOne = 0; 
				else {
					plusOne = 1;
					res[i] -= 10;
				}
			}
			res[lBig] = plusOne;
			return new SuperInt(res, lBig + plusOne);
		}
	}

	// obs.: there's some code duplication between plus() and minus().

	public SuperInt minus (SuperInt si) {
		SuperInt[] ints = {this, si}; // easy reference.
		int bigger = (si._l > _l) ? 1 : 0, // bigger length. 0 = this; 1 = si;
			lBig = ints[bigger]._l, // big length.
			lSmall = ints[1 - bigger]._l, // small lenght.
			res[] = new int[100],
			positive = 1;

		// look for the biggest number.
		{
			int i = -1, l2 = lBig - lSmall,
				a, b;
			while(++i < lBig) {
				a = ints[bigger]._x[lBig - i - 1];
				b = i < l2 ? 0 : ints[1 - bigger]._x[lBig - i - 1];
				if (a == b) continue;
				if (b > a) { 
					// if we got '1-2', we calculate '(-) 2-1'.
					positive = 0; 
					bigger = 1 - bigger;
					break;
				}
				break;
			}
			if (bigger + positive == 2) positive = 0; // fix the example: '1-20'.
		}
		
		// calculate the subtraction.
		{
			int i = -1, minusTen = 0;
			while(++i < lBig) {
				res[i] = ints[bigger]._x[i] - ( i < lSmall ? ints[1 - bigger]._x[i] : 0) - minusTen;
				if (res[i] >= 0) minusTen = 0;
				else {
					minusTen = 1;
					res[i] += 10;
				}  
			}
			res[lBig] -= minusTen;
			return new SuperInt(res, lBig, positive);
		}
	}

	public SuperInt times (SuperInt si) {
		SuperInt[] ints = {this, si}; // easy reference.
		int bigger = (si._l > _l) ? 1 : 0, // bigger length. 0 = this; 1 = si;
			lBig = ints[bigger]._l, // big length.
			lSmall = ints[1-bigger]._l, // small length.
			res[] = new int[100];
		SuperInt siRes = new SuperInt("0"); 

		// calculate the multiplication.
		{
			int i = -1, j, plus = 0; // 0 to 8.
			while(++i < lBig) {
				j = -1;
				plus = 0;
				while(++j < lSmall) {
					res[j+i] = ints[bigger]._x[i] * ints[1 - bigger]._x[j] + plus;
					plus = res[j+i] / 10;
					res[j+i] %= 10;
				}
				res[j+i] += plus;
				siRes = siRes.plus(new SuperInt(res, lBig + lSmall)); // TODO: optmize.
				res = new int[100]; // TODO: optmize.
			}
			return siRes;
		}
	}
}
