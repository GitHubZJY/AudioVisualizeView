# AudioVisualizeView [![](https://jitpack.io/v/GitHubZJY/AudioVisualizeView.svg)](https://jitpack.io/#GitHubZJY/AudioVisualizeView)
    许多音乐App上常见的音频可视化效果，读取音频数据并利用傅里叶转换成可视化的字节数据，再通过自定义View渲染而成.
    组件源码目录：https://github.com/GitHubZJY/AudioVisualizeView/tree/master/audiovisualize
    demo目录：https://github.com/GitHubZJY/AudioVisualizeView/tree/master/app

## 特性
1. 支持自定义可视化颜色 <br/>
2. 支持本地路径、url、raw的音频路径读取，或自定义外部MediaPlayer传入音频数据渲染 <br/>
3. 支持自定义频谱展示数目 <br/>
4. 支持多种可视化展示形式，包括圆形、水平、波浪、网状、粒子等 <br/>
5. 支持AndroidX <br/>

## 效果预览
![](https://github.com/GitHubZJY/AudioVisualizeView/blob/master/image/SingleVisualize.gif)
![](https://github.com/GitHubZJY/AudioVisualizeView/blob/master/image/ReflectVisualize.gif)
![](https://github.com/GitHubZJY/AudioVisualizeView/blob/master/image/CircleVisualize.gif)
![](https://github.com/GitHubZJY/AudioVisualizeView/blob/master/image/WaveVisualize.gif)
![](https://github.com/GitHubZJY/AudioVisualizeView/blob/master/image/NetVisualize.gif)
![](https://github.com/GitHubZJY/AudioVisualizeView/blob/master/image/GrainVisualize.gif)

## 如何使用
在项目根目录的build.gradle添加：
```
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

在项目的build.gradle添加如下依赖：
```
implementation 'com.github.GitHubZJY:AudioVisualizeView:v1.0.0'
```

### 1.申请录音权限和读写权限
```xml
<uses-permission android:name="android.permission.RECORD_AUDIO" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
```
由于需要读取音频数据和音频文件，需要先动态申请所需的权限，例：
```java
if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
    int checkCallPhonePermission = ContextCompat.checkSelfPermission(activity, Manifest.permission.RECORD_AUDIO);
    if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
        ActivityCompat.requestPermissions(activity, new String[]{permission}, requestCode);
    }
}
```
详情可参考本项目demo

### 2.在xml中引用

```xml
<com.zjy.audiovisualize.view.SingleVisualizeView
        android:id="@+id/audio_visualize_view"
        app:visualize_ratio="2"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content" />
```

### 3.在代码中初始化
```java
public class SingleVisualizeActivity extends AppCompatActivity {

    private AudioVisualizeView vAudioVisualize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_visualize);

        vAudioVisualize = findViewById(R.id.audio_visualize_view);
        vAudioVisualize.doPlay(R.raw.sound);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (vAudioVisualize != null) {
           vAudioVisualize.release();
        }
    }
}
```
在需要播放的位置，调用 `doPlay` 方法，并传入对应的音频资源id或者音频文件路径，即可开始播放。
在页面销毁时记得调用release释放对应的音频资源，避免内存泄漏。

## About Me
    一个在奋斗路上的Android小生
    欢迎关注简书: https://www.jianshu.com/u/4cb2688ddf31
