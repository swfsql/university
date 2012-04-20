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

	public Fracao plus (Fracao f) {
		return new Fracao(this.num * f.den + f.num * this.den, this.den * f.den);
	}

	public Fracao minus (Fracao f) {
		int n1 = this.num * f.den, n2 = f.num * this.den;
		return new Fracao(n1 - n2, this.den * f.den, n1 >= n2);
	}

	public Fracao times (Fracao f) {
		return new Fracao(this.num * f.num, this.den * f.den);
	}

	// assuming its not x/0 or 0/0.
	public Fracao divide (Fracao f) {
		return new Fracao(this.num * f.den, this.den * f.num);
	}
}
