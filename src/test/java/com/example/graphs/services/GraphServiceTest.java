package com.example.graphs.services;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GraphServiceTest {
    private final GraphService service = new GraphService();

    @Test
    void createEdge_unidirectional() {
        String v1 = "a";
        String v2 = "b";
        service.createEdge(v1, v2, false);

        var result = service.getAdjacencyList();
        assertArrayEquals(Arrays.array(v1, v2), result.keySet().toArray());
        assertArrayEquals(Arrays.array(v2), result.get(v1).toArray());
        assertEquals(0, result.get(v2).size());
    }

    @Test
    void createEdge_bidirectional() {
        String v1 = "a";
        String v2 = "b";
        service.createEdge(v1, v2, true);

        var result = service.getAdjacencyList();
        assertArrayEquals(Arrays.array(v1, v2), result.keySet().toArray());
        assertArrayEquals(Arrays.array(v2), result.get(v1).toArray());
        assertArrayEquals(Arrays.array(v1), result.get(v2).toArray());
    }

    @Test
    void createEdge_loop() {
        String v1 = "a";
        service.createEdge(v1, v1, false);

        var result = service.getAdjacencyList();
        String[] v1Result = Arrays.array(v1);
        assertArrayEquals(v1Result, result.keySet().toArray());
        assertArrayEquals(v1Result, result.get(v1).toArray());
    }

    @Test
    void removeEdges() {
        String v1 = "a";
        String v2 = "b";
        String v3 = "c";
        service.createEdge(v1, v2, true);
        service.createEdge(v1, v3, true);
        service.createEdge(v2, v3, false);

        service.removeEdges(v1, v2);

        var result = service.getAdjacencyList();
        assertArrayEquals(Arrays.array(v1, v2, v3), result.keySet().toArray());
        String[] v3Result = Arrays.array(v3);
        assertArrayEquals(v3Result, result.get(v1).toArray());
        assertArrayEquals(v3Result, result.get(v2).toArray());
    }

    @Test
    void isConnected_true() {
        String v1 = "a";
        String v2 = "b";
        String v3 = "c";
        service.createEdge(v1, v2, false);
        service.createEdge(v2, v3, false);

        assertTrue(service.isConnected(v1, v2));
    }

    @Test
    void isConnected_disjoint() {
        String v1 = "a";
        String v2 = "b";
        String v3 = "c";
        String v4 = "d";
        service.createEdge(v1, v2, false);
        service.createEdge(v3, v4, false);

        assertFalse(service.isConnected(v1, v4));
    }

    @Test
    void isConnected_selfNoLoop() {
        String v1 = "a";
        String v2 = "b";
        service.createEdge(v1, v2, false);

        assertFalse(service.isConnected(v1, v1));
    }

    @Test
    void isConnected_self() {
        String v1 = "a";
        String v2 = "b";
        service.createEdge(v1, v1, false);
        service.createEdge(v1, v2, false);

        assertTrue(service.isConnected(v1, v1));
    }

    @Test
    void isConnected_long() {
        String v1 = "a";
        String v2 = "b";
        String v3 = "c";
        String v4 = "d";
        String v5 = "e";
        service.createEdge(v1, v1, false);
        service.createEdge(v1, v2, false);
        service.createEdge(v1, v3, true);
        service.createEdge(v3, v4, true);
        service.createEdge(v3, v2, false);
        service.createEdge(v4, v5, false);

        assertTrue(service.isConnected(v1, v5));
    }
}