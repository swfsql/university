public class Exc extends Exception {

	public void divideByZero(String s) throws Exception {
		int i = -1, l = s.length();
		//System.out.print("num chars (de "); System.out.print(s); System.out.print("): "); System.out.println(l);
		while(++i < l) if (s.charAt(i) != '-' && s.charAt(i) != '0') return;
		throw new Exception("erro: divisao por zero.");
	}


}