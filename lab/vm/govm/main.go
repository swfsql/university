package main

import (
	"unicode/utf8"
	//"encoding/binary"
	"flag"
	"fmt"
	"io"
	"os"
)

var versionFlag = flag.Bool("version", false, "version")

var flags = [...]string{"overflow", "direction", "interrupt", "trap", "sign", "zero", "auxiliary", "parity", "carry"}

var intelVersions = [...]string{"8086", "8088", "80186", "80188", "80286", "80386", "80486"}

var ins []*instruction

func main() {
	flag.BoolVar(versionFlag, "v", false, "version")
	flag.Parse()
	inputs := flag.Args()

	if *versionFlag {
		fmt.Printf("0.01")
		return
	}

	addInstructions()

	if len(inputs) > 0 {
		for _, name := range inputs {
			temp(name)
		}
	}

}

func addInstructions() {
	ins = make([]*instruction, 0)
	data := [][]string{
		{"AAA", "37", "1"},
		{"AAD", "D5 0A", ""},
		{"AAM", "D4 0A", ""},
		{"AAS", "3F", ""},
	}
	for i := 0; i < len(data); i++ {
		tmp_instruction := newInstruction("AAA", "37", "1")
		ins = append(ins, tmp_instruction)
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

type instruction struct {
	mnemonics    string
	opcodeString []string
	modrm        byte
	sib          byte
	displacement byte
	immediate    byte
	length       [2]int
}

func newInstruction(mne string, op string, length string) (in *instruction) {
	in = new(instruction)
	in.mnemonics = mne

	// digest op string
	in.opcodeString = make([]string, 0)
	var s string = ""
	for i, w := 0, 0; i < len(op); i += w {
		var runeValue rune
		runeValue, w = utf8.DecodeRuneInString(op[i:])
		if string(runeValue) == " " {
			in.opcodeString = append(in.opcodeString, s)
			s = ""
		} else {
			s += string(runeValue)
		}
	}
	in.opcodeString = append(in.opcodeString, s)

	runeValue, w := utf8.DecodeRuneInString(length[0:])
	in.length[0] = int(runeValue - '0')
	in.length[1] = in.length[0] // when len="const"
	if len(length) > w {        // when len="min~max"
		w2 := w
		runeValue, w = utf8.DecodeRuneInString(length[w2:]) // skip the ~
		w2 += w
		runeValue, w = utf8.DecodeRuneInString(length[w2:])
		in.length[1] = int(runeValue - '0') // set the max
	}

	return
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
