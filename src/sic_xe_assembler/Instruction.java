package sic_xe_assembler;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Instruction {
    
    String opcode = "";
    String operand1 = "";
    String operand2 = "";
    String address = "";
    String label = "";
    String comment = "";
    String Error = "";
    String object = "";
    
    private static String[] arr2 = {"RMO", "SUBR", "TIXR", "COMR", "ADDR"};
    private static String[] arr3 = {"LDA", "LDX", "LDS", "LDT", "LDF", "STA", "STX", "STS", "STT", "STF", 
                             "LDCH", "STCH", "ADD", "SUB", "COMP", "J", "JEQ", "JLT", 
                             "JGT", "TIX", "TIXR"};
    private static String[] arr4 = {"+LDA", "+LDX", "+LDS", "+LDT", "+LDF", "+STA", "+STX", "+STS", "+STT", "+STF", 
                             "+LDCH", "+STCH", "+ADD", "+SUB", "+COMP", "+J", "+JEQ", "+JLT", 
                             "+JGT", "+TIX", "+TIXR"};
    
    static Set<String> format2 = new HashSet<>(Arrays.asList(arr2));
    static Set<String> format3 = new HashSet<>(Arrays.asList(arr3));
    static Set<String> format4 = new HashSet<>(Arrays.asList(arr4));
    
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
}
