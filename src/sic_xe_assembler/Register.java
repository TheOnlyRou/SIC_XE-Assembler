package sic_xe_assembler;


public class Register {
    int size;
    public final int BYTE = 8;
    public final int WORD = 24;
    Register A = new Register(24);
    Register X = new Register(24);
    Register S = new Register(24);
    Register T = new Register(24);
    Register F = new Register(48);
    
    public Register(int size)
    {
        this.size = size;
    }
}
