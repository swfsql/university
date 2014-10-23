public class Q1 {

  public static void main (String[] args) {
    int[] array = {10, 8, 2, 4, 6, 7, 0};
    int a;
    
    int i = 0, l = array.length, i2;
    for (i = 0; i < l; i++) {
      for (i2 = 0; i2 < l - 1; i2++) {
        if (array[i2] > array[i2 + 1]) {
	  a = array[i2];
	  array[i2] = array[i2 + 1];
	  array[i2 + 1] = a;
        }
      }
    }

    for (i = 0; i < l; i++) {
      System.out.println(array[i]); 
    }

  }

} 


