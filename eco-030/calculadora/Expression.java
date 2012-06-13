public class Expression {

	private String _input;
	private List _right;
	public Boolean flag = false;
	// TODO - extra: private List _left;
	// 'left = right'. for example: 'A = 123 + 123'. left = A. right = 123 + 123.

	public Expression(){
		_input = "";
		_right = new List();
	}

	public String getInput() {
		return _input;
	}
	// overload.
	public Boolean getInput(String s) {
		return _input.equals(s); // compares.
	}

	public List getRight() {
		return _right;
	}

	public List getRightRpn() {
		return a3;
	}

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
			if (iNow >= 48 && iNow <= 57) { // TODO: use regular expressions.
				// building an number: 1) build a list of char. 2) an array of char. 3) then an string. 4) That string will be part of an List of String's.
				// a) input example: .. 132 ..

				// (40 )41 [91 ]93 {123 }125	
				if (iLast == 41) _right.add("*"); // ex.: (3) 2 -> 3 * 2

				j = i;
				numHead = num = new NumberSegment('0');
				while(iNow >= '0' && iNow <= '9') {
					num = num.next = new NumberSegment(new Character((char) iNow));
					if (++j >= l) break; // input has ended.
					iNow = (int) _input.charAt(j);
				} // b) [1->3->2]

				// TODO: example: 3,*,-,1 -> 3,*,-1.
				

				char[] number = new char[j-i];
				num = numHead;
				int k = i-1; 
				while( (num = num.next) != null) number[++k-i] = num.value; // c) [132]
				

				_right.add(new String(number));
				i = j-1;
				j = 0;
				continue;
			} 

			// get operators. *42 +43 -45 /47
			if (iNow == '*' || iNow == '+' || iNow == '-' || iNow == '/') { // TODO: use regular expressions.
				_right.add(new Character((char) iNow).toString());
				continue;
			}

			// no . or ,

			// (40 [91 {123	
			if (iNow == '(' || iNow == '[' || iNow == '{') {
				flag = true;

				// != function or operator. *42 +43 -45 /47. 
				if (iLast != '*' && iLast != '+' && iLast != '-' && iLast != '/' && iLast != 0) _right.add("*"); // ex.: 3 (2) -> 3 * 2
				_right.add("(");
				iNow = '('; // ([{ -> (((
				continue;
			}

			// (40 )41 [91 ]93 {123 }125	
			if (iNow == ')' || iNow == ']' || iNow == '}') {
				_right.add(")");
				iNow = ')'; // )]} -> )))
				continue;
			}
		}

		rpn();
	}

	private List a2 = new List(), a3 = new List();
	public void rpn () {

		System.out.println("\nbuilding rpn..");

		a2.clear();
		a3.clear();

		int iNow;
		String now;

		_right.start(); // iteration, from head
		while(_right.next() != null) {
			System.out.print("exp: "); a3.print(); System.out.println("");
			System.out.print("stack: "); a2.print(); System.out.println("");System.out.println("");
			now = _right.now.value;
			iNow = (int) now.charAt(0);

			// number
			if (iNow >= 48 && iNow <= 57) 
			{
				a3.add(now);
				continue;
			}

			// (40 )41	
			if (iNow == 40 || iNow == 41) {
				a2.add(now);
				if (iNow == 41) close();
				continue;
			}

			// *42 +43 -45 /47
			{
				a2.add(now);
				op();
			}
			
		}

		System.out.println("reversing stack..");

		a2.end(); // reverse iteration
		while(a2.now != null) {
			now = a2.now.value;
			a3.add(now);
			a2.prev();
		}

		System.out.print("exp: "); a3.print(); System.out.println("");
			System.out.print("stack: "); a2.print(); System.out.println("");System.out.println("");

		// a3 is the resulting rpn'zed expression
	}

	// close parentesis
	private void close() {
		int iNow;
		String now;
		a2.end();
		now = a2.prev().value;
		iNow = (int) now.charAt(0);

		// (40
		if(iNow != 40) {
			a3.add(now);
			a2.prev();
			a2.rmNext();
			if(flag) close();
		} else {
			a2.prev();
			System.out.print("rm: "); System.out.println(a2.rmNext());
			System.out.print("rm: "); System.out.println(a2.rmNext());
		}

	}

	private void op() {
		int a, b;

		a2.end();
		a2.prev();
		if(a2.now == null) {
			System.out.println("FUUUUU- (2)");
			return;
		}
		String now = a2.now.value;
		
		a = (int) _right.now.value.charAt(0);
		b = (int) now.charAt(0);

		System.out.print("a: "); System.out.println(a);
		System.out.print("b: "); System.out.println(b);

		// *42 +43 -45 /47
		a = a == 43 || a == 45 ? 1 : 2;
		b = b == 43 || b == 45 ? 1 : b == 42 || b == 47 ? 2 : 0;

		if (a <= b) {
			a3.add(now);
			a2.prev();
			a2.rmNext();
			op();
		}
	}


}

// TODO: try to create just an object instead of an entire class.
class NumberSegment {
	public NumberSegment next;
	public char value;

	public NumberSegment(char c) {
		this.value = c;
		next = null;
	}

}