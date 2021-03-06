// Symbols generated by assembly code. Can be viewed in the Symbol table in AssemblyResults
package sic_xe_assembler;

public class Symbol {

    String data;
    String name;
    String type="";
    String address;
    int size;
    
    public Symbol(String name, String data, String type,String address)
    {
        this.name = name;
        this.data = data;
        this.type = type;
        this.address = address;
    }
    
    public Symbol(String name, int size, String type,String address)
    {
        this.name = name;
        this.size = size;
        this.type = type;
        this.address = address;
    }
    
    public Symbol(String name, String address)
    {
        this.name = name;
        this.address = address;
    }
}
