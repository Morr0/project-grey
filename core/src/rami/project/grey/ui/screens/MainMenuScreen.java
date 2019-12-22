package rami.project.grey.ui.screens;

import rami.project.grey.Game;

public class MainMenuScreen extends BaseScreen {
    public MainMenuScreen(Game game) {
        super(game, Screen.MENU);
    }

    @Override
    public void update(float dt) {
        log("Main menu");
        if (true)
            game.switchScreen(Screen.PLAY);
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
