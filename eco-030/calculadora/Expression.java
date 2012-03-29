public class Expression {

	

	private String _input;
	private List _left, _right; // 'left = right'. for example: 'A = 123 + 123'.

	public Expression(){
		_input = "";
		_left = new List(); // we dont use left yet, we dont support named variable creation.
		_right = new List();
	}

	public void setInput(String s) {
		
		_input = s;
		if (s.equals("") || s.equals("exit")) return;

		// TODO: get an Number, an Operator, an Number.
		_right.clear();

		char c;
		NumberSegment numHead, num;

		int i = -1, j = 0, l = _input.length();
		while(++i < l) {
			c = _input.charAt(i);
			//System.out.println(c);



			if ((int) c >= 48 && (int) c <= 57) {
				// to get the number: by each char, build an list, then make it a char array, then an String, then send to SuperInt.

				// TODO - extra: (a) b -> (a) * b 

				j = i;
				numHead = num = new NumberSegment('0');
				while((int) c >= 48 && (int) c <= 57) {
					num = num.next = new NumberSegment(c);
					if (++j >= l) break; // idk if I can reach an element outside the String length. TODO: verify that, to see if I can remove that if().
					c = _input.charAt(j);
				}

				// TODO: a * -b -> a * (-b)

				// TODO: make the positive String. TODO: negative if need to, but I'll have to change the SuperInt class.
				char[] number = new char[j-i];
				num = numHead;
				int k = i-1; 
				while( (num = num.next) != null) {
					number[++k-i] = num.value;
				}

				System.out.println(number);

				i = j-1;
				j = 0;
			}


		}


		// TODO - extra: get an expression and turn it into an RPN expression, including variables.

	}

	public String getInput() {
		return _input;
	}
	public Boolean getInput(String s) {
		return _input.equals(s);
	}


}

// Since idk how to create objects, I'll create a class instead.
class NumberSegment {
	public NumberSegment next;
	public char value;

	public NumberSegment(char c) {
		this.value = c;
		next = null;
	}
}