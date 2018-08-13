package com.example.sennevervaecke.mystable;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by sennevervaecke on 8/13/2018.
 */
public class HelperTest {
    //first to upper
    @Test
    public void firstToUpperTestAllLower(){
        assertEquals("Jeno", Helper.firstToUpper("jeno"));
    }
    @Test
    public void firstToUpperTestMix(){
        assertEquals("JeNo", Helper.firstToUpper("jeNo"));
    }
    @Test
    public void firstToUpperTestSingle(){
        assertEquals("J", Helper.firstToUpper("j"));
    }
    @Test
    public void firstToUpperTestEmpty(){
        assertEquals("", Helper.firstToUpper(""));
    }
    @Test
    public void firstToUpperTestNumber(){
        assertEquals("27", Helper.firstToUpper("27"));
    }
    @Test
    public void firstToUpperTestSymbols(){
        assertEquals("(:", Helper.firstToUpper("(:"));
    }
    //title case
    @Test
    public void titleCaseTestAllLower(){
        assertEquals("Jeno Het Paard", Helper.toTitleCase("jeno het paard"));
    }
    @Test
    public void titleCaseTestAllUpper(){
        assertEquals("Jeno Het Paard", Helper.toTitleCase("JENO HET PAARD"));
    }
    @Test
    public void titleCaseTestMix(){
        assertEquals("Jeno Het Paard", Helper.toTitleCase("jEnO HeT PaArD"));
    }
    @Test
    public void titleCaseTestSingle(){
        assertEquals("J", Helper.toTitleCase("j"));
    }
    @Test
    public void titleCaseTestEmpty(){
        assertEquals("", Helper.toTitleCase(""));
    }
    @Test
    public void titleCaseTestNumber(){
        assertEquals("27", Helper.toTitleCase("27"));
    }
    @Test
    public void titleCaseTestSymbols(){
        assertEquals("(:", Helper.toTitleCase("(:"));
    }

}