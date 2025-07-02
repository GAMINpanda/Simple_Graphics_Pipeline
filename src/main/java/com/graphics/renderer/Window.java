package com.graphics.renderer;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;

/**
 * Window class that manages GLFW window creation and lifecycle.
 * Designed with Test-Driven Development approach.
 */
public class Window {
    
    private final int width;
    private final int height;
    private final String title;
    private long windowHandle;
    private boolean destroyed = false;
    
    // Static initialization flag to ensure GLFW is only initialized once
    private static boolean glfwInitialized = false;
    
    /**
     * Creates a new window with specified dimensions and title.
     * 
     * @param width Window width in pixels
     * @param height Window height in pixels  
     * @param title Window title
     * @throws IllegalArgumentException if parameters are invalid
     */
    public Window(int width, int height, String title) {
        // RULE: Input validation as specified in TDD tests
        validateDimensions(width, height);
        validateTitle(title);
        
        this.width = width;
        this.height = height;
        this.title = title;
        
        initializeGLFW();
        createWindow();
    }
    
    /**
     * Validates window dimensions.
     */
    private void validateDimensions(int width, int height) {
        if (width <= 0) {
            throw new IllegalArgumentException("Width must be positive, got: " + width);
        }
        if (height <= 0) {
            throw new IllegalArgumentException("Height must be positive, got: " + height);
        }
    }
    
    /**
     * Validates window title.
     */
    private void validateTitle(String title) {
        if (title == null) {
            throw new IllegalArgumentException("Title cannot be null");
        }
        if (title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be empty");
        }
    }
    
    /**
     * Initializes GLFW if not already initialized.
     */
    private static synchronized void initializeGLFW() {
        if (!glfwInitialized) {
            // Set error callback
            GLFWErrorCallback.createPrint(System.err).set();
            
            // Initialize GLFW
            if (!GLFW.glfwInit()) {
                throw new RuntimeException("Failed to initialize GLFW");
            }
            
            glfwInitialized = true;
            
            // Set terminate callback for JVM shutdown
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                GLFW.glfwTerminate();
                glfwInitialized = false;
            }));
        }
    }
    
    /**
     * Creates the actual GLFW window.
     */
    private void createWindow() {
        // Configure GLFW window hints
        GLFW.glfwDefaultWindowHints();
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE); // Hidden initially
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_TRUE);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 3);
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_CORE_PROFILE);
        
        // Create window
        windowHandle = GLFW.glfwCreateWindow(width, height, title, 0, 0);
        if (windowHandle == 0) {
            throw new RuntimeException("Failed to create GLFW window");
        }
        
        // Make the OpenGL context current
        GLFW.glfwMakeContextCurrent(windowHandle);
        
        // Show the window
        GLFW.glfwShowWindow(windowHandle);
    }
    
    // Getter methods as required by tests
    
    public int getWidth() {
        return width;
    }
    
    public int getHeight() {
        return height;
    }
    
    public String getTitle() {
        return title;
    }
    
    public boolean isDestroyed() {
        return destroyed;
    }
    
    /**
     * Destroys the window and releases resources.
     */
    public void destroy() {
        if (!destroyed && windowHandle != 0) {
            GLFW.glfwDestroyWindow(windowHandle);
            windowHandle = 0;
            destroyed = true;
        }
    }
    
    /**
     * Checks if the window should close.
     */
    public boolean shouldClose() {
        if (destroyed || windowHandle == 0) {
            return true;
        }
        return GLFW.glfwWindowShouldClose(windowHandle);
    }
    
    /**
     * Polls for window events.
     */
    public void pollEvents() {
        if (!destroyed) {
            GLFW.glfwPollEvents();
        }
    }
    
    /**
     * Swaps the front and back buffers.
     */
    public void swapBuffers() {
        if (!destroyed && windowHandle != 0) {
            GLFW.glfwSwapBuffers(windowHandle);
        }
    }
    
    /**
     * Gets the GLFW window handle (for advanced use).
     */
    public long getWindowHandle() {
        return windowHandle;
    }
} 