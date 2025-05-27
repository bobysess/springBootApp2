package com.bobysess.springBootApp2;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class MathTest {
    
    @Test
    void mathTest () {
        assertEquals(7,  4 + 3, "Addition");
        assertEquals(1,  4 - 3, "Substraction");        
        assertEquals(12,  4 * 3, "Multiplication");        
        assertEquals(2,  8 / 4, "Division");        
    }
}
