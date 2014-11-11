START:
	lit,2      ; push 2.
	lit,3      ; push 3.
	opr,add    ; pop two constants 2 and 3, and push the value of '2+3'.
	opr,odd	   ; if the stack top is odd, push 1(true). Otherwise, push 0(false).
	jpc,EVEN   ; jump to EVEN if the stack top is 0(false).
	lit,0	   ; push 0.
	jmp,OUTPUT ; jump to OUTPUT.
EVEN:
	lit,1      ; push 1.
OUTPUT:	
	opr,wrt    ; pop a value and output the value.
	opr,wrl    ; output a new line code.
	jmp,START  ; jump to the address 0 to terminate this program.
