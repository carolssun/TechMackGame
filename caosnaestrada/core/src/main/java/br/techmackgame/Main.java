package br.techmackgame;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */


public class Main implements ApplicationListener {
    
    SpriteBatch spriteBatch;
    FitViewport viewport;
    
    Texture playerTexture;
    Player player; 

    @Override
    public void create() {
        spriteBatch = new SpriteBatch();
        viewport = new FitViewport(8,5);

        Texture playerTexture = new Texture("standingRight.png");
        player = new Player(playerTexture, 1, 1, 0.5f, 1f, viewport);
    }

    @Override
    public void resize(int width, int height) {
        //if(width <= 0 || height <= 0) return;
        viewport.update(width, height, true);
    }

    @Override
    public void render() { // loop game
        float delta = Gdx.graphics.getDeltaTime();
        updateGameObjects(delta);
        drawGameObjects();
        //handleCollisions();
        // drawUI();
    
    }
    private void drawGameObjects (){
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);

        spriteBatch.begin();

         player.draw(spriteBatch);

        spriteBatch.end();
    }
    private void updateGameObjects(float delta){
        player.update(delta);
    }

    @Override
    public void pause() {
        // Invoked when your application is paused.
    }

    @Override
    public void resume() {
        // Invoked when your application is resumed after pause.
    }

    @Override
    public void dispose() {
        // Destroy application's resources here.
    }
}