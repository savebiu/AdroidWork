package com.example.mytestprogram;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.VideoView;
import android.widget.MediaController;
import android.content.Intent;
import android.net.Uri;

import androidx.appcompat.app.AppCompatActivity;
//WebView加载b站视频
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class VideoActivity extends AppCompatActivity {

    private EditText videoUrlInput;
    private Button playButton;
    private VideoView videoView;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        //初始化Viewview和WebView
        videoView = findViewById(R.id.video_view);
        webView = findViewById(R.id.webview);       //调用b站网页

        // 启用 WebView 的 JavaScript
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());

        // 初始化 URL 输入框和播放按钮
        videoUrlInput = findViewById(R.id.videoUrlInput);
        playButton = findViewById(R.id.playButton);

        // 获取 MainActivity 传递的 URL
        Intent intent = getIntent();
        String videoUri = intent.getStringExtra("video_uri");

        if (videoUri != null) {
            if(videoUri.contains("http")){
                // 如果是 HTTP 链接，使用 WebView 加载
                webView.loadUrl(videoUri);
                webView.setVisibility(WebView.VISIBLE);
                videoView.setVisibility(VideoView.GONE);
            } else {
                // 否则使用 VideoView 播放本地视频
                playVideo(videoUri);
            }
        }

        playButton.setOnClickListener(v -> {
            String url = videoUrlInput.getText().toString();
            if (!url.isEmpty()) {
                if (url.contains("http")) {
                    // 使用 WebView 加载网络视频
                    webView.loadUrl(url);
                    webView.setVisibility(WebView.VISIBLE);
                    videoView.setVisibility(VideoView.GONE);
                } else {
                    // 使用 VideoView 播放本地视频
                    playVideo(url);
                }
            } else {
                Toast.makeText(VideoActivity.this, "请输入有效的视频链接", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void playVideo(String url) {
        videoView.setVisibility(VideoView.VISIBLE);
        webView.setVisibility(WebView.GONE);

        Uri videoUri = Uri.parse(url);
        videoView.setVideoURI(videoUri);
        videoView.setMediaController(new MediaController(this));
        videoView.setOnErrorListener((mp, what, extra) -> {
            Toast.makeText(VideoActivity.this, "视频加载失败，请检查链接", Toast.LENGTH_SHORT).show();
            return true;
        });
        videoView.start();
    }
}
