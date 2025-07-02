package com.graphics.renderer;

/**
 * Mock implementation of shader functionality for testing purposes.
 * This allows us to test shader logic without requiring actual OpenGL context.
 */
public class MockShader {
    
    private final String vertexSource;
    private final String fragmentSource;
    private final int programId;
    private boolean destroyed = false;
    private boolean valid = true;
    
    /**
     * Creates a mock shader with the specified source code.
     */
    public MockShader(String vertexSource, String fragmentSource) {
        validateShaderSource(vertexSource, "vertex");
        validateShaderSource(fragmentSource, "fragment");
        
        this.vertexSource = vertexSource;
        this.fragmentSource = fragmentSource;
        this.programId = Math.abs(vertexSource.hashCode() + fragmentSource.hashCode());
    }
    
    /**
     * Validates shader source code.
     */
    private void validateShaderSource(String source, String shaderType) {
        if (source == null) {
            throw new IllegalArgumentException(shaderType + " shader source cannot be null");
        }
        
        if (source.trim().isEmpty()) {
            throw new IllegalArgumentException(shaderType + " shader source cannot be empty");
        }
    }
    
    /**
     * Checks if the shader is destroyed.
     */
    public boolean isDestroyed() {
        return destroyed;
    }
    
    /**
     * Checks if the shader is valid and ready to use.
     */
    public boolean isValid() {
        return valid && !destroyed;
    }
    
    /**
     * Gets the mock program ID.
     */
    public int getProgramId() {
        return programId;
    }
    
    /**
     * Uses this shader program for rendering.
     */
    public void use() {
        if (destroyed) {
            throw new IllegalStateException("Cannot use destroyed shader");
        }
        // Mock implementation - no actual OpenGL calls
    }
    
    /**
     * Stops using any shader program.
     */
    public void unuse() {
        if (destroyed) {
            throw new IllegalStateException("Cannot unuse destroyed shader");
        }
        // Mock implementation - no actual OpenGL calls
    }
    
    /**
     * Destroys the shader program.
     */
    public void destroy() {
        destroyed = true;
        valid = false;
    }
    
    /**
     * Gets the vertex shader source (for testing).
     */
    public String getVertexSource() {
        return vertexSource;
    }
    
    /**
     * Gets the fragment shader source (for testing).
     */
    public String getFragmentSource() {
        return fragmentSource;
    }
} 