package sic_xe_assembler;

public class Instructions {
    public enum Op_format2 {RMO, SUBR, TIXR, COMR};
    public enum Op_format34 {LDA, LDX, LDS, LDT, LDF, STA, STX, STD, STT, STS, 
                             STF, LDCH, STCH, ADD, SUB, ADDR, COMP, J, JEQ, JLT, 
                             JGT, TIX, TIXR};
    public char comment = '.';
}
