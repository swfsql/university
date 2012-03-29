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
		int i = -1, l = _input.length();
		while(++i < l) {
			c = _input.charAt(i);
			System.out.println(c);

			if ((int) c >= 48 && (int) c <= 57) {
				// build the number by each character. Then make it an String, then pass it to the SuperInt. 
				System.out.println("eh numero!");

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