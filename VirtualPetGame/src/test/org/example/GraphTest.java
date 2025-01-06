package org.example;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.*;

class GraphTest {

    private Graph graph;

    @BeforeAll
    static void initToolkit() {
        Platform.startup(() -> {});
    }

    @BeforeEach
    void setUp() {
        graph = new Graph();
    }

    @Test
    void testAddAndRetrieveScene() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);

        Platform.runLater(() -> {
            try {
                Scene scene1 = new Scene(new Pane());
                graph.addScene("MainMenu", scene1);

                assertTrue(graph.hasScene("MainMenu"));
                assertEquals(scene1, graph.getScene("MainMenu"));
            } finally {
                latch.countDown();
            }
        });

        latch.await();
    }

    @Test
    void testRetrieveNonExistentScene() {
        assertNull(graph.getScene("NonExistent"));
    }

    @Test
    void testDuplicateScene() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);

        Platform.runLater(() -> {
            try {
                Scene scene1 = new Scene(new Pane());
                Scene scene2 = new Scene(new Pane());

                graph.addScene("MainMenu", scene1);
                graph.addScene("MainMenu", scene2); // Overwrites the previous scene

                assertTrue(graph.hasScene("MainMenu"));
                assertEquals(scene2, graph.getScene("MainMenu"));
            } finally {
                latch.countDown();
            }
        });

        latch.await();
    }
}
