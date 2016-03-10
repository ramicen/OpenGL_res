package com.ramicen.opengl_res;

import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.GL_FLOAT;
import static android.opengl.GLES20.GL_TRIANGLES;
import static android.opengl.GLES20.GL_TRIANGLE_FAN;
import static android.opengl.GLES20.GL_LINES;
import static android.opengl.GLES20.GL_POINTS;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glViewport;
import static android.opengl.GLES20.glUseProgram;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glVertexAttribPointer;
import static android.opengl.GLES20.glEnableVertexAttribArray;
import static android.opengl.GLES20.glUniform4f;
import static android.opengl.GLES20.glDrawArrays;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLSurfaceView.Renderer;
import android.util.Log;

import com.ramicen.opengl_res.util.LoggerConfig;
import com.ramicen.opengl_res.util.TextResourceReader;

public class OpenGLResRenderer implements Renderer {

	private static final String A_POSITION = "a_Position";
	private int aPositionLocation;

	private final Context context;

	private static final int POSITION_COMPONENT_COUNT = 2;
	
	public static final int BYTES_PER_FLOAT = 4;
	private final FloatBuffer vertexData;
	
	private static final String A_COLOR = "a_Color";
	private static final int COLOR_COMPONENT_COUNT= 3;
	private static final int STRIDE= (POSITION_COMPONENT_COUNT + COLOR_COMPONENT_COUNT) * BYTES_PER_FLOAT;
	private int aColorLocation;

	// private final String TAG = "Renderer";

	private int program;

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		String vertexShaderCode = TextResourceReader.readTextFileFromResource(
				context, R.raw.simple_vertex_shader);

		String vertexShaderFragmentCode = TextResourceReader
				.readTextFileFromResource(context, R.raw.simple_fragment_shader);
			
		int vertexShader = ShaderHelper.compileVertexShader(vertexShaderCode);
		int fragmentShader = ShaderHelper
				.compileFragmentShader(vertexShaderFragmentCode);

		program = ShaderHelper.linkProgram(vertexShader, fragmentShader);

		if (LoggerConfig.ON) {

			ShaderHelper.validateProgram(program);
		}

		glUseProgram(program);

		aColorLocation = glGetAttribLocation(program, A_COLOR);
		aPositionLocation = glGetAttribLocation(program, A_POSITION);

		vertexData.position(0);
		glVertexAttribPointer(aPositionLocation, POSITION_COMPONENT_COUNT,
				GL_FLOAT, false, STRIDE, vertexData);
		glEnableVertexAttribArray(aPositionLocation);		
		
		vertexData.position(POSITION_COMPONENT_COUNT);
		glVertexAttribPointer(aColorLocation, COLOR_COMPONENT_COUNT, GL_FLOAT,
				false, STRIDE, vertexData);
		glEnableVertexAttribArray(aColorLocation);
	}

	public OpenGLResRenderer(Context context) {
		float[] tableVertices = {
				// Triangle 1
				//  0.00f,  0.00f,
				// -0.76f, -0.76f,
				//  0.76f, -0.76f,
				//  0.76f,  0.76f,
				// -0.76f,  0.76f,
				// -0.76f, -0.76f,
				
				// Triangle 1
				 0.00f,  0.00f, 1.0f, 1.0f, 1.0f,
				-0.75f, -0.75f, 0.7f, 0.7f, 0.7f,
				 0.75f, -0.75f, 0.7f, 0.7f, 0.7f,
				 0.75f,  0.75f, 0.7f, 0.7f, 0.7f,
				-0.75f,  0.75f, 0.7f, 0.7f, 0.7f,
				-0.75f, -0.75f, 0.7f, 0.7f, 0.7f,

				// Line
				-0.75f, 0.0f, 1.0f, 0.0f, 0.0f,
				 0.75f, 0.0f, 1.0f, 0.0f, 0.0f,

				// Mallets
				0.0f,  0.5f, 0.0f, 0.0f, 1.0f, 
				0.0f, -0.5f, 1.0f, 0.0f, 0.0f
				};

		this.context = context;

		vertexData = ByteBuffer
				.allocateDirect(tableVertices.length * BYTES_PER_FLOAT)
				.order(ByteOrder.nativeOrder()).asFloatBuffer();

		vertexData.put(tableVertices);
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		glViewport(0, 0, width, height);
	}

	@Override
	public void onDrawFrame(GL10 gl) {
		glClear(GL_COLOR_BUFFER_BIT);

		// glUniform4f(aColorLocation, 0.3f, 0.3f, 0.3f, 1.0f);
		// glDrawArrays(GL_TRIANGLE_FAN, 0, 6);

		// glUniform4f(aColorLocation, 1.0f, 1.0f, 1.0f, 1.0f);
		glDrawArrays(GL_TRIANGLE_FAN, 0, 6);

		// glUniform4f(aColorLocation, 1.0f, 0.0f, 0.0f, 1.0f);
		glDrawArrays(GL_LINES, 6, 2);

		// glUniform4f(aColorLocation, 0.0f, 0.0f, 1.0f, 1.0f);
		glDrawArrays(GL_POINTS, 8, 1);

		// glUniform4f(aColorLocation, 1.0f, 0.0f, 0.0f, 1.0f);
		glDrawArrays(GL_POINTS, 9, 1);
	}

}
