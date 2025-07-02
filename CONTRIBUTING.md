# Contributing to Graphics Renderer

Thank you for your interest in contributing to the Graphics Renderer project! This document provides guidelines for contributing to the project.

## Development Setup

### Prerequisites
- Java 21 or higher
- Git
- A graphics card with OpenGL 3.3+ support

### Local Development
1. Clone the repository:
   ```bash
   git clone https://github.com/YOUR_USERNAME/AIGraphicsPractice.git
   cd AIGraphicsPractice
   ```

2. Build the project:
   ```bash
   ./gradlew build
   ```

3. Run tests:
   ```bash
   ./gradlew test
   ```

4. Run the application:
   ```bash
   ./gradlew run
   ```

## Code Style

- Follow standard Java conventions
- Use meaningful variable and method names
- Add comments for complex graphics/rendering logic
- Keep methods focused and concise
- Use proper exception handling

## Testing

- Write unit tests for new functionality
- Test graphics functionality on multiple platforms when possible
- Include integration tests for rendering pipeline changes
- Ensure all tests pass before submitting a PR

## Submitting Changes

1. Fork the repository
2. Create a feature branch: `git checkout -b feature/your-feature-name`
3. Make your changes
4. Add tests for new functionality
5. Commit your changes: `git commit -m "Add your descriptive commit message"`
6. Push to your fork: `git push origin feature/your-feature-name`
7. Submit a pull request

## Pull Request Guidelines

- Use the provided PR template
- Include a clear description of changes
- Reference any related issues
- Ensure CI/CD pipeline passes
- Add screenshots for visual changes
- Update documentation if needed

## Graphics-Specific Guidelines

- Test shader changes on different graphics drivers
- Consider performance implications of rendering changes
- Document any new graphics techniques used
- Ensure cross-platform compatibility
- Profile performance-critical code paths

## Reporting Issues

Use the provided issue templates for:
- Bug reports (include system specs and graphics info)
- Feature requests
- Performance issues

## Questions?

Feel free to open an issue for questions about contributing or the codebase. 