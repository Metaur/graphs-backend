package com.example.graphs.services;

import com.example.graphs.exceptions.VertexNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GraphService {
    private final Map<String, Set<String>> adjacencyList = new HashMap<>();

    private void safeCreateEdge(String v1, String v2) {
        adjacencyList.putIfAbsent(v1, new HashSet<>());
        adjacencyList.putIfAbsent(v2, new HashSet<>());

        adjacencyList.get(v1).add(v2);
    }

    public void createEdge(String v1, String v2, boolean bidirectional) {
        safeCreateEdge(v1, v2);

        if (bidirectional) {
            safeCreateEdge(v2, v1);
        }
    }

    public Map<String, Set<String>> getAdjacencyList() {
        return adjacencyList;
    }

    public void removeEdges(String v1, String v2) {
        Set<String> emptyList = Collections.emptySet();
        adjacencyList.getOrDefault(v1, emptyList)
                .remove(v2);
        adjacencyList.getOrDefault(v2, emptyList)
                .remove(v1);
    }

    public boolean isConnected(String v1, String v2) {
        var visited = new HashSet<String>();

        if (!adjacencyList.containsKey(v1) || !adjacencyList.containsKey(v2)) {
            throw new VertexNotFoundException("Start or end vertex was not found");
        }

        // get first edges here, so that we do not make a loop more complex with first iteration checks
        var a = adjacencyList.getOrDefault(v1, Collections.emptySet());
        var queue = new LinkedList<>(a);

        while(!queue.isEmpty()) {
            var next = queue.poll();

            if (visited.contains(next)) {
                continue;
            }
            visited.add(next);

            if (next.equals(v2)) {
                return true;
            }
            queue.addAll(adjacencyList.getOrDefault(next, Collections.emptySet()));
        }

        return false;
    }
}
