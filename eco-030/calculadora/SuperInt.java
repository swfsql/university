public class SuperInt {
	private int 
		_x[], // TODO - extra: bits array - java.util.BitSet.
		_l, // length.
		_positive = 1; // 0 = negative; 1 = positive;

	public SuperInt(String v) {
		int i = -1, j = 0, k = 0;
		_x = new int[100];
		_l = v.length();

		// if enter -1, makes it 1, positive = 0.
		if(v.charAt(0) == '-') {
			_positive = 0;
			++i;
			++k;
			--_l;
		}
		
		while(++i < _l) if (v.charAt(i) == '0') ++j; else break; // 00001 -> 01.
		i = -1; _l -= j;
		while(++i < _l) _x[_l - i - 1] = (int) v.charAt(i + j + k) - 48;
		if (_l == 0) _x[_l++] = 0;
	}
	// overload (by vector).
	private SuperInt(int v[], int n) { _init(v, n);}
	// overload (negative).
	private SuperInt(int v[], int n, int positive) {
		_positive = positive;
		_init(v, n);
	}
	private void _init(int v[], int n) {
		_x = v;
		_l = n;
		int i = -1, j = 0;
		while(++i < _l) if (v[_l - i - 1] == 0) ++j; else break; // 00001 -> 01.
		_l -= j;
		if (_l == 0) _x[_l++] = 0;
	}

	public String toString() {
		byte temp[] = new byte[_l + 1 - _positive];
		int i = -1;
		while(++i < _l) temp[_l - i - _positive] = (byte) (_x[i] + 48);
		if(_positive == 0) temp[0] = '-';
		return new String(temp);
	}

	// misc functions.

	// in this case, 1 for positive, -1 for negative.
	private int getPositive() {	return _positive * 2 - 1;}

	// 10 ^ e.
	private SuperInt e10(int e) {
		_x = new int[100];
		if (e < 0) {
			_l = 1;
			_x[0] = 0;
			return this;
		}
		_l = e + 1;
		_x[_l - 1] = 1;
		return this;
	}

	// divide by 2.
	private SuperInt d2 () { 
		//obs. easier in bits.
		int i = -1, plusTen = 0;
		while(++i < _l) {
			_x[_l - i - 1] += plusTen;
			plusTen = (_x[_l - i - 1] & 1) * 10;
			_x[_l - i - 1] >>= 1;
		}
		if (_x[_l - 1] == 0 && _l > 1) --_l;
		return this;
	}

	// obs.: there's some code duplication in the functions below.

	public SuperInt plus (SuperInt si) {
		SuperInt[] ints = {this, si}; // easy reference.

		// maybe we will subtract.
		if(ints[0]._positive != ints[1]._positive) {
			int a = ints[0]._positive;
			ints[0]._positive = ints[1]._positive = 1;
			return a == 1 ? minus(ints[1]) : ints[1].minus(ints[0]);
		}

		int bigger = (si._l > _l) ? 1 : 0, // bigger length. 0 = this; 1 = si.
			lBig = ints[bigger]._l, // big length.
			lSmall = ints[1-bigger]._l, // small length.
			res[] = new int[100];

		// calculate the sum.
		{
			int i = -1, plusOne = 0;
			while(++i < lBig) {
				res[i] = ints[bigger]._x[i] + ( i < lSmall ?  ints[1 - bigger]._x[i] : 0 ) + plusOne;
				plusOne = res[i] / 10;
				res[i] %= 10;
			}
			res[lBig] = plusOne;
			return new SuperInt(res, lBig + plusOne, _positive);
		}
	}

	public SuperInt minus (SuperInt si) {
		SuperInt[] ints = {this, si}; // easy reference.

		// maybe we will sum
		if(ints[0]._positive != ints[1]._positive) {
			int a = ints[0]._positive;
			ints[0]._positive = ints[1]._positive = a == 1 ? 1 : 0;
			return a == 1 ? plus(ints[1]) : ints[1].plus(ints[0]);
		}

		int bigger = (si._l > _l) ? 1 : 0, // bigger length. 0 = this; 1 = si.
			lBig = ints[bigger]._l, // big length.
			lSmall = ints[1 - bigger]._l, // small lenght.
			res[] = new int[100],
			positive = 1;

		// look for the biggest number.
		{
			int i = -1, l = lBig - lSmall,
				a, b;
			while(++i < lBig) {
				a = ints[bigger]._x[lBig - i - 1];
				b = i < l ? 0 : ints[1 - bigger]._x[lBig - i - 1];
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
				minusTen = res[i] < 0 ? 1 : 0;
				res[i] += minusTen * 10;
			}
			return new SuperInt(res, lBig, positive);
		}
	}

	public SuperInt times (SuperInt si) {
		SuperInt[] ints = {this, si}; // easy reference.
		int bigger = (si._l > _l) ? 1 : 0, // bigger length. 0 = this; 1 = si.
			lBig = ints[bigger]._l, // big length.
			lSmall = ints[1-bigger]._l, // small length.
			res[] = new int[100];
		SuperInt siRes = new SuperInt("0");

		// calculate the multiplication.
		try {
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
			siRes._positive = ints[0]._positive * ints[1]._positive;
			return siRes;
		} catch (Exception e) {

			e.getStackTrace();
			return new SuperInt("0");
		}
	}

	// binary search the answer, assuming its not x/0 or 0/0.
	public SuperInt divide (SuperInt si) { // very slow.




		int positive = this._positive * si._positive;
		this._positive = si._positive = 1;
		SuperInt mid = new SuperInt("0");
		SuperInt 
			si1 = new SuperInt("1"), // has a value of 1.
			temp,
			left = new SuperInt("0"), // for binary search. TODO: change this constructor, ("0") is ugly.
			right = new SuperInt("0"); // for binary search.
		{
			int bigger = (si._l > _l) ? 1 : 0, // bigger length. 0 = this; 1 = si;
				lBig = this._l, // big length.
				lSmall = si._l; // small length.
			if (lBig < lSmall) return mid;
			right = right.e10(lBig - lSmall + 1).minus(si1);
			left = left.e10(lBig - lSmall - 1).minus(si1);
		}

		// example: 20 / 3. what happens: lets say we're on '6'. 20 - 6*3 = 2 > 0, so maybe its not '6'. 
		// now lets say we're on '7'. 20 - 7*3 = -1 < 0, so its below 7, wich is 6.
		while(right.minus(left).getPositive() == 1) {
			mid = new SuperInt("0").plus(left).plus(right);
			mid.d2();
			temp = this.minus(si.times(mid)); 
			if (temp._l + temp._x[0] == 1) {
				mid._positive = positive;
				return mid; // found the exact answer.
			}
			if (temp.getPositive() == 1) left = new SuperInt("0").plus(mid).plus(si1);
			else right = new SuperInt("0").plus(mid).minus(si1);
		}

		temp = this.minus(si.times(mid));
		mid._positive = positive;
		if (temp.getPositive() == 1) return mid;
		else return mid.minus(si1);
	}
}
