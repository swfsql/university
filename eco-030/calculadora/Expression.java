public class Expression {

	/*
	 *
	 *
	 *
	 */

	private String _input;

	public Expression(){
		_input = "";





	}

	public void setInput(String s) {
		
		_input = s;
		if (s.equals("") || s.equals("exit")) return;
	}

	public String getInput() {
		return _input;
	}
	public Boolean getInput(String s) {
		return _input.equals(s);
	}


}