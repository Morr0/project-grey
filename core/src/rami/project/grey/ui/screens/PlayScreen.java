package rami.project.grey.ui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import java.util.Map;

import rami.project.grey.Game;
import rami.project.grey.core.entity.IEntity;
import rami.project.grey.core.entity.chika.BigChika;
import rami.project.grey.core.entity.chika.Chika;
import rami.project.grey.core.gridsystem.GridManager;
import rami.project.grey.core.spawning.Spawner;
import rami.project.grey.gameplay.PlayerController;
import rami.project.grey.ui.util.PixelGridCalculator;

public class PlayScreen extends BaseScreen {

    // BASE VARIABLES
    private OrthographicCamera camera;
    private SpriteBatch batch;

    // Grid system
    private PixelGridCalculator gCal;
    private GridManager gManager;
    private int gridColumns, gridRows;

    // Initialise the stuff here
    public PlayScreen(Game game){
        super(game, Screen.PLAY);

        camera = new OrthographicCamera(gWidth, gHeight);
        // To keep the camera centred
        camera.translate(gWidth / 2, gHeight / 2);
        camera.update();

        batch = new SpriteBatch();
        batch.setProjectionMatrix(camera.combined);

        updateGridSystem();

        initDrawables();
        initControllers();
    }

    private void updateGridSystem(){
        // Initialising the grid manager
        gridColumns = gridRows = 9;
        gCal = new PixelGridCalculator(gWidth, gHeight, gridColumns, gridRows);
        gManager = new GridManager(gridColumns, gridRows);
    }

    private void initDrawables(){
        background = new ScreenBackground(game, this, batch);

        bigChikaX = bigChikaY = 4;
        bigChika = new Sprite(game.res.getChika1(), gCal.gridWidth, gCal.gridHeight);
        Vector2 bigChikaCoords = gCal.getPixelPosFromGrid(bigChikaX, bigChikaY);
        bigChika.setPosition(bigChikaCoords.x, bigChikaCoords.y);
    }

    private void initControllers(){
        // Player controller
        playerView = new BigChika();
        controller = new PlayerController(playerView);

        // Spawning
        spawner = new Spawner(gManager, gridColumns, gridRows, controller.getMaximumAllowableSpawns());
    }

    private BigChika playerView;

    /* ALL UNITS ARE IN GRID UNLESS SPECIFIED OTHERWISE */
    // UPDATING/DRAWING VARIABLES
    private ScreenBackground background;

    private Sprite bigChika;
    private int bigChikaX, bigChikaY;

    // Gameplay variables
    private PlayerController controller;

    private Spawner spawner;

    @Override
    public void update(float dt) {
        background.update(dt);
        controller.update(background.getSpeed(), background.getAcceleration());
        spawner.update();

        log("Current score: " + controller.getScore());

        camera.update();
    }

    @Override
    public void draw(float dt) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        background.draw();
        bigChika.draw(batch);

        for (Map.Entry<IEntity, Vector2> entry: gManager.getEntities().entrySet()){
            Vector2 pixels = gCal.getPixelPosFromGrid((int) entry.getValue().x, (int) entry.getValue().y);
            drawEntity(entry.getKey(), pixels);
        }

        batch.end();


    }

    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
    }

    @Override
    protected void screenResolutionUpdated() {
        // TODO update things needing updating
    }

    // INPUT HANDLER


    @Override
    public boolean keyDown(int keycode) {
        switch (keycode){
            case Input.Keys.UP:

                break;
            case Input.Keys.DOWN:

                break;
            case Input.Keys.SPACE:
                background.toggleStop(false);
                break;
        }

        return super.keyDown(keycode);
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

    // ---------------------- UTILS ----------------------

    // DRAWING

    private void drawEntity(IEntity entity, Vector2 pos){
        if (entity instanceof Chika)
            drawChika((Chika) entity, pos);
    }

    private void drawChika(Chika entity, Vector2 pos){
        Chika.ChikaSize size = entity.getSize();
        Vector2 dimensions = new Vector2(gCal.gridWidth / size.towWeight, gCal.gridHeight / size.towWeight);
        batch.draw(bigChika.getTexture(), pos.x, pos.y, dimensions.x, dimensions.y);
    }
}
