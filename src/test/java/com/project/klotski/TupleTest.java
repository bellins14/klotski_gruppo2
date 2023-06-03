package com.project.klotski;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TupleTest {
    private Tuple tuple;

    @Test
    void getXZero() {
        System.out.println("test getXZero");
        tuple = new Tuple(0,0);
        assertEquals(0, tuple.getX());
    }

    @Test
    void getYZero() {
        System.out.println("test getYZero");
        tuple = new Tuple(0,0);
        assertEquals(0, tuple.getY());
    }
}