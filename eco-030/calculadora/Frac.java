public class Frac {
	public SuperInt num, den; // numerator, denominator.
	public Boolean
		ind = false,
		error = false; 

	public Frac(String sa, String sb) throws Exception {
		_init(new SuperInt(sa), new SuperInt(sb));
	}
	public Frac(String s) throws Exception {
		if (hasDivision(s)) {
			System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			System.out.print("fr: "); System.out.println(s);
			System.out.print("num: "); System.out.print(numerator(s));
			System.out.print("  den: "); System.out.println(denominator(s));
			_init(new SuperInt(numerator(s)), new SuperInt(denominator(s)));
			System.out.println("FUUU");
		}
		else _init(new SuperInt(s), new SuperInt("1"));
	}
	// overload (by ints).
	private Frac(int ia, int ib) throws Exception {
		_init(new SuperInt(Integer.toString(ia)), new SuperInt(Integer.toString(ib)));
	}
	

	private void _init(SuperInt ia, SuperInt ib) throws Exception {
		// errors and indeterminances.
		if(ib.equals0()) throw new Exception ("error: divide by 0.");

		System.out.println("~.~.~.~.~.~.~.~.~.~.~.~.~.~.~.~.~.~.~. VIX");
		int positive = ia.getPositive(); // fix this
		System.out.print("a: "); System.out.println(ia);
				System.out.print("b: "); System.out.println(ib);


		// simplify
		{
			SuperInt i = new SuperInt("1"), ic, t1, t2;
			ic = ib.minus(ia);
			ic = ic.getPositive() == 1 ? ib.copy() : ia.copy();
			SuperInt s0 = new SuperInt("0"), s1 = new SuperInt("1"), s2, i2;
			i = i.plus(s1);
			i2 = ic.minus(i);
			while(i2.getPositive() == 1) {
				t1 = ia.module(i);
				t2 = ib.module(i);
				System.out.print("i: "); System.out.println(i);
				System.out.print("i2: "); System.out.println(i2);
				System.out.print("a: "); System.out.println(ia);
				System.out.print("b: "); System.out.println(ib);
				System.out.print("a%i: "); System.out.println(t1);
				System.out.print("b%i: "); System.out.println(t2);

				
				s2 = s0.minus(t1.plus(t2));
				System.out.print("s2: "); System.out.println(t1.plus(t2));
				if (s2.equals0()) {
					ia = ia.divide(i);
					ib = ib.divide(i);
					ic = ib.minus(ia);
					ic = ic.getPositive() == 1 ? ib.copy() : ia.copy();
					i = i.minus(s1);
				}
				i = i.plus(s1);
				i2 = ic.minus(i);
			}

		}

		System.out.println("~~~~~~~~~~~");

		num = ia.copy();
		den = ib.copy();
		System.out.println("AHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH");
		num.setPositive(positive); // fix this
		System.out.println("EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE");

		System.out.print("chckpoint: "); System.out.println(toString());
	}

	private Boolean hasDivision(String s) {
		int i = -1, l = s.length();
		while(++i < l) if (s.charAt(i) == '/') return true;
		return false;
	}
	public String numerator(String s) {
		int i = -1, l = s.length();
		while(++i < l) if (s.charAt(i) == '/') break;
		l = i;
		i = -1;
		char[] s2 = new char[l];
		while(++i < l) s2[i] = s.charAt(i);
		return new String(s2);
	}

	public String denominator(String s) {
		int i = -1, l = s.length(), l2;
		while(++i < l) if (s.charAt(i) == '/') break;
		l2 = i;
		l -= i + 1;
		i = -1;
		char[] s2 = new char[l];
		while(++i < l) s2[i] = s.charAt(l2 + i + 1);
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
		System.out.print("soma: seria "); System.out.print(this); System.out.print(" + "); System.out.println(that);
		System.out.print("dai a soma daria: "); System.out.print(t1.plus(t2).toString()); System.out.print(" / "); System.out.println(this.den.times(that.den).toString());
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
