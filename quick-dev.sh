#!/bin/zsh

# Quick development build script
# This script runs continuous development with optimizations

echo "🚀 Starting quick development mode..."
echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"

# Use cached build for faster rebuilds
./gradlew wasmJsBrowserDevelopmentRun --continuous --build-cache --parallel

echo "✨ Development server stopped"

