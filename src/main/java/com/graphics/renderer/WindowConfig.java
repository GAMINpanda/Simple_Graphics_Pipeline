package com.graphics.renderer;

/**
 * Configuration record for window properties.
 * This separates window configuration from actual window creation,
 * making it easily testable without graphics dependencies.
 */
public record WindowConfig(int width, int height, String title) {
    
    /**
     * Creates a new window configuration with validation.
     */
    public WindowConfig {
        validateDimensions(width, height);
        validateTitle(title);
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
} 