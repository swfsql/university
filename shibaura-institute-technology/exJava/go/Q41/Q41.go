package main

import (
	"flag"
	"io"
	"os"
)

func main() {
	flag.Parse()
	inputs := flag.Args()
	output := inputs[len(inputs)-1]
	inputs = inputs[:len(inputs)-1]

	reads := make([]io.Reader, len(inputs))
	for i, s := range inputs {
		reads[i], _ = os.Open(s)
	}

	out, _ := os.Create(output)
	io.Copy(out, io.MultiReader(reads...))
}
