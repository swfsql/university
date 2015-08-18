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

var intelVersions = [...]string{"8086", "8088", "80186", "80188", "80286", "80386", "80486", "P5", "P54", "P55", "P6", "PentiumII", "Celeron", "PentiumIII", "PentiumIV", "PentiumM", "CeleronM", "IntelCore"}

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
		{"AAA", "37", "1", "Ascii Adjust fir Addition", "", ""},
		{"AAD", "D5 0A", "2", "Ascii Adjust for Division", "", ""},
		{"AAM", "D4 0A", "2", "Ascii Adjust for Multiplication", "", ""},
		{"AAS", "3F", "1", "Ascii Adjust for Subtraction", "", ""},

		{"ADC AL,ib", "14 i0", "2", "Add With Carry", "", "dest,src"},
		{"ADC AX,iw", "15 i0 i1", "3", "Add With Carry", "", "dest,src"},
		{"ADC rb,rmb", "12 mr d0 d1", "2~4", "Add With Carry", "", "dest,src"},
		{"ADC rw,rmw", "13 mr d0 d1", "2~4", "Add With Carry", "", "dest,src"},
		{"ADC rmb,ib", "80 /2 d0 d2 i0", "3~5", "Add With Carry", "", "dest,src"},
		{"ADC rmw,iw", "81 /2 d0 d1 i0 i1", "4~6", "Add With Carry", "", "dest,src"},
		{"ADC rmw,ib", "83 /2 d0 d1 i0", "3~5", "Add With Carry", "", "dest,src"},
		{"ADC rmw,rb", "10 mr d0 d1", "2~4", "Add With Carry", "", "dest,src"},
		{"ADC rmw,rw", "11 mr d0 d1", "2~4", "Add With Carry", "", "dest,src"},

		{"ADD AL,ib", "04 i0", "2", "Arithmetic Addition", "", "dest,src"},
		{"ADD AX,iw", "05 i0 i1", "3", "Arithmetic Addition", "", "dest,src"},
		{"ADD rb,rmb", "02 mr d0 d1", "2~4", "Arithmetic Addition", "", "dest,src"},
		{"ADD rw,rmw", "03 mr d0 d1", "2~4", "Arithmetic Addition", "", "dest,src"},
		{"ADD rmb,ib", "80 /0 d0 d1 i0", "3~5", "Arithmetic Addition", "", "dest,src"},
		{"ADD rmw,iw", "81 /0 d0 d1 i0 i1", "4~6", "Arithmetic Addition", "", "dest,src"},
		{"ADD rmw,ib", "83 /0 d0 d1 i0", "3~5", "Arithmetic Addition", "", "dest,src"},
		{"ADD rmb,rb", "00 mr d0 d1", "2~4", "Arithmetic Addition", "", "dest,src"},
		{"ADD rmw,rw", "01 mr d0 d1", "2~4", "Arithmetic Addition", "", "dest,src"},

		{"AND AL,ib", "24 i0", "2", "Logical And", "", "dest,src"},
		{"AND AX,iw", "25 i0 i1", "3", "Logical And", "", "dest,src"},
		{"AND rb,rmb", "22 mr d0 d1", "2~4", "Logical And", "", "dest,src"},
		{"AND rw,rmw", "23 mr d0 d1", "2~4", "Logical And", "", "dest,src"},
		{"AND rmb,ib", "80 /4 d0 d1 i0", "3~5", "Logical And", "", "dest,src"},
		{"AND rmw,iw", "81 /4 d0 d1 i0 i1", "4~6", "Logical And", "", "dest,src"},
		{"AND rmw,ib", "83 /4 d0 d1 i0", "3~5", "Logical And", "", "dest,src"},
		{"AND rmb,rb", "20 mr d0 d1", "2~4", "Logical And", "", "dest,src"},
		{"AND rmw,rw", "21 mr d0 d1", "2~4", "Logical And", "", "dest,src"},

		{"ARPL rmw,rw[286]", "63 mr d0 d1", "2~4", "Adjusted Requested Privilege Level of Selector", "286+ protected mode", "dest,src"},
		{"BOUND rw,rmw[186]", "62 mr d0 d1", "2~4", "Array Index Bound Check", "80188+", "src,limit"},

		{"BSF rw,rmw[386]", "0F BC mr d0 d1", "3~5", "Bit Scan Forward", "386+", "dest,src"},
		{"BSR rw,rmw[386]", "0F BD mr d0 d1", "3~5", "Bit Scan Reverse", "386+", "dest,src"},

		{"BSWAP eax[486]", "0F C8", "2", "Byte Swap", "486+", "reg32"},
		{"BSWAP ecx[486]", "0F C9", "2", "Byte Swap", "486+", "reg32"},
		{"BSWAP edx[486]", "0F CA", "2", "Byte Swap", "486+", "reg32"},
		{"BSWAP ebx[486]", "0F CB", "2", "Byte Swap", "486+", "reg32"},
		{"BSWAP esp[486]", "0F CC", "2", "Byte Swap", "486+", "reg32"},
		{"BSWAP ebp[486]", "0F CD", "2", "Byte Swap", "486+", "reg32"},
		{"BSWAP esi[486]", "0F CE", "2", "Byte Swap", "486+", "reg32"},
		{"BSWAP edi[486]", "0F CF", "2", "Byte Swap", "486+", "reg32"},

		{"BT rmw,ib[386]", "0F BA /4 d0 d1 i0", "4~6", "Bit Test", "386+", "dest,src"},
		{"BT rmw,rw[386]", "0F A3 mr d0 d1", "3~5", "Bit Test", "386+", "dest,src"},
		{"BTC rmw,ib[386]", "0F BA /7 d0 d1 i0", "4~6", "Bit Test with Compliment", "386+", "dest,src"},
		{"BTC rmw,rw[386]", "0F BB mr d0 d1", "3~5", "Bit Test with Compliment", "386+", "dest,src"},
		{"BTR rmw,ib[386]", "0F BA /6 d0 d1 i0", "4~6", "Bit Test with Reset", "386+", "dest,src"},
		{"BTR rmw,rw[386]", "0F B3 mr d0 d1", "3~5", "Bit Test with Reset", "386+", "dest,src"},
		{"BTS rmw,ib[386]", "0F BA /5 d0 d1 i0", "4~6", "Bit Test and Set", "386+", "dest,src"},
		{"BTS rmw,rw[386]", "0F AB mr d0 d1", "3~5", "Bit Test and Set", "386+", "dest,src"},

		{"CALL np", "E8 o0 o1", "3", "Procedure Call", "", "destination"},
		{"CALL rw", "FF /2 d0 d1", "2~4", "Procedure Call", "", "destination"},
		{"CALL DWORD PTR[rw]", "FF /3 d0 d1", "2~4", "Procedure Call", "", "destination"},
		{"CALL FAR PTR fp", "9A o0 01 s1 sh", "5", "Procedure Call", "", "destination"},

		{"CBW", "98", "1", "Convert Byte to Word", "", ""},
		{"CDQ [32bit]", "66|99", "1+1", "Convert Double to Quad", "386+", ""},

		{"CLC", "F8", "1", "Clear Carry", "", ""},
		{"CDC", "FC", "1", "Clear Direction Flag", "", ""},
		{"CLI", "FA", "1", "Clear Interruption Flag (Disable Interrupt)", "", ""},
		{"CLTS [286]", "0F 06", "2", "Clear Task Switched Flag", "286+ privileged", ""},
		{"CMC", "F5", "1", "Complement Carry Flag", "", ""},

		{"CMP AL,ib", "3C i0", "2", "Compare", "", "dest,src"},
		{"CMP AX,iw", "3D i0 i1", "3", "Compare", "", "dest,src"},
		{"CMP rb,rmb", "3A mr d0 d1", "2~4", "Compare", "", "dest,src"},
		{"CMP rw,rmw", "3B mr d0 d1", "2~4", "Compare", "", "dest,src"},
		{"CMP rmb,ib", "80 /7 d0 d1 i0", "3~5", "Compare", "", "dest,src"},
		{"CMP rmw,iw", "81 /7 d0 d1 i0 i1", "4~6", "Compare", "", "dest,src"},
		{"CMP rmw,ib", "83 /7 d0 d1 i0", "3~5", "Compare", "", "dest,src"},
		{"CMP rmb,rb", "38 mr d0 d1", "2~4", "Compare", "", "dest,src"},
		{"CMP rmw,rw", "39 mr d0 d1", "2~4", "Compare", "", "dest,src"},

		{"CMPSB", "A6", "1", "Compare String (Byte)", "", "dest,src"},
		{"CMPSW", "A7", "1", "Compare String (Word)", "", ""},
		{"CMPSD [32bit]", "66|A7", "1+1", "Compare String (Doubleword)", "386+", ""},

		{"CMPXCHG rmb,rb[486]", "0F A6 mr d0 d1", "3~5", "Compare and Exchange", "486+", "dest,src"},
		{"CMPXCHG rmw,rw[486]", "0F A7 mr d0 d1", "3~5", "Compare and Exchange", "486+", "dest,src"},
		{"CMPXCHG rmb,rb[486]", "0F B0 mr d0 d1", "3~5", "Compare and Exchange", "486+", "dest,src"},
		{"CMPXCHG rmw,rw[486]", "0F B1 mr d0 d1", "3~5", "Compare and Exchange", "486+", "dest,src"},
		{"CMPXCHG8B rmq,rd[P5]", "0F C7 mr d0 d1", "3~5", "Compare and Exchange", "P5+", "dest,src"},

		{"CWD", "99", "1", "Convert Word to Doubleword", "", ""},
		{"CWDE [32bit]", "66|98", "1+1", "Convert Word to Extended Doubleword", "", ""},

		{"DAA", "27", "1", "Decimal Adjust for Addition", "", ""},
		{"DAS", "2F", "1", "Decimal Adjust for Subtraction", "", ""},

		{"DEC AX", "48", "1", "Decrement", "", "dest"},
		{"DEC BP", "4C", "1", "Decrement", "", "dest"},
		{"DEC BX", "4A", "1", "Decrement", "", "dest"},
		{"DEC CX", "49", "1", "Decrement", "", "dest"}, // so far they look exactly the same.
		{"DEC DI", "4F", "1", "Decrement", "", "dest"},
		{"DEC DX", "49", "1", "Decrement", "", "dest"}, // so far they look exactly the same.
		{"DEC rmb", "FE /1 d0 d1", "2~4", "Decrement", "", "dest"},
		{"DEC rmw", "FF /1 d0 d1", "2~4", "Decrement", "", "dest"},
		{"DEC SI", "4D", "1", "Decrement", "", "dest"},
		{"DEC SP", "4B", "1", "Decrement", "", "dest"},

		{"DIV rmb", "F6 /6 d0 d1", "2~4", "Divide", "", "src"},
		{"DIV rmw", "F7 /6 d0 d1", "2~4", "Divide", "", "src"},

		{"ENTER iw,ib[186]", "C8 i0 i1 i0", "4", "Make Stack Frame", "80188+", "locals,level"},

		// Floating point instructions (without full name, system version requirement, usage description)
		{"F2XM1", "D9 F0", "2", "", "", ""},
		{"FABS", "D9 E1", "2", "", "", ""},
		{"FADD", "DE C1", "2", "", "", ""},
		{"FADD mdr", "D8 /0 d0 d1", "2~4", "", "", ""},
		{"FADD mqr", "DC /0 d0 d1", "2~4", "", "", ""},
		{"FADD st(i),st", "DC C0+i", "2", "", "", ""},
		{"FADD st,st(i)", "D8 C0+i", "2", "", "", ""},
		{"FADDP st(i),st", "DE C0+i", "2", "", "", ""},
		{"FBLD mtr", "DF /4 d0 d1", "2~4", "", "", ""},
		{"FBSTP mtr", "DF /6 d0 d1", "2~4", "", "", ""},
		{"FCHS", "D9 E0", "2", "", "", ""},
		{"FCLEX", "9B DB E2", "3", "", "", ""},
		{"FCOM", "D8 D1", "2", "", "", ""},
		{"FCOM mdr", "D8 /2 d0 d1", "2~4", "", "", ""},
		{"FCOM mqr", "DC /2 d0 d1", "2~4", "", "", ""},
		{"FCOM st(i)", "D8 D0+i", "2", "", "", ""},
		{"FCOMP", "D8 D9", "2", "", "", ""},
		{"FCOMP mdr", "D8 /3 d0 d1", "2~4", "", "", ""},
		{"FCOMP mqr", "DC /3 d0 d1", "2~4", "", "", ""},
		{"FCOMP st(i)", "D8 D8+i", "2", "", "", ""},
		{"FCOMPP", "DE D9", "2", "", "", ""},
		{"FCOS [387]", "D9 FF", "2", "", "", ""},
		{"FDECSTP", "D9 F6", "2", "", "", ""},
		{"FDISI", "9B DB E1", "3", "", "", ""},
		{"FDIV mdr", "D8 /6 d0 d1", "2~4", "", "", ""},
		{"FDIV mqr", "DC /6 d0 d1", "2~4", "", "", ""},
		{"FDIV st(i),st", "DC F8+i", "2", "", "", ""},
		{"FDIV st,st(i)", "DC F0+i", "2", "", "", ""},
		{"FDIVP", "DE F9", "2", "", "", ""},
		{"FDIVP st(i),st", "DE F8+i", "2", "", "", ""},
		{"FDIVR mdr", "D8 /7 d0 d1", "2~4", "", "", ""},
		{"FDIVR mqr", "DC /7 d0 d1", "2~4", "", "", ""},
		{"FDIVR st(i),st", "DC F0+i", "2", "", "", ""},
		{"FDIVR st,st(i)", "DC F8+i", "2", "", "", ""},
		{"FDIVRP", "DE F1", "2", "", "", ""},
		{"FDIVRP st(i),st", "DE F0+i", "2", "", "", ""},
		{"FENI", "9B DB E0", "3", "", "", ""},
		{"FFREE st(i)", "DD C0+i", "2", "", "", ""},
		{"FIADD mw", "DE /0 d0 d1", "2~4", "", "", ""},
		{"FIADD md", "DA /0 d0 d1", "2~4", "", "", ""},
		{"FICOM mdr", "DE /2 d0 d1", "2~4", "", "", ""},
		{"FICOM mqr", "DA /2 d0 d1", "2~4", "", "", ""},
		{"FICOMP md", "DE /3 d0 d1", "2~4", "", "", ""},
		{"FICOMP mq", "DA /3 d0 d1", "2~4", "", "", ""},
		{"FIDIV mw", "DE /6 d0 d1", "2~4", "", "", ""},
		{"FIDIV md", "DA /6 d0 d1", "2~4", "", "", ""},
		{"FIDIVR mw", "DE /7 d0 d1", "2~4", "", "", ""},
		{"FIDIVR md", "DA /7 d0 d1", "2~4", "", "", ""},
		{"FILD mw", "DF /0 d0 d1", "2~4", "", "", ""},
		{"FILD md", "DB /0 d0 d1", "2~4", "", "", ""},
		{"FILD mq", "DF /5 d0 d1", "2~4", "", "", ""},
		{"FIMUL mw", "DE /1 d0 d1", "2~4", "", "", ""},
		{"FIMUL md", "DA /1 d0 d1", "2~4", "", "", ""},
		{"FINCSTP", "D9 F7", "2", "", "", ""},
		{"FINIT", "9B DB E3", "3", "", "", ""},
		{"FIST mw", "DF /2 d0 d1", "2~4", "", "", ""},
		{"FIST md", "DB /2 d0 d1", "2~4", "", "", ""},
		{"FISTP mw", "DF /3 d0 d1", "2~4", "", "", ""},
		{"FISTP md", "DB /3 d0 d1", "2~4", "", "", ""},
		{"FISTP mq", "DF /7 d0 d1", "2~4", "", "", ""},
		{"FISUB mw", "DE /4 d0 d1", "2~4", "", "", ""},
		{"FISUB md", "DA /4 d0 d1", "2~4", "", "", ""},
		{"FISUBR mw", "DE /5 d0 d1", "2~4", "", "", ""},
		{"FISUBR md", "DA /5 d0 d1", "2~4", "", "", ""},
		{"FLD mdr", "D9 /0 d0 d1", "2~4", "", "", ""},
		{"FLD mqr", "DD /0 d0 d1", "2~4", "", "", ""},
		{"FLD mtr", "DB /5 d0 d1", "2~4", "", "", ""},
		{"FLD st(i)", "D9 C0+i", "2", "", "", ""},
		{"FLD1", "D9 E8", "2", "", "", ""},
		{"FLDCW mw", "D9 /5 d0 d1", "2~4", "", "", ""},
		{"FLDENV m14", "D9 /4 d0 d1", "2~4", "", "", ""},
		{"FLDL2E", "D9 EA", "2", "", "", ""},
		{"FLDL2T", "D9 E9", "2", "", "", ""},
		{"FLDLG2", "D9 EC", "2", "", "", ""},
		{"FLDLN2", "D9 ED", "2", "", "", ""},
		{"FLDPI", "D9 EB", "2", "", "", ""},
		{"FLDZ", "D9 EE", "2", "", "", ""},
		{"FMUL", "DE C9", "2", "", "", ""},
		{"FMUL mdr", "D8 /1 d0 d1", "2~4", "", "", ""},
		{"FMUL mqr", "DC /1 d0 d1", "2~4", "", "", ""},
		{"FMUL st(i),st", "DC C8+i", "2", "", "", ""},
		{"FMUL st,st(i)", "D8 C8+i", "2", "", "", ""},
		{"FMULP st(i),st", "DE C8+i", "2", "", "", ""},
		{"FNCLEX", "DB E2", "2", "", "", ""},
		{"FNDISI", "DB E1", "2", "", "", ""},
		{"FNENI", "DB E0", "2", "", "", ""},
		{"FNINIT", "DB E3", "2", "", "", ""},
		{"FNOP", "D9 D0", "2", "", "", ""},
		{"FNSAVE m94", "DD /6 d0 d1", "2~4", "", "", ""},
		{"FNSTCW mw", "D9 /7 d0 d1", "2~4", "", "", ""},
		{"FNSTENV m14", "D9 /6 d0 d1", "2~4", "", "", ""},
		{"FNSTSW ax", "DF E0", "2", "", "", ""},
		{"FNSTSW mw", "DD /7 d0 d1", "2~4", "", "", ""},
		{"FPATAN", "D9 F3", "2", "", "", ""},
		{"FPREM", "D9 F8", "2", "", "", ""},
		{"FPREM1 [387]", "D9 F5", "2", "", "", ""},
		{"FPTAN", "D9 F2", "2", "", "", ""},
		{"FRNDINT", "D9 FC", "2", "", "", ""},
		{"FRSTOR m94", "DD /4 d0 d1", "2~4", "", "", ""},
		{"FSAVE m94", "9B DD /6 d0 d1", "3~5", "", "", ""},
		{"FSCALE", "D9 FD", "2", "", "", ""},
		{"FSETPM", "DB E4", "2", "", "", ""},
		{"FSIN [387]", "D9 FE", "2", "", "", ""},
		{"FSINCOS [387]", "D9 FB", "2", "", "", ""},
		{"FSQRT", "D9 FA", "2", "", "", ""},
		{"FST mdr", "D9 /2 d0 d1", "2~4", "", "", ""},
		{"FST mqr", "DD /2 d0 d1", "2~4", "", "", ""},
		{"FST st(i)", "DD D0+i", "2", "", "", ""},
		{"FSTCW mw", "9B D9 /7 d0 d1", "3~5", "", "", ""},
		{"FSTENV m14", "9B D9 /6 d0 d1", "3~5", "", "", ""},
		{"FSTP mdr", "D9 /3 d0 d1", "2~4", "", "", ""},
		{"FSTP mqr", "DD /3 d0 d1", "2~4", "", "", ""},
		{"FSTP mtr", "DB /7 d0 d1", "2~4", "", "", ""},
		{"FSTP st(i)", "DD D8+i", "2", "", "", ""},
		{"FSTSW ax", "9B DF E0", "3", "", "", ""},
		{"FSTSW mw", "9B DD /7 d0 d1", "3~5", "", "", ""},
		{"FSUB mdr", "D8 /4 d0 d1", "2~4", "", "", ""},
		{"FSUB mqr", "DC /4 d0 d1", "2~4", "", "", ""},
		{"FSUB st(i),st", "DC E8+i", "2", "", "", ""},
		{"FSUB st,st(i)", "D8 E0+i", "2", "", "", ""},
		{"FSUBP", "DE E9", "2", "", "", ""},
		{"FSUBP st(i),st", "DE E8+i", "2", "", "", ""},
		{"FSUBR", "DE E1", "2", "", "", ""},
		{"FSUBR mdr", "D8 /5 d0 d1", "2~4", "", "", ""},
		{"FSUBR mqr", "DC /5 d0 d1", "2~4", "", "", ""},
		{"FSUBR st(i),st", "DC E0+i", "2", "", "", ""},
		{"FSUBR st,st(i)", "D8 E8+i", "2", "", "", ""},
		{"FSUBRP st(i),st", "DE E0+i", "2", "", "", ""},
		{"FTST", "D9 E4", "2", "", "", ""},
		{"FUCOM [387]", "DD E1", "2", "", "", ""},
		{"FUCOM st(i) [387]", "DD E0+i", "2", "", "", ""},
		{"FUCOMP st(i) [387]", "DD E8+i", "2", "", "", ""},
		{"FUCOMPP [387]", "DA E9", "2", "", "", ""},
		{"FXAM", "D9 E5", "2", "", "", ""},
		{"FXCH", "D9 C9", "2", "", "", ""},
		{"FXCH st(i)", "D9 C8+i", "2", "", "", ""},
		{"FXTRACT", "D9 F4", "2", "", "", ""},
		{"FYL2X", "D9 F1", "2", "", "", ""},
		{"FYL2XP1", "D9 F9", "2", "", "", ""},

		{"ESC", "?", "2", "Escape", "", "immed,src"},
		{"HLT", "F4", "1", "Halt CPU", "", ""},

		{"IDIV rmb", "F6 /7 d0 d1", "2~4", "Signed Integer Division", "", "src"},
		{"IDIV rmw", "F7 /7 d0 d1", "2~4", "Signed Integer Division", "", "src"},

		{"IMUL rb,rmb[386]", "0F AF mr d0 d1", "3~5", "Signed Multiply", "", "src; src,immed[286+]; dest,src,immed[286+]; dest,src[386+]"},
		{"IMUL rd,ib", "6B mr i0", "3", "Signed Multiply", "", "src; src,immed[286+]; dest,src,immed[286+]; dest,src[386+]"},
		{"IMUL rd,id", "69 mr i0 i1 i2 i3", "6", "Signed Multiply", "", "src; src,immed[286+]; dest,src,immed[286+]; dest,src[386+]"},
		{"IMUL rd,rmb,ib", "6B mr d0 d1 i0", "3~5", "Signed Multiply", "", "src; src,immed[286+]; dest,src,immed[286+]; dest,src[386+]"},
		{"IMUL rd,rmb,id", "69 mr d0 d1 i0~i3", "6~8", "Signed Multiply", "", "src; src,immed[286+]; dest,src,immed[286+]; dest,src[386+]"},
		{"IMUL rmb", "F6 /5 d0 d1", "2~4", "Signed Multiply", "", "src; src,immed[286+]; dest,src,immed[286+]; dest,src[386+]"},
		{"IMUL rmw", "F7 /5 d0 d1", "2~4", "Signed Multiply", "", "src; src,immed[286+]; dest,src,immed[286+]; dest,src[386+]"},
		{"IMUL rw,ib", "6B mr i0", "3", "Signed Multiply", "", "src; src,immed[286+]; dest,src,immed[286+]; dest,src[386+]"},
		{"IMUL rw,iw", "69 mr i0 i1", "4", "Signed Multiply", "", "src; src,immed[286+]; dest,src,immed[286+]; dest,src[386+]"},
		{"IMUL rw,rmw[386]", "0F AF mr d0 d1", "3~5", "Signed Multiply", "", "src; src,immed[286+]; dest,src,immed[286+]; dest,src[386+]"},
		{"IMUL rw,rmw,ib", "6B mr d0 d1 i0", "3~5", "Signed Multiply", "", "src; src,immed[286+]; dest,src,immed[286+]; dest,src[386+]"},
		{"IMUL rw,rmw,iw", "69 mr d0 d1 i0 i1", "4~6", "Signed Multiply", "", "src; src,immed[286+]; dest,src,immed[286+]; dest,src[386+]"},

		{"IN AL,ib", "E4 i0", "2", "Input Byte or Word From Port", "", "accum,port"},
		{"IN AL,DX", "EC", "1", "Input Byte or Word From Port", "", "accum,port"},
		{"IN AX,ib", "E5 i0", "2", "Input Byte or Word From Port", "", "accum,port"},
		{"IN AX,DX", "ED", "1", "Input Byte or Word From Port", "", "accum,port"},

		{"INC AX", "40", "1", "Increment", "", "dest"},
		{"INC BX", "41", "1", "Increment", "", "dest"},
		{"INC CX", "42", "1", "Increment", "", "dest"},
		{"INC DX", "43", "1", "Increment", "", "dest"},
		{"INC SP", "44", "1", "Increment", "", "dest"},
		{"INC BP", "45", "1", "Increment", "", "dest"},
		{"INC SI", "46", "1", "Increment", "", "dest"},
		{"INC DI", "47", "1", "Increment", "", "dest"},
		{"INC rmb", "FE /0 d0 d1", "2~4", "Increment", "", "dest"},
		{"INC rmw", "FF /0 d0 d1", "2~4", "Increment", "", "dest"},

		{"INSB [186]", "6C", "1", "Input String from Port", "80188+", ""},
		{"INSB [186]", "6D", "1", "Input String from Port", "80188+", ""},
		{"INSB [32bit]", "66|6D", "1+1", "Input String from Port", "80188+", "386+"},
		// INT~IRET
		{"", "", "", "", "", ""},
		{"", "", "", "", "", ""},
		{"", "", "", "", "", ""},
		{"", "", "", "", "", ""},
		{"", "", "", "", "", ""},
		{"", "", "", "", "", ""},
	}
	for i := 0; i < len(data); i++ {
		tmp_instruction := newInstruction(data[i][0], data[i][1], data[i][2], data[i][3], data[i][4], data[i][5])
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
	name         string
	opcodeString []string
	modrm        byte
	sib          byte
	displacement byte
	immediate    byte
	length       [2]int
}

func newInstruction(mne, op, length, name, version, usage string) (in *instruction) {
	// verify version

	// if version fails, do not continue

	in = new(instruction)
	in.mnemonics = mne
	in.name = name

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
