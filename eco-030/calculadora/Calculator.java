public class Calculator {
	
	public String right(Expression e) {

		System.out.println("___");
		StringUnit rpn = e.getRightRpn().head();
		while(rpn.next != null) {
			rpn = rpn.next;
			System.out.print("rpn: "); System.out.println(rpn.value);
			
		}

		System.out.println("___");

		return _right(e.getRightRpn());
	}

	private String _right(List rpn) {
		SuperInt a, b;
		String now;
		int iNow;

		System.out.println("-=-=-=-=-=-=-=-=");

		rpn.start();
		while(rpn.next() != null) {
			System.out.print("exp: "); rpn.print(); System.out.println("");

			now = rpn.now.value;
			iNow = (int) now.charAt(0);
			System.out.print("rpn: "); System.out.println(now);

			// number
			if (iNow >= 48 && iNow <= 57) continue;

			b = new SuperInt(rpn.prev().value);
			a = new SuperInt(rpn.prev().value);
			rpn.rmNext();
			rpn.rmNext();

			// *42 +43 -45 /47
			if (iNow == 43)	rpn.now.value = a.plus(b).toString();
			if (iNow == 45)	rpn.now.value = a.minus(b).toString();
			if (iNow == 42)	rpn.now.value = a.times(b).toString();
			if (iNow == 47)	rpn.now.value = a.divide(b).toString();
		}

		System.out.print("exp: "); rpn.print(); System.out.println("");

		rpn.start();
		return rpn.next().value;
	}

	/*private String _right(StringUnit h) {
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
		if (op == '*') return num1.times(num2).toString();
		if (op == '/') return num2.toString().equals("0") ? 
				(num1.toString().equals("0") ? 
					"indeterminate" :  
					"error - divide by 0") : 
			num1.divide(num2).toString();
		return "error";
	}*/
}