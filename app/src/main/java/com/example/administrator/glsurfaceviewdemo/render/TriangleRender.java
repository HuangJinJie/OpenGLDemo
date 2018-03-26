package com.example.administrator.glsurfaceviewdemo.render;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * <pre>
 *     author : Administrator (Jacket)
 *     e-mail : 378315764@qq.com
 *     time   : 2018/03/02
 *     desc   :
 *     version: 3.2
 * </pre>
 */

public class TriangleRender extends BaseRenderer implements GLSurfaceView.Renderer {

    private int mProgram;
    private FloatBuffer vertexBuffer;

    private final String vertexShaderCode =
            "attribute vec4 vPosition;" +
                    "void main() {" +
                    "  gl_Position = vPosition;" +
                    "}";

    private final String fragmentShaderCode =
            "precision mediump float;" +
                    "uniform vec4 vColor;" +
                    "void main() {" +
                    "  gl_FragColor = vColor;" +
                    "}";

    static float triangleCoords[] = {
            0.5f,  0.5f, 0.0f, // top
            -0.5f, -0.5f, 0.0f, // bottom left
            0.5f, -0.5f, 0.0f  // bottom right
    };

    //设置颜色，依次为红绿蓝和透明通道
    float color[] = { 1.0f, 0f, 0f, 1.0f };
    static final int COORDS_PER_VERTEX = 3;

    private int mPositionHandle;
    private int mColorHandle;

    //顶点个数
    private final int vertexCount = triangleCoords.length / COORDS_PER_VERTEX;
    //顶点之间的偏移量
    private final int vertexStride = COORDS_PER_VERTEX * 4; // 每个顶点四个字节


    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        //将背景设置为灰色
        GLES20.glClearColor(0.5f,0.5f,0.5f,1.0f);
        //申请底层空间
        ByteBuffer bb = ByteBuffer.allocateDirect(triangleCoords.length * 4);
        bb.order(ByteOrder.nativeOrder());
        //将坐标数据转换为FloatBuffer，用以传入OpenGL ES程序
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(triangleCoords);
        vertexBuffer.position(0);

        int vertexShader =  loadShader(GLES20.GL_VERTEX_SHADER,vertexShaderCode);
        int fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER,fragmentShaderCode);

        //创建一个空的OpenGLES程序
        mProgram = GLES20.glCreateProgram();
        //将顶点着色器加入到程序
        GLES20.glAttachShader(mProgram,vertexShader);
        //将片元着色器加入到程序中
        GLES20.glAttachShader(mProgram,fragmentShader);
        //连接到着色器程序
        GLES20.glLinkProgram(mProgram);
    }





    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        GLES20.glViewport(0,0,width,height);
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        //将程序加入到OpenGLES2.0环境
        GLES20.glUseProgram(mProgram);

        //获取顶点着色器的vPosition成员句柄
        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");
        //启用三角形顶点的句柄
        GLES20.glEnableVertexAttribArray(mPositionHandle);
        //准备三角形的坐标数据
        GLES20.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX,
                GLES20.GL_FLOAT, false,
                vertexStride, vertexBuffer);
        //获取片元着色器的vColor成员的句柄
        mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");
        //设置绘制三角形的颜色
        GLES20.glUniform4fv(mColorHandle, 1, color, 0);
        //绘制三角形
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);
        //禁止顶点数组的句柄
        GLES20.glDisableVertexAttribArray(mPositionHandle);
    }
}
