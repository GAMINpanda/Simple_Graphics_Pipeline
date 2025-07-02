package com.graphics.renderer;

/**
 * Mock implementation of IWindow for testing purposes.
 * This allows us to test window logic without requiring actual graphics context.
 */
public class MockWindow implements IWindow {
    
    private final WindowConfig config;
    private boolean destroyed = false;
    private boolean shouldClose = false;
    
    /**
     * Creates a mock window with the specified configuration.
     */
    public MockWindow(WindowConfig config) {
        this.config = config;
    }
    
    /**
     * Convenience constructor for tests.
     */
    public MockWindow(int width, int height, String title) {
        this(new WindowConfig(width, height, title));
    }
    
    @Override
    public int getWidth() {
        return config.width();
    }
    
    @Override
    public int getHeight() {
        return config.height();
    }
    
    @Override
    public String getTitle() {
        return config.title();
    }
    
    @Override
    public boolean isDestroyed() {
        return destroyed;
    }
    
    @Override
    public void destroy() {
        destroyed = true;
    }
    
    @Override
    public boolean shouldClose() {
        return shouldClose || destroyed;
    }
    
    @Override
    public void pollEvents() {
        // Mock implementation - no actual event polling
    }
    
    @Override
    public void swapBuffers() {
        // Mock implementation - no actual buffer swapping
    }
    
    // Test helper methods
    
    /**
     * Test helper to simulate window close request.
     */
    public void requestClose() {
        shouldClose = true;
    }
    
    /**
     * Test helper to reset close request.
     */
    public void resetCloseRequest() {
        shouldClose = false;
    }
} 