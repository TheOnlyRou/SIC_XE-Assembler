package sic_xe_assembler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;


public class SIC_XE_Assembler {

    File f;
    Editor editor = new Editor(this);
    ArrayList<Instruction> instructions = new ArrayList<Instruction>();
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
    
    public void Assemble() throws FileNotFoundException, IOException
    {
        BufferedReader br = new BufferedReader(new FileReader(f)); 
        String st; 
        System.out.println(f.getAbsolutePath());
        while ((st = br.readLine()) != null) 
        {
            String line = st.toUpperCase();
            System.out.println(line);
            stringToInstruction(line);
        }
        testInstructions();
    }
    
    private void stringToInstruction(String text)
    {
        String[] result = text.split("\\s");
        for(int i =0; i<result.length;i++)
        {
            System.out.println(i + "\t" + result[i]);
        }
        if(result[0].startsWith("."))
        {
            instructions.add(new Instruction(text));
        }
        else
        {
            switch(result.length)
            {
                case 2:
                    instructions.add(new Instruction(result[0],result[1]));
                    break;
                case 3:
                    instructions.add(new Instruction(result[0],result[1],result[2],true));
                    break;
                case 4:
                    instructions.add(new Instruction(result[0],result[1],result[3],false));
                    break;
                case 5:
                    instructions.add(new Instruction(result[0],result[1],result[2],result[4]));
                    break;
            }
        }
    }
    
    private void testInstructions()
    {
        for(int i=0; i<instructions.size(); i++)
        {
            String test = "";
            if(instructions.get(i).comment!=null)
                test = instructions.get(i).comment;
            if(instructions.get(i)!=null)
                test = test + "\t" + instructions.get(i).label;
            if(instructions.get(i)!=null)
                test = test + "\t" + instructions.get(i).opcode;
            if(instructions.get(i)!=null)
                test = test + "\t" + instructions.get(i).operand1;
            if(instructions.get(i)!=null)
                test = test + "\t" + instructions.get(i).operand2;            
            System.out.println(test);
        }
    }
}
