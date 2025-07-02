package com.graphics.renderer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * TDD tests for Shader functionality using MockShader - defining shader compilation and management.
 */
public class ShaderTest {
    
    private MockShader shader;
    
    @AfterEach
    void tearDown() {
        if (shader != null && !shader.isDestroyed()) {
            shader.destroy();
        }
    }
    
    @Test
    @DisplayName("Shader should be created with valid vertex and fragment source")
    void testShaderCreation() {
        // Arrange
        String vertexSource = """
            #version 330 core
            layout (location = 0) in vec3 aPos;
            layout (location = 1) in vec3 aColor;
            
            out vec3 vertexColor;
            
            void main() {
                vertexColor = aColor;
                gl_Position = vec4(aPos, 1.0);
            }
            """;
            
        String fragmentSource = """
            #version 330 core
            in vec3 vertexColor;
            out vec4 FragColor;
            
            void main() {
                FragColor = vec4(vertexColor, 1.0);
            }
            """;
        
        // Act
        shader = new MockShader(vertexSource, fragmentSource);
        
        // Assert
        assertNotNull(shader, "Shader should not be null");
        assertFalse(shader.isDestroyed(), "New shader should not be destroyed");
        assertTrue(shader.isValid(), "Shader should be valid after creation");
    }
    
    @Test
    @DisplayName("Shader should reject null or empty source code")
    void testInvalidShaderSource() {
        String validVertex = "#version 330 core\nvoid main() { gl_Position = vec4(0.0); }";
        String validFragment = "#version 330 core\nout vec4 FragColor; void main() { FragColor = vec4(1.0); }";
        
        // Test null vertex shader
        assertThrows(IllegalArgumentException.class, () -> {
            new MockShader(null, validFragment);
        }, "Should throw exception for null vertex shader");
        
        // Test null fragment shader
        assertThrows(IllegalArgumentException.class, () -> {
            new MockShader(validVertex, null);
        }, "Should throw exception for null fragment shader");
        
        // Test empty vertex shader
        assertThrows(IllegalArgumentException.class, () -> {
            new MockShader("", validFragment);
        }, "Should throw exception for empty vertex shader");
        
        // Test empty fragment shader
        assertThrows(IllegalArgumentException.class, () -> {
            new MockShader(validVertex, "");
        }, "Should throw exception for empty fragment shader");
    }
    
    @Test
    @DisplayName("Shader should support use and unuse operations")
    void testShaderUse() {
        // Arrange
        String vertexSource = createValidVertexShader();
        String fragmentSource = createValidFragmentShader();
        shader = new MockShader(vertexSource, fragmentSource);
        
        // Act & Assert
        assertDoesNotThrow(() -> {
            shader.use();
        }, "Using shader should not throw exception");
        
        assertDoesNotThrow(() -> {
            shader.unuse();
        }, "Unused shader should not throw exception");
    }
    
    @Test
    @DisplayName("Shader should support resource cleanup")
    void testShaderDestruction() {
        // Arrange
        shader = new MockShader(createValidVertexShader(), createValidFragmentShader());
        assertFalse(shader.isDestroyed(), "New shader should not be destroyed");
        assertTrue(shader.isValid(), "New shader should be valid");
        
        // Act
        shader.destroy();
        
        // Assert
        assertTrue(shader.isDestroyed(), "Shader should be marked as destroyed");
        assertFalse(shader.isValid(), "Destroyed shader should not be valid");
    }
    
    @Test
    @DisplayName("Shader should provide program ID for OpenGL operations")
    void testShaderProgramId() {
        // Arrange
        shader = new MockShader(createValidVertexShader(), createValidFragmentShader());
        
        // Act
        int programId = shader.getProgramId();
        
        // Assert
        assertTrue(programId >= 0, "Program ID should be non-negative");
    }
    
    @Test 
    @DisplayName("Destroyed shader should reject operations")
    void testDestroyedShaderOperations() {
        // Arrange
        shader = new MockShader(createValidVertexShader(), createValidFragmentShader());
        shader.destroy();
        
        // Act & Assert
        assertThrows(IllegalStateException.class, () -> {
            shader.use();
        }, "Should throw exception when using destroyed shader");
        
        assertThrows(IllegalStateException.class, () -> {
            shader.unuse();
        }, "Should throw exception when unused destroyed shader");
    }
    
    private String createValidVertexShader() {
        return """
            #version 330 core
            layout (location = 0) in vec3 aPos;
            layout (location = 1) in vec3 aColor;
            
            out vec3 vertexColor;
            
            void main() {
                vertexColor = aColor;
                gl_Position = vec4(aPos, 1.0);
            }
            """;
    }
    
    private String createValidFragmentShader() {
        return """
            #version 330 core
            in vec3 vertexColor;
            out vec4 FragColor;
            
            void main() {
                FragColor = vec4(vertexColor, 1.0);
            }
            """;
    }
} 