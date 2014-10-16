package main

import "fmt"

func main() {
  newFeature();
}

type Feature struct {
  animal Animal
}
func newFeature() (*Feature) {
  var line string
  fmt.Printf("input: ")
  fmt.Scanf("%s\n", &line)

  f := new(Feature)
  f.animal = new(Creator).create(line) 
  f.check()
  return f
}
func (f *Feature) check() {
  if f.animal == nil {
    fmt.Printf("Error")
  } else {
    fmt.Printf("Kind: %s\n", (f.animal).getKind())
    fmt.Printf("Move: %s\n", (f.animal).getMove())
    fmt.Printf("Child: %s\n", (f.animal).getHowChild())
    fmt.Printf("Die: %t\n", (f.animal).willDie())
  }
}

type Creator struct { }
func (_ Creator) create (line string) Animal {
  if line == "inu" {
    return new(Dog)
  } else if line == "bird" {
    return new(Bird)
  } else if line == "human" {
    return new(Human)
  } else {
    return nil 
  }
}

type Animal interface {
  getKind() string
  getMove() string
  willDie() bool
  getHowChild() string
}

type Mammal struct { } 
func (_ Mammal) getKind() string {
  return "Animal"
}
func (_ Mammal) getMove() string {
  return "Move on Foot"
}
func (_ Mammal) willDie() bool {
  return true 
}
func (_ Mammal) getHowChild() string {
  return "as Child"
}

type Human struct {
  Mammal
} 
func (_ Human) getKind() string {
  return "Human"
}
func (_ Human) getMove() string {
  return "Two legs walking"
}

type Dog struct {
  Mammal
} 
func (_ Dog) getKind() string {
  return "Dog"
}
func (_ Dog) getMove() string {
  return "Four legs walking"
}

type Bird struct { } 
func (_ Bird) getKind() string {
  return "Bird"
}
func (_ Bird) getMove() string {
  return "Fly"
}
func (_ Bird) willDie() bool {
  return true 
}
func (_ Bird) getHowChild() string {
  return "as Egg"
}

