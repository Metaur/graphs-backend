package com.example.graphs.controllers;

import com.example.graphs.services.GraphService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class GraphControllerTest {

    @SpyBean
    private GraphService service;

    @Autowired
    private MockMvc mvc;

    @BeforeEach
    void setup() {
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

        Mockito.clearInvocations(service);
    }

    @Test
    void getGraph() throws Exception {
        mvc.perform(get("/v1/graph"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("{" +
                                "\"verteces\":[\"a\",\"b\",\"c\",\"d\",\"e\"]," +
                                "\"adjacencyList\":{" +
                                "\"a\":[\"a\",\"b\",\"c\"]," +
                                "\"b\":[]," +
                                "\"c\":[\"a\",\"b\",\"d\"]," +
                                "\"d\":[\"c\",\"e\"]," +
                                "\"e\":[]}}"))
                );

        Mockito.verify(service).getAdjacencyList();
    }

    @Test
    void isConnected() throws Exception {
        mvc.perform(
                get("/v1/graph/connected")
                        .param("v1", "a")
                        .param("v2", "e")
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("true"));

        Mockito.verify(service).isConnected("a", "e");
    }

    @Test
    void isConnected_notFound() throws Exception {
        mvc.perform(
                get("/v1/graph/connected")
                        .queryParam("v1", "z")
                        .queryParam("v2", "e")
        )
                .andDo(print())
                .andExpect(status().isNotFound());

        Mockito.verify(service).isConnected("z", "e");
    }

    @Test
    void createEdge() throws Exception {
        mvc.perform(
                post("/v1/graph")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"from\": \"a\", \"to\": \"c\", \"bidirectional\": false}")
        )
                .andDo(print())
                .andExpect(status().isCreated());

        Mockito.verify(service).createEdge("a", "c", false);
    }

    @Test
    void removeAllEdges() throws Exception {
        mvc.perform(
                delete("/v1/graph/edges")
                        .queryParam("v1", "a")
                        .queryParam("v2", "b")
        )
                .andDo(print())
                .andExpect(status().isOk());

        Mockito.verify(service).removeEdges("a", "b");
    }
}