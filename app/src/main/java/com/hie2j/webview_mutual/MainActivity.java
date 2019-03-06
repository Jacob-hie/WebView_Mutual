package com.hie2j.webview_mutual;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;

import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private WebView my_WebView;
    private String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        my_WebView = findViewById(R.id.my_webview);
        WebSettings webSettings = my_WebView.getSettings();
        //设置编码格式
        webSettings.setDefaultTextEncodingName("utf-8");
        // 设置允许JS弹窗
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        // 设置与Js交互的权限
        webSettings.setJavaScriptEnabled(true);
        // 步骤1：加载JS代码
        // 格式规定为:file:///android_asset/文件名.html
        my_WebView.loadUrl("file:///android_asset/test.html");

        my_WebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
                Log.e(TAG,"message = " + message);
                Uri uri = Uri.parse(message);
                if (uri.getScheme().equals("js")){
                    if (uri.getAuthority().equals("webview")){
                        if (uri.getQueryParameter("func").equals("showAlert")){
                            AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this)
                                    .setTitle("这是一个警告框！")
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            result.confirm();
                                            dialog.cancel();
                                        }
                                    })
                                    .create();
                            alertDialog.show();
                        }
                    }
                }
                return true;
            }

            @Override
            public boolean onJsConfirm(WebView view, String url, String message, final JsResult result) {
                Log.e(TAG,"message = " + message);
                Uri uri = Uri.parse(message);
                if (uri.getScheme().equals("js")){
                    if (uri.getAuthority().equals("webview")){
                        if (uri.getQueryParameter("func").equals("getInfo")){
                            AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this)
                                    .setTitle("你的性别是：")
                                    .setPositiveButton("男", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            result.confirm();
                                            dialog.cancel();
                                        }
                                    })
                                    .setNegativeButton("女", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            result.cancel();
                                            dialog.cancel();
                                        }
                                    })
                                    .create();
                            alertDialog.show();
                        }
                    }
                }
                return true;
            }

            @Override
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, final JsPromptResult result) {
                Log.e(TAG,"message = " + message);
                Uri uri = Uri.parse(message);
                final EditText editText = new EditText(MainActivity.this);
                if (uri.getScheme().equals("js")){
                    if (uri.getAuthority().equals("webview")){
                        if (uri.getQueryParameter("func").equals("getName")){
                            AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this)
                                    .setTitle("请输入你的名字：")
                                    .setView(editText)
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            String name = editText.getText().toString();
                                            result.confirm(name);
                                            dialog.cancel();
                                        }
                                    })
                                    .create();
                            alertDialog.show();
                        }
                    }
                }
                return true;
            }
        });

        my_WebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.e(TAG,"url = " + url);
                Uri uri = Uri.parse(url);
                Log.e(TAG,"Scheme = " + uri.getScheme());
                Log.e(TAG,"Authority = " + uri.getAuthority());

                if (uri.getScheme().equals("js")){
                    if (uri.getAuthority().equals("webview")){
//                        Set<String> paramSet = uri.getQueryParameterNames();
//                        for (String param : paramSet){
//                            String value = uri.getQueryParameter(param);
//                            Log.e(TAG,"param = " + param + ", value = " + value);
//                        }
                        String func = uri.getQueryParameter("func");
                        String id = uri.getQueryParameter("id");
                        Log.e(TAG,"func = " + func + ", id = " + id);
                        if (func.equals("showDetail")){
                            Intent intent = new Intent(MainActivity.this,DetailActivity.class);
                            intent.putExtra("id",id);
                            startActivity(intent);
                        }
                    }
                }
                return true;
            }
        });
    }
}
