public class Fracao {
	private int	num, den; // numerator, denominator 
	public Boolean
		positive = true,
		ind = false,
		error = false; 

	public Fracao(String sa, String sb) {
		_init(Integer.parseInt(sa), Integer.parseInt(sb));
	}
	// overload (by ints)
	private Fracao(int ia, int ib) {
		_init(ia, ib);
	}
	// overload (negative)
	private Fracao(int ia, int ib, Boolean positive) {
		this.positive = positive;
		_init(ia, ib);
	}
	private void _init(int ia, int ib) {
		// errors and indeterminances
		if(ib == 0) {
			if (ia == 0) ind = true;
			else error = true;
		}

		if (ib < 0) { // never happens anyway..
			ia = -ia;
			ib = -ib;
		}

		// TODO: simplificacao de ia e ib..

		num = ia;
		den = ib;
	}

	public String toString() {
		return Integer.toString(num) + "/" + Integer.toString(den);
	}

	// TODO: sepah vai precisar dessas funcoes, que nao foram feitas.

	private int _lcm () { // least commom multiple (mmc in pt-br).

		return 0;
	}

	private int _gcd () { // greatest commom divisor (mdc in pt-br).

		return 0;
	}

	//

	public Fracao plus (Fracao that) {
		return new Fracao(this.num * that.den + that.num * this.den, this.den * that.den);
	}

	public Fracao minus (Fracao that) {
		int n1 = this.num * that.den, n2 = that.num * this.den;
		return new Fracao(n1 - n2, this.den * that.den, n1 >= n2);
	}

	public Fracao times (Fracao that) {
		return new Fracao(this.num * that.num, this.den * that.den);
	}

	// assuming its not x/0 or 0/0.
	public Fracao divide (Fracao that) {
		return new Fracao(this.num * that.den, this.den * that.num);
	}
}
