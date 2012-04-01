public class Calculator {
	
	public String right(Expression e) {
		return _right(e.getRight().head());
	}

	private String _right(StringUnit h) {
		SuperInt num1, num2;
		char op;

		if (h.next == null) return "";
		if (h.next.next != null && h.next.next.next != null) {
			num1 = new SuperInt (h.next.value);
			op = h.next.next.value.charAt(0);
			num2 = new SuperInt (h.next.next.next.value);
			if (op != 42 && op != 43 && op != 45 && op != 47) return "error";
		} else return "error";

		if (op == '+') return num1.plus(num2).toString();
		if (op == '-') return num1.minus(num2).toString();
		return "";
	}
}