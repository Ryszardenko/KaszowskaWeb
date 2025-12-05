// Add public directory as static content for development server
const path = require('path');

// Resolve the public directory path
const publicDir = path.resolve(__dirname, '../../src/webMain/resources/public');

// Configure static file serving for development
if (config.devServer) {
    config.devServer.static = config.devServer.static || [];

    if (Array.isArray(config.devServer.static)) {
        config.devServer.static.push({
            directory: publicDir,
            publicPath: '/',
            watch: true
        });
    }

    console.log('📁 Serving static files from:', publicDir);
}

