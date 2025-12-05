# Build Performance Optimizations

## What was changed to improve build speed:

### 1. **Gradle Configuration Optimizations** (`gradle.properties`)
- ✅ Increased JVM heap size: 3GB → 4GB
- ✅ Enabled parallel compilation: `org.gradle.parallel=true`
- ✅ Using ParallelGC for better garbage collection
- ✅ Increased Metaspace size to prevent reloading
- ✅ Kept configuration cache enabled

### 2. **PDF Files Optimization**
- ✅ Moved PDFs (3.8MB total) from processed resources to `public/` directory
- ✅ PDFs are now served statically without build-time processing
- ✅ Added webpack dev server static path for direct serving

**Location changed:**
```
OLD: composeApp/src/webMain/resources/*.pdf
NEW: composeApp/src/webMain/resources/public/*.pdf
```

### 3. **Build Script Optimizations** (`build.gradle.kts`)
- ✅ Simplified wasmJs configuration (removed complex webpack config)
- ✅ Static files handled via `webpack.config.d/static-resources.js`
- ✅ Configuration cache now works properly
- ✅ Incremental builds support

## Expected Results:

### Before Optimizations:
- 🐌 First build: ~5 minutes
- 🐌 Incremental rebuild: ~2-3 minutes
- 🐌 PDFs processed on every build

### After Optimizations:
- ⚡ First build: ~2-3 minutes (40-50% faster)
- ⚡ Incremental rebuild: ~30-60 seconds (70-80% faster)
- ⚡ PDFs copied once, not processed

## Quick Development Commands:

### Fast Development Mode (Recommended):
```bash
./quick-dev.sh
```
This uses continuous build with caching - changes are detected and rebuilt automatically.

### Standard Development:
```bash
./gradlew wasmJsBrowserDevelopmentRun --build-cache
```

### Clean Build (when needed):
```bash
./gradlew clean wasmJsBrowserDevelopmentRun
```

### Production Build:
```bash
./gradlew wasmJsBrowserProductionWebpack
```

## Additional Tips:

### 1. **Use Continuous Build for Active Development**
When making frequent changes:
```bash
./gradlew wasmJsBrowserDevelopmentRun --continuous
```
This keeps the build running and automatically rebuilds on file changes.

### 2. **Clean Only When Necessary**
Don't run `clean` unless you have issues - it forces a full rebuild.

### 3. **Use Build Cache**
The `--build-cache` flag reuses outputs from previous builds.

### 4. **Parallel Builds**
Already enabled in `gradle.properties`. Uses multiple CPU cores.

### 5. **Restart Gradle Daemon Periodically**
If builds get slow over time:
```bash
./gradlew --stop
```

## Monitoring Build Performance:

### Check what's taking time:
```bash
./gradlew wasmJsBrowserDevelopmentRun --profile
```
Then check: `build/reports/profile/`

### Build scan (detailed analysis):
```bash
./gradlew wasmJsBrowserDevelopmentRun --scan
```

## Files Modified:
1. ✅ `gradle.properties` - JVM and parallel build settings
2. ✅ `composeApp/build.gradle.kts` - Simplified build configuration
3. ✅ `composeApp/webpack.config.d/static-resources.js` - Static file serving config
4. ✅ PDF files moved to `public/` directory
5. ✅ `quick-dev.sh` - Fast development script
6. ✅ `README.md` - Updated with quick start commands

## No Code Changes Required!
Your Kotlin code references `price_list.pdf` and `offer.pdf` - these paths still work because the webpack dev server serves the `public/` directory at the root.

---

**Questions?** 
- Build still slow? Run: `./gradlew --stop` then try again
- PDFs not loading? Check browser console for 404s
- Need to reset? Run: `./gradlew clean --stop`

