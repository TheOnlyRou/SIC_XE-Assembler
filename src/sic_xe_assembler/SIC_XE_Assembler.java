package sic_xe_assembler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


public class SIC_XE_Assembler {

    File f;
    Editor editor = new Editor(this);
    Directive dir = new Directive();
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
    Set<Character> hex = new HashSet<Character>();
            
    public static void main(String[] args) 
    {
        SIC_XE_Assembler ass = new SIC_XE_Assembler();
        ass.hex.add('A');
        ass.hex.add('B');
        ass.hex.add('C');
        ass.hex.add('D');
        ass.hex.add('E');
        ass.hex.add('F');
        ass.hex.add('1');
        ass.hex.add('2');
        ass.hex.add('3');
        ass.hex.add('4');
        ass.hex.add('5');
        ass.hex.add('6');
        ass.hex.add('7');
        ass.hex.add('8');
        ass.hex.add('9');
        ass.hex.add('0');
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
        START = false;
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
        detectErrors();
        analyseInstructions();
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
            instructions.add(new Instruction(text,true));
        }
        else
        {
            switch(result.length)
            {
                case 1:
                    instructions.add(new Instruction(result[0],false));
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
    
    private void detectErrors()
    {
        for(int i = 0; i < instructions.size(); i++)
        {
            if(instructions.get(i).opcode.equals("START") || instructions.get(i).opcode.equals("ORG"))
            {
                START = true;
                char[] test = instructions.get(i).operand1.toCharArray();
                for(int j = 0; j<test.length;j++)
                {
                    if(!hex.contains(test[j]))
                    {
                        instructions.get(i).Error = "ERROR: ADDRESS IS NOT HEXADECIMAL";
                    }
                }
                instructions.get(i).address = instructions.get(i).operand1;
            }
            else if(instructions.get(i).comment.isEmpty() && !START)
            {
                instructions.get(i).Error = "ERROR: MISSING START STATEMENT";
            }
            else if(!instructions.get(i).opcode.isEmpty() && !instructions.get(i).format2.contains(instructions.get(i).opcode) &&
                    !instructions.get(i).format3.contains(instructions.get(i).opcode) &&
                    !instructions.get(i).format4.contains(instructions.get(i).opcode))
            {
                instructions.get(i).Error = "ERROR: UNRECOGNIZED OPERATION CODE";
            }
            else if(instructions.get(i).opcode.equals("+TIXR") || instructions.get(i).opcode.equals("+COMR") || instructions.get(i).opcode.equals("+SUBR") ||
                    instructions.get(i).opcode.equals("+RMO"))
            {
                instructions.get(i).Error = "ERROR: INSTRUCTION CAN'T BE FORMAT 4";
            }
            else if(instructions.get(i).opcode.equals("S") || instructions.get(i).opcode.equals("A") || instructions.get(i).opcode.equals("T") || instructions.get(i).opcode.equals("F")
                    || instructions.get(i).opcode.equals("X") || instructions.get(i).opcode.startsWith("C'") || instructions.get(i).opcode.startsWith("X'")
                    || instructions.get(i).opcode.startsWith("*") || instructions.get(i).opcode.startsWith("@") || instructions.get(i).opcode.startsWith("#"))
            {
                instructions.get(i).Error = "ERROR: MISSING OR MISPLACD OPERATION MNEMONIC";
            }
            else if(dir.directives.contains(instructions.get(i).opcode) && instructions.get(i).label.isEmpty())
            {
                instructions.get(i).Error = "ERROR: DIRECTIVE IS MISSING A LABEL";
            }
            else if(instructions.get(i).opcode.equals("RESW") || instructions.get(i).opcode.equals("RESB"))
            {
                try {
                        double d = Double.parseDouble(instructions.get(i).operand1);
                    } catch (NumberFormatException | NullPointerException nfe) {
                           instructions.get(i).Error = "ERROR: NON NUMERIC OPERAND";
                    }
            }
            else if(!instructions.get(instructions.size()-1).opcode.equals("END"))
            {
                   instructions.get(i).Error = "ERROR: MISSING END STATEMENT";
            }
            else if(instructions.get(i).operand1.startsWith("+") || instructions.get(i).operand2.startsWith("+"))
            {
                   instructions.get(i).Error = "ERROR: WRONG OPERAND PREFIX";
            }            
        }
    }
    
    private void analyseInstructions()
    {
        for(int i = 0; i < instructions.size();i++)
        {
            if(instructions.get(i).opcode.equals("START"))
            {
                for(int j = 0; j <= i; j++)
                {
                    instructions.get(j).address = instructions.get(i).operand1;
                }
                PC = instructions.get(i).operand1;
            }
            
        }
    }
}