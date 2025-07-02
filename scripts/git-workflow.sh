#!/bin/bash

# Git Workflow Helper Script
# Usage: ./scripts/git-workflow.sh <command> [arguments]

set -e

function show_help() {
    echo "Git Workflow Helper - Simple Graphics Pipeline"
    echo ""
    echo "Usage: $0 <command> [arguments]"
    echo ""
    echo "Commands:"
    echo "  feature <name>    Create a new feature branch"
    echo "  fix <name>        Create a new bugfix branch"
    echo "  docs <name>       Create a new documentation branch"
    echo "  test <name>       Create a new test branch"
    echo "  finish            Push current branch and prepare for PR"
    echo "  cleanup           Delete merged branches"
    echo "  status            Show git status and branch info"
    echo ""
    echo "Examples:"
    echo "  $0 feature add-lighting-system"
    echo "  $0 fix memory-leak-shader"
    echo "  $0 docs update-api-documentation"
    echo "  $0 finish"
}

function create_branch() {
    local type=$1
    local name=$2
    
    if [ -z "$name" ]; then
        echo "❌ Error: Branch name is required"
        echo "Usage: $0 $type <name>"
        exit 1
    fi
    
    # Sanitize branch name (replace spaces with dashes, lowercase)
    name=$(echo "$name" | tr '[:upper:]' '[:lower:]' | sed 's/ /-/g' | sed 's/[^a-z0-9-]//g')
    local branch_name="${type}/${name}"
    
    echo "🔄 Switching to main and pulling latest changes..."
    git checkout main
    git pull origin main
    
    echo "🌟 Creating and switching to branch: $branch_name"
    git checkout -b "$branch_name"
    
    echo "✅ Successfully created branch: $branch_name"
    echo ""
    echo "Next steps:"
    echo "1. Make your changes"
    echo "2. Commit with conventional commit messages:"
    echo "   git commit -m \"$type: your change description\""
    echo "3. When ready, run: $0 finish"
}

function finish_branch() {
    local current_branch=$(git branch --show-current)
    
    if [ "$current_branch" = "main" ] || [ "$current_branch" = "develop" ]; then
        echo "❌ Error: You're on the $current_branch branch. Switch to a feature branch first."
        exit 1
    fi
    
    echo "🔄 Pushing branch: $current_branch"
    git push -u origin "$current_branch"
    
    echo ""
    echo "✅ Branch pushed successfully!"
    echo "🌐 Create a Pull Request at:"
    echo "   https://github.com/GAMINpanda/Simple_Graphics_Pipeline/compare/$current_branch"
    echo ""
    echo "📋 PR Checklist:"
    echo "  ✅ Fill out the PR template"
    echo "  ✅ Wait for status checks to pass"
    echo "  ✅ Request review from maintainers"
    echo "  ✅ Address any feedback"
    echo "  ✅ Merge when approved"
}

function cleanup_branches() {
    echo "🧹 Cleaning up merged branches..."
    
    # Switch to main
    git checkout main
    git pull origin main
    
    # Delete local branches that have been merged
    git branch --merged | grep -v "main\|develop\|\*" | xargs -n 1 git branch -d || true
    
    # Prune remote tracking branches
    git remote prune origin
    
    echo "✅ Cleanup complete!"
}

function show_status() {
    echo "📊 Git Status and Branch Information"
    echo "=================================="
    echo ""
    
    echo "Current Branch:"
    git branch --show-current
    echo ""
    
    echo "Status:"
    git status --short
    echo ""
    
    echo "Recent Commits:"
    git log --oneline -10
    echo ""
    
    echo "Remote Branches:"
    git branch -r
}

# Main script logic
case "$1" in
    "feature")
        create_branch "feature" "$2"
        ;;
    "fix")
        create_branch "fix" "$2"
        ;;
    "docs")
        create_branch "docs" "$2"
        ;;
    "test")
        create_branch "test" "$2"
        ;;
    "finish")
        finish_branch
        ;;
    "cleanup")
        cleanup_branches
        ;;
    "status")
        show_status
        ;;
    "help"|"-h"|"--help"|"")
        show_help
        ;;
    *)
        echo "❌ Unknown command: $1"
        echo ""
        show_help
        exit 1
        ;;
esac 