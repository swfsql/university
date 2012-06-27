public class Calculator {

	public String right(Expression e) throws Exception { return _right(e.getRightRpn());}

	private String _right(List rpn) throws Exception {
		SuperInt sa, sb;
		Frac fa, fb;
		try {
			sa = sb = new SuperInt("0");
			fa = fb = new Frac("1/1");

		} catch (Exception e) {
			throw e;
		}
		String now;
		int iNow;
		Boolean hasDiv;

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
			try {
				hasDiv = hasDivision(rpn.prev().value);
				if (hasDiv) fb = new Frac(rpn.now.value);
				else sb = new SuperInt(rpn.now.value);
				
				if (rpn.prev(false) != null) {
					if (hasDiv) fa = new Frac(rpn.now.value);
					else sa = new SuperInt(rpn.now.value);
				}
				else { 
					if (hasDiv) fa = new Frac("0/1");
					else sa = new SuperInt("0");
					rpn.start();
					rpn.add("0");
					rpn.next();
				}
			} catch (Exception e) { return e.getMessage();}
			rpn.rmNext();
			rpn.rmNext();

			try {
				if (hasDiv) {
					if (iNow == '+') rpn.now.value = fa.plus(fb).toString();
					if (iNow == '-') rpn.now.value = fa.minus(fb).toString();
					if (iNow == '*') rpn.now.value = fa.times(fb).toString();
					if (iNow == '/') rpn.now.value = fa.divide(fb).toString();
				} else {
					if (iNow == '+') rpn.now.value = sa.plus(sb).toString();
					if (iNow == '-') rpn.now.value = sa.minus(sb).toString();
					if (iNow == '*') rpn.now.value = sa.times(sb).toString();
					if (iNow == '/') rpn.now.value = sa.divide(sb).toString();
				}
				
			} catch (Exception e) {	return e.getMessage();}
		}

		rpn.start();
		return rpn.next() != null ? rpn.now.value : "";
	}

	private Boolean hasDivision(String s) {
		int i = -1, l = s.length();
		while(++i < l) if (s.charAt(i) == '/') return true;
		return false;
	}
}