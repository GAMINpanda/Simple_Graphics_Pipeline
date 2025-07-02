# Software Architecture and Design Patterns

## Test-Driven Development (TDD) Architecture

### TDD Design Philosophy
**Location**: All classes designed with TDD approach, evident in test files

The entire codebase follows **Test-First Design**, where tests define the interface before implementation:

```java
// Test defines the interface contract
@Test
@DisplayName("Shader should be created with valid vertex and fragment source")
void testShaderCreation() {
    shader = new MockShader(vertexSource, fragmentSource);
    
    assertNotNull(shader, "Shader should not be null");
    assertFalse(shader.isDestroyed(), "New shader should not be destroyed");
    assertTrue(shader.isValid(), "Shader should be valid after creation");
}
```

**TDD Benefits Realized**:
1. **Interface-First Design**: Classes have clean, minimal APIs
2. **Validation Logic**: Comprehensive input validation from test requirements
3. **Lifecycle Management**: Explicit resource lifecycle from test scenarios
4. **Error Handling**: Predictable exception behavior

### Abstraction Through Interfaces
**Location**: `IWindow.java` - Interface abstraction pattern

```java
public interface IWindow {
    int getWidth();
    int getHeight();
    String getTitle();
    boolean isDestroyed();
    void destroy();
    boolean shouldClose();
    void pollEvents();
    void swapBuffers();
}
```

**Design Benefits**:
- **Testability**: Enables mock implementations for unit testing
- **Dependency Inversion**: High-level modules don't depend on concrete classes
- **Polymorphism**: Same interface works for real and mock windows

## Separation of Concerns Architecture

### Configuration vs Implementation
**Location**: `WindowConfig.java` vs `Window.java`

**Configuration Object** (Data):
```java
public record WindowConfig(int width, int height, String title) {
    public WindowConfig {
        validateDimensions(width, height);
        validateTitle(title);
    }
}
```

**Implementation Object** (Behavior):
```java
public class Window {
    public Window(int width, int height, String title) {
        validateDimensions(width, height);
        validateTitle(title);
        
        this.width = width;
        this.height = height;
        this.title = title;
        
        initializeGLFW();
        createWindow();
    }
}
```

**Architectural Advantage**:
- **Pure Data Validation**: Configuration validates without side effects
- **Behavior Isolation**: Window handles GLFW lifecycle management
- **Testability**: Configuration testable without graphics context

### Resource Management Pattern
**Location**: All classes implement explicit resource cleanup

**Consistent Lifecycle Pattern**:
```java
public class Triangle {
    private boolean destroyed = false;
    
    public boolean isDestroyed() {
        return destroyed;
    }
    
    public void destroy() {
        destroyed = true;
    }
}
```

**RAII-Style Management**:
- **Explicit Destruction**: All resources have `destroy()` method
- **State Tracking**: `isDestroyed()` prevents use-after-free
- **Exception Safety**: Cleanup in `finally` blocks or shutdown hooks

## Component-Based Architecture

### Modular Component Design
**Location**: `Main.java` demonstrates composition over inheritance

```java
// Composition of independent components
Triangle triangle = createColorfulTriangle();
Shader shader = createTriangleShader();
TriangleRenderer renderer = new TriangleRenderer(triangle, shader);
```

**Component Relationships**:
```
Main
├── Window (windowing system)
├── Triangle (geometry data)
├── Shader (GPU programs)
└── TriangleRenderer (rendering logic)
```

**Design Benefits**:
- **Single Responsibility**: Each component has one clear purpose
- **Loose Coupling**: Components interact through minimal interfaces
- **Reusability**: Components can be used in different contexts

### Data-Driven Geometry
**Location**: `Triangle.java` lines 18-42

**Flexible Vertex Format**:
```java
private static final int VERTEX_STRIDE = 6; // x,y,z,r,g,b
private static final int VERTICES_PER_TRIANGLE = 3;
private static final int EXPECTED_VERTEX_COUNT = VERTICES_PER_TRIANGLE * VERTEX_STRIDE;

public Triangle(float[] vertices) {
    validateVertices(vertices);
    this.vertices = vertices.clone(); // Defensive copy
}
```

**Data Access Methods**:
```java
public float[] getPositions() { /* Extract positions */ }
public float[] getColors() { /* Extract colors */ }
public float[] getVertices() { /* Get all data */ }
```

**Architectural Advantages**:
- **Format Flexibility**: Easy to extend vertex format
- **Data Validation**: Strict format enforcement
- **Immutable Access**: Defensive copying prevents external mutation

## Error Handling Architecture

### Defensive Programming
**Location**: Input validation throughout all classes

**Multi-Layer Validation**:
```java
// Layer 1: Constructor validation
public WindowConfig(int width, int height, String title) {
    validateDimensions(width, height);
    validateTitle(title);
}

// Layer 2: Implementation validation  
public Window(int width, int height, String title) {
    validateDimensions(width, height);  // Redundant but safe
    validateTitle(title);
    // ... implementation
}
```

**Exception Design**:
- **Fail Fast**: Invalid inputs rejected immediately
- **Specific Exceptions**: `IllegalArgumentException` for invalid inputs
- **Clear Messages**: Exception messages include actual vs expected values

### Resource Leak Prevention
**Location**: `Shader.java` lines 44-95 demonstrates exception safety

**Exception-Safe Resource Management**:
```java
try {
    int vertexShaderId = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);
    // ... compilation
    int fragmentShaderId = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);
    // ... compilation
    programId = GL20.glCreateProgram();
    // ... linking
} catch (Exception e) {
    // Cleanup partially created resources
    GL20.glDeleteShader(vertexShaderId);
    GL20.glDeleteShader(fragmentShaderId);
    GL20.glDeleteProgram(programId);
    throw e;
}
```

## Immutability and Thread Safety

### Immutable Data Objects
**Location**: `WindowConfig` record, defensive copying in `Triangle`

**Record Pattern**:
```java
public record WindowConfig(int width, int height, String title) {
    // Immutable by design - no setters, final fields
}
```

**Defensive Copying**:
```java
public float[] getVertices() {
    return vertices.clone(); // Return copy, not reference
}
```

### Thread Safety Considerations
**Location**: `Window.java` lines 66-76

**Static Initialization Safety**:
```java
private static boolean glfwInitialized = false;

private static synchronized void initializeGLFW() {
    if (!glfwInitialized) {
        // ... initialization
        glfwInitialized = true;
    }
}
```

**Singleton Pattern**: GLFW initialization uses synchronized singleton pattern

## Performance-Oriented Design

### Object Pooling Opportunities
**Location**: `TriangleRenderer` buffer management

**Efficient Buffer Usage**:
```java
// VBO uploaded once, reused many times
GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertexBuffer, GL15.GL_STATIC_DRAW);
```

**Memory Efficiency**:
- **Static Allocation**: Buffers allocated once during initialization
- **Reuse Pattern**: Same buffers used for all render calls
- **Minimal Allocations**: No per-frame object creation

### State Change Minimization
**Location**: `TriangleRenderer.render()` optimizes OpenGL state changes

**Efficient Rendering**:
```java
public void render() {
    shader.use();              // Minimize shader switches
    GL30.glBindVertexArray(vao); // VAO encapsulates all vertex state
    GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, triangle.getVertexCount());
    GL30.glBindVertexArray(0);   // Clean state
    shader.unuse();
}
```

This architecture demonstrates modern software engineering practices: clean separation of concerns, comprehensive testing, resource safety, and performance-conscious design. 