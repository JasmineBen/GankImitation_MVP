import 'dart:async';
import 'dart:ui';

import 'package:flutter/material.dart';
import 'package:package_info/package_info.dart';
import 'pages/webview.dart';

Future<void> main() async {
  final PackageInfo packageInfo = await PackageInfo.fromPlatform();
  runApp(Directionality(textDirection: TextDirection.ltr, child: Router(packageInfo)));
}

class Router extends StatelessWidget {
  Router(this.packageInfo);

  final PackageInfo packageInfo;

  @override
  Widget build(BuildContext context) {
    final String route = window.defaultRouteName;
    Widget home = null;
    if(route != null){
      if(route.startsWith('webfragment://')){
        String url = route.substring('webfragment://'.length);
        home = new Webview(url);
      }
    }
    if(home != null){
      return new MaterialApp(
        theme: new ThemeData(
          primaryColor: const Color(0xFF8BC34A),
          primaryColorDark: const Color(0xFF689F38),
          accentColor: const Color(0xFFFF5252),
        ),
        home: home,
      );
    }else {
      return null;
    }
  }
}
