import 'package:flutter/material.dart';
import 'package:flutter_webview_plugin/flutter_webview_plugin.dart';
import 'package:flutter/services.dart';

class Webview extends StatelessWidget {
  final String url;

  Webview(this.url);
  static const _platform = const MethodChannel('app.channel.closeActivity');

  @override
  Widget build(BuildContext context) {
    return new WebviewScaffold(
      appBar: new AppBar(
        title: new Text('Web', style: new TextStyle(color: Colors.white)),
        iconTheme: IconThemeData(color: Colors.white),
        leading: new IconButton(
          icon: new Icon(Icons.arrow_back),
          onPressed: _onBackPressed)
      ),
      url: url,
      withJavascript: true,
      withLocalStorage: true,
      withZoom: false,
    );
  }

  void _onBackPressed(){
    Map argument = new Map();
    var result = _platform.invokeMethod('closeActivity',argument);
  }
}
