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
			System.out.println("a");
			now = rpn.now.value;
			iNow = (int) now.charAt(0); 
			if(iNow == '-' && now.length() > 1) {
				int i2 =  now.charAt(1);
				if (i2 >= '0' && i2 <= '9') iNow = i2;
			}
			System.out.println("b");

			// number.
			if (iNow >= '0' && iNow <= '9') continue;
			System.out.println("c");

			// operator.
			try {
				hasDiv = hasDivision(rpn.prev().value);
				if (hasDiv) fb = new Frac(rpn.now.value);
				else sb = new SuperInt(rpn.now.value);
				System.out.println("c1");
				
				if (rpn.prev(false) != null) {
					System.out.println("c2");
					if (hasDiv) {
						System.out.println("c2.1");
						fa = new Frac(rpn.now.value);
						System.out.println("c2.1.1");
					}
					else if (hasDivision(rpn.now.value)) {
						System.out.println("c2.2");
						hasDiv = true;
						fb = new Frac(sb.toString() + "/1");
					} else {
						System.out.println("c2.3");
						sa = new SuperInt(rpn.now.value);
					}
				}
				else { 
					System.out.println("c3");
					if (hasDiv) fa = new Frac("0/1");
					else sa = new SuperInt("0");
					rpn.start();
					rpn.add("0");
					rpn.next();
				}
			} catch (Exception e) { return e.getMessage();}
			System.out.println("d");
			rpn.rmNext();
			rpn.rmNext();

			// debug
			if (hasDiv) {
				System.out.print(fa); System.out.print(" "); System.out.print(iNow); System.out.print(" "); System.out.println(fb);
			} else {
				System.out.print(sa); System.out.print(" "); System.out.print(iNow); System.out.print(" "); System.out.println(sb);

			}

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
					if (iNow == '/') {
						if(sa.module(sb).equals0()) rpn.now.value = sa.divide(sb).toString();
						else rpn.now.value = sa + "/" + sb;
					}
				}
				
			} catch (Exception e) {	return e.getMessage();}
			System.out.println(rpn.now.value);
			System.out.println("...");
		}
		System.out.println("_____");

		rpn.start();
		return rpn.next() != null ? rpn.now.value : "";
	}

	private Boolean hasDivision(String s) {
		int i = -1, l = s.length();
		while(++i < l) if (s.charAt(i) == '/') return true;
		return false;
	}
}