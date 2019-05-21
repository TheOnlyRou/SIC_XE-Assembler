/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sic_xe_assembler;

import java.util.ArrayList;

/**
 *
 * @author user
 */
public class ObjectCode {
    
    public ArrayList<ObjectInstruction> objinst = new ArrayList<ObjectInstruction>();
    
    public static String[][] opcodes = {{"ADD","18"} , {"ADDR","90"} , {"COMP","28"} , {"COMR","A0"},
                          {"J","3C"} , {"JEQ","30"} , {"JGT","34"} , {"JLT","38"},
                          {"LDA","00"} , {"LDB","68"} , {"LDCH","50"} , {"LDL","08"},
                          {"LDS","6C"} , {"LDT", "74"} , {"LDX","04"} , {"RMO","AC"},
                          {"STA","0C"} , {"STB", "78"} , {"STCH","54"} , {"STL","14"},
                          {"STS","7C"} , {"STT", "84"} , {"STX","10"} , {"SUB","1C"},
                          {"SUBR","94"} , {"TIX", "2C"} , {"TIXR","B8"}
                          };    
    
    public class ObjectInstruction{
        String address;
        String opcode;
        String NIXBPE;
        String operand;
        String result;
        int format;
        
        public ObjectInstruction(String opcode, String NIXBPE, String operand, String address)
        {
            //format3,4
            this.address = address;
            this.opcode = opcode;
            this.NIXBPE = NIXBPE;
            this.operand = operand;
        }     
        
        public ObjectInstruction(String opcode, String operand, String address)
        {
            //format2
            this.address = address;
            this.opcode = opcode;
            this.operand = operand;
        }      
        
        public ObjectInstruction(String operand, String address)
        {
            //directives
            this.address = address;
            this.operand = operand;
        }          
        
        public ObjectInstruction(String result, String address, int format)
        {
            
            this.result = result;
            this.address = address;
            this.format = format;
        }         
    }
    
    
    public void addObjectInstruction(String opcode,String NIXBPE,String operand, String address)
    {
        objinst.add(new ObjectInstruction(opcode,NIXBPE,operand,address));
    }
    
    public void addObjectInstruction(String result, String address, int format)
    {
        objinst.add(new ObjectInstruction(result,address,format));
    }
    
    public void addObjectInstruction(String opcode,String operand,String address)
    {
        objinst.add(new ObjectInstruction(opcode,operand,address));
    }
    
    public void addObjectInstruction(String opcode, String address)
    {
        objinst.add(new ObjectInstruction(opcode,address));
    }
    
}
