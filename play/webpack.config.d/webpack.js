let devServer = config.devServer

// noinspection JSUnresolvedVariable
if (devServer) {

    config.mode = "development"

    devServer.before = function (app, server, compiler) {
        app.get('/__info__', function (req, res) {
            res.json({dir: __dirname, devServer, config});
        });
    }

    // devServer.hot = true
    devServer.port = 9990
    devServer.overlay = true
    devServer.open = false
    devServer.noInfo = false
    devServer.watchOptions = {
        aggregateTimeout: 100,
        poll: 100
    };
    devServer.stats = {
        warnings: true
    };
    // config.devServer.clientLogLevel = 'error';

    config.devtool = "source-map"
}

console.error(config)

// throw new Error(JSON.stringify(config))
