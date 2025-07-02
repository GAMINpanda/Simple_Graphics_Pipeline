package com.graphics.renderer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * TDD tests for WindowConfig - testing validation logic without graphics dependencies.
 */
public class WindowConfigTest {
    
    @Test
    @DisplayName("WindowConfig should be created with valid parameters")
    void testValidWindowConfig() {
        // Arrange
        int width = 800;
        int height = 600;
        String title = "Test Window";
        
        // Act
        WindowConfig config = new WindowConfig(width, height, title);
        
        // Assert
        assertEquals(width, config.width());
        assertEquals(height, config.height());
        assertEquals(title, config.title());
    }
    
    @Test
    @DisplayName("WindowConfig should reject invalid dimensions")
    void testInvalidDimensions() {
        // Test negative width
        assertThrows(IllegalArgumentException.class, () -> {
            new WindowConfig(-1, 600, "Test");
        }, "Should throw exception for negative width");
        
        // Test negative height
        assertThrows(IllegalArgumentException.class, () -> {
            new WindowConfig(800, -1, "Test");
        }, "Should throw exception for negative height");
        
        // Test zero dimensions
        assertThrows(IllegalArgumentException.class, () -> {
            new WindowConfig(0, 0, "Test");
        }, "Should throw exception for zero dimensions");
        
        // Test zero width
        assertThrows(IllegalArgumentException.class, () -> {
            new WindowConfig(0, 600, "Test");
        }, "Should throw exception for zero width");
        
        // Test zero height
        assertThrows(IllegalArgumentException.class, () -> {
            new WindowConfig(800, 0, "Test");
        }, "Should throw exception for zero height");
    }
    
    @Test
    @DisplayName("WindowConfig should reject invalid titles")
    void testInvalidTitles() {
        // Test null title
        assertThrows(IllegalArgumentException.class, () -> {
            new WindowConfig(800, 600, null);
        }, "Should throw exception for null title");
        
        // Test empty title
        assertThrows(IllegalArgumentException.class, () -> {
            new WindowConfig(800, 600, "");
        }, "Should throw exception for empty title");
        
        // Test whitespace-only title
        assertThrows(IllegalArgumentException.class, () -> {
            new WindowConfig(800, 600, "   ");
        }, "Should throw exception for whitespace-only title");
    }
    
    @Test
    @DisplayName("WindowConfig should handle edge cases properly")
    void testEdgeCases() {
        // Test minimum valid dimensions
        assertDoesNotThrow(() -> {
            new WindowConfig(1, 1, "Tiny");
        }, "Should accept minimum valid dimensions");
        
        // Test very large dimensions
        assertDoesNotThrow(() -> {
            new WindowConfig(4096, 2160, "Large");
        }, "Should accept large dimensions");
        
        // Test title with special characters
        assertDoesNotThrow(() -> {
            new WindowConfig(800, 600, "Test Window - 123!@#");
        }, "Should accept title with special characters");
    }
} 