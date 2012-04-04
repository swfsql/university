// made this while writing the calculator java
class WTF {


	public static void main(String[] args) {
		int u = 0; Boolean h;


		System.out.println("u = 0");
		System.out.print("++u  "); System.out.println(++u);
		u = 0;
		System.out.print("u++  "); System.out.println(u++);
		u = 0;


		h = u++ == (++u);
		System.out.print("\n\nu++ == (++u)  ");
		System.out.println(h);
		u = 0;

		h = ++u == (u++);
		System.out.print("\n++u == (u++)  ");
		System.out.println(h);

		System.out.print("\n\nwith that, we see that java executes first the left side, then the right, even if theres an () in the right side.");
	}

}