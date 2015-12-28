package main

import "fmt"

func main() {
	pl := newPairList()
	pl.add("key1", 10)
	pl.add("key2", 20)
	pl.add("key3", 30)

	e1, e2 := pl.get(0)
	showAll(e1, e2)

	e1, e2 = pl.get(1)
	showAll(e1, e2)

	e1, e2 = pl.get(2)
	showAll(e1, e2)
}

type pairList struct {
	list1 []interface{}
	list2 []interface{}
}

func newPairList() *pairList {
	return &pairList{make([]interface{}, 0), make([]interface{}, 0)}
}
func (pl *pairList) add(e1 interface{}, e2 interface{}) {
	pl.list1 = append(pl.list1, e1)
	pl.list2 = append(pl.list2, e2)
}
func (pl *pairList) get(index int) (interface{}, interface{}) {
	return pl.list1[index], pl.list2[index]
}

// no need for holders, functions can return multiple values
func showAll(e1, e2 interface{}) {
	fmt.Printf("v1:v2 = %s:%d\n", e1, e2)
}
