package com.graphics.renderer;

/**
 * Triangle class representing a triangle with vertex positions and colors.
 * Designed with Test-Driven Development approach.
 */
public class Triangle {
    
    private static final int VERTEX_STRIDE = 6; // x,y,z,r,g,b
    private static final int VERTICES_PER_TRIANGLE = 3;
    private static final int EXPECTED_VERTEX_COUNT = VERTICES_PER_TRIANGLE * VERTEX_STRIDE; // 18
    
    private final float[] vertices;
    private boolean destroyed = false;
    
    /**
     * Creates a triangle with the specified vertex data.
     * 
     * @param vertices Array of vertex data in format: [x,y,z,r,g,b, x,y,z,r,g,b, x,y,z,r,g,b]
     * @throws IllegalArgumentException if vertex data is invalid
     */
    public Triangle(float[] vertices) {
        validateVertices(vertices);
        this.vertices = vertices.clone(); // Defensive copy
    }
    
    /**
     * Validates vertex data.
     */
    private void validateVertices(float[] vertices) {
        if (vertices == null) {
            throw new IllegalArgumentException("Vertices cannot be null");
        }
        
        if (vertices.length == 0) {
            throw new IllegalArgumentException("Vertices cannot be empty");
        }
        
        if (vertices.length % VERTEX_STRIDE != 0) {
            throw new IllegalArgumentException(
                "Vertex data must be multiple of " + VERTEX_STRIDE + " (x,y,z,r,g,b), got: " + vertices.length);
        }
        
        if (vertices.length != EXPECTED_VERTEX_COUNT) {
            throw new IllegalArgumentException(
                "Triangle requires exactly " + EXPECTED_VERTEX_COUNT + " floats (3 vertices * 6 components), got: " + vertices.length);
        }
    }
    
    /**
     * Gets the complete vertex data array.
     */
    public float[] getVertices() {
        return vertices.clone(); // Defensive copy
    }
    
    /**
     * Gets the number of vertices (always 3 for a triangle).
     */
    public int getVertexCount() {
        return VERTICES_PER_TRIANGLE;
    }
    
    /**
     * Gets the vertex stride (components per vertex).
     */
    public int getVertexStride() {
        return VERTEX_STRIDE;
    }
    
    /**
     * Checks if the triangle is destroyed.
     */
    public boolean isDestroyed() {
        return destroyed;
    }
    
    /**
     * Destroys the triangle and releases resources.
     */
    public void destroy() {
        destroyed = true;
    }
    
    /**
     * Extracts position data (x,y,z) from vertices.
     * 
     * @return Array of position data [x1,y1,z1, x2,y2,z2, x3,y3,z3]
     */
    public float[] getPositions() {
        float[] positions = new float[VERTICES_PER_TRIANGLE * 3]; // 3 components per position
        
        for (int i = 0; i < VERTICES_PER_TRIANGLE; i++) {
            int vertexOffset = i * VERTEX_STRIDE;
            int positionOffset = i * 3;
            
            positions[positionOffset] = vertices[vertexOffset];         // x
            positions[positionOffset + 1] = vertices[vertexOffset + 1]; // y
            positions[positionOffset + 2] = vertices[vertexOffset + 2]; // z
        }
        
        return positions;
    }
    
    /**
     * Extracts color data (r,g,b) from vertices.
     * 
     * @return Array of color data [r1,g1,b1, r2,g2,b2, r3,g3,b3]
     */
    public float[] getColors() {
        float[] colors = new float[VERTICES_PER_TRIANGLE * 3]; // 3 components per color
        
        for (int i = 0; i < VERTICES_PER_TRIANGLE; i++) {
            int vertexOffset = i * VERTEX_STRIDE;
            int colorOffset = i * 3;
            
            colors[colorOffset] = vertices[vertexOffset + 3];         // r
            colors[colorOffset + 1] = vertices[vertexOffset + 4];     // g
            colors[colorOffset + 2] = vertices[vertexOffset + 5];     // b
        }
        
        return colors;
    }
} 