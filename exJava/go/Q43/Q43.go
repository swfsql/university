// java "chars" have 2 bytes. go "runes" have 1~4 bytes.
package main

import (
	"bytes"
	"encoding/binary"
	"fmt"
	"io"
	"os"
	"unicode/utf8"
)

func main() {
	data := []interface{}{1, '日', '本', 'g', 0, -1}
	newTinyOut("output.dat").write(data)

	in := newTinyIn("output.dat", 4)
	fmt.Printf("load %d %c %c %c %d %d", in.readInt(), in.readChar(), in.readChar(), in.readChar(), in.readInt(), in.readInt())
}

type tinyOut struct {
	file  *os.File
	buf   []byte
	datab *bytes.Buffer
}

func newTinyOut(fileName string) (to *tinyOut) {
	to = new(tinyOut)
	to.buf = make([]byte, 4)
	to.file, _ = os.Create(fileName)
	return
}
func (to *tinyOut) write(data []interface{}) {
	fmt.Printf("save %#U\n", data)
	for _, d := range data {
		to.buf = to.buf[:4]
		switch d.(type) {
		case rune:
			n := utf8.EncodeRune(to.buf, d.(rune))
			to.buf = to.buf[:n]
		case int:
			binary.BigEndian.PutUint32(to.buf, uint32(d.(int)))
		}
		to.file.Write(to.buf)
	}
}

type tinyIn struct {
	file    *os.File
	buf     []byte
	buf2    []byte
	discard *int
}

func newTinyIn(fileName string, width int) (ti *tinyIn) {
	ti = new(tinyIn)
	ti.file, _ = os.Open(fileName)
	ti.discard = &width
	ti.buf = make([]byte, 4)
	ti.buf2 = make([]byte, 4)
	return
}
func (ti *tinyIn) readChar() (i rune) {
	ti.update()
	i, *ti.discard = utf8.DecodeRune(ti.buf)
	return
}
func (ti *tinyIn) readInt() (i int) {
	ti.update()
	i, *ti.discard = int(int32(binary.BigEndian.Uint32(ti.buf))), 4
	return
}
func (ti *tinyIn) update() {
	ti.buf2 = ti.buf2[:*ti.discard]
	io.ReadFull(ti.file, ti.buf2)
	ti.buf = append(ti.buf[*ti.discard:], ti.buf2...)
}
