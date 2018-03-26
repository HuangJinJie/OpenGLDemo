package com.example.administrator.glsurfaceviewdemo.activity;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.administrator.glsurfaceviewdemo.R;
import com.example.administrator.glsurfaceviewdemo.render.BackgroundRender;
import com.example.administrator.glsurfaceviewdemo.render.EquicruralTriangleRender;
import com.example.administrator.glsurfaceviewdemo.render.Square;
import com.example.administrator.glsurfaceviewdemo.render.TriangleColorRender;
import com.example.administrator.glsurfaceviewdemo.render.TriangleRender;

public class MainActivity extends AppCompatActivity {

    private String TAG = MainActivity.class.getSimpleName();
    private GLSurfaceView glSurfaceView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        glSurfaceView = (GLSurfaceView) findViewById(R.id.glSurfaceView);
        //GLContext设置OpenGLES2.0
        glSurfaceView.setEGLContextClientVersion(2);
        // 在setRenderer之前，可以调用以下方法进行EGL设置
//        glSurfaceView.setEGLConfigChooser(true);//颜色，深度，模板等等设置
//        glSurfaceView.setEGLWindowSurfaceFactory(new GLSurfaceView.EGLWindowSurfaceFactory() {   //窗口设置
//            @Override
//            public EGLSurface createWindowSurface(EGL10 egl10, EGLDisplay eglDisplay, EGLConfig eglConfig, Object o) {
//                return null;
//            }
//
//            @Override
//            public void destroySurface(EGL10 egl10, EGLDisplay eglDisplay, EGLSurface eglSurface) {
//
//            }
//        });
        glSurfaceView.setRenderer(new TriangleRender());
        /*渲染方式，RENDERMODE_WHEN_DIRTY表示被动渲染，只有在调用requestRender或者onResume等方法时才会进行渲染。RENDERMODE_CONTINUOUSLY表示持续渲染*/
        glSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }

    @Override
    protected void onResume() {
        super.onResume();
        glSurfaceView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        glSurfaceView.onPause();
    }
}
