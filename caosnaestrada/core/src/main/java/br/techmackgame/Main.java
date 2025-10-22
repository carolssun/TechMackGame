package br.techmackgame;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Main implements ApplicationListener {

    SpriteBatch spriteBatch;
    FitViewport viewport;

    Player player;
    Truck truck;

    @Override
    public void create() {
        spriteBatch = new SpriteBatch();
        viewport = new FitViewport(8, 5);

        // Player
        Texture playerTexture = new Texture("standingRight.png");
        player = new Player(playerTexture, 1, 1, 0.5f, 1f, viewport);

        // Caminhão - parado no canto direito
        Texture truckTexture = new Texture("caminhao.png"); // 1 frame por enquanto
        float truckWidth = 4f;  // largura em unidades do mundo
        float truckHeight = 2f; // altura em unidades
        float truckX = viewport.getWorldWidth() - truckWidth / 2; // metade aparece
        float truckY = 1f;

        truck = new Truck(truckTexture, truckX, truckY, truckWidth, truckHeight);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();

        updateGameObjects(delta);
        drawGameObjects();
    }

    private void updateGameObjects(float delta) {
        player.update(delta);
        truck.update(delta);

        // Colisão simples: impede que o player entre no caminhão
        if (player.getBounds().overlaps(truck.getBounds())) {
            // Se estiver à esquerda do caminhão, empurra para trás
            if (player.getX() < truck.getX()) {
                player.setPosition(truck.getX() - player.getWidth(), player.getY());
            } else { // se estiver à direita, empurra para frente
                player.setPosition(truck.getX() + truck.getWidth(), player.getY());
            }
        }
    }

    private void drawGameObjects() {
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);

        spriteBatch.begin();
        player.draw(spriteBatch);
        truck.draw(spriteBatch);
        spriteBatch.end();
    }

    @Override
    public void pause() { }

    @Override
    public void resume() { }

    @Override
    public void dispose() {
        spriteBatch.dispose();
    }
}
