package com.example.graphs.controllers;

import com.example.graphs.dto.CreateEdgeDTO;
import com.example.graphs.dto.GraphDTO;
import com.example.graphs.services.GraphService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/graph")
public class GraphController {
    private final GraphService service;

    public GraphController(GraphService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<GraphDTO> getGraph() {
        GraphDTO graph = new GraphDTO(service.getAdjacencyList());
        return ResponseEntity.ok(graph);
    }

    @GetMapping("connected")
    public ResponseEntity<Boolean> isConnected(@RequestParam String v1, @RequestParam String v2) {
        return ResponseEntity.ok(service.isConnected(v1, v2));
    }

    @PostMapping
    public ResponseEntity<Void> createEdge(@RequestBody CreateEdgeDTO dto) {
        service.createEdge(dto.getFrom(), dto.getTo(), dto.isBidirectional());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("edges")
    public ResponseEntity<Void> removeAllEdges(@RequestParam  String v1, @RequestParam String v2) {
        service.removeEdges(v1, v2);
        return ResponseEntity.ok().build();
    }
}
