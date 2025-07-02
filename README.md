# Simple Graphics Pipeline

A Java 21 graphics project using LWJGL 3.3.3 and OpenGL for real-time rendering. This project demonstrates modern graphics programming techniques with professional development practices including comprehensive CI/CD, testing, and security scanning.

## 🚀 Quick Start

### Prerequisites
- **Java 21** (Temurin recommended)
- **Gradle** (wrapper included)
- **Git**
- **Docker** (optional, for local CI simulation)

### Development Setup
```bash
# Clone the repository
git clone https://github.com/GAMINpanda/Simple_Graphics_Pipeline.git
cd Simple_Graphics_Pipeline

# Make gradlew executable (if needed)
chmod +x gradlew

# Test the setup
./gradlew compileJava compileTestJava --no-daemon
```

## 🛠️ Development Workflow

### Before Every Push (MANDATORY)
**Always run these commands before pushing to prevent CI failures:**

```bash
# 1. Validate workflows
./scripts/validate-workflow.sh

# 2. Test locally (runs same commands as CI)
./scripts/test-ci-locally.sh
```

### Standard Development Process
```bash
# 1. Create feature branch
git checkout -b feature/my-new-feature

# 2. Make your changes
# ... edit files ...

# 3. Test locally (REQUIRED)
./scripts/test-ci-locally.sh

# 4. Commit and push
git add .
git commit -m "feat: add new graphics feature"
git push origin feature/my-new-feature

# 5. Create pull request via GitHub web interface
```

## 🧪 Testing

### Local Testing Scripts
- **`./scripts/test-ci-locally.sh`** - Runs exact CI commands locally
- **`./scripts/validate-workflow.sh`** - Validates GitHub Actions YAML
- **`act`** - Full CI simulation with Docker (optional)

### Test Categories
- **Compilation**: Java source compilation and test compilation
- **Unit Tests**: JUnit tests with graphics context handling
- **Graphics Tests**: OpenGL context creation and LWJGL functionality
- **Cross-Platform**: Multi-OS compatibility testing
- **Memory Management**: Native memory and resource cleanup
- **Shader Validation**: GLSL syntax checking

## 🎮 Graphics Features

### Core Components
- **Window Management**: GLFW-based window creation and event handling
- **OpenGL Context**: OpenGL 3.3+ core profile context management
- **Shader System**: GLSL shader compilation and program management
- **Geometry Rendering**: Vertex buffer objects and rendering primitives
- **Resource Management**: Proper cleanup of OpenGL and native resources

### LWJGL Integration
- **Native Memory**: Efficient memory stack usage and buffer management
- **Cross-Platform**: Support for Windows, macOS, and Linux
- **Error Handling**: Comprehensive OpenGL error checking and reporting
- **Debug Support**: Built-in debugging and profiling capabilities

## 🔧 Build System

### Gradle Tasks
```bash
# Compilation
./gradlew compileJava compileTestJava

# Testing
./gradlew test                          # Run all tests
./gradlew test --tests="*Window*"       # Run specific tests

# Building
./gradlew build                         # Full build with tests
./gradlew fatJar                        # Create executable JAR

# Cleaning
./gradlew clean                         # Clean build artifacts
```

### Cross-Platform Builds
The project automatically includes platform-specific LWJGL natives for:
- **Windows** (x64)
- **macOS** (ARM64 and Intel)
- **Linux** (x64)

## 🛡️ CI/CD Pipeline

### GitHub Actions Workflows
1. **Main CI Pipeline** (`.github/workflows/ci.yml`)
   - Compilation and basic testing
   - Cross-platform JAR building
   - Automated releases

2. **Graphics CI Pipeline** (`.github/workflows/graphics-ci.yml`)
   - OpenGL context testing
   - Shader compilation validation
   - Cross-platform graphics testing
   - Memory management verification

### Branch Protection
- Direct pushes to `main` are blocked
- All changes require pull requests
- CI checks must pass
- Code owner reviews required

## 📚 Documentation

### Development Guides
- **[Development Workflow](docs/development-workflow.md)** - Complete development process
- **[Graphics CI](docs/graphics-ci.md)** - Graphics testing pipeline
- **[Architecture](docs/architecture.md)** - System architecture overview

### Code Guidelines
- **[Cursor Rules](.cursorrules)** - Development standards and practices
- **Java 21** conventions and modern language features
- **Graphics-specific** patterns and LWJGL best practices
- **Testing requirements** and CI compliance

## 🔍 Troubleshooting

### Common Issues

#### Graphics Context Errors
```bash
# Enable LWJGL debugging
export LWJGL_DEBUG=true
./gradlew run
```

#### Build Failures
```bash
# Clean and rebuild
./gradlew clean build --no-daemon

# Check for permission issues
chmod +x gradlew
```

#### CI Failures
```bash
# Test locally first
./scripts/test-ci-locally.sh

# Validate workflows
./scripts/validate-workflow.sh
```

### Platform-Specific Notes

#### macOS
- Requires `-XstartOnFirstThread` JVM argument for GLFW
- OpenGL limited to 4.1 Core Profile
- Handle Retina display scaling

#### Windows
- Uses DirectX-compatible OpenGL drivers
- May require graphics driver updates
- Windows Defender can slow builds

#### Linux
- Uses Mesa drivers for software rendering in CI
- Install graphics libraries: `libgl1-mesa-dev`, `libglfw3-dev`
- Use Xvfb for headless testing

## 🤝 Contributing

### Getting Started
1. **Fork** the repository
2. **Follow** the development workflow
3. **Run** mandatory tests before pushing
4. **Create** focused pull requests
5. **Respond** to review feedback

### Code Quality
- **Format**: Follow Checkstyle rules (120 char limit)
- **Testing**: Include tests for new features
- **Documentation**: Update docs for significant changes
- **Commits**: Use conventional commit messages

### Review Process
- All PRs require approval from code owners
- CI checks must pass
- Graphics functionality must be tested
- Cross-platform compatibility verified

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🙏 Acknowledgments

- **LWJGL Team** - Lightweight Java Game Library
- **JOML** - Java OpenGL Math Library
- **GitHub Actions** - CI/CD automation
- **OpenGL Community** - Graphics programming resources 