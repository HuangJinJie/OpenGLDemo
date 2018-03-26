package com.example.administrator.glsurfaceviewdemo.render;

import android.opengl.GLES20;

/**
 * <pre>
 *     author : Administrator (Jacket)
 *     e-mail : 378315764@qq.com
 *     time   : 2018/03/26
 *     desc   :
 *     version: 3.2
 * </pre>
 */

public class BaseRenderer {

    public int loadShader(int type, String shaderCode){
        //根据type创建顶点着色器或者片元着色器
        int shader = GLES20.glCreateShader(type);
        //将资源加入到着色器中，并编译
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);
        return shader;
    }
}
