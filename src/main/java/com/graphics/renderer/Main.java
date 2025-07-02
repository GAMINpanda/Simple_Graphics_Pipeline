package com.graphics.renderer;

import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;

/**
 * Main application class for the LWJGL graphics renderer.
 * Demonstrates our TDD-designed triangle rendering with shaders.
 */
public class Main {
    
    public static void main(String[] args) {
        System.out.println("🎨 Starting LWJGL Triangle Renderer...");
        
        try {
            // Create window configuration
            WindowConfig config = new WindowConfig(800, 600, "LWJGL Triangle Renderer - TDD Success!");
            System.out.println("✅ Window configuration created: " + config.width() + "x" + config.height());
            
            // Create actual GLFW window (not mock for real application)
            Window window = new Window(config.width(), config.height(), config.title());
            System.out.println("✅ GLFW window created successfully");
            
            // Initialize OpenGL context
            GL.createCapabilities();
            System.out.println("✅ OpenGL context initialized");
            
            // Create our TDD-designed triangle
            Triangle triangle = createColorfulTriangle();
            System.out.println("✅ Triangle created with " + triangle.getVertexCount() + " vertices");
            
            // Create shader program
            Shader shader = createTriangleShader();
            System.out.println("✅ Shader program compiled and linked");
            
            // Set up OpenGL buffers
            TriangleRenderer renderer = new TriangleRenderer(triangle, shader);
            System.out.println("✅ Triangle renderer initialized");
            
            // Set clear color (dark blue background)
            GL11.glClearColor(0.1f, 0.1f, 0.3f, 1.0f);
            System.out.println("✅ OpenGL state configured");
            
            System.out.println("🚀 Entering triangle render loop...");
            System.out.println("   (Press ESC or close window to exit)");
            System.out.println("   🔺 You should see a colorful triangle!");
            
            // Main render loop
            int frameCount = 0;
            while (!window.shouldClose()) {
                // Clear the screen
                GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
                
                // Render our triangle
                renderer.render();
                
                // Poll for window events
                window.pollEvents();
                
                // Swap front and back buffers
                window.swapBuffers();
                
                frameCount++;
                
                // Print status every 120 frames (2 seconds at 60fps)
                if (frameCount % 120 == 0) {
                    System.out.println("🔺 Frame " + frameCount + " - Triangle rendering beautifully!");
                }
            }
            
            System.out.println("🛑 Render loop ended, cleaning up...");
            
            // Cleanup resources
            renderer.destroy();
            shader.destroy();
            triangle.destroy();
            window.destroy();
            System.out.println("✅ All resources cleaned up successfully");
            
        } catch (Exception e) {
            System.err.println("❌ Application error: " + e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println("👋 Triangle renderer terminated");
    }
    
    /**
     * Creates a colorful triangle using our TDD-designed Triangle class.
     */
    private static Triangle createColorfulTriangle() {
        // Triangle vertices: position (x,y,z) + color (r,g,b)
        float[] vertices = {
            // Top vertex (red)
             0.0f,  0.6f, 0.0f,   1.0f, 0.0f, 0.0f,
            // Bottom-left vertex (green)
            -0.6f, -0.6f, 0.0f,   0.0f, 1.0f, 0.0f,
            // Bottom-right vertex (blue)
             0.6f, -0.6f, 0.0f,   0.0f, 0.0f, 1.0f
        };
        
        return new Triangle(vertices);
    }
    
    /**
     * Creates shader program for triangle rendering.
     */
    private static Shader createTriangleShader() {
        String vertexShaderSource = """
            #version 330 core
            layout (location = 0) in vec3 aPos;
            layout (location = 1) in vec3 aColor;
            
            out vec3 vertexColor;
            
            void main() {
                vertexColor = aColor;
                gl_Position = vec4(aPos, 1.0);
            }
            """;
        
        String fragmentShaderSource = """
            #version 330 core
            in vec3 vertexColor;
            out vec4 FragColor;
            
            void main() {
                FragColor = vec4(vertexColor, 1.0);
            }
            """;
        
        return new Shader(vertexShaderSource, fragmentShaderSource);
    }
}

/**
 * Simple triangle renderer that manages OpenGL buffers and rendering.
 */
class TriangleRenderer {
    
    private final Triangle triangle;
    private final Shader shader;
    private final int vao;
    private final int vbo;
    
    public TriangleRenderer(Triangle triangle, Shader shader) {
        this.triangle = triangle;
        this.shader = shader;
        
        // Generate VAO and VBO
        this.vao = GL30.glGenVertexArrays();
        this.vbo = GL15.glGenBuffers();
        
        setupBuffers();
    }
    
    private void setupBuffers() {
        // Bind VAO
        GL30.glBindVertexArray(vao);
        
        // Create buffer with triangle data
        FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(triangle.getVertices().length);
        vertexBuffer.put(triangle.getVertices()).flip();
        
        // Bind and upload VBO
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertexBuffer, GL15.GL_STATIC_DRAW);
        
        // Position attribute (location 0)
        GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 6 * Float.BYTES, 0);
        GL20.glEnableVertexAttribArray(0);
        
        // Color attribute (location 1)  
        GL20.glVertexAttribPointer(1, 3, GL11.GL_FLOAT, false, 6 * Float.BYTES, 3 * Float.BYTES);
        GL20.glEnableVertexAttribArray(1);
        
        // Unbind
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        GL30.glBindVertexArray(0);
    }
    
    public void render() {
        shader.use();
        GL30.glBindVertexArray(vao);
        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, triangle.getVertexCount());
        GL30.glBindVertexArray(0);
        shader.unuse();
    }
    
    public void destroy() {
        GL15.glDeleteBuffers(vbo);
        GL30.glDeleteVertexArrays(vao);
    }
} 