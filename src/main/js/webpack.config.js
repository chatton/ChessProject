const path = require("path");
const webpack = require("webpack");
module.exports = {
  entry: "./src/app.js",

  output: {
    path: path.join(__dirname, "..", "resources", "static"),
    filename: "bundle.min.js"
  },
  module: {
    rules: [
      {
        loader: "babel-loader",
        test: /\.js$/,
        exclude: /node_modules/
      }
    ]
  },
  plugins: [
    new webpack.optimize.UglifyJsPlugin({
      include: /\.min\.js$/,
      minimize: true
    })
  ],
  devtool: "cheap-module-eval-source-map"
};
