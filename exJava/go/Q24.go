package main
import "fmt"

func main() {
  ohm := newOrderedHashMap()
  ohm.put("AAA", 100)
  ohm.put("BBB", 200)
  ohm.put("CCC", 300)
  ohm.put("DDD", 400)
  ohm.put("EEE", 500)
  ohm.put("DDD", 1000)
  ohm.put("DDD", 2000)
  ohm.put("XYX", 10000)
  
  keyList := ohm.KeyList()
  for _, v := range keyList {
    fmt.Printf("%s = %d\n", v, ohm.get(v))
  }
}

type orderedHashMap struct {
  keys []string
  m map[string]int
}

func newOrderedHashMap() (ohm *orderedHashMap) {
  ohm = new(orderedHashMap)
  ohm.m = make(map[string]int)
  ohm.keys = make([]string, 0)
  return 
}
func (ohm *orderedHashMap) put(str string, value int) {
  if _, ok := ohm.m[str]; !ok {
    ohm.keys = append(ohm.keys, str)
  }
  ohm.m[str] = value
}
func (ohm *orderedHashMap) get(str string) int {
  return ohm.m[str]
}
func (ohm *orderedHashMap) KeyList() []string {
  return ohm.keys
}
