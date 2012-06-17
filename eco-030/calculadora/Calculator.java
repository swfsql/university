public class Calculator {

	public String right(Expression e) {	return _right(e.getRightRpn());}

	private String _right(List rpn) {
		SuperInt a, b;
		String now;
		int iNow;

		rpn.start();
		while(rpn.next() != null) {

			now = rpn.now.value;
			iNow = (int) now.charAt(0); 
			if(iNow == '-' && now.length() > 1) {
				int i2 =  now.charAt(1);
				if (i2 >= '0' && i2 <= '9') iNow = i2;
			}

			// number.
			if (iNow >= '0' && iNow <= '9') continue;

			// operator.
			b = new SuperInt(rpn.prev().value);
			if (rpn.prev(false) != null) a = new SuperInt(rpn.now.value); 
			else { 
				a = new SuperInt("0");
				rpn.start();
				rpn.add("0");
				rpn.next();
			}
			rpn.rmNext();
			rpn.rmNext();

			try {
				if (iNow == '+') rpn.now.value = a.plus(b).toString();
				if (iNow == '-') rpn.now.value = a.minus(b).toString();
				if (iNow == '*') rpn.now.value = a.times(b).toString();
				if (iNow == '/') rpn.now.value = a.divide(b).toString();
			} catch (Exception e) {
				System.out.print("FUUUUUUUUUUUUUUUUUUUUUUUUU-");
			}
		}

		rpn.start();
		return rpn.next().value;
	}
}