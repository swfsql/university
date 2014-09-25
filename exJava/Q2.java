public class Q2 {

  public static void main (String[] args) {
    int[] array = {10, 8, 2, 4, 6, 7, 0};
    
    new Q2().sort(array);
    new Q2().print(array);
    //sort(array);
    //print(array);

  }

  public void sort (int[] a) {
    int l = a.length, i2, temp;
    for (int i = 0; i < l; i++) {
      for (i2 = 0; i2 < l - 1; i2++) {
  
      if (a[i2] > a[i2 + 1]) {
	      temp = a[i2];
	      a[i2] = a[i2 + 1];
	      a[i2 + 1] = temp;
        }
      }
    }
  }

  public void print (int[] a) {
    int l = a.length;
    for (int i = 0; i < l; i++) {
      System.out.println(a[i]); 
    }
  }



} 
  


