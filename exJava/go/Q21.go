package main

import "fmt"

func main() {
  var b iBank

  b = new(mizuhoBank)
  fmt.Printf("%s\n", b.Name())
  fmt.Printf("%d\n", b.MaxDeposit())
  fmt.Printf("%d\n\n", b.Fee())

  b = new(ufjBank)
  fmt.Printf("%s\n", b.Name())
  fmt.Printf("%d\n", b.MaxDeposit())
  fmt.Printf("%d\n", b.Fee())
}

type iBank interface {
  Name() string
  MaxDeposit() int
  Fee() int
}

type mizuhoBank struct { }
func (_ mizuhoBank) Name() string {
  return "This is Mizuho Bank"
}
func (_ mizuhoBank) MaxDeposit() int {
  return 100000
}
func (_ mizuhoBank) Fee() int {
  return 210
}

type ufjBank struct { }
func (_ ufjBank) Name() string {
  return "UFJ"
}
func (_ ufjBank) MaxDeposit() int {
  return 2000000
}
func (_ ufjBank) Fee() int {
  return 105 
}

