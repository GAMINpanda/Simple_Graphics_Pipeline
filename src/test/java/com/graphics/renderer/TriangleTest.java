package com.graphics.renderer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * TDD tests for Triangle class - defining vertex data structure.
 */
public class TriangleTest {
    
    @Test
    @DisplayName("Triangle should be created with three vertices")
    void testTriangleCreation() {
        // Arrange - Triangle vertices (x, y, z, r, g, b)
        float[] vertices = {
            // Vertex 1: Top (red)
             0.0f,  0.5f, 0.0f,  1.0f, 0.0f, 0.0f,
            // Vertex 2: Bottom-left (green)  
            -0.5f, -0.5f, 0.0f,  0.0f, 1.0f, 0.0f,
            // Vertex 3: Bottom-right (blue)
             0.5f, -0.5f, 0.0f,  0.0f, 0.0f, 1.0f
        };
        
        // Act
        Triangle triangle = new Triangle(vertices);
        
        // Assert
        assertNotNull(triangle, "Triangle should not be null");
        assertArrayEquals(vertices, triangle.getVertices(), "Vertices should match input");
        assertEquals(3, triangle.getVertexCount(), "Triangle should have 3 vertices");
        assertFalse(triangle.isDestroyed(), "New triangle should not be destroyed");
    }
    
    @Test
    @DisplayName("Triangle should reject invalid vertex data")
    void testInvalidVertexData() {
        // Test null vertices
        assertThrows(IllegalArgumentException.class, () -> {
            new Triangle(null);
        }, "Should throw exception for null vertices");
        
        // Test empty vertices
        assertThrows(IllegalArgumentException.class, () -> {
            new Triangle(new float[0]);
        }, "Should throw exception for empty vertices");
        
        // Test incorrect vertex count (not multiple of 6: x,y,z,r,g,b)
        assertThrows(IllegalArgumentException.class, () -> {
            new Triangle(new float[]{1.0f, 2.0f, 3.0f}); // Only 3 floats
        }, "Should throw exception for invalid vertex format");
        
        // Test too few vertices for triangle (need exactly 18 floats: 3 vertices * 6 components)
        assertThrows(IllegalArgumentException.class, () -> {
            new Triangle(new float[]{
                0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, // 6 floats (1 vertex)
                0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f  // 6 floats (1 vertex) = 12 total
            });
        }, "Should throw exception for insufficient vertices");
    }
    
    @Test
    @DisplayName("Triangle should support resource cleanup")
    void testTriangleDestruction() {
        // Arrange
        float[] vertices = createValidTriangleVertices();
        Triangle triangle = new Triangle(vertices);
        
        assertFalse(triangle.isDestroyed(), "New triangle should not be destroyed");
        
        // Act
        triangle.destroy();
        
        // Assert
        assertTrue(triangle.isDestroyed(), "Triangle should be marked as destroyed");
    }
    
    @Test
    @DisplayName("Triangle should provide vertex stride information")
    void testVertexStride() {
        // Arrange
        Triangle triangle = new Triangle(createValidTriangleVertices());
        
        // Act & Assert
        assertEquals(6, triangle.getVertexStride(), "Vertex stride should be 6 (x,y,z,r,g,b)");
        assertEquals(18, triangle.getVertices().length, "Total vertex data should be 18 floats");
    }
    
    @Test
    @DisplayName("Triangle should provide position and color data separately")
    void testVertexComponents() {
        // Arrange
        Triangle triangle = new Triangle(createValidTriangleVertices());
        
        // Act
        float[] positions = triangle.getPositions();
        float[] colors = triangle.getColors();
        
        // Assert
        assertEquals(9, positions.length, "Positions should have 9 floats (3 vertices * 3 components)");
        assertEquals(9, colors.length, "Colors should have 9 floats (3 vertices * 3 components)");
        
        // Verify first vertex position
        assertEquals(0.0f, positions[0], "First vertex X should be 0.0");
        assertEquals(0.5f, positions[1], "First vertex Y should be 0.5");
        assertEquals(0.0f, positions[2], "First vertex Z should be 0.0");
        
        // Verify first vertex color (red)
        assertEquals(1.0f, colors[0], "First vertex R should be 1.0");
        assertEquals(0.0f, colors[1], "First vertex G should be 0.0");
        assertEquals(0.0f, colors[2], "First vertex B should be 0.0");
    }
    
    private float[] createValidTriangleVertices() {
        return new float[]{
            // Top vertex (red)
             0.0f,  0.5f, 0.0f,  1.0f, 0.0f, 0.0f,
            // Bottom-left vertex (green)
            -0.5f, -0.5f, 0.0f,  0.0f, 1.0f, 0.0f,
            // Bottom-right vertex (blue)
             0.5f, -0.5f, 0.0f,  0.0f, 0.0f, 1.0f
        };
    }
} 