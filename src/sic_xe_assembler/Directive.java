package sic_xe_assembler;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Directive {
            
    public String[] dir = {"BYTE", "WORD", "RESW", "RESB", "EQU", "ORG"};
    Set<String> directives = new HashSet<>(Arrays.asList(dir));
}
