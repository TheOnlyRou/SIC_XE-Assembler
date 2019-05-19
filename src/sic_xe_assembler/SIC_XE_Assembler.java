package sic_xe_assembler;

import static java.awt.SystemColor.text;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
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
    static ArrayList objectCode = new ArrayList();
    private String PC;
    private String startAddress;
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
    String programName ="";
    String text ="";

    
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
        ass.registers.add("T");
        ass.registers.add("X");
        ass.registers.add("F");
        ass.editor.run();
        String text ="";
        System.out.println(text);
               
    }
         public String writetext(){
      
      String first = instructions.get(0).address;    
      String last = (String) instructions.get(instructions.size()-1).address;
      int diff  = (Integer.parseInt(first,16)) -(Integer.parseInt(last,16));
      String length = Integer.toHexString(diff);
      text = text+"H^"+programName+"^"+instructions.get(1).address+"^"+length+"\n";
      // address and size to be confirmed later by if conditions,no comments allowed//
       int i = 1;
      
      String Tot=instructions.get(instructions.size()-1).address;
      int num = Integer.parseInt("" + Tot, 16);
      
      while(i < 12000){
          String sidetext ="";
          int len = 0;
          
            
              
            int c = 0; 
              while(c<30){
                  
                  if((instructions.get(i).opcode.equals("RESW")) ||(instructions.get(i).opcode.equals("RESB"))){
                    break;
                }
                  if(instructions.get(i).opcode.equals("END"))
                      break;
                  sidetext+="^"+(String) objectCode.get(i);
             if(instructions.get(i).opcode.equals("WORD") ){
                      c = c+3;}
             else if(instructions.get(i).opcode.equals("BYTE")) {
                      String temp = instructions.get(i-1).opcode;
                      double byt1;
                      int byt;
                      if(temp.charAt(0) == 'C'){
                          byt = temp.length() - 3;
                          
                      }
                      else{
                          byt1 = (temp.length() -3)/2.0;
                          byt = (int) Math.ceil(byt1);
                      }     
                      c = c + byt;
             }
             else {
                      String func = instructions.get(i).opcode;
                      char[] funcarr = func.toCharArray();
                      //format 4
                      if(funcarr[0] == '+'){
                          
                          
                          c = c+ 4;
                          
                      }
                      else if(instructions.get(i).opcode.equals("ADDR") || instructions.get(i).opcode.equals("SUBR") || instructions.get(i).opcode.equals("RMO") || instructions.get(i).opcode.equals("COMR") || instructions.get(i).opcode.equals("TIXR")){
                              c = c + 2;
                              
                          }
                          else if(instructions.get(i).opcode.equals("LDA") ||instructions.get(i).opcode.equals("LDX") ||instructions.get(i).opcode.equals("LDS") ||
                                  instructions.get(i).opcode.equals("LDT") ||instructions.get(i).opcode.equals("LDF") ||instructions.get(i).opcode.equals("STA") ||
                                  instructions.get(i).opcode.equals("STX") ||instructions.get(i).opcode.equals("STS") ||instructions.get(i).opcode.equals("STT") ||
                                  instructions.get(i).opcode.equals("STF") ||instructions.get(i).opcode.equals("LDCH") ||instructions.get(i).opcode.equals("STCH") ||
                                  instructions.get(i).opcode.equals("ADD") ||instructions.get(i).opcode.equals("SUB") ||instructions.get(i).opcode.equals("COMP") ||
                                  instructions.get(i).opcode.equals("J") ||instructions.get(i).opcode.equals("JEQ") ||instructions.get(i).opcode.equals("JLT") ||
                                  instructions.get(i).opcode.equals("JGT") ||instructions.get(i).opcode.equals("TIX")){
                              c = c + 3;
                          }
                          else{
                              c = c + 1;
                              
                          }
                          
                      }      
                      len = c;
                      
              }
               
            i++;  
      
             
              if(c != 0){
              text+= "T^" ; 
              String tlength = Integer.toHexString(c);
              
              text+=tlength+sidetext;
               
              text+="\n";
              }
              i++;
          }
            text+="E^"+instructions.get(0).address;
            return text;

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
            else if(!instructions.get(i).opcode.equals("") && !instructions.get(i).format2.contains(instructions.get(i).opcode) &&
                    !instructions.get(i).format3.contains(instructions.get(i).opcode) &&
                    !instructions.get(i).format4.contains(instructions.get(i).opcode) && !dir.directives.contains(instructions.get(i).opcode) && !instructions.get(i).opcode.equals("END"))
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
            else if(instructions.get(i).format2.contains(instructions.get(i).opcode)){
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
                if(!instructions.get(i).label.equals(""))
                    programName = instructions.get(i).label;
                for(int j = 0; j <= i; j++)
                {
                    instructions.get(j).address = instructions.get(i).operand1;
                }
                PC = instructions.get(i).operand1;
                startAddress = PC;
            }
            else if(!instructions.get(i).comment.equals("")){
                
            }                
            else if(instructions.get(i).opcode.equals("+ADD")){
                instructions.get(i).address=PC;
                incrementPC(4);
                instructions.get(i).object="18";
            }
            else if(instructions.get(i).opcode.equals("ADD")){
                instructions.get(i).address=PC;
                incrementPC(3);
                instructions.get(i).object="18";
            }
            else if(instructions.get(i).opcode.equals("+SUB")){
                instructions.get(i).address=PC;
                incrementPC(4);
                instructions.get(i).object="1C";
            }
            else if(instructions.get(i).opcode.equals("SUB")){
                instructions.get(i).address=PC;
                incrementPC(3);
                instructions.get(i).object="1C";
            }
            else if(instructions.get(i).opcode.equals("LDCH")){
                instructions.get(i).address=PC;
                incrementPC(3);
                instructions.get(i).object="50";
            }
            else if(instructions.get(i).opcode.equals("LDA")){
                instructions.get(i).address=PC;
                incrementPC(3);
                instructions.get(i).object="00";
            }
            else if(instructions.get(i).opcode.equals("LDS")){
                instructions.get(i).address=PC;
                incrementPC(3);
                instructions.get(i).object="6C";
            }
            else if(instructions.get(i).opcode.equals("LDB")){
                instructions.get(i).address=PC;
                incrementPC(3);
                instructions.get(i).object="68";
            }
            else if(instructions.get(i).opcode.equals("LDT")){
                instructions.get(i).address=PC;
                incrementPC(3);
                instructions.get(i).object="74";
            }
            else if(instructions.get(i).opcode.equals("LDL")){
                instructions.get(i).address=PC;
                incrementPC(3);
                instructions.get(i).object="08";
            }
            else if(instructions.get(i).opcode.equals("LDX")){
                instructions.get(i).address=PC;
                incrementPC(3);
                instructions.get(i).object="04";
            }            
            else if(instructions.get(i).opcode.equals("STCH")){
                instructions.get(i).address=PC;
                incrementPC(3);
                instructions.get(i).object="54";
            }
            else if(instructions.get(i).opcode.equals("STA")){
                instructions.get(i).address=PC;
                incrementPC(3);
                instructions.get(i).object="0C";
            }
            else if(instructions.get(i).opcode.equals("STS")){
                instructions.get(i).address=PC;
                incrementPC(3);
                instructions.get(i).object="7C";
            }
            else if(instructions.get(i).opcode.equals("STB")){
                instructions.get(i).address=PC;
                incrementPC(3);
                instructions.get(i).object="78";
            }
            else if(instructions.get(i).opcode.equals("STT")){
                instructions.get(i).address=PC;
                incrementPC(3);
                instructions.get(i).object="84";
            }
            else if(instructions.get(i).opcode.equals("STL")){
                instructions.get(i).address=PC;
                incrementPC(3);
                instructions.get(i).object="14";
            }
            else if(instructions.get(i).opcode.equals("COMP")){
                instructions.get(i).address=PC;
                incrementPC(3);
                instructions.get(i).object="28";
            }
            else if(instructions.get(i).opcode.equals("TIX")){                
                instructions.get(i).address=PC;
                incrementPC(3);
                instructions.get(i).object="2C";
            }
            else if(instructions.get(i).opcode.equals("+LDCH")){
                instructions.get(i).address=PC;
                incrementPC(4);
                instructions.get(i).object="50";
            }
            else if(instructions.get(i).opcode.equals("+LDA")){
                instructions.get(i).address=PC;
                incrementPC(4);
                instructions.get(i).object="00";
            }
            else if(instructions.get(i).opcode.equals("+LDS")){
                instructions.get(i).address=PC;
                incrementPC(4);
                instructions.get(i).object="6C";
            }
            else if(instructions.get(i).opcode.equals("+LDB")){
                instructions.get(i).address=PC;
                incrementPC(4);
                instructions.get(i).object="68";
            }
            else if(instructions.get(i).opcode.equals("+LDT")){
                instructions.get(i).address=PC;
                incrementPC(4);
                instructions.get(i).object="74";
            }
            else if(instructions.get(i).opcode.equals("+LDL")){
                instructions.get(i).address=PC;
                incrementPC(4);
                instructions.get(i).object="08";
            }
            else if(instructions.get(i).opcode.equals("+LDX")){
                instructions.get(i).address=PC;
                incrementPC(4);
                instructions.get(i).object="04";
            }              
            else if(instructions.get(i).opcode.equals("+STCH")){
                instructions.get(i).address=PC;
                incrementPC(4);
                instructions.get(i).object="54";
            }
            else if(instructions.get(i).opcode.equals("+STA")){
                instructions.get(i).address=PC;
                incrementPC(4);
                instructions.get(i).object="0C";
            }
            else if(instructions.get(i).opcode.equals("+STS")){
                instructions.get(i).address=PC;
                incrementPC(4);
                instructions.get(i).object="7C";
            }
            else if(instructions.get(i).opcode.equals("+STB")){
                instructions.get(i).address=PC;
                incrementPC(4);
                instructions.get(i).object="78";
            }
            else if(instructions.get(i).opcode.equals("+STT")){
                instructions.get(i).address=PC;
                incrementPC(4);
                instructions.get(i).object="84";
            }
            else if(instructions.get(i).opcode.equals("+STL")){
                instructions.get(i).address=PC;
                incrementPC(4);
                instructions.get(i).object="14";
            }
            else if(instructions.get(i).opcode.equals("+COMP")){
                instructions.get(i).address=PC;
                incrementPC(4);
                instructions.get(i).object="28";
            }
            else if(instructions.get(i).opcode.equals("+TIX")){
                instructions.get(i).address=PC;
                incrementPC(4);
                instructions.get(i).object="2C";
            }
            else if(instructions.get(i).opcode.equals("JEQ")){
                instructions.get(i).address=PC;
                incrementPC(3);
                instructions.get(i).object="30";
            }
            else if(instructions.get(i).opcode.equals("+JEQ")){
                instructions.get(i).address=PC;
                incrementPC(4);
                instructions.get(i).object="30";
            }
            else if(instructions.get(i).opcode.equals("+J")){
                instructions.get(i).address=PC;
                incrementPC(4);
                instructions.get(i).object="3C";
            }
            else if(instructions.get(i).opcode.equals("J")){
                instructions.get(i).address=PC;
                incrementPC(3);
                instructions.get(i).object="3C";
            }
            else if(instructions.get(i).opcode.equals("+JLT")){
                instructions.get(i).address=PC;
                incrementPC(4);  
                instructions.get(i).object="38";
            }
            else if(instructions.get(i).opcode.equals("JLT")){
                instructions.get(i).address=PC;
                incrementPC(3);
                instructions.get(i).object="38";
            }
            else if(instructions.get(i).opcode.equals("+JGT")){
                instructions.get(i).address=PC;
                incrementPC(4); 
                instructions.get(i).object="34";
            }
            else if(instructions.get(i).opcode.equals("JGT")){
                instructions.get(i).address=PC;
                incrementPC(3); 
                instructions.get(i).object="34";
            }
            else if(instructions.get(i).opcode.equals("RMO")){
                instructions.get(i).address=PC;
                incrementPC(2);
                instructions.get(i).object="AC";
            }
            else if(instructions.get(i).opcode.equals("SUBR")){
                instructions.get(i).address=PC;
                incrementPC(2);
                instructions.get(i).object="94";
            }
            else if(instructions.get(i).opcode.equals("ADDR")){
                instructions.get(i).address=PC;
                incrementPC(2);
                instructions.get(i).object="90";
            }
            else if(instructions.get(i).opcode.equals("COMR")){                
                instructions.get(i).address=PC;
                incrementPC(2);
                instructions.get(i).object="A0";
            }
            else if(instructions.get(i).opcode.equals("TIXR")){
                
                instructions.get(i).address=PC;
                incrementPC(2);
                instructions.get(i).object="B8";
            }
            else if(instructions.get(i).opcode.equals("EQU")){
                //address calculation here
                
                boolean newsymbol = true;
                for(int j=0;j<symbols.size();j++){
                    if(symbols.get(j).name.equals(instructions.get(i).label)){
                        newsymbol= false;
                    }
                }
                if(newsymbol)
                    symbols.add(new Symbol(instructions.get(i).label,instructions.get(i).operand1,"EQU",instructions.get(i).address));
                else
                    instructions.get(i).Error = "ERROR: LABEL " + instructions.get(i).label + " WAS ALREADY DEFINED";
                instructions.get(i).address=PC;
                if(instructions.get(i).operand1.startsWith("X") || instructions.get(i).operand1.startsWith("C")){
                    int c = instructions.get(i).operand1.length();
                    c=c-3;
                    incrementPC(c);
                }
            }
            else if(instructions.get(i).opcode.equals("BYTE")){                
                instructions.get(i).address=PC;
                if(instructions.get(i).operand1.startsWith("X") || instructions.get(i).operand1.startsWith("C")){
                    int c = instructions.get(i).operand1.length();
                    c=c-3;
                    incrementPC(c);
                }
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
    
    private void detectUndefinedSymbols()
    {
        for(int i=0; i<instructions.size();i++)
        {
            if(!instructions.get(i).operand1.equals("") && !instructions.get(i).opcode.equals("START"))
            {   
                boolean defined1 = false;                
                if(!registers.contains(instructions.get(i).operand1) && !instructions.get(i).operand1.startsWith("C'")
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
        objectCode.add("");
        boolean ERROR=false;
        for (Instruction instruction : instructions) {
            if(!instruction.Error.equals(""))
                ERROR=true;
        }
        if(ASSEMBLED && !ERROR)
        {
            generateNIXBPE();
            
        }
        else
        {
            if(ERROR)
                this.editor.displayError("Assembly incomplete", "Please fix the code errors and try again");                
            else if(!ASSEMBLED)
                this.editor.displayError("Assembly incomplete", "Please assemble a file and try again");
        } 
        writetext();
        System.out.println(text);
    }
    
    private void generateNIXBPE()
    {
        for(Instruction instruction: instructions)
        {
            if(instruction.opcode.startsWith("+"))
            {
                instruction.e = true;
            }        
            if(instruction.operand1.startsWith("@"))
            {
                instruction.n = true;
                instruction.i = false;
                instruction.x = false;
            }
            else if(instruction.operand1.startsWith("#"))
            {                
                instruction.n = false;
                instruction.i = true;
                instruction.x = false;
            }
            else if(!instruction.operand2.equals(""))
            {
                instruction.n = true;
                instruction.i = true;
                instruction.x = true;
            }
            else{
                instruction.n = true;
                instruction.i = true;
                instruction.x = false;
            }
            
            char[] testLabel = instruction.operand1.toCharArray();
            if(testLabel[0]=='#' || testLabel[0]=='@')
            {
                testLabel[0]=' ';                
            }
            String test = Arrays.toString(testLabel);
            test = test.trim();
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
                nextAddress = findNextAddress(instruction.address);                
                int result = Integer.parseInt(nextAddress,16) - Integer.parseInt(address,16);
                if(result >= -2048 && result <=2047)
                {
                    instruction.p = true;
                    instruction.b = false;
                    generateOpcode(result);
                }
                else if(result<=4095)
                {
                    instruction.p = false;
                    instruction.b = true;
                    generateOpcode(result);
                }
                else
                    System.out.println("DISPLACEMENT OUT OF BOUNDS");               
            }
            else
            {
                int result = Integer.parseInt(test);  
                instruction.b = false;
                instruction.p = false;
                generateOpcode(result);
            }
            
            
        }
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
                return instructions.get(i+1).address;
            }
        }
        return null;
    }
    private String generateOpcode(int disp){
         for(int i=0; i<instructions.size();i++){
             if(instructions.get(i).opcode.equals("ADDR")||instructions.get(i).opcode.equals("SUBR")||instructions.get(i).opcode.equals("COMR")||instructions.get(i).opcode.equals("RMO")||instructions.get(i).opcode.equals("TIXR"))
             {
                 int j=0;
                 String opcode1 = instructions.get(i).object;
                 char r1=' ',r2=' ';
                switch(instructions.get(i).operand1){
                     case "A":
                         r1='0';
                         break;
                     case "B":
                         r1='3';
                         break;
                     case "S":
                         r1='4';
                         break;
                     case "T":
                         r1='5';
                         break;
                      case "L":
                         r1='2';
                         break;
                      case "X":
                         r1='1';
                          break;
                          
                 }
                 switch(instructions.get(i).operand2){
                     case "A":
                         r2='0';
                         break;
                     case "B":
                         r2='3';
                         break;
                     case "S":
                         r2='4';
                         break;
                     case "T":
                         r2='5';
                         break;
                      case "L":
                         r2='2';
                         break;
                      case "X":
                         r2='1';
                         break;
                          
                 }
                 
                 String fin = opcode1+r1;
                 fin=fin+r2;
                 objectCode.add(fin) ;
                 return fin ;
                 }
             else if(instructions.get(i).opcode.startsWith("+")){
                 String opcode1=instructions.get(i).object;
                 String bin = hexToBinary(opcode1);
                 char[] bin1 = bin.toCharArray();
                 char[] bin2 = new char[6];
                 for(int j=0;j<6;j++){
                     bin2[j]=bin1[j];
                 }
                 String opcode2=Arrays.toString(bin2);
                 if(instructions.get(i).n){
                     opcode2=opcode2+'1';
                 }
                 else
                     opcode2=opcode2+'0';
                 if(instructions.get(i).i)
                     opcode2=opcode2+'1';
                 else
                     opcode2=opcode2+'0';
                 if(instructions.get(i).x)
                     opcode2=opcode2+'1';
                 else
                     opcode2=opcode2+'0';
                 if(instructions.get(i).b)
                     opcode2=opcode2+'1';
                 else
                     opcode2=opcode2+'0';
                 if(instructions.get(i).p)
                     opcode2=opcode2+'1';
                 else
                     opcode2=opcode2+'0';
                 if(instructions.get(i).e)
                     opcode2=opcode2+'1';
                 else
                     opcode2=opcode2+'0';
                 
                 String ad = (String)instructions.get(i).address ;
                 int foo= Integer.parseInt(ad,16);
                 String addr = Integer.toHexString(0x100000 | foo).substring(1);
                 String adr =hexToBinary(addr);
                 opcode2 += adr;
                 String fin = binToHex (opcode2);
                 objectCode.add(fin);
               
             }
             else{
                 String opcode1=instructions.get(i).object;
                 String bin = hexToBinary(opcode1);
                 char[] bin1 = bin.toCharArray();
                 char[] bin2 = new char[6];
                 for(int j=0;j<6;j++){
                     bin2[j]=bin1[j];
                 }
                 String opcode2=Arrays.toString(bin2);
                 if(instructions.get(i).n){
                     opcode2=opcode2+'1';
                 }
                 else
                     opcode2=opcode2+'0';
                 if(instructions.get(i).i)
                     opcode2=opcode2+'1';
                 else
                     opcode2=opcode2+'0';
                 if(instructions.get(i).x)
                     opcode2=opcode2+'1';
                 else
                     opcode2=opcode2+'0';
                 if(instructions.get(i).b)
                     opcode2=opcode2+'1';
                 else
                     opcode2=opcode2+'0';
                 if(instructions.get(i).p)
                     opcode2=opcode2+'1';
                 else
                     opcode2=opcode2+'0';
                 if(instructions.get(i).e)
                     opcode2=opcode2+'1';
                 else
                     opcode2=opcode2+'0';
                 //String di = Integer.toHexString(i);//
                 String dispo = Integer.toHexString(0x1000 | disp).substring(1);
                 String dispa =hexToBinary(dispo);
                 opcode2+= dispa;
                 String fin = binToHex(opcode2);
                 objectCode.add (fin);
             }
                 
         }
        String fin ="";
        return fin;
    }
    public static String hexToBinary(String hex) {
    int len = hex.length() * 4;
    String bin = new BigInteger(hex, 16).toString(2);

    //left pad the string result with 0s if converting to BigInteger removes them.
    if(bin.length() < len){
        int diff = len - bin.length();
        String pad = "";
        for(int i = 0; i < diff; ++i){
            pad = pad.concat("0");
        }
        bin = pad.concat(bin);
    }
    return bin;
}
    public static String binToHex(String bin){
        int decimal = Integer.parseInt(bin,2);
        String hexStr = Integer.toString(decimal,16);
        return hexStr;
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
        this.programName="";
        
    }
}