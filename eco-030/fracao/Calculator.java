public class Calculator {
	
	public String right(Expression e) {
		return _right(e.getRight().head());
	}

	private String _right(StringUnit h) {
		Fracao f1, f2;
		char op;

		if (h.next == null) return "";
		if (h.next.next != null && h.next.next.next != null) {
			f1 = new Fracao (h.next.value, h.next.next.value, h.next.next.next.value)
			if (op != 42 && op != 43 && op != 45 && op != 47) return "error";
		} else return "error";

		if (op == '+') return num1.plus(num2).toString();
		if (op == '-') return num1.minus(num2).toString();
		if (op == '*') return num1.times(num2).toString();
		if (op == '/') return num2.toString().equals("0") ? 
				(num1.toString().equals("0") ? 
					"indeterminate" :  
					"error - divide by 0") : 
			num1.divide(num2).toString();
		return "error";
	}
}