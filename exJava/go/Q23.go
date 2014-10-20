package main

import "fmt"
import "container/list"

func main() {
  mc := newMultiColl()
  
  mc.addList("AAA")
  mc.addList("BBB")
  mc.addList("CCC")

  mc.addMap("X", 100)
  mc.addMap("Y", 200)
  mc.addMap("Z", 300)

  mc.showAll()
}

type multiCollection struct {
  l list.List
  m map[string]int
}
func newMultiColl () (mc *multiCollection) {
  mc = new(multiCollection)
  mc.m = make(map[string]int)
  return 
}
func (mc *multiCollection) addList(str string) {
  mc.l.PushBack(str);
}
func (mc *multiCollection) addMap(str string, value int) {
  mc.m[str] = value
}
func (mc *multiCollection) showAll() {

  fmt.Printf("\n## List ##\n")
  for e := mc.l.Front(); e != nil; e = e.Next() {
    fmt.Printf("%s\n", e.Value)
  }

  fmt.Printf("\n## Map ##\n")
  for key, value := range mc.m {
    fmt.Printf("%s: %d\n", key, value)
  }
}

