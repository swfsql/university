package main

import "fmt"

func main() {
	// in go there is no inherance + polymorphism.
	pl := []*phone{
		&phone{name: "IPhone", price: 50000, numOfApp: 5000}, // iphone
		&phone{name: "XPeria", price: 45000, numOfApp: 300},  // xperia
	}

	bl := []*book{
		&book{index: "a, b, c, d, e", pageNum: 360, price: 4000},              // disctionary
		&book{index: "1. Intro, 2. It's Happening", pageNum: 280, price: 760}, // novel
	}

	// no need for shop struct
	phoneDisplay(pl)
	bookDisplay(bl)

	// need to convert to []interface{} before
	pli := make([]interface{}, len(pl)+len(bl))
	for i, v := range pl {
		pli[i] = v
	}
	for i, v := range bl {
		pli[i+len(pl)] = v
	}

	display(pli)
}

func bookDisplay(l []*book) {
	fmt.Printf("\nbookDisplay\n")
	for _, v := range l {
		fmt.Printf("Index\n\t%s\n\tTotal page = %d\n\tPrice = %d\n", v.index, v.pageNum, v.price)
	}
}
func phoneDisplay(l []*phone) {
	fmt.Printf("\nphoneDisplay\n")
	for _, v := range l {
		fmt.Printf("Name = %s\n\tApp Num = %d\n\tPrice = %d\n", v.name, v.numOfApp, v.price)
	}
}
func display(l []interface{}) {
	fmt.Printf("\ndisplay\n")
	for _, v := range l {
		switch v.(type) {
		case *book:
			bk := v.(*book)
			fmt.Printf("Index\n\t%s\n\tTotal page = %d\n\tPrice = %d\n", bk.index, bk.pageNum, bk.price)
		case *phone:
			ph := v.(*phone)
			fmt.Printf("Name = %s\n\tApp Num = %d\n\tPrice = %d\n", ph.name, ph.numOfApp, ph.price)
		}
	}
}

type phone struct {
	name            string
	numOfApp, price int
}

type book struct {
	index          string
	pageNum, price int
}
