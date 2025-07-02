#!/bin/bash

# GitHub Repository Setup Script
# This script helps you push your local repository to GitHub

echo "🚀 GitHub Repository Setup Script"
echo "=================================="

# Check if we're in a git repository
if [ ! -d ".git" ]; then
    echo "❌ Error: Not in a git repository"
    exit 1
fi

# Check if there are any commits
if ! git rev-parse --verify HEAD >/dev/null 2>&1; then
    echo "❌ Error: No commits found. Please commit your changes first."
    exit 1
fi

echo ""
echo "📋 Prerequisites:"
echo "1. You should have created a new repository on GitHub"
echo "2. You should have GitHub CLI installed OR know your repository URL"
echo ""

# Ask for repository information
read -p "Enter your GitHub username: " github_username
read -p "Enter your repository name (e.g., AIGraphicsPractice): " repo_name

# Construct the repository URL
repo_url="https://github.com/${github_username}/${repo_name}.git"

echo ""
echo "🔗 Repository URL: $repo_url"
echo ""

# Ask for confirmation
read -p "Is this correct? (y/n): " confirm
if [[ $confirm != [yY] && $confirm != [yY][eE][sS] ]]; then
    echo "❌ Setup cancelled"
    exit 1
fi

# Set up the remote
echo "🔧 Setting up remote origin..."
if git remote get-url origin >/dev/null 2>&1; then
    echo "⚠️  Remote 'origin' already exists. Removing it..."
    git remote remove origin
fi

git remote add origin "$repo_url"

# Push to GitHub
echo "📤 Pushing to GitHub..."
git branch -M main
git push -u origin main

if [ $? -eq 0 ]; then
    echo ""
    echo "✅ Success! Your repository has been pushed to GitHub"
    echo "🌐 Repository URL: https://github.com/${github_username}/${repo_name}"
    echo ""
    echo "🎯 Next steps:"
    echo "1. Visit your repository on GitHub"
    echo "2. Check that the CI/CD pipeline is running"
    echo "3. Review the documentation in the docs/ folder"
    echo "4. Start contributing to your graphics project!"
    echo ""
    echo "🔄 CI/CD Pipeline Features:"
    echo "- ✅ Automated testing on push/PR"
    echo "- 🏗️  Multi-platform builds (Linux, Windows, macOS)"
    echo "- 📊 Code quality checks"
    echo "- 🚀 Automated releases on main branch"
    echo "- 📋 Issue and PR templates"
else
    echo ""
    echo "❌ Failed to push to GitHub"
    echo "💡 Common solutions:"
    echo "1. Make sure the repository exists on GitHub"
    echo "2. Check your GitHub credentials"
    echo "3. Verify you have push access to the repository"
    echo ""
    echo "🔧 Manual setup:"
    echo "git remote add origin $repo_url"
    echo "git branch -M main"
    echo "git push -u origin main"
fi 