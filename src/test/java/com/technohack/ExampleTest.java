package com.technohack;

import org.junit.jupiter.api.*;
import java.util.concurrent.TimeUnit;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExampleTest {

   private static String name;

   @BeforeAll
   public static void setName(){
       System.out.println("Inside of set name");
       name="harsh";
   }

    @Test
    @Timeout(value=10, unit= TimeUnit.MILLISECONDS)      //  @Timeout annotation to set a maximum execution time in seconds
    public void testName(){
        System.out.println("inside of test name method");
        assertEquals("harsh", name);
    }

    @AfterAll
    public static void unsetValue(){
       System.out.println("Inside unset-value");
       name="";
       System.out.println("Name is ::"+name);
    }

}
