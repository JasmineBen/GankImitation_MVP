# GankImitation_MVP
基于"干货集中营"的开放API，采用MVP架构、RxJava、dagger2、glide、retrofit、GreenDao、butterknife、rxpermissions2等技术实现了一个简单的Gank客户端

![image](https://github.com/JasmineBen/GankImitation_MVP/blob/master/images/1.png)

![image](https://github.com/JasmineBen/GankImitation_MVP/blob/master/images/2.png)
![image](https://github.com/JasmineBen/GankImitation_MVP/blob/master/images/3.png)

2018.7.9
引入了Flutter技术，在现有的Native工程中增加了Flutter module，这样就可以在原生应用中使用FlutterView或者FlutterFragment。关于Flutter_module的引用方式如下：
https://github.com/flutter/flutter/wiki/Add-Flutter-to-existing-apps#experiment-turn-the-flutter-project-into-a-module

注意事项：需要将gradle升级到 classpath 'com.android.tools.build:gradle:3.1.2'或者以上

解决FlutterView add到界面时的黑屏问题：参考FlutterWebActivity的实现