package com.graphics.renderer;

/**
 * Interface for window operations.
 * This allows us to create testable implementations without requiring actual graphics context.
 */
public interface IWindow {
    
    /**
     * Gets the window width.
     */
    int getWidth();
    
    /**
     * Gets the window height.
     */
    int getHeight();
    
    /**
     * Gets the window title.
     */
    String getTitle();
    
    /**
     * Checks if the window is destroyed.
     */
    boolean isDestroyed();
    
    /**
     * Destroys the window and releases resources.
     */
    void destroy();
    
    /**
     * Checks if the window should close.
     */
    boolean shouldClose();
    
    /**
     * Polls for window events.
     */
    void pollEvents();
    
    /**
     * Swaps the front and back buffers.
     */
    void swapBuffers();
} 