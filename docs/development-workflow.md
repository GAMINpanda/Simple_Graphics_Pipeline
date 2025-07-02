# Development Workflow

This document outlines the complete development workflow for the Simple Graphics Pipeline project, including mandatory testing procedures and best practices.

## Quick Start

### Essential Commands
```bash
# Create feature branch
git checkout -b feature/my-feature-name

# Test before pushing (MANDATORY)
./scripts/validate-workflow.sh
./scripts/test-ci-locally.sh

# Standard development cycle
git add .
git commit -m "feat: descriptive commit message"
git push origin feature/my-feature-name
```

## Pre-Push Testing (MANDATORY)

**NEVER push without running these tests first:**

### 1. Quick Validation
```bash
./scripts/validate-workflow.sh
```
- Checks YAML syntax in GitHub Actions workflows
- Validates workflow structure and common issues
- Fast execution (< 10 seconds)

### 2. Local CI Testing
```bash
./scripts/test-ci-locally.sh
```
- Runs the exact same commands as CI
- Tests compilation, unit tests, JAR building
- Validates graphics-specific functionality
- Takes 1-3 minutes depending on project size

### 3. Full CI Simulation (Optional)
```bash
# For major changes or workflow modifications
act -W .github/workflows/graphics-ci.yml --job graphics-context-test
act -W .github/workflows/ci.yml --job compile-and-test
```

## Development Workflow Steps

### 1. Branch Creation
```bash
# From main branch
git checkout main
git pull origin main

# Create feature branch
git checkout -b feature/add-lighting-system
# or
git checkout -b fix/shader-compilation-error
```

### 2. Development Process
- Make your code changes
- Write/update tests as needed
- Update documentation if required
- Follow the graphics coding guidelines in `.cursorrules`

### 3. Testing (Critical Step)
```bash
# Always run before committing
./scripts/validate-workflow.sh
./scripts/test-ci-locally.sh

# Check output for any failures or warnings
# Fix issues before proceeding
```

### 4. Committing Changes
```bash
# Stage changes
git add .

# Commit with conventional format
git commit -m "feat: add PBR material system with metallic-roughness workflow"
git commit -m "fix: resolve shader compilation error on Intel graphics drivers"
git commit -m "docs: update graphics pipeline documentation"
git commit -m "test: add unit tests for vertex buffer management"
```

### 5. Pre-Push Final Check
```bash
# Run tests one more time if you made significant changes
./scripts/test-ci-locally.sh

# Push to remote
git push origin feature/add-lighting-system
```

### 6. Pull Request Creation
1. Go to GitHub repository
2. Click "Compare & pull request"
3. Fill out PR template
4. Request reviews from code owners
5. Wait for CI checks to pass
6. Address any review feedback

## Testing Guidelines

### Local Test Expectations
- **Compilation**: Must pass without errors
- **Unit Tests**: Should complete (graphics warnings are normal locally)
- **JAR Build**: Must succeed
- **Graphics Tests**: May show warnings due to lack of display context

### When Tests Fail Locally
1. **Compilation Errors**: Fix before pushing
2. **Unit Test Failures**: Investigate and fix critical issues
3. **Graphics Context Warnings**: Usually safe to ignore locally
4. **JAR Build Failures**: Must be resolved

### CI-Only Testing
Some tests only run properly in CI environment:
- Full graphics context creation
- Cross-platform compatibility
- Memory profiling with specialized tools
- Platform-specific native library loading

## Graphics Development Specifics

### Graphics Code Guidelines
- Always initialize GLFW before OpenGL calls
- Use try-with-resources for native memory
- Implement proper resource cleanup
- Add OpenGL error checking in debug mode
- Handle headless environments gracefully

### Shader Development
- Place shaders in `src/main/resources/shaders/`
- Use `.vert` for vertex shaders, `.frag` for fragment shaders
- Always include `main()` function
- Test shader compilation in CI

### Cross-Platform Considerations
- Test on different operating systems when possible
- Use platform-specific flags (e.g., `-XstartOnFirstThread` for macOS)
- Handle different OpenGL driver behaviors
- Consider integrated vs discrete graphics

## Troubleshooting

### Common Issues

#### "gradlew permission denied"
```bash
chmod +x gradlew
git add gradlew
git commit -m "fix: make gradlew executable"
```

#### "Graphics context creation failed"
- Normal in headless environments
- Tests should handle gracefully with warnings
- Real testing happens in CI with virtual display

#### "Package not found" in CI
- Check package names in workflow files
- Ubuntu package names change between versions
- Test with local Docker if needed

#### "act command not found"
```bash
brew install act
```

### Debug Information
Enable detailed logging:
```bash
# Local testing with debug output
GRADLE_OPTS="-Dorg.lwjgl.util.Debug=true" ./scripts/test-ci-locally.sh

# GitHub Actions simulation with debug
act --verbose -W .github/workflows/graphics-ci.yml
```

## Branch Protection Compliance

This project uses branch protection rules:
- Direct pushes to `main` are forbidden
- All changes must go through pull requests
- PR must have at least 1 approval
- All CI checks must pass
- Code owners must review certain files

## Performance Tips

### Faster Local Testing
```bash
# Test only compilation (fastest)
./gradlew compileJava compileTestJava --no-daemon

# Test specific classes
./gradlew test --tests="*Window*" --no-daemon

# Skip tests during JAR building if already tested
./gradlew build -x test --no-daemon
```

### Gradle Optimization
- Use `--no-daemon` in CI scripts
- Enable caching in workflows (already configured)
- Clean build directory if seeing weird issues: `./gradlew clean`

## Integration with IDEs

### VS Code / Cursor
- Install Java Extension Pack
- Configure LWJGL/OpenGL syntax highlighting
- Set up tasks for common testing commands
- Use integrated terminal for script execution

### IntelliJ IDEA
- Import as Gradle project
- Configure run configurations with graphics properties
- Set up external tools for testing scripts
- Enable OpenGL/GLSL file support

## Contributing Guidelines

1. **Follow the workflow**: No exceptions to testing requirements
2. **Keep PRs focused**: One feature or fix per PR
3. **Update documentation**: If your change affects workflows
4. **Test thoroughly**: Both locally and review CI results
5. **Respond to feedback**: Address review comments promptly

## Continuous Improvement

This workflow will evolve as the project grows:
- Add more sophisticated testing as needed
- Integrate additional tools (profilers, debuggers)
- Enhance automation where beneficial
- Update based on team feedback and CI experiences 