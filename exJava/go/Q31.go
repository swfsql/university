package main

import "fmt"
import "container/list"
import "strings"

func main() {
	mc := newMultiColl()
	for {
		var str string
		fmt.Scanf("%s\n", &str)
		if strs := strings.Split(str, ","); len(strs) > 1 {
			mc.addMap(strs[0], strs[1])
		} else if str == "end" {
			break
		} else {
			mc.addList(str)
		}
	}
	mc.showAll()
}

type multiCollection struct {
	l list.List
	m map[string]string
}

func newMultiColl() (mc *multiCollection) {
	mc = new(multiCollection)
	mc.m = make(map[string]string)
	return
}
func (mc *multiCollection) addList(str string) {
	mc.l.PushBack(str)
}
func (mc *multiCollection) addMap(key string, value string) {
	mc.m[key] = value
}
func (mc *multiCollection) showAll() {

	fmt.Printf("\n## List ##\n")
	for e := mc.l.Front(); e != nil; e = e.Next() {
		fmt.Printf("%s\n", e.Value)
	}

	fmt.Printf("\n## Map ##\n")
	for key, value := range mc.m {
		fmt.Printf("%s: %s\n", key, value)
	}
}
