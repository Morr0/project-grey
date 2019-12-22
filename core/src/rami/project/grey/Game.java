package rami.project.grey;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;

import rami.project.grey.ui.resources.ResourcesManager;
import rami.project.grey.ui.screens.BaseScreen;
import rami.project.grey.ui.screens.MainMenuScreen;
import rami.project.grey.ui.screens.PlayScreen;
import rami.project.grey.ui.screens.SplashScreen;

// CODE NOTES
/*
* -Each screen takes care of drawing and updating frames to have a complete control of everything.
* -The reason for many screens is for expansion and ease of code.
*
*
* */

public class Game extends ApplicationAdapter {
	private BaseScreen currentScreen;

	public ResourcesManager res;

	public boolean needsExtraLoading = false;
	
	@Override
	public void create () {
		res = new ResourcesManager();
		startGame();
	}

	@Override
	public void render () {
		float dt = Gdx.graphics.getDeltaTime();

		if (res.finishedLoadingEverything())
			res.assignReferences();

		currentScreen.update(dt);
		currentScreen.draw(dt);
	}
	
	@Override
	public void dispose () {
		currentScreen.dispose();
		res.dispose();
	}

	// Should only be called once
	public void startGame(){
		switchScreen(BaseScreen.Screen.SPLASH);
	}

	public void switchScreen(BaseScreen.Screen newScreen){
		if (currentScreen != null) {
			currentScreen.dispose();
		}

		switch (newScreen){
			default: case SPLASH: case SPLASH_EXTRA:
				currentScreen = new SplashScreen(this, needsExtraLoading);
				break;
			case MENU:
				currentScreen = new MainMenuScreen(this);
				break;
			case PLAY:
				currentScreen = new PlayScreen(this);
				break;
		}
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		currentScreen.updateResolution();
	}
}
