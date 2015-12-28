package main

import "fmt"
import "container/list"

func main() {
  rlist := list.New()
  rlist.PushBack("test1")
  rlist.PushBack("test2")
  rlist.PushBack("test3")
  rlist.PushBack("test4")
  rlist.PushBack("test5")

  for e := rlist.Back(); e != nil; e = e.Prev() {
    fmt.Printf("%s\n", e.Value)
  }
}

