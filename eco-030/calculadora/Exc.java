public class Exc extends Exception {
	public void divideByZero(String s) throws Exception {
		int i = -1, l = s.length();
		while(++i < l) if (s.charAt(i) != '-' && s.charAt(i) != '0') return;
		throw new Exception("erro: divisao por zero.");
	}
}