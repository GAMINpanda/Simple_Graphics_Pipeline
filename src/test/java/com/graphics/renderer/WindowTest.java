package com.graphics.renderer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test-Driven Development tests for Window interface using MockWindow.
 * These tests define the expected behavior before implementation.
 */
public class WindowTest {
    
    private IWindow window;
    
    @BeforeEach
    void setUp() {
        // Each test gets a fresh window instance
        // Note: We'll need to handle GLFW initialization carefully
    }
    
    @AfterEach
    void tearDown() {
        // Ensure proper cleanup after each test
        if (window != null && !window.isDestroyed()) {
            window.destroy();
        }
    }
    
    @Test
    @DisplayName("Window should be created with valid dimensions and title")
    void testWindowCreation() {
        // Arrange
        int width = 800;
        int height = 600;
        String title = "Test Window";
        
        // Act
        window = new MockWindow(width, height, title);
        
        // Assert
        assertNotNull(window, "Window should not be null");
        assertEquals(width, window.getWidth(), "Window width should match");
        assertEquals(height, window.getHeight(), "Window height should match");
        assertEquals(title, window.getTitle(), "Window title should match");
        assertFalse(window.isDestroyed(), "New window should not be destroyed");
    }
    
    @Test
    @DisplayName("Window should reject invalid dimensions")
    void testInvalidDimensions() {
        // Test negative width
        assertThrows(IllegalArgumentException.class, () -> {
            new MockWindow(-1, 600, "Test");
        }, "Should throw exception for negative width");
        
        // Test negative height
        assertThrows(IllegalArgumentException.class, () -> {
            new MockWindow(800, -1, "Test");
        }, "Should throw exception for negative height");
        
        // Test zero dimensions
        assertThrows(IllegalArgumentException.class, () -> {
            new MockWindow(0, 0, "Test");
        }, "Should throw exception for zero dimensions");
    }
    
    @Test
    @DisplayName("Window should reject null or empty title")
    void testInvalidTitle() {
        assertThrows(IllegalArgumentException.class, () -> {
            new MockWindow(800, 600, null);
        }, "Should throw exception for null title");
        
        assertThrows(IllegalArgumentException.class, () -> {
            new MockWindow(800, 600, "");
        }, "Should throw exception for empty title");
    }
    
    @Test
    @DisplayName("Window should be destroyable and track destruction state")
    void testWindowDestruction() {
        // Arrange
        window = new MockWindow(800, 600, "Test Window");
        assertFalse(window.isDestroyed(), "New window should not be destroyed");
        
        // Act
        window.destroy();
        
        // Assert
        assertTrue(window.isDestroyed(), "Window should be marked as destroyed");
    }
    
    @Test
    @DisplayName("Window should detect close requests")
    void testWindowShouldClose() {
        // Arrange
        window = new MockWindow(800, 600, "Test Window");
        
        // Act & Assert
        // Initially, window should not be closing
        assertFalse(window.shouldClose(), "New window should not be closing");
        
        // Test close request with MockWindow helper
        if (window instanceof MockWindow mockWindow) {
            mockWindow.requestClose();
            assertTrue(window.shouldClose(), "Window should be closing after close request");
        }
    }
    
    @Test
    @DisplayName("Window should support polling events")
    void testEventPolling() {
        // Arrange
        window = new MockWindow(800, 600, "Test Window");
        
        // Act & Assert
        // This should not throw an exception
        assertDoesNotThrow(() -> {
            window.pollEvents();
        }, "Polling events should not throw exception");
    }
    
    @Test
    @DisplayName("Window should support buffer swapping")
    void testBufferSwap() {
        // Arrange
        window = new MockWindow(800, 600, "Test Window");
        
        // Act & Assert
        // This should not throw an exception
        assertDoesNotThrow(() -> {
            window.swapBuffers();
        }, "Swapping buffers should not throw exception");
    }
} 