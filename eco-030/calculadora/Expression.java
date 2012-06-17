public class Expression {

	private String _input;
	private List _right; // TODO: _left, example: A = 1+1. 'A' is left, '1+1' is right.
	public Boolean flag = false;

	public Expression(){
		_input = "";
		_right = new List();
	}

	public String getInput() { return _input;}
	// overload (compares).
	public Boolean getInput(String s) {	return _input.equals(s);}

	public List getRight() {return _right;}

	public List getRightRpn() {return a3;}

	public void setInput(String s) {

		_right.clear();
		_input = s;
		if (s.equals("") || s.equals("exit")) return;

		flag = false;
		int iNow = 0, iLast;
		NumberSegment numHead, num; // number builder.
		int i = -1, j = 0, l = _input.length();

		while(++i < l) {
			iLast = iNow;
			iNow = (int) _input.charAt(i);

			// numbers.
			if (iNow >= 48 && iNow <= 57) {
				// building an number: 1) build a list of char. 2) an array of char. 3) then an string. 4) That string will be part of an List of String's.
				// a) input example: .. 132 ..

				if (iLast == ')') _right.add("*"); // eg.: (3) 2 -> 3 * 2.

				// b) [1->3->2].
				j = i;
				numHead = num = new NumberSegment('0');
				while(iNow >= '0' && iNow <= '9') {
					num = num.next = new NumberSegment(new Character((char) iNow));
					if (++j >= l) break; // input has ended.
					iNow = (int) _input.charAt(j);
				} 

				// 3,*,-,1 -> 3,*,-1.
				int minus = 0;
				if (iLast == '-') {
					_right.end();
					StringUnit lastLast = _right.prev(false);
					int iLastLast = 0;
					if(lastLast != null) iLastLast = (int) lastLast.value.charAt(0);
					if (iLastLast == 0 || iLastLast == '(' || iLastLast == '+' || iLastLast == '-' || iLastLast == '*' || iLastLast == '/') {
						minus = 1;
						if (lastLast == null) _right.start();
						_right.rmNext();
					}
				}
				
				// c) [132].
				char[] number = new char[j - i + minus];
				num = numHead;
				int k = i-1; 
				while( (num = num.next) != null) number[++k-i+minus] = num.value; 
				if (minus == 1) number[0] = '-';
				

				_right.add(new String(number));
				_right.end();
				i = j-1;
				j = 0;
				continue;
			} 

			// get operators.
			if (iNow == '*' || iNow == '+' || iNow == '-' || iNow == '/') {
				_right.add(new Character((char) iNow).toString());
				continue;
			}

			// TODO: . , (real numbers).

			// oppening parenthesis.
			if (iNow == '(' || iNow == '[' || iNow == '{') {
				flag = true;
				// != function or operator.
				if (iLast != '*' && iLast != '+' && iLast != '-' && iLast != '/' && iLast != 0) _right.add("*"); // ex.: 3 (2) -> 3 * 2.
				_right.add("(");
				iNow = '('; // ([{ -> (((.
				continue;
			}

			// closing parenthesis.
			if (iNow == ')' || iNow == ']' || iNow == '}') {
				_right.add(")");
				iNow = ')'; // )]} -> ))).
				continue;
			}
		}

		rpn();
	}

	private List a2 = new List(), a3 = new List();
	public void rpn () {
		a2.clear();
		a3.clear();

		int iNow;
		String now;

		_right.start(); // iteration, from head
		while(_right.next() != null) {
			a2.end();
			now = _right.now.value;
			iNow = (int) now.charAt(0);
			if (iNow == '-' && now.length() > 1) iNow = (int) now.charAt(1); // -4 reads the number

			// number.
			if (iNow >= 48 && iNow <= 57) {
				a3.add(now);
				continue;
			}

			// parenthesis.
			if (iNow == '(' || iNow == ')') {
				a2.add(now);
				if (iNow == 41) close();
				continue;
			}

			// operators.
			{
				op(now);
				a2.add(now);
			}
		}

		a2.end(); // reverse iteration. could use stacks.
		while(a2.now != null) {
			now = a2.now.value;
			a3.add(now);
			a2.prev();
		}

		// a3 is the resulting rpn'zed expression.
	}

	// close parentesis.
	private void close() {
		int iNow;
		String now;
		a2.end();
		now = a2.prev().value;
		iNow = (int) now.charAt(0);

		// oppening parenthesis.
		if(iNow != '(') {
			a3.add(now);
			a2.prev();
			a2.rmNext();
			if(flag) close();
		} else {
			a2.prev();
			a2.rmNext();
			a2.rmNext();
		}
	}

	private void op(String now) {
		int iNow, iLast;
		a2.end();

		if(a2.now == null) return;

		String last = a2.now.value;
		iNow = (int) now.charAt(0);
		iLast = (int) last.charAt(0);

		// +-1 */2.
		iNow = iNow == '+' || iNow == '-' ? 1 : 2;
		iLast = iLast == '+' || iLast == '-' ? 1 : iLast == '*' || iLast == '/' ? 2 : 0;

		if (iNow <= iLast) {
			a3.add(last);
			a2.prev();
			a2.rmNext();
			op(now);
		}
	}
}

class NumberSegment {
	public NumberSegment next;
	public char value;

	public NumberSegment(char c) {
		this.value = c;
		next = null;
	}
}
