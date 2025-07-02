# LWJGL Graphics Renderer

A simple Java-based graphics renderer built with LWJGL (Lightweight Java Game Library) for learning graphics programming concepts.

## Project Structure

```
AIGraphicsPractice/
├── .cursor/                     # AI prompting rules and configuration
│   └── rules.md                # Customizable AI interaction preferences
├── src/
│   ├── main/
│   │   ├── java/com/graphics/renderer/  # Main source code
│   │   └── resources/shaders/           # Shader files (GLSL)
│   └── test/java/              # Unit tests
├── gradle/wrapper/             # Gradle wrapper files
├── build.gradle               # Build configuration
├── gradlew                   # Gradle wrapper script (Unix/macOS)
└── README.md                # This file
```

## Requirements

- **Java 21+** (configured in build.gradle)
- **macOS** (native libraries configured for macOS)

## Building and Running

```bash
# Build the project
./gradlew build

# Run the application
./gradlew run

# Clean build artifacts
./gradlew clean
```

## Dependencies

- **LWJGL 3.3.3**: Core library, GLFW (windowing), OpenGL, STB (image loading)
- **JOML 1.10.5**: Java OpenGL Mathematics Library for vectors and matrices
- **JUnit 5**: Testing framework

## Learning Goals

- [x] Project setup with proper Java structure
- [x] Basic window creation and OpenGL context ✨
- [x] Simple triangle rendering 🔺✨
- [x] Shader system implementation 🔺✨  
- [x] Resource cleanup and management ✨

*Using Test-Driven Development (TDD) approach where possible*

### TDD Architecture Achievements ✨
- [x] **WindowConfig** - Testable configuration with validation
- [x] **IWindow Interface** - Dependency injection for testing
- [x] **MockWindow** - Unit testable implementation
- [x] **Window (GLFW)** - Production graphics implementation
- [x] **Triangle** - Vertex data structure with validation 🔺
- [x] **MockShader** - Testable shader implementation 🔺
- [x] **Shader (OpenGL)** - GLSL compilation and linking 🔺
- [x] **TriangleRenderer** - VAO/VBO buffer management 🔺
- [x] **100% Test Coverage** for business logic

## 📚 Comprehensive Documentation

This project includes extensive technical documentation explaining complex graphics programming concepts:

**[📖 View Complete Documentation →](docs/README.md)**

### Documentation Highlights
- **[Graphics Pipeline](docs/graphics-pipeline.md)**: Complete modern OpenGL pipeline implementation
- **[GPU Operations](docs/gpu-operations.md)**: Deep dive into GPU architecture and OpenGL technical details
- **[Software Architecture](docs/architecture.md)**: TDD approach, design patterns, and clean code practices
- **[Testing & Monitoring](docs/testing-monitoring.md)**: Comprehensive testing strategies and runtime monitoring
- **[Maintenance Rules](docs/maintenance-rules.md)**: Guidelines for keeping documentation current

## Graphics Programming Concepts Covered

This project demonstrates:

1. **OpenGL Context Management**: Creating and managing OpenGL contexts
2. **Vertex Buffer Objects (VBOs)**: Efficient vertex data storage
3. **Vertex Array Objects (VAOs)**: Modern OpenGL state management
4. **Shader Programming**: Writing and compiling GLSL shaders
5. **Matrix Transformations**: Model, View, and Projection matrices
6. **Resource Management**: Proper cleanup of GPU resources
7. **Test-Driven Development**: TDD approach for graphics programming
8. **Mock-Based Testing**: Hardware-independent testing strategies

## Development Notes

- Check `.cursor/rules.md` for AI prompting preferences
- All OpenGL resources must be explicitly cleaned up
- Use JOML for all mathematical operations
- Keep shaders in separate files under `src/main/resources/shaders/` 