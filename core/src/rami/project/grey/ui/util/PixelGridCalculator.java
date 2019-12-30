package rami.project.grey.ui.util;

import com.badlogic.gdx.math.Vector2;

// TO BE USED IN CONJUNCTION WITH THE PLAY SCREEN ALL THE TIME
public final class PixelGridCalculator {

    private int gWidth, gHeight;
    private int gridColumns, gridRows;
    public int gridWidth, gridHeight;

    public PixelGridCalculator(int gWidth, int gHeight, int gridColumns, int gridRows) {
        this.gWidth = gWidth;
        this.gHeight = gHeight;
        this.gridColumns = gridColumns;
        this.gridRows = gridRows;

        gridWidth = gWidth / gridColumns;
        gridHeight = gHeight / gridRows;
    }

    // Assuming the point given is inside the screen width and height AND CARTESIAN COORDINATES
    public final Vector2 getGridPosFromPixel(int screenX, int screenY){
        int gridOffsetX = screenX / gridWidth;
        int gridOffsetY = screenY / gridHeight;

        return new Vector2(gridOffsetX, gridOffsetY);
    }

    /**
     * @return the point describing the bottom left corner of the grid
     * */
    public final Vector2 getPixelPosFromGrid(Vector2 pos){
        float screenX = pos.x * gridWidth;
        float screenY =  pos.y * gridHeight;

        return new Vector2(screenX, screenY);
    }

    public final Vector2 getPixelPosFromGrid(int x, int y){
        int screenX = x * gridWidth;
        int screenY = y * gridHeight;

        return new Vector2(screenX, screenY);
    }
}
