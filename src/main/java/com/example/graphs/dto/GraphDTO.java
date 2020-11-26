package com.example.graphs.dto;

import java.util.Map;
import java.util.Set;

public class GraphDTO {
    private final Set<String> verteces;
    private final Map<String, Set<String>> adjacencyList;

    public GraphDTO(Map<String, Set<String>> adjacencyList) {
        this.adjacencyList = adjacencyList;
        this.verteces = adjacencyList.keySet();
    }

    public Set<String> getVerteces() {
        return verteces;
    }

    public Map<String, Set<String>> getAdjacencyList() {
        return adjacencyList;
    }
}
