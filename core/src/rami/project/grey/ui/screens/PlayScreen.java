package rami.project.grey.ui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import rami.project.grey.Game;
import rami.project.grey.core.entity.EntitySize;
import rami.project.grey.core.entity.chika.BigChika;
import rami.project.grey.core.entity.chika.Chika;
import rami.project.grey.core.entity.consumable.attachables.IAttachable;
import rami.project.grey.core.entity.consumable.attachables.thruster.Thruster;
import rami.project.grey.core.entity.consumable.loot.ILoot;
import rami.project.grey.core.entity.enemy.Enemy;
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

        // The grid manager stuff
        gridColumns = 9;
        gridRows = 9;
        gCal = new PixelGridCalculator(gWidth, gHeight, gridColumns, gridRows);
        gManager = GridManager.initGridManager(gridColumns, gridRows);

        initDrawables();
        initControllers();
    }

    private void initDrawables(){
        background = new ScreenBackground(game, this, batch);

        // Hud
        hud = new PlayerHud(gWidth, gHeight);
        hud.showFps = true;
    }

    private void initControllers(){
        // Player controller
        controller = new PlayerController(background, hud);

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
            case Input.Keys.R:
                if (controller.bigChika.canRelease())
                    controller.bigChika.release();
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
        Vector2 grids = gCal.getGridPosFromPixel(screenX, screenY);
        gManager.touchedAt(grids, controller.bigChika);
    }

    // ---------------------- UTILS ----------------------

    // DRAWING

    private void drawGridResider(Grid grid){
        Vector2 pixelPos = gCal.getPixelPosFromGrid(grid.x, grid.y);

        if (grid.currentResider instanceof Chika)
            drawChika((Chika) grid.currentResider, pixelPos);
        else if (grid.currentResider instanceof IAttachable)
            drawAttachments((IAttachable) grid.currentResider, pixelPos);
        else if (grid.currentResider instanceof ILoot)
            drawLoot((ILoot) grid.currentResider, pixelPos);
        else if (grid.currentResider instanceof Enemy)
            drawEnemy((Enemy) grid.currentResider, pixelPos);
    }

    private void drawAttachments(IAttachable attachment, Vector2 pixels){
        if (attachment instanceof Thruster){
            batch.draw(game.res.getBackground(), pixels.x, pixels.y, gCal.gridWidth, gCal.gridHeight);
        }
    }

    private void drawLoot(ILoot loot, Vector2 pixels){
        batch.draw(game.res.getCoin(), pixels.x, pixels.y, gCal.gridWidth, gCal.gridHeight);
    }

    private void drawEnemy(Enemy enemy, Vector2 pixels){
        batch.draw(game.res.getCoin(), pixels.x, pixels.y, gCal.gridWidth, gCal.gridHeight);
    }

    private void drawChika(Chika chika, Vector2 pixels){
        if (chika instanceof BigChika)
            drawBigChika(pixels);
        else if (!chika.isHeldByStacker()){ // Draws only who have no parents
            EntitySize size = chika.getSize();
            Vector2 dimensions = new Vector2(gCal.gridWidth / size.number, gCal.gridHeight / size.number);
            batch.draw(game.res.getChika1(), pixels.x, pixels.y, dimensions.x, dimensions.y);
        }
    }

    // This draws the big chika and all towed chikas
    private void drawBigChika(Vector2 pixels){
        // Drawing the BigChika first
        batch.draw(game.res.getBigChika(), pixels.x, pixels.y, gCal.gridWidth, gCal.gridHeight);

//        if (controller.bigChika.attachments.hasWeapon()){
//            batch.draw(game.res.getGun(), pixels.x + gCal.gridWidth, pixels.y + gCal.gridHeight);
//        }

        // Drawing the stacked ones
        if (controller.bigChika.getStack().empty())
            return;

        for (int i = 1; i <= controller.bigChika.getStack().size(); i++){
            int size = controller.bigChika.getStack().elementAt(i - 1).getSize().number;
            batch.draw(game.res.getTowedChika(), pixels.x, pixels.y + (i * gCal.gridHeight),
                    gCal.gridWidth / size, gCal.gridHeight / size);
        }
    }
}
