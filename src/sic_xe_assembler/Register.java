package sic_xe_assembler;


public class Register {
    int size;
    int data;
    public Register(int size)
    {
        this.size = size;
    }
    
    public void setData(int data)
    {
        this.data = data;
    }
    
    public int getData()
    {
        return this.data;
    }
}
