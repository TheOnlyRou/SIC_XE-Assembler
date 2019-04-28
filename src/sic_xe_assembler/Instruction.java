package sic_xe_assembler;

public class Instruction {
    
    String opcode;
    String operand1;
    String operand2;
    String address;
    String label;
    
    public enum Op_format2 {RMO, SUBR, TIXR, COMR};
    public enum Op_format3 {LDA, LDX, LDS, LDT, LDF, STA, STX, STD, STT, STS, 
                             STF, LDCH, STCH, ADD, SUB, ADDR, COMP, J, JEQ, JLT, 
                             JGT, TIX, TIXR};
    public enum Op_format4 { LDA, LDX, LDS, LDT, LDF, STA, STX, STD, STT, STS, 
                             STF, LDCH, STCH, ADD, SUB, ADDR, COMP, J, JEQ, JLT, 
                             JGT, TIX, TIXR};
    public char comment = '.';
    
    
}
