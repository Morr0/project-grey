package rami.project.grey.ui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import rami.project.grey.Game;
import rami.project.grey.core.entity.chika.BigChika;
import rami.project.grey.core.entity.chika.Chika;
import rami.project.grey.core.entity.consumable.attachables.IAttachable;
import rami.project.grey.core.entity.consumable.attachables.thruster.Thruster;
import rami.project.grey.core.gridsystem.Grid;
import rami.project.grey.core.gridsystem.GridManager;
import rami.project.grey.core.gridsystem.Spawner;
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

        initGrids();
        initDrawables();
        initControllers();
    }

    private void initGrids(){
        // Initialising the grid manager
        gridColumns = 9;
        gridRows = 9;
        gCal = new PixelGridCalculator(gWidth, gHeight, gridColumns, gridRows);
        gManager = new GridManager(gridColumns, gridRows);
    }

    private void initDrawables(){
        background = new ScreenBackground(game, this, batch);

        // Hud
        hud = new PlayerHud(gWidth, gHeight);
        hud.showFps = true;
    }

    private void initControllers(){
        // Player controller
        controller = new PlayerController(background, hud, gridColumns, gridRows, gManager);

        // Spawning
        spawner = new Spawner(gManager, controller.getMaximumAllowableSpawns(),
                (int) controller.gridPos.x, (int)controller.gridPos.y);
        controller.addSpawner(spawner);
    }

    /* ALL UNITS ARE IN GRID UNLESS SPECIFIED OTHERWISE */
    // UPDATING/DRAWING VARIABLES
    private ScreenBackground background;
    PlayerHud hud;

    // Gameplay variables
    private PlayerController controller;

    private Spawner spawner;

    @Override
    public void update(float dt) {
        controller.update(dt);

        log("Total items: " + controller.inventory.totalItems());

        // KEEP LAST
        camera.update();
    }

    @Override
    public void draw(float dt) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        background.draw();

        for (Grid grid: gManager.getOccupiedGrids()){
            drawGridResider(grid);
        }

        // Must be last because it is on top
        hud.draw(batch, controller.getScore(), Gdx.graphics.getFramesPerSecond());
        batch.end();
    }

    @Override
    public void dispose() {
        super.dispose();
        controller.endGame();
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
                    controller.moveUp();
                break;
            case Input.Keys.DOWN:
                    controller.moveDown();
                break;
            case Input.Keys.RIGHT:
                    controller.moveRight();
                break;
            case Input.Keys.LEFT:
                    controller.moveLeft();
                break;
            case Input.Keys.SPACE:
                controller.toggleStop();
                break;
            case Input.Keys.F:
                hud.showFps = !hud.showFps;
                break;
            case Input.Keys.T:
                controller.toggleThruster();
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

    private void drawGridResider(Grid grid){
        Vector2 pixelPos = gCal.getPixelPosFromGrid(grid.x, grid.y);

        if (grid.currentResider instanceof Chika)
            drawChika((Chika) grid.currentResider, pixelPos);
        else if (grid.currentResider instanceof IAttachable)
            drawAttachments((IAttachable) grid.currentResider, pixelPos);
    }

    private void drawAttachments(IAttachable attachment, Vector2 pixels){
        if (attachment instanceof Thruster){
            batch.draw(game.res.getBackground(), pixels.x, pixels.y, gCal.gridWidth, gCal.gridHeight);
        }
    }

    // This relays all attached towes to not be drawn by this but from the parent
    private void drawChika(Chika chika, Vector2 pixels){
        if (chika instanceof BigChika)
            drawBigChika();
        else if (!chika.hasParent){
            Chika.ChikaSize size = chika.getSize();
            Vector2 dimensions = new Vector2(gCal.gridWidth / size.number, gCal.gridHeight / size.number);
            batch.draw(game.res.getChika1(), pixels.x, pixels.y, dimensions.x, dimensions.y);
        }
    }

    // This draws the big chika and all towed chikas
    private void drawBigChika(){
        // Drawing the BigChika first
        Vector2 pixels = gCal.getPixelPosFromGrid(controller.gridPos);
        batch.draw(game.res.getBigChika(), pixels.x, pixels.y, gCal.gridWidth, gCal.gridHeight);

        // Drawing the towed ones
//        for (int i = 1; i <= playerView.getNoTows(); i++){
//            batch.draw(game.res.getTowedChika(), pixels.x, pixels.y + (i * gCal.gridHeight));
//        }
    }
}
