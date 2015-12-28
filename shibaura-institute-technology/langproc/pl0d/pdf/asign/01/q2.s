start:
	lit,7     ; push 7.
	lit,2     ; push 2.
	lit,5     ; push 5.
	lit,1     ; push 1.
	opr,sub   ; pop two constants 5 and 1, and push the value of '5-1'.
	opr,mul   ; pop two constants 2 and (5-1), and push the value of '2*4'.
	opr,add   ; pop two constants 7 and (2*4), and push the value of '7+8'.
	opr,wrt   ; pop a value and output the value.
	opr,wrl   ; output a new line code.
	jmp,start ; jump to the address 0 to terminate this program.
