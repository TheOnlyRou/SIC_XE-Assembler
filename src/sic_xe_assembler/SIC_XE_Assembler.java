package sic_xe_assembler;

import java.io.File;


public class SIC_XE_Assembler {

    File f;
    Editor editor = new Editor(this);
    Instruction[] inst = new Instruction[100];
    private String PC;
    private int[] memory = new int[1024];
    public final int BYTE = 8;
    public final int WORD = 24;
    Register A = new Register(24);
    Register X = new Register(24);
    Register S = new Register(24);
    Register T = new Register(24);
    Register F = new Register(48);
   
    public boolean START = false;
    public boolean END = false;
    public boolean ORG = false;
    public boolean STARTDEC = false;
    public boolean ENDDEC = false;
            
    public static void main(String[] args) 
    {
        SIC_XE_Assembler ass = new SIC_XE_Assembler();
        ass.editor.run();
    }
    
    public void incrementPC(int bytes)
    {
        int decimal = Integer.parseInt(PC, 16);
        decimal +=bytes;
        PC = Integer.toHexString(decimal);
    }
}
