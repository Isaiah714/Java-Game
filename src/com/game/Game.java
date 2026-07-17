package com.game;

import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.nio.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

class FilePath {
	public String vPath;
	public String fPath;
	public String tPath;

	public FilePath(String vertPath, String fragPath, String texPath) {
		this.vPath = vertPath;
		this.fPath = fragPath;
		this.tPath = texPath;
	}
}

public class Game  {
	private long window;
	private FilePath tile;

	public void run() {
		init();
		loop();

		glfwFreeCallbacks(window);
		glfwDestroyWindow(window);

		glfwTerminate();
		glfwSetErrorCallback(null).free();
	}

	public void init() {
		this.tile = new FilePath("/shaders/tile.vs", "/shaders/tile.fs", "/textures/tiles.jpg");
		GLFWErrorCallback.createPrint(System.err).set();

		if (!glfwInit()) {
			throw new IllegalStateException("Unable to initialize GLFW");
		}

		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

		window = glfwCreateWindow(960, 960, "Bullet Hell", NULL, NULL);
		if (window == NULL) {
			throw new RuntimeException("Failed to create the GLFW window");
		}

		window = glfwCreateWindow(960, 960, "Bullet Hell", NULL, NULL);
		if(window == NULL) {
			throw new RuntimeException("Failed to create window");
		}

		glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
			if(key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
				glfwSetWindowShouldClose(window, true);
			}
		});

		try( MemoryStack stack = stackPush()) {
			IntBuffer pWidth = stack.mallocInt(1);
			IntBuffer pHeight = stack.mallocInt(1);

			glfwGetWindowSize(window, pWidth, pHeight);

			GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
			glfwSetWindowPos(
				window,
				(vidmode.width() - pWidth.get(0)) / 2,
				(vidmode.height() - pHeight.get(0)) / 2 
			);
		}

		glfwMakeContextCurrent(window);
		glfwSwapInterval(1);

		glfwShowWindow(window);
	}

	public void loop() {
		GL.createCapabilities();

		glClearColor(0.4f, 0.4f, 1.0f, 1.0f);

		// ---------------Shader objects--------------- //
		Shader tile = Shader.createShader(this.tile.vPath, this.tile.fPath, this.tile.tPath, ShapeType.SQUARE);
		//tile.changeColor(0.0f, 0.0f, 0.0f);
		// --------------------------------------------- //
		while(!glfwWindowShouldClose(window)) {
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

			tile.draw();

			glfwSwapBuffers(window);

			glfwPollEvents();
		}
		tile.cleanup();
	}

	public static void main(String[] args) {
		new Game().run();
	}
}
