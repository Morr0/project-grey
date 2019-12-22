package rami.project.grey.ui.screens;

import rami.project.grey.Game;

public class SplashScreen extends BaseScreen {

    public SplashScreen(Game game, boolean extraLoading) {
        super(game, extraLoading? Screen.SPLASH_EXTRA: Screen.SPLASH);
    }

    @Override
    public void update(float dt) {
        if (game.res.finishedLoadingEverything())
            game.switchScreen(Screen.MENU);

        log("Still loading");
    }

    @Override
    public void draw(float dt) {

    }

    @Override
    public void dispose() {
        super.dispose();
    }

    @Override
    protected void screenResolutionUpdated() {

    }

    @Override
    protected void touchedOn(int screenX, int screenY) {

    }

    @Override
    protected void touchedOff(int screenX, int screenY) {

    }

    @Override
    protected void clicked(int screenX, int screenY, float elaspedMillisSeconds) {

    }
}
