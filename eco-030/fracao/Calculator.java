// get the input and send to the exercise's Class, Fracao, and returns the output.
// this class, Calculator, its still not general enought, so we must adapt it to each exercise from the teacher.
public class Calculator {
	
	public String right(Expression e) {
		return _right(e.getRight().head());
	}

	private String _right(StringUnit h) {
		// h example: 1,/,2, +, 3,/,6. another example: 5,/,10. 
		String[] as = h.array(); // example: ["1","/","2","+","3","/","6"]
		int l = h.length();

		Fracao f1, f2;
		String op;

		if (l == 0) return ""; // (enter)
		if (l >= 3) { // 1,/,2
			if (!as[1].equals("/")) return "error";
			f1 = new Fracao(as[0], as[2]);

			if (l == 7) { // 1,/,2, +, 3,/,6
				op = as[3]; // operator
				if (!op.equals("+") && !op.equals("-") && !op.equals("*") && !op.equals("/")) return "error"; // not an operator
				if(!as[5].equals("/")) return "error";
				f2 = new Fracao (as[4], as[6]); // 3,/,6
				return f1.ind || f2.ind ? "indeterminate" : f1.error || f2.error ? "error - divide by 0" :
					   op.equals("+") ? f1.plus(f2).toString() : 
					   op.equals("-") ? f1.minus(f2).toString() :
					   op.equals("*") ? f1.times(f2).toString() :
                     /*op  = "/"     */ f1.divide(f2).toString();
			}
			return f1.ind ? "indeterminate" : f1.error ? "error - divide by 0" :
				   f1.toString(); // 1,/,2
		}
		return "error";
	}
}