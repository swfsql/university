package main

import "fmt"

func main() {
	iter := newIterator()
	for i := 0; i < 10; i++ {
		iter.add(i)
	}
	iter.remove()
	iter.add(30)
	iter = iter.iterator()
	for iter.hasNext() {
		if i, ok := iter.next().(int); ok {
			fmt.Printf("%d\n", i)
		}
	}
}

type iterator interface {
	hasNext() bool
	next() interface{}
	remove()
	iterator() iterator
	add(interface{})
}

func newIterator() iterator {
	iter := new(myIterator)
	iter.arr = make([]interface{}, 0)
	iter.index = -1
	return iter
}

type myIterator struct {
	arr   []interface{}
	index int
}

func (iter *myIterator) add(value interface{}) {
	iter.index++
	iter.arr = append(iter.arr, value)
}
func (iter *myIterator) hasNext() bool {
	return iter.index < len(iter.arr)-1
}
func (iter *myIterator) next() interface{} {
	iter.index++
	return iter.arr[iter.index]
}
func (iter *myIterator) remove() {
	if len(iter.arr) == 0 { // try iter.arr == nil
		return
	}
	iter.arr = iter.arr[:len(iter.arr)-1]
}
func (old *myIterator) iterator() iterator {
	iter := new(myIterator)
	iter.arr = old.arr
	iter.index = -1
	return iter
}
