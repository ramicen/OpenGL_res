package com.ramicen.opengl_res;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.Menu;

public class OGLResActivity extends Activity {
	
	private GLSurfaceView glSurfaceView;
	private OpenGLResRenderer renderer;

	private boolean rendererSet = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		glSurfaceView = new GLSurfaceView(this);
		renderer = new OpenGLResRenderer(this);

		glSurfaceView.setEGLContextClientVersion(2);

		glSurfaceView.setRenderer(renderer);
		rendererSet = true;
		this.setContentView(glSurfaceView);
	}

	@Override
	protected void onResume() {
		super.onResume();

		if (rendererSet) {
			glSurfaceView.onResume();
		}
	}

	@Override
	protected void onPause() {
		super.onPause();

		if (rendererSet) {
			glSurfaceView.onPause();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		return super.onCreateOptionsMenu(menu);
	}

}
