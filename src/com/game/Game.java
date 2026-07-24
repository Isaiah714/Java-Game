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

class WindowSize {
	public static final int WIDTH = 960;
	public static final int HEIGHT = 960;
}

public class Game  {
	private long window;

	public void run() {
		init();
		loop();

		glfwFreeCallbacks(window);
		glfwDestroyWindow(window);

		glfwTerminate();
		glfwSetErrorCallback(null).free();
	}

	public void init() {
		GLFWErrorCallback.createPrint(System.err).set();

		if (!glfwInit()) {
			throw new IllegalStateException("Unable to initialize GLFW");
		}

		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

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

		//=----------------Game objects----------------=//
		Player player = new Player(window);
		Scene flowerScene = new Scene(LevelArea.FLOWERLAND, player);
		//=---------------------------------------------=//
		while(!glfwWindowShouldClose(window)) {
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

			//=-Render Here-=//
			flowerScene.renderScene();
			//=--------------=//

			glfwSwapBuffers(window);

			glfwPollEvents();
		}
	}

	public static void main(String[] args) {
		new Game().run();
	}
}
