; start compilation
; 1 errors

; code
      jmp,L016
      jmp,L002
L002: ict,2
      lod,1,-1
      lit,1
      opr,eq
      jpc,L009
      lit,1
      ret,1,1
L009: lod,1,-1
      lod,1,-1
      lit,1
      opr,sub
      cal,0,L002
      opr,mul
      ret,1,1
L016: ict,3
      lit,1
      sto,0,2
L019: lod,0,2
      lit,10
      opr,ls
      jpc,L034
      lod,0,2
      opr,wrt
      lod,0,2
      cal,0,L002
      opr,wrt
      opr,wrl
      lod,0,2
      lit,1
      opr,add
      sto,0,2
      jmp,L019
L034: ret,0,0
