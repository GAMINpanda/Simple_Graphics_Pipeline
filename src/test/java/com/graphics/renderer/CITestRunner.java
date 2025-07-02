package com.graphics.renderer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.api.condition.DisabledIfSystemProperty;

import static org.junit.jupiter.api.Assertions.*;

/**
 * CI-friendly tests that don't require graphics initialization
 */
public class CITestRunner {
    
    @Test
    public void testBasicProjectStructure() {
        // Test that basic classes can be instantiated without graphics
        assertDoesNotThrow(() -> {
            var config = new WindowConfig(800, 600, "Test Window");
            assertNotNull(config);
        });
    }
    
    @Test
    public void testWindowConfigCreation() {
        var config = new WindowConfig(800, 600, "Graphics Window");
        assertEquals(800, config.width());
        assertEquals(600, config.height());
        assertEquals("Graphics Window", config.title());
    }
    
    @Test
    public void testWindowConfigValidation() {
        // Test that validation works
        assertThrows(IllegalArgumentException.class, () -> {
            new WindowConfig(0, 600, "Test");
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            new WindowConfig(800, 0, "Test");  
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            new WindowConfig(800, 600, null);
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            new WindowConfig(800, 600, "");
        });
    }
    
    @Test
    @DisabledIfSystemProperty(named = "java.awt.headless", matches = "true")
    public void testGraphicsComponents() {
        // This test only runs when NOT in headless mode
        assertDoesNotThrow(() -> {
            // Graphics-related tests would go here
            System.out.println("Running graphics tests...");
        });
    }
    
    @Test
    @EnabledIfSystemProperty(named = "java.awt.headless", matches = "true")
    public void testHeadlessMode() {
        // This test only runs in headless mode (CI)
        assertTrue(Boolean.parseBoolean(System.getProperty("java.awt.headless")));
        System.out.println("Running in headless CI mode");
    }
} 