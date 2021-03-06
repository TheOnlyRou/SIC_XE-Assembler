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
    ArrayList<Symbol> symbols = new ArrayList<Symbol>();
    private String PC;
    private String startAddress;
    private String programname;
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
    public boolean ASSEMBLED = false;
    Set<Character> hex = new HashSet<Character>();
    Set<String> registers = new HashSet<String>();
    ObjectCode objcode = new ObjectCode();
    
    
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
        ass.registers.add("A");
        ass.registers.add("S");
        ass.registers.add("B");
        ass.registers.add("L");
        ass.registers.add("T");
        ass.registers.add("X");
        ass.registers.add("F");
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
        ASSEMBLED = true;
        PC = "";
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
        detectUndefinedSymbols();
        AssemblyResults display = new AssemblyResults(this);
        display.run();
        display.displayResults(instructions, symbols);
        simulate();        
    }
    
    private void stringToInstruction(String text)
    {
        String[] result = text.split("\\s+");
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
    
    public boolean isNumeric(String test)
    {            
        char[] testarr = test.toCharArray();
        for(int j = 0; j<testarr.length;j++)
        {
            if(!Character.isDigit(testarr[j]))
            {
                return false; 
            }
        }    
        return true;
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
                if(instructions.get(i).Error.equals(""))
                    instructions.get(i).address = instructions.get(i).operand1;
                else
                    instructions.get(i).address = "1000";
            }
            else if(instructions.get(i).comment.equals("") && !START)
            {
                instructions.get(i).Error = "ERROR: MISSING START STATEMENT";
                instructions.get(i).address = "1000";
                START = true;
            }
            else if(instructions.get(i).opcode.startsWith("#") || instructions.get(i).opcode.startsWith("%") ||
                    instructions.get(i).opcode.startsWith("*") || instructions.get(i).opcode.startsWith("$")
                    || instructions.get(i).opcode.startsWith("@"))
            {
                instructions.get(i).Error = "ERROR: WRONG OPERATION PREFIX";
            }
             
             else if(instructions.get(i).opcode.equals("S") || instructions.get(i).opcode.equals("A") || instructions.get(i).opcode.equals("T") || instructions.get(i).opcode.equals("F")
                    || instructions.get(i).opcode.equals("X") || instructions.get(i).opcode.startsWith("C'") || instructions.get(i).opcode.startsWith("X'")
                    || instructions.get(i).opcode.startsWith("*") || instructions.get(i).opcode.startsWith("@") || instructions.get(i).opcode.startsWith("#"))
            {
                instructions.get(i).Error = "ERROR: MISSING OR MISPLACD OPERATION MNEMONIC";
            }
             else if(instructions.get(i).opcode.equals("+TIXR") || instructions.get(i).opcode.equals("+COMR") || instructions.get(i).opcode.equals("+SUBR") ||
                    instructions.get(i).opcode.equals("+RMO"))
            {
                instructions.get(i).Error = "ERROR: INSTRUCTION CAN'T BE FORMAT 4";
            }
            else if(!instructions.get(i).opcode.equals("") && !Instruction.format2.contains(instructions.get(i).opcode) &&
                    !Instruction.format3.contains(instructions.get(i).opcode) &&
                    !Instruction.format4.contains(instructions.get(i).opcode) && !dir.directives.contains(instructions.get(i).opcode) && !instructions.get(i).opcode.equals("END"))
            {
                instructions.get(i).Error = "ERROR: UNRECOGNIZED OPERATION CODE";
            }
            
            else if(dir.directives.contains(instructions.get(i).opcode) && instructions.get(i).label.equals(""))
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
            else if(instructions.get(i).operand1.startsWith("+") || instructions.get(i).operand2.startsWith("+"))
            {
                instructions.get(i).Error = "ERROR: WRONG OPERAND PREFIX";
            } 
            else if((instructions.get(i).format3.contains(instructions.get(i).opcode) || instructions.get(i).format4.contains(instructions.get(i).opcode)))
            {
                if(!instructions.get(i).operand2.equals(""))
                {
                    instructions.get(i).Error = "ERROR: THIS OPERATION ACCEPTS ONLY 1 OPERAND";
                }
            }           
            else if(Instruction.format2.contains(instructions.get(i).opcode)){
                if (!registers.contains(instructions.get(i).operand1))
                {
                    instructions.get(i).Error = "ERROR: OPERAND 1 IS NOT A REGISTER";
                }
                if (!registers.contains(instructions.get(i).operand2) && !instructions.get(i).operand2.equals("") && !instructions.get(i).opcode.equals("TIXR"))
                {
                    instructions.get(i).Error = "ERROR: OPERAND 2 IS NOT A REGISTER";
                }
            }
            else if (instructions.get(i).opcode.equals("ADDR") || instructions.get(i).opcode.equals("SUBR") || instructions.get(i).opcode.equals("RMO") || instructions.get(i).opcode.equals("COMR"))
            {
                 if(instructions.get(i).operand2.equals("")){
                     
                    instructions.get(i).Error = "ERROR: OPERAND 2 MISSING";
                 }
            }
        }
        if(!instructions.get(instructions.size()-1).opcode.equals("END"))
        {
            instructions.get(instructions.size()-1).Error = "ERROR: MISSING END STATEMENT";
        }
    }
    
    private void analyseInstructions()
    {
        boolean addfound=false;
        int count = 0;
        while(!addfound && count<instructions.size())
        {
            if(!instructions.get(count).address.equals(""))
            {
                PC = instructions.get(count).address;
                addfound = true;
            }
            count++;
        }        
        for(int i = 0; i < instructions.size();i++)
        {
            if(instructions.get(i).opcode.equals("START"))
            {
                for(int j = 0; j <= i; j++)
                {
                    instructions.get(j).address = instructions.get(i).operand1;
                }
                PC = instructions.get(i).operand1;
                programname = instructions.get(i).label;
                startAddress = PC;
            }
            else if(!instructions.get(i).comment.equals("")){
                
            }                
            else if(instructions.get(i).opcode.equals("+ADD")){
                instructions.get(i).address=PC;
                incrementPC(4);
            }
            else if(instructions.get(i).opcode.equals("ADD")){
                instructions.get(i).address=PC;
                incrementPC(3);
            }
            else if(instructions.get(i).opcode.equals("+SUB")){
                instructions.get(i).address=PC;
                incrementPC(4);
            }
            else if(instructions.get(i).opcode.equals("SUB")){
                instructions.get(i).address=PC;
                incrementPC(3);
            }
            else if(instructions.get(i).opcode.equals("LDCH")){
                instructions.get(i).address=PC;
                incrementPC(3);
            }
            else if(instructions.get(i).opcode.equals("LDA")){
                instructions.get(i).address=PC;
                incrementPC(3);
            }
            else if(instructions.get(i).opcode.equals("LDS")){
                instructions.get(i).address=PC;
                incrementPC(3);
            }
            else if(instructions.get(i).opcode.equals("LDB")){
                instructions.get(i).address=PC;
                incrementPC(3);
            }
            else if(instructions.get(i).opcode.equals("LDT")){
                instructions.get(i).address=PC;
                incrementPC(3);
            }
            else if(instructions.get(i).opcode.equals("LDF")){
                instructions.get(i).address=PC;
                incrementPC(3);
            }
            else if(instructions.get(i).opcode.equals("LDX")){
                instructions.get(i).address=PC;
                incrementPC(3);
            }            
            else if(instructions.get(i).opcode.equals("STCH")){
                instructions.get(i).address=PC;
                incrementPC(3);
            }
            else if(instructions.get(i).opcode.equals("STA")){
                instructions.get(i).address=PC;
                incrementPC(3);
            }
            else if(instructions.get(i).opcode.equals("STS")){
                instructions.get(i).address=PC;
                incrementPC(3);
            }
            else if(instructions.get(i).opcode.equals("STB")){
                instructions.get(i).address=PC;
                incrementPC(3);
            }
            else if(instructions.get(i).opcode.equals("STT")){
                instructions.get(i).address=PC;
                incrementPC(3);
            }
            else if(instructions.get(i).opcode.equals("STF")){
                instructions.get(i).address=PC;
                incrementPC(3);
            }
            else if(instructions.get(i).opcode.equals("COMP")){
                instructions.get(i).address=PC;
                incrementPC(3);
            }
            else if(instructions.get(i).opcode.equals("TIX")){                
                instructions.get(i).address=PC;
                incrementPC(3);
            }
            else if(instructions.get(i).opcode.equals("+LDCH")){
                instructions.get(i).address=PC;
                incrementPC(4);
            }
            else if(instructions.get(i).opcode.equals("+LDA")){
                instructions.get(i).address=PC;
                incrementPC(4);
            }
            else if(instructions.get(i).opcode.equals("+LDS")){
                instructions.get(i).address=PC;
                incrementPC(4);
            }
            else if(instructions.get(i).opcode.equals("+LDB")){
                instructions.get(i).address=PC;
                incrementPC(4);
            }
            else if(instructions.get(i).opcode.equals("+LDT")){
                instructions.get(i).address=PC;
                incrementPC(4);
            }
            else if(instructions.get(i).opcode.equals("+LDF")){
                instructions.get(i).address=PC;
                incrementPC(4);
            }
            else if(instructions.get(i).opcode.equals("+LDX")){
                instructions.get(i).address=PC;
                incrementPC(4);
            }              
            else if(instructions.get(i).opcode.equals("+STCH")){
                instructions.get(i).address=PC;
                incrementPC(4);
            }
            else if(instructions.get(i).opcode.equals("+STA")){
                instructions.get(i).address=PC;
                incrementPC(4);
            }
            else if(instructions.get(i).opcode.equals("+STS")){
                instructions.get(i).address=PC;
                incrementPC(4);
            }
            else if(instructions.get(i).opcode.equals("+STB")){
                instructions.get(i).address=PC;
                incrementPC(4);
            }
            else if(instructions.get(i).opcode.equals("+STT")){
                instructions.get(i).address=PC;
                incrementPC(4);
            }
            else if(instructions.get(i).opcode.equals("+STF")){
                instructions.get(i).address=PC;
                incrementPC(4);
            }
            else if(instructions.get(i).opcode.equals("+COMP")){
                instructions.get(i).address=PC;
                incrementPC(4);
            }
            else if(instructions.get(i).opcode.equals("+TIX")){
                instructions.get(i).address=PC;
                incrementPC(4);
            }
            else if(instructions.get(i).opcode.equals("JEQ")){
                instructions.get(i).address=PC;
                incrementPC(3);
            }
            else if(instructions.get(i).opcode.equals("+JEQ")){
                instructions.get(i).address=PC;
                incrementPC(4);
            }
            else if(instructions.get(i).opcode.equals("+J")){
                instructions.get(i).address=PC;
                incrementPC(4);
            }
            else if(instructions.get(i).opcode.equals("J")){
                instructions.get(i).address=PC;
                incrementPC(3);
            }
            else if(instructions.get(i).opcode.equals("+JLT")){
                instructions.get(i).address=PC;
                incrementPC(4);                
            }
            else if(instructions.get(i).opcode.equals("JLT")){
                instructions.get(i).address=PC;
                incrementPC(3);                
            }
            else if(instructions.get(i).opcode.equals("+JGT")){
                instructions.get(i).address=PC;
                incrementPC(4);                
            }
            else if(instructions.get(i).opcode.equals("JGT")){
                instructions.get(i).address=PC;
                incrementPC(3);                
            }
            else if(instructions.get(i).opcode.equals("RMO")){
                instructions.get(i).address=PC;
                incrementPC(2);
            }
            else if(instructions.get(i).opcode.equals("SUBR")){
                instructions.get(i).address=PC;
                incrementPC(2);
            }
            else if(instructions.get(i).opcode.equals("ADDR")){
                instructions.get(i).address=PC;
                incrementPC(2);
            }
            else if(instructions.get(i).opcode.equals("COMR")){                
                instructions.get(i).address=PC;
                incrementPC(2);
            }
            else if(instructions.get(i).opcode.equals("TIXR")){
                
                instructions.get(i).address=PC;
                incrementPC(2);
            }
            else if(instructions.get(i).opcode.equals("EQU")){
                //address calculation here
                
                if(isExpression(instructions.get(i).operand1))
                {
                    instructions.get(i).address=PC;
                    symbols.add(new Symbol(instructions.get(i).operand1,instructions.get(i).operand1,"EXP",instructions.get(i).address));                    
                    incrementPC(3);                    
                }
                else
                {
                    boolean newsymbol = true;
                
                    for(int j=0;j<symbols.size();j++)
                    {
                        if(symbols.get(j).name.equals(instructions.get(i).label))
                        {
                            newsymbol= false;
                        }
                    }
                    if(newsymbol)
                        symbols.add(new Symbol(instructions.get(i).label,instructions.get(i).operand1,"EQU",instructions.get(i).address));
                    else
                        instructions.get(i).Error = "ERROR: LABEL " + instructions.get(i).label + " WAS ALREADY DEFINED";
                    instructions.get(i).address=PC;
                    if(instructions.get(i).operand1.startsWith("X") || instructions.get(i).operand1.startsWith("C"))
                    {
                        int c = instructions.get(i).operand1.length();
                        c=c-3;
                        incrementPC(c);
                    }
                }
            }
            else if(instructions.get(i).opcode.equals("BYTE")){                
                instructions.get(i).address=PC;
                if(instructions.get(i).operand1.startsWith("X") || instructions.get(i).operand1.startsWith("C")){
                    int c = instructions.get(i).operand1.length();
                    c=c-3;
                    incrementPC(c);
                }
                else
                    incrementPC(1);
                boolean newsymbol = true;
                for(int j = 0; j<symbols.size();j++)
                {
                    if(symbols.get(j).name.equals(instructions.get(i).label))
                        newsymbol = false;
                }
                if(newsymbol)
                    symbols.add(new Symbol(instructions.get(i).label,instructions.get(i).operand1,"BYTE",instructions.get(i).address));
                else
                    instructions.get(i).Error = "ERROR: LABEL " + instructions.get(i).label + " WAS ALREADY DEFINED";
            }
            else if(instructions.get(i).opcode.equals("WORD")){
                instructions.get(i).address=PC;
                incrementPC(3);
                boolean newsymbol = true;
                for(int j = 0; j<symbols.size();j++)
                {
                    if(symbols.get(j).name.equals(instructions.get(i).label))
                        newsymbol = false;
                }
                if(newsymbol)
                    symbols.add(new Symbol(instructions.get(i).label,instructions.get(i).operand1,"WORD",instructions.get(i).address));
                else
                    instructions.get(i).Error = "ERROR: LABEL " + instructions.get(i).label + " WAS ALREADY DEFINED";
            }
            else if(instructions.get(i).opcode.equals("RESB")){
                try{
                    boolean newsymbol = true;
                    String s = instructions.get(i).operand1;
                    int z = Integer.parseInt(s.trim());
                    instructions.get(i).address=PC;
                    incrementPC(z);
                    for(int j = 0; j<symbols.size();j++)
                    {
                        if(symbols.get(j).name.equals(instructions.get(i).label))
                            newsymbol = false;
                    }
                    if(newsymbol)
                        symbols.add(new Symbol(instructions.get(i).label,z,"BYTE",instructions.get(i).address));
                    else
                        instructions.get(i).Error = "ERROR: LABEL " + instructions.get(i).label + " WAS ALREADY DEFINED";
                }catch(NumberFormatException nfe){ 
                    instructions.get(i).address=PC;
                    incrementPC(1);
                }                
            }
            else if(instructions.get(i).opcode.equals("RESW")){
                try{
                    boolean newsymbol = true;
                    String s = instructions.get(i).operand1;
                    int z = Integer.parseInt(s.trim());
                    z=z*3;
                    incrementPC(z);
                    instructions.get(i).address=PC;
                    for(int j = 0; j<symbols.size();j++)
                    {
                        if(symbols.get(j).name.equals(instructions.get(i).label))
                            newsymbol = false;
                    }
                    if(newsymbol)
                        symbols.add(new Symbol(instructions.get(i).label,z,"WORD",instructions.get(i).address));
                    else
                        instructions.get(i).Error = "ERROR: LABEL " + instructions.get(i).label + " WAS ALREADY DEFINED";
                }catch(NumberFormatException nfe){
                         
                }   
                instructions.get(i).address=PC;
                incrementPC(3);                
            }
            else if(instructions.get(i).opcode.equals("END"))
            { 
                System.out.println(instructions.get(i).opcode);
                if(!instructions.get(i).label.equals("")){
                    instructions.get(i).Error="ERROR: LABEL SHOULDN'T BE PRESENT";
                }
                instructions.get(i).address = startAddress;
            }
            else if(instructions.get(i).opcode.equals("ORG")){
                PC = instructions.get(i).operand1;
            
                if(!instructions.get(i).label.equals("")){
                    instructions.get(i).Error="ERROR: LABEL SHOULDN'T BE PRESENT";
                }
                instructions.get(i).address =  instructions.get(i).operand1;  
            }
            if(!instructions.get(i).label.equals(""))
            {
                boolean newsymbol=true;
                for(int j = 0; j<symbols.size();j++)
                {
                    if(symbols.get(j).name.equals(instructions.get(i).label))
                    newsymbol = false;
                }
                if(newsymbol)
                    symbols.add(new Symbol(instructions.get(i).label,instructions.get(i).address));
            }
        }
    }
    
    private boolean isExpression(String test)
    {
        if(test.endsWith("+") ||test.endsWith("-")|| test.endsWith("*")|| test.endsWith("/"))
        {
            System.out.println("3");
            return false;
        }
        char[] temp = test.toCharArray();
        if(temp.length<3)
        {
            System.out.println("4");
            return false;
        }
        if( temp[1]!='+' && temp[1]=='-' && temp[1]=='*' && temp[1]=='/')
        {
            System.out.println("5");
            return false;
        }
        String op1 = String.valueOf(temp[0]);
        String op2 = String.valueOf(temp[2]);
        boolean def1 = false;
        boolean def2 = false;
        for(int j =0; j<symbols.size();j++)
        {
            if(op1.equals(symbols.get(j).name))
                def1 = true;
        }        
        for(int j =0; j<symbols.size();j++)
        {
            if(op1.equals(symbols.get(j).name))
                def2 = true;
        }
        if(registers.contains(op1))
            def1 = true;
        if(registers.contains(op2))
            def2 = true;
        return def1 && def2;
    }
    
    private void detectUndefinedSymbols()
    {
        for(int i=0; i<instructions.size();i++)
        {
            if(!instructions.get(i).operand1.equals("") && !instructions.get(i).opcode.equals("START"))
            {   
                boolean defined1 = false;
                if(!instructions.get(i).operand1.startsWith("+") && (instructions.get(i).operand1.contains("+") || instructions.get(i).operand1.contains("-") || instructions.get(i).operand1.contains("*") || instructions.get(i).operand1.contains("/")))
                {
                    if(isExpression(instructions.get(i).operand1))
                    {
                        boolean newsym=true;
                        for(Symbol sym : symbols)
                        {
                            if(sym.name.equals(instructions.get(i).operand1))
                                newsym = false;
                        }
                        if(newsym)
                        {
                            symbols.add(new Symbol(instructions.get(i).operand1,instructions.get(i).operand1,"EXP",instructions.get(i).address));
                        }
                    }
                    else
                    {
                        System.out.println("1");
                        instructions.get(i).Error="Unsupported Expression";
                    }
                }
                else if(!registers.contains(instructions.get(i).operand1) && !instructions.get(i).operand1.startsWith("C'")
                        && !instructions.get(i).operand1.startsWith("X'") && !instructions.get(i).operand1.startsWith(Character.toString('#'))
                        && !instructions.get(i).operand1.chars().allMatch(Character::isDigit)
                        && !registers.contains(instructions.get(i).operand1)&& !instructions.get(i).operand2.startsWith("W'"))
                {
                  //  System.out.println(instructions.get(i).operand1 + " is a register: " + registers.contains(instructions.get(i).operand1));
                    for(int j =0; j<symbols.size();j++)
                    {
                        if(instructions.get(i).operand1.equals(symbols.get(j).name))
                            defined1 = true;
                    }
                    for(int j = 0; j<instructions.size();j++)
                    {
                        if(instructions.get(i).operand1.equals(instructions.get(j).label))
                            defined1 = true;
                    }
                    if(!defined1)
                    {
                        instructions.get(i).Error = "ERROR: UNDEFINED SYMBOL " + instructions.get(i).operand1;
                    }                    
                }
            }
            if(!instructions.get(i).operand2.equals("") && !instructions.get(i).opcode.equals("START"))
            {
                boolean defined2 = false;
                if(!registers.contains(instructions.get(i).operand2)
                    && !instructions.get(i).operand2.startsWith("C'") && !instructions.get(i).operand2.startsWith(Character.toString('#'))
                    && !instructions.get(i).operand2.startsWith("X'") && !instructions.get(i).operand2.chars().allMatch(Character::isDigit)
                    && !instructions.get(i).operand2.startsWith("W'"))
                {               
                    //System.out.println(instructions.get(i).operand2 + " is a register: " + registers.contains(instructions.get(i).operand2));
                    for(int j =0; j<symbols.size();j++)
                    {
                        if(instructions.get(i).operand2.equals(symbols.get(j).name))
                         defined2 = true;
                    }
                    for(int j = 0; j<instructions.size();j++)
                    {
                        if(instructions.get(i).operand2.equals(instructions.get(j).label))
                        defined2 = true;
                    }                
                    if(!defined2)
                    {
                        instructions.get(i).Error = "ERROR: UNDEFINED SYMBOL " + instructions.get(i).operand2;
                    }                    
                }
            }
        }
    }   
    
    public void simulate()
    {
        boolean ERROR=false;
        for (Instruction instruction : instructions) {
            if(!instruction.Error.equals(""))
                ERROR=true;
        }
        if(ASSEMBLED && !ERROR)
        {
            for(Instruction instruction: instructions)
            {
                if(instruction.comment.equals("") && !instruction.opcode.equals("START") 
                        && !instruction.opcode.equals("END")&& !instruction.opcode.equals("RESW") 
                        && !instruction.opcode.equals("RESB") && !instruction.opcode.equals("EQU")
                        && !instruction.opcode.equals("ORG"))
                {
                    String NIXBPE = "";
                    String opcode = generateOpcode(instruction);
                    if(!Instruction.format2.contains(instruction.opcode))
                        NIXBPE = generateNIXBPE(instruction);
                    String operand = generateOperand(instruction);
                    System.out.println(instruction.opcode + "_" + opcode + "_"  + NIXBPE+ "_"  + operand);
                    if(Instruction.format2.contains(instruction.opcode))
                    {
                        String result = opcode+operand;
                        long foo = Long.parseLong(result,2);
                        result = Long.toHexString(foo);
                        System.out.println(result);
                        result = "000000".substring(result.length()) + result;
                        objcode.addObjectInstruction(result,instruction.address.toUpperCase(),2);
                    }
                    else if(Instruction.format3.contains(instruction.opcode))
                    {
                        String result = opcode+NIXBPE+operand;
                        long foo = Long.parseLong(result,2);
                        result = Long.toHexString(foo);
                        System.out.println(result);
                        result = "000000".substring(result.length()) + result;                        
                        objcode.addObjectInstruction(result,instruction.address.toUpperCase(),3);
                    }
                    else if( Instruction.format4.contains(instruction.opcode))
                    {
                        String result = opcode+NIXBPE+operand;
                        long foo = Long.parseLong(result,2);
                        result = Long.toHexString(foo);
                        System.out.println(result);
                        result = "000000".substring(result.length()) + result;                        
                        objcode.addObjectInstruction(result,instruction.address.toUpperCase(),4);
                    }
                    else if(instruction.opcode.equals("BYTE"))
                    {
                        String result = instruction.operand1;
                        if(instruction.operand1.startsWith("X") || instruction.operand1.startsWith("C"))                        
                            result = Integer.toHexString(instruction.operand1.length()-3);
                        else if( isNumeric(result))                          
                            result = Integer.toHexString(Integer.parseInt(instruction.operand1));
                        result = "000000".substring(result.length()) + result;                        
                        objcode.addObjectInstruction(result,instruction.address,5);
                    }
                    else if(instruction.opcode.equals("WORD"))
                    {                        
                        String result = instruction.operand1;
                        if(isNumeric(result))
                        {
                            result = Integer.toHexString(Integer.parseInt(instruction.operand1));
                        }
                        else if(instruction.operand1.startsWith("X") || instruction.operand1.startsWith("C"))
                            result = Integer.toHexString(instruction.operand1.length()-3);
                        result = "000000".substring(result.length()) + result;                        
                        objcode.addObjectInstruction(result,instruction.address,5);
                    }                   
                }
            }
            ObjectCodeDisplay display = new ObjectCodeDisplay();
            display.displayResults(objcode.objinst,programname,startAddress,PC);
        }
        else
        {
            if(ERROR)
                this.editor.displayError("Assembly incomplete", "Please fix the code errors and try again");                
            else if(!ASSEMBLED)
                this.editor.displayError("Assembly incomplete", "Please assemble a file and try again");
        }   
    }
    
    private String generateOpcode(Instruction inst)
    {
        String temp;
        int type;
        if(inst.opcode.startsWith("+"))
            temp = inst.opcode.substring(1);
        else
            temp = inst.opcode;
        for(int i=0; i<ObjectCode.opcodes.length;i++)
        {
            if(temp.equals(ObjectCode.opcodes[i][0]))
            {
                int foo = Integer.parseInt(ObjectCode.opcodes[i][1],16);
                String result = Integer.toBinaryString(foo);
                result = "00000000".substring(result.length()) + result;
                if(Instruction.format3.contains(inst.opcode) || Instruction.format4.contains(inst.opcode))
                    return result.substring(2);
                else
                    return result;
            }
        }
        return "00";
    }
    
    private String generateNIXBPE(Instruction inst)
    {
        String NIXBPE;
        if(inst.operand1.startsWith("@"))
        {
            NIXBPE = "100";
        }
        else if(inst.operand1.startsWith("#"))
        {          
            NIXBPE = "101";
        }
        else if(!inst.operand2.equals(""))
        {
            NIXBPE = "111";
        }
        else{
            NIXBPE = "110";
        }
        
        String test = inst.operand1;
        if(test.startsWith("#") || test.startsWith("@"))
        {
           test = inst.operand1.substring(1);
        }
            
        boolean LABEL = false;
        char[] testarr = test.toCharArray();
        for(int j = 0; j<testarr.length;j++)
        {
            if(!Character.isDigit(testarr[j]))
            {
                LABEL = true; 
            }
        }
        String address;
        String nextAddress;
        if(LABEL)
        {
            address = findAddress(test);
            nextAddress = findNextAddress(inst.address);   
            int result = Integer.parseInt(nextAddress,16) - Integer.parseInt(address,16);
            if(result >= -2048 && result <=2047)
            {
                NIXBPE = NIXBPE + "01";
            }
            else if(result<=4095)
            {
                NIXBPE = NIXBPE + "10";
            }
            else
                System.out.println("DISPLACEMENT OUT OF BOUNDS");               
            }
        else
        {
            int result = Integer.parseInt(test);  
            NIXBPE = NIXBPE + "00";
        }
            
        if(inst.opcode.startsWith("+"))
        {
            NIXBPE = NIXBPE + "1";
        }
        else
            NIXBPE = NIXBPE + "0";
        return NIXBPE;
    }
    
    private String generateOperand(Instruction inst)
    {
        //Format 2
        if(inst.opcode.equals("TIXR"))
        {
            String r1="";
        
            switch(inst.operand1){
                case "A":
                    r1="0";
                    break;
                case "B":
                    r1="3";
                    break;
                case "S":
                    r1="4";
                    break;
                case "T":
                    r1="5";
                    break;
                case "L":
                    r1="2";
                    break;
                }   
            return r1 + "1";
        }        
        if(Instruction.format2.contains(inst.opcode)&& !inst.opcode.equals("TIXR"))
        {
            String r1="",r2="";
            switch(inst.operand1){
                case "A":
                    r1="0";
                    break;
                case "B":
                    r1="3";
                    break;
                case "S":
                    r1="4";
                    break;
                case "T":
                    r1="5";
                    break;
                case "L":
                    r1="2";
                    break;
                case "X":
                    r1="1";
                    break;                          
                }
                switch(inst.operand2){
                case "A":
                    r2="0";
                    break;
                case "B":
                    r2="3";
                    break;
                case "S":
                    r2="4";
                    break;
                case "T":
                    r2="5";
                    break;
                case "L":
                    r2="2";
                    break;
                case "X":
                    r2="1";
                    break;                       
                }
            int foo = Integer.parseInt(r1+r2,16);
            String result = Integer.toBinaryString(foo);
            result = "00000000".substring(result.length()) + result;            
            return result;
        }
        //Format 4
        else if(inst.opcode.startsWith("+")){
            String test = inst.operand1.substring(1);
            if(isNumeric(test))
            {
                int foo = Integer.parseInt(test,16);
                String result = Integer.toBinaryString(foo);
                if(result.length() > 20)
                    result = result.substring(result.length()-20);                
                result = "00000000000000000000".substring(result.length()) + result;                
                return result;
            }
            else
            {
                int foo = Integer.parseInt(findAddress(test),16);                
                String result = Integer.toBinaryString(foo);
                if(result.length() > 20)
                    result = result.substring(result.length()-20);
                result = "00000000000000000000".substring(result.length()) + result;                
                return result;
            }
        }
        //format 3
        else{
                String test = inst.operand1;
                if(inst.operand1.startsWith("#") || inst.operand1.startsWith("@"))
                     test = inst.operand1.substring(1);
                if(isNumeric(test))
                {
                    int foo = Integer.parseInt(test,16);
                    String result = Integer.toBinaryString(foo);                    
                    if(result.length() > 12)
                        result = result.substring(result.length()-12);
                    result = "000000000000".substring(result.length()) + result;                
                    return result;
                }
                else
                {
                    int foo = Integer.parseInt(findAddress(test),16);
                    String result = Integer.toBinaryString(foo);
                    if(result.length() > 12)
                        result = result.substring(result.length()-12);                    
                    result = "000000000000".substring(result.length()) + result;                
                    return result;
                }
            }        
    }
    
    private boolean isLabel(String label)
    {
        for(Symbol sym:symbols)
        {
            if(sym.name.equals(label))
            {
                return true;
            }
        }
        return false;
    }
    
    private String findAddress(String name)
    {
        for(Symbol sym:symbols)
        {
            if(sym.name.equals(name))
            {
                return sym.address;
            }
        }
        return null;
    }
    
    private String findNextAddress(String address)
    {
        for(int i=0; i<instructions.size();i++)
        {
            if(instructions.get(i).address.equals(address))
            {
                int j=i+1;
                while(!instructions.get(j).comment.equals(""))
                    j++;
                return instructions.get(j).address;
            }
        }
        return null;
    }
    
    public void newFile()
    {
        this.f = null;
        this.ASSEMBLED = false;
        this.START = false;
        this.instructions = new ArrayList<Instruction>();
        this.symbols = new ArrayList<Symbol>();
        this.PC = null;
        this.startAddress = null;
    }
}