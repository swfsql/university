package main

import "fmt"

func main() {
  array := []int{10, 8, 2, 4, 6, 7, 0};
  sort(array);
  print(array);
}

func sort(array []int) {
  for i := 0; i < len(array); i++ {
    for j:= 0; j < len(array) - 1; j++ {
      if array[j] > array[j + 1] {
        a := array[j];
        array[j] = array[j + 1];
        array[j + 1] = a;
      }
    }
  }
}

func print(array []int) {
  for i := 0; i < len(array); i++ {
    fmt.Printf("%d ", array[i]);
  }
}
