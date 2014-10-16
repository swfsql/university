package main

import "fmt"

func main() {
  if new(StrCompare).compare() {
    fmt.Printf("judje = true");
  } else {
    fmt.Printf("judje = false");
  }
}

type StrCompare struct { }
func (_ StrCompare) compare () bool {
  var line string
  fmt.Printf("Input First String -> ");
  fmt.Scanf("%s\n", &line);
  line1 := []rune(line)
  l1 := len(line1)
  
  fmt.Printf("Input Second String -> ");
  fmt.Scanf("%s\n", &line);
  line2 := []rune(line)
  l2 := len(line2)

  j := 0
  for i := 0; i < l1 - l2 + 1; i++ {
    for j = 0; j < l2; j++ {
      if line1[i + j] != line2[j] {
        break;
      }
    }
    if j == l2 {
      return true
    }
  }
  return false
}

