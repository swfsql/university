package main

import "fmt"

func main() {
	tl := newTinyList()
	tl.add("+1")
	tl.add("+2")
	tl.add("+3")
	tl.add("+4")
	tl.add("+5")
	tl.add("+6")
	tl.add("+7")
	tl.add("+8")
	tl.add("+9")
	tl.add("+10")

	if v, ok := tl.get(-1); ok {
		fmt.Printf("1>%s\n", v) // nil
	}

	tl.add("+11")
	if v, ok := tl.get(9); ok {
		fmt.Printf("2>%s\n", v) // +10
	}
	if v, ok := tl.get(10); ok {
		fmt.Printf("3>%s\n", v) // +11
	}

	fmt.Printf("4>%s\n", tl.remove()) // +11
	if v, ok := tl.get(10); ok {
		fmt.Printf("5>%s\n", v) // nil
	}
	tl.add("+12")
	if v, ok := tl.get(10); ok {
		fmt.Printf("6>%s\n", v) // +12
	}
}

type tinyList struct {
	list []interface{}
}

func newTinyList() *tinyList {
	return &tinyList{make([]interface{}, 0)}
}
func (tl *tinyList) add(e interface{}) {
	tl.list = append(tl.list, e)
}
func (tl *tinyList) remove() (e interface{}) {
	if len(tl.list) < 1 {
		e = nil
	} else {
		e = tl.list[len(tl.list)-1]
		tl.list = tl.list[:len(tl.list)-1]
	}
	return
}
func (tl *tinyList) get(index int) (e interface{}, ok bool) {
	if ok = index >= 0 && index < len(tl.list); ok {
		return tl.list[index], ok
	} else {
		return nil, ok
	}
}
