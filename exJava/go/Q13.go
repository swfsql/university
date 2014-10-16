package main

import "fmt"

func main() {
  var line string
  fmt.Printf("Please input your name -> ");
  fmt.Scanf("%s\n", &line );
  fmt.Printf("Hello ");
  if line == "" {
    fmt.Printf("World");
  } else {
    fmt.Printf("%s", line);
  }
}

