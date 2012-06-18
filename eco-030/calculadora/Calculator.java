<<<<<<< HEAD
=======
<<<<<<< HEAD
>>>>>>> origin/thyviado
<<<<<<< HEAD
=======
<<<<<<< HEAD
=======
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

			if (iNow == '+') rpn.now.value = a.plus(b).toString();
			if (iNow == '-') rpn.now.value = a.minus(b).toString();
			if (iNow == '*') rpn.now.value = a.times(b).toString();
			if (iNow == '/') rpn.now.value = a.divide(b).toString();
		}

		rpn.start();
		return rpn.next().value;
	}
<<<<<<< HEAD
=======
=======
>>>>>>> a557b2f5d26563a178a6e47404797482dff0cecb
>>>>>>> bee6ac6828420ec7f8771ed388470a4df673f82d
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

			if (iNow == '+') rpn.now.value = a.plus(b).toString();
			if (iNow == '-') rpn.now.value = a.minus(b).toString();
			if (iNow == '*') rpn.now.value = a.times(b).toString();
			if (iNow == '/') rpn.now.value = a.divide(b).toString();
		}

		rpn.start();
		return rpn.next().value;
	}
<<<<<<< HEAD
=======
=======
<<<<<<< HEAD
>>>>>>> a557b2f5d26563a178a6e47404797482dff0cecb
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
<<<<<<< HEAD
			b = new SuperInt(rpn.prev().value);
			if (rpn.prev(false) != null) a = new SuperInt(rpn.now.value); 
			else { 
				a = new SuperInt("0");
				rpn.start();
				rpn.add("0");
				rpn.next();
			}
=======
			try {
				b = new SuperInt(rpn.prev().value);
				if (rpn.prev(false) != null) a = new SuperInt(rpn.now.value); 
				else { 
					a = new SuperInt("0");
					rpn.start();
					rpn.add("0");
					rpn.next();
				}
			} catch (Exception e) { return e.getMessage();}
>>>>>>> origin/subber
			rpn.rmNext();
			rpn.rmNext();

			if (iNow == '+') rpn.now.value = a.plus(b).toString();
			if (iNow == '-') rpn.now.value = a.minus(b).toString();
			if (iNow == '*') rpn.now.value = a.times(b).toString();
			if (iNow == '/') rpn.now.value = a.divide(b).toString();
		}

		rpn.start();
		return rpn.next().value;
	}
<<<<<<< HEAD
=======
=======
=======
>>>>>>> bee6ac6828420ec7f8771ed388470a4df673f82d
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
			try {
				b = new SuperInt(rpn.prev().value);
				if (rpn.prev(false) != null) a = new SuperInt(rpn.now.value); 
				else { 
					a = new SuperInt("0");
					rpn.start();
					rpn.add("0");
					rpn.next();
				}
			} catch (Exception e) { return e.getMessage();}
			rpn.rmNext();
			rpn.rmNext();

			try {
				if (iNow == '+') rpn.now.value = a.plus(b).toString();
				if (iNow == '-') rpn.now.value = a.minus(b).toString();
				if (iNow == '*') rpn.now.value = a.times(b).toString();
				if (iNow == '/') rpn.now.value = a.divide(b).toString();
			} catch (Exception e) {	return e.getMessage();}
		}

		rpn.start();
		return rpn.next().value;
	}
>>>>>>> origin/master
>>>>>>> origin/thyviado
}