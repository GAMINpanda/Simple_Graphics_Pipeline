# Documentation Maintenance Rules

## Core Principle
**Every new feature, architectural change, or complex implementation MUST be documented before the pull request is merged.**

## When to Update Documentation

### 1. New Graphics Pipeline Features
**Trigger**: Adding new rendering capabilities, shaders, or pipeline stages
**Required Updates**:
- Update `graphics-pipeline.md` with new pipeline stages
- Add code references and technical implementation details
- Include GPU operation explanations in `gpu-operations.md`
- Update pipeline data flow diagrams

**Example Scenarios**:
- Adding texture mapping → Document texture pipeline stage
- Implementing MVP matrices → Document transformation pipeline
- Adding depth testing → Document depth buffer operations
- New shader types → Document shader compilation process

### 2. New Architecture Components
**Trigger**: Adding new classes, design patterns, or architectural layers
**Required Updates**:
- Update `architecture.md` with new component relationships
- Document new design patterns or architectural decisions
- Add component interaction diagrams
- Update separation of concerns explanations

**Example Scenarios**:
- Adding Material class → Document material system architecture
- Implementing Scene Graph → Document hierarchical rendering architecture
- New resource managers → Document resource management patterns
- Camera system → Document view/projection architecture

### 3. Testing and Monitoring Changes
**Trigger**: New testing approaches, monitoring systems, or validation logic
**Required Updates**:
- Update `testing-monitoring.md` with new testing patterns
- Document new validation rules or error handling
- Add examples of new test categories
- Update monitoring implementation details

**Example Scenarios**:
- Performance profiling → Document profiling methodology
- New mock implementations → Document mock architecture
- Integration tests → Document integration test approach
- Runtime metrics → Document monitoring systems

### 4. GPU/OpenGL Technical Changes
**Trigger**: New OpenGL features, GPU optimizations, or graphics API usage
**Required Updates**:
- Update `gpu-operations.md` with new GPU concepts
- Document OpenGL state changes and optimizations
- Add performance analysis for new features
- Update memory management explanations

**Example Scenarios**:
- Uniform buffer objects → Document UBO usage and benefits
- Instanced rendering → Document instancing GPU operations
- Compute shaders → Document compute pipeline
- New OpenGL extensions → Document extension usage

## Documentation Update Process

### Step 1: Code Review Checklist
Before approving any pull request, reviewers must verify:

- [ ] **Graphics Pipeline Impact**: Does this change affect rendering pipeline?
- [ ] **Architecture Changes**: Does this introduce new components or patterns?
- [ ] **Testing Changes**: Does this modify testing approach or add new test categories?
- [ ] **GPU/OpenGL Changes**: Does this use new OpenGL features or GPU concepts?
- [ ] **Documentation Updated**: Are all relevant documentation files updated?

### Step 2: Documentation Quality Standards
Each documentation update must include:

1. **Code References**: Specific line numbers and file locations
   ```markdown
   **Location**: `TriangleRenderer.setupBuffers()` in `Main.java` lines 163-188
   ```

2. **Technical Explanations**: Why the implementation works this way
   ```markdown
   **GPU Memory Management**:
   - **Buffer Type**: `GL_ARRAY_BUFFER` for vertex attributes
   - **Usage Pattern**: `GL_STATIC_DRAW` optimizes for data uploaded once, drawn many times
   ```

3. **Code Examples**: Actual code snippets with explanations
   ```java
   // Example implementation with explanation
   GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertexBuffer, GL15.GL_STATIC_DRAW);
   ```

4. **Diagrams When Appropriate**: Mermaid diagrams for complex relationships
   ```mermaid
   graph TD
       A[Component A] --> B[Component B]
   ```

### Step 3: Documentation Review Process
1. **Technical Review**: Ensure technical accuracy of explanations
2. **Code Reference Verification**: Confirm all line numbers and file references are current
3. **Completeness Check**: Verify all aspects of the change are documented
4. **Clarity Review**: Ensure explanations are understandable to intended audience

## Documentation Maintenance Schedule

### Immediate Updates (With Each PR)
- Code reference line numbers
- New feature explanations
- Architecture diagrams
- Technical implementation details

### Monthly Reviews
- Review all documentation for accuracy
- Update outdated line number references
- Consolidate related documentation sections
- Verify all examples still work

### Major Version Updates
- Comprehensive documentation review
- Restructure documentation if architecture significantly changed
- Update all diagrams and code examples
- Review and update maintenance rules if needed

## Automation Opportunities

### Documentation Validation
Consider implementing automated checks:

```yaml
# GitHub Actions example
- name: Check Documentation Updates
  run: |
    # Verify that graphics pipeline changes include doc updates
    if git diff --name-only | grep -q "renderer/"; then
      if ! git diff --name-only | grep -q "docs/"; then
        echo "Graphics code changed but no documentation updated"
        exit 1
      fi
    fi
```

### Link Validation
```yaml
# Automated verification of code references
- name: Validate Code References
  run: |
    # Check that all documented line numbers exist
    grep -r "lines [0-9]" docs/ | while read ref; do
      # Validate each line number reference
    done
```

## Special Cases

### Breaking Changes
When making breaking changes to APIs or architecture:
1. Document the old approach in a "Migration" section
2. Explain why the change was necessary
3. Provide examples of updating existing code
4. Update all affected documentation sections

### Performance Optimizations
When adding performance optimizations:
1. Document the performance characteristics
2. Explain the trade-offs made
3. Include benchmarking methodology if applicable
4. Update GPU operations documentation with efficiency explanations

### Experimental Features
For experimental or preview features:
1. Mark sections as "Experimental" or "Preview"
2. Document known limitations
3. Explain the experimental nature
4. Provide feedback mechanisms

## Documentation Quality Metrics

Track documentation health through:
- **Coverage**: Percentage of code with corresponding documentation
- **Freshness**: How recently documentation was updated relative to code changes
- **Accuracy**: Verification that code references are current
- **Completeness**: All architectural components documented

## Responsibility Matrix

| Change Type | Primary Docs | Secondary Docs | Reviewer Focus |
|-------------|--------------|----------------|----------------|
| Pipeline Stage | graphics-pipeline.md | gpu-operations.md | Technical accuracy |
| New Component | architecture.md | testing-monitoring.md | Design patterns |
| OpenGL Feature | gpu-operations.md | graphics-pipeline.md | GPU concepts |
| Test Pattern | testing-monitoring.md | architecture.md | Test methodology |

This maintenance system ensures documentation remains a living, accurate resource that grows with the codebase complexity. 