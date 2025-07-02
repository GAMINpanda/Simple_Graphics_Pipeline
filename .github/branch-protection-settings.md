# Branch Protection Rules Setup

This document explains how to configure branch protection rules for your repository to enforce proper Git workflow and security.

## Required Setup (Via GitHub Web Interface)

### 1. Navigate to Branch Protection Settings
1. Go to your repository on GitHub
2. Click **Settings** tab
3. Click **Branches** in the left sidebar
4. Click **Add rule** or edit existing rule for `main` branch

### 2. Branch Protection Rule Configuration

#### Branch name pattern: `main`

**✅ Enable these protections:**

- **Require a pull request before merging**
  - ✅ Require approvals: `1` (minimum)
  - ✅ Dismiss stale PR approvals when new commits are pushed
  - ✅ Require review from code owners (uses CODEOWNERS file)

- **Require status checks to pass before merging**
  - ✅ Require branches to be up to date before merging
  - **Required status checks:**
    - `Validate PR`
    - `Security Scan`
    - `Code Quality Check`
    - `Dependency Review`
    - `Compile & Test` (from main CI workflow)

- **Require conversation resolution before merging**

- **Require signed commits** (optional but recommended)

- **Require linear history** (prevents merge commits)

- **Do not allow bypassing the above settings**
  - ❌ Allow force pushes
  - ❌ Allow deletions

### 3. Additional Security Settings

#### Security & Analysis (Repository Settings > Security & analysis)
- ✅ **Dependency graph**
- ✅ **Dependabot alerts**
- ✅ **Dependabot security updates**
- ✅ **Secret scanning**
- ✅ **Push protection** (prevents committing secrets)

#### Actions Settings (Repository Settings > Actions > General)
- **Actions permissions**: Allow all actions and reusable workflows
- **Artifact and log retention**: 90 days
- **Fork pull request workflows**: Require approval for first-time contributors

## Recommended Git Workflow

### 1. Creating Feature Branches
```bash
# Create and switch to new feature branch
git checkout -b feature/add-new-shader
git checkout -b fix/memory-leak
git checkout -b docs/update-readme
```

### 2. Branch Naming Convention
- `feature/` - New features
- `fix/` - Bug fixes  
- `docs/` - Documentation updates
- `refactor/` - Code refactoring
- `test/` - Test improvements
- `ci/` - CI/CD improvements

### 3. Making Changes
```bash
# Make your changes
git add .
git commit -m "feat: add new particle shader system"
git push origin feature/add-new-shader
```

### 4. Creating Pull Requests
1. Push your branch to GitHub
2. Create Pull Request via GitHub web interface
3. Fill out the PR template
4. Wait for status checks to pass
5. Request review from maintainers
6. Address any feedback
7. Merge when approved

## Commit Message Format

Use conventional commits for automatic changelog generation:

- `feat:` - New features
- `fix:` - Bug fixes
- `docs:` - Documentation changes
- `style:` - Code style changes
- `refactor:` - Code refactoring
- `perf:` - Performance improvements
- `test:` - Test changes
- `chore:` - Build process or auxiliary tool changes
- `ci:` - CI configuration changes

Example: `feat: add real-time shadow rendering` 