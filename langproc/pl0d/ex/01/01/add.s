start:
	lit,2     ; push 2.
	lit,3     ; push 3.
	opr,add   ; pop two constants 2 and 3, and push the value of '2+3'.
	opr,wrt   ; pop a value and output the value.
	opr,wrl   ; output a new line code.
	jmp,start ; jump to the address 0 to terminate this program.
