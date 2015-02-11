package main

import (
	//"encoding/binary"
	"flag"
	"fmt"
	"io"
	"os"
)

var versionFlag = flag.Bool("version", false, "version")

func main() {
	flag.BoolVar(versionFlag, "v", false, "version")
	flag.Parse()
	inputs := flag.Args()

	if *versionFlag {
		fmt.Printf("0.01")
		return
	}

	if len(inputs) > 0 {
		for _, name := range inputs {
			temp(name)
		}
	}
}

func temp(name string) {
	in := newInput(name, 128)
	stat, err := in.file.Stat()
	if err != nil {
		fmt.Printf("error when openning the file")
		return
	}
	var i int64
	for ; i < stat.Size(); i++ {
		if i%16 == 0 {
			fmt.Printf("\n %8X ", i)
		}
		fmt.Printf("%2X ", in.readByte())
	}
}

type input struct {
	file    *os.File
	buf     []byte
	buf2    []byte
	discard *int
}

func newInput(filename string, length int) (in *input) {
	in = new(input)
	in.file, _ = os.Open(filename)
	in.buf = make([]byte, 0, length)
	in.buf2 = make([]byte, 0, length)
	in.discard = new(int)
	return
}
func (in *input) bufUpdate() {
	if discarded := cap(in.buf2) - len(in.buf) + *in.discard; discarded >= cap(in.buf2) {
		in.buf2 = in.buf2[:discarded]
		io.ReadFull(in.file, in.buf2)
		in.buf = append(in.buf[*in.discard:], in.buf2...)
	} else {
		in.buf = in.buf[*in.discard:]
	}
}
func (in *input) readByte() (b int16) {
	in.bufUpdate()
	b, *in.discard = int16(in.buf[0]), 1
	return
}
