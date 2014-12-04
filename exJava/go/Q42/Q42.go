package main

import (
	"encoding/binary"
	"fmt"
	"io"
	"os"
)

func main() {
	reader, _ := os.Open("test.dat")
	switch 0 {
	case 0:
		for i, buff := 0, make([]byte, 1); i < 26; i++ {
			io.ReadFull(reader, buff)
			fmt.Printf("char %c\n", buff[0])
		}
	case 1:
		for i, buff := 0, make([]byte, 2); i < 13; i++ {
			io.ReadFull(reader, buff)
			fmt.Printf("short %d\n", binary.BigEndian.Uint16(buff))
		}
	case 2:
		for i, buff := 0, make([]byte, 4); i < 6; i++ {
			io.ReadFull(reader, buff)
			fmt.Printf("int %d\n", binary.BigEndian.Uint32(buff))
		}
	}
}
