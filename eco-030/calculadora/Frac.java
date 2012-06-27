public class Frac {
	private SuperInt num, den; // numerator, denominator.
	public Boolean
		positive = true,
		ind = false,
		error = false; 

	public Frac(String sa, String sb) throws Exception {
		_init(new SuperInt(sa), new SuperInt(sb));
	}
	public Frac(String s) throws Exception {
		if (hasDivision(s)) {

		} else 
		_init(new SuperInt(s), new SuperInt("1"));
	}
	// overload (by ints).
	private Frac(int ia, int ib) throws Exception {
		_init(new SuperInt(Integer.toString(ia)), new SuperInt(Integer.toString(ib)));
	}
	// overload (negative).
	private Frac(int ia, int ib, Boolean positive) throws Exception {
		this.positive = positive;
		_init(new SuperInt(Integer.toString(ia)), new SuperInt(Integer.toString(ib)));
	}

	private void _init(SuperInt ia, SuperInt ib) throws Exception {
		// errors and indeterminances.
		if(ib.equals0()) throw new Exception ("error: divide by 0.");

		// simplify
		{
			SuperInt i = new SuperInt("1"), ic;
			ic = ib.minus(ia);
			ic = ic.getPositive() == 1 ? ib.copy() : ib.copy();
			SuperInt s0 = new SuperInt("0"), s1 = new SuperInt("1"), s2, i2;
			i = i.plus(s1);
			i2 = i.minus(ic);
			while(i2.getPositive() == 1) {
				s2 = s0.minus(ia.module(i).plus(ib.module(i)));
				if (s2.equals0()) {
					ia = ia.divide(i);
					ib = ib.divide(i);
					ic = ib.minus(ia);
					ic = ic.getPositive() == 1 ? ib.copy() : ia.copy();
					i = i.minus(s1);
				}
				i = i.plus(s1);
				i2 = i.minus(ic);
			}
		}

		num = ia.copy();
		den = ib.copy();
	}

	private Boolean hasDivision(String s) {
		int i = -1, l = s.length();
		while(++i < l) if (s.charAt(i) == '/') return true;
		return false;
	}
	private String numerator(String s) {
		int i = -1, l = s.length();
		while(++i < l) if (s.charAt(i) == '/') break;
		l = i;
		i = -1;
		char[] s2 = new char[l];
		while(++i < l) s2[i] = s.charAt(i);
		return new String(s2);
	}

	private String denominator(String s) {
		int i = -1, l = s.length();
		while(++i < l) if (s.charAt(i) == '/') break;
		l -= i;
		i = -1;
		char[] s2 = new char[l];
		while(++i < l) s2[i] = s.charAt(i);
		return new String(s2);
	}

	public String toString() {
		return num.toString() + "/" + den.toString();
	}

	public Frac plus (Frac that) throws Exception {
		SuperInt t1, t2;
		try {
			t1 = this.num.times(that.den);
			t2 = that.num.times(this.den);
		} catch (Exception e) {
			throw e;
		}
		return new Frac(t1.plus(t2).toString(), this.den.times(that.den).toString());
	}

	public Frac minus (Frac that) throws Exception {
		SuperInt n1, n2;
		try {
			n1 = this.num.times(that.den);
			n2 = that.num.times(this.den);
		} catch (Exception e) {
			throw e;
		}
		return new Frac(n1.minus(n2).toString(), this.den.times(that.den).toString());
	}

	public Frac times (Frac that) throws Exception {
		SuperInt n, d;
		try {
			n = this.num.times(that.num);
			d = this.den.times(that.den);
		} catch (Exception e) {
			throw e;
		}
		return new Frac(n.toString(), d.toString());
	}

	// assuming its not x/0 or 0/0.
	public Frac divide (Frac that) throws Exception {
		SuperInt n, d;
		try {
			n = this.num.times(that.den);
			d = this.den.times(that.num);
		} catch (Exception e) {
			throw e;
		}
		return new Frac(n.toString(), d.toString());
	}
}
