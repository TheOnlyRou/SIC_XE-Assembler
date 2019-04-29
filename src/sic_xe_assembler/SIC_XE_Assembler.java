package sic_xe_assembler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;


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
    
    int count= 0;
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
        System.out.print(Arrays.toString(result));
        if(result[0].startsWith("."))
        {
            inst[count++].comment = Arrays.toString(result);
        }
        else
        {
            switch(result.length)
            {
                case 2:
                    inst[count].opcode = result[0];
                    inst[count++].operand1 = result[1];
                    break;
                case 3:
                    inst[count].label = result[0];
                    inst[count].opcode = result[1];
                    inst[count++].operand1 = result[2];
                    break;
                case 4:
                    inst[count].opcode = result[0];
                    inst[count].operand1 = result[1];
                    // comma takes result[2]
                    inst[count++].operand2 = result[3];
                    break;
                case 5:
                    inst[count].label = result[0];
                    inst[count].opcode = result[1];
                    inst[count].operand1 = result[2];
                    inst[count++].operand2 = result[4];
                    break;
            }
        }
    }
    
    private void testInstructions()
    {
        for(int i=0; i<count; i++)
        {
            String test = "";
            if(inst[i].comment!=null)
                test = inst[i].comment;
            if(inst[i].label!=null)
                test = test + "\t" + inst[i].label;
            if(inst[i].opcode!=null)
                test = test + "\t" + inst[i].opcode;
            if(inst[i].operand1!=null)
                test = test + "\t" + inst[i].operand1;
            if(inst[i].operand2!=null)
                test = test + "\t" + inst[i].operand2;            
            System.out.println(test);
        }
    }
}
