public class Expression {

	private String _input;
	private List _right;
	// TODO - extra: private List _left;
	// 'left = right'. for example: 'A = 123 + 123'. left = A. right = 123 + 123.

	public Expression(){
		_input = "";
		_right = new List();
	}

	public void setInput(String s) {

		_right.clear();
		_input = s;
		if (s.equals("") || s.equals("exit")) return;

		char c;
		int ic;
		NumberSegment numHead, num; // number builder.
		int i = -1, j = 0, l = _input.length();
		while(++i < l) {
			c = _input.charAt(i);
			ic = (int) c;

			// numbers.
			if (ic >= 48 && ic <= 57) { // TODO: use regular expressions.
				// building an number: 1) build a list of char. 2) an array of char. 3) then an string. 4) That string will be part of an List of String's.

				j = i;
				numHead = num = new NumberSegment('0');
				while(ic >= 48 && ic <= 57) {
					num = num.next = new NumberSegment(c);
					if (++j >= l) break; // input has ended.
					c = _input.charAt(j);
					ic = (int) c;
				}

				char[] number = new char[j-i];
				num = numHead;
				int k = i-1; 
				while( (num = num.next) != null) {
					number[++k-i] = num.value;
				}

				_right.add(new String(number));
				i = j-1;
				j = 0;
				continue;
			} 

			// get operators.
			if (ic == 42 || ic == 43 || ic == 45 || ic == 47) { // TODO: use regular expressions.
				// *42 +43 -45 /47
				_right.add(Character.toString(c));
				continue;
			}
		}
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