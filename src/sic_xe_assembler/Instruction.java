package sic_xe_assembler;

public class Instruction {
    
    String opcode = "";
    String operand1 = "";
    String operand2 = "";
    String address = "";
    String label = "";
    String comment = "";
    String Error = "";
    
    public Instruction(String comment,boolean type)
    {
        if(type)
            this.comment = comment;
        else
            this.opcode = comment;
    }
 
    public Instruction(String opcode, String op1)
    {
        this.opcode = opcode;
        this.operand1 = op1;
    }
    
    public Instruction(String label, String opcode, String op1, boolean type)
    {
        if(type)
        {
            this.label = label;
            this.opcode = opcode;
            this.operand1 = op1;
        }
        else
        {
            this.opcode = label;
            this.operand1 = opcode;
            this.operand2 = op1;
        }
    }
    
    public Instruction(String label, String opcode, String op1, String op2)
    {
        this.label = label;
        this.opcode = opcode;
        this.operand1 = op1;
        this.operand2 = op2;
    }
    
    public enum Op_format2 {RMO, SUBR, TIXR, COMR};
    public enum Op_format3 {LDA, LDX, LDS, LDT, LDF, STA, STX, STD, STT, STS, 
                             STF, LDCH, STCH, ADD, SUB, ADDR, COMP, J, JEQ, JLT, 
                             JGT, TIX, TIXR};
    public enum Op_format4 { LDA, LDX, LDS, LDT, LDF, STA, STX, STD, STT, STS, 
                             STF, LDCH, STCH, ADD, SUB, ADDR, COMP, J, JEQ, JLT, 
                             JGT, TIX, TIXR};
}
