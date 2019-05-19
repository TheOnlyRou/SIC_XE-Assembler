/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sic_xe_assembler;

import static org.testng.Assert.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author HP
 */
public class SIC_XE_AssemblerNGTest {
    
    public SIC_XE_AssemblerNGTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }

    /**
     * Test of main method, of class SIC_XE_Assembler.
     */
    @Test
    public void testMain() {
        System.out.println("main");
        String[] args = null;
        SIC_XE_Assembler.main(args);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of incrementPC method, of class SIC_XE_Assembler.
     */
    @Test
    public void testIncrementPC() {
        System.out.println("incrementPC");
        int bytes = 0;
        SIC_XE_Assembler instance = new SIC_XE_Assembler();
        instance.incrementPC(bytes);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of Assemble method, of class SIC_XE_Assembler.
     */
    @Test
    public void testAssemble() throws Exception {
        System.out.println("Assemble");
        SIC_XE_Assembler instance = new SIC_XE_Assembler();
        instance.Assemble();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of simulate method, of class SIC_XE_Assembler.
     */
    @Test
    public void testSimulate() {
        System.out.println("simulate");
        SIC_XE_Assembler instance = new SIC_XE_Assembler();
        instance.simulate();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of hexToBinary method, of class SIC_XE_Assembler.
     */
    @Test
    public void testHexToBinary() {
        System.out.println("hexToBinary");
        String hex = "";
        String expResult = "";
        String result = SIC_XE_Assembler.hexToBinary(hex);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of binToHex method, of class SIC_XE_Assembler.
     */
    @Test
    public void testBinToHex() {
        System.out.println("binToHex");
        String bin = "";
        String expResult = "";
        String result = SIC_XE_Assembler.binToHex(bin);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of newFile method, of class SIC_XE_Assembler.
     */
    @Test
    public void testNewFile() {
        System.out.println("newFile");
        SIC_XE_Assembler instance = new SIC_XE_Assembler();
        instance.newFile();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");}
        
        @Test
    public void testwritetext() {
        System.out.println("text");
        SIC_XE_Assembler instance = new SIC_XE_Assembler();
        instance.writetext();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
