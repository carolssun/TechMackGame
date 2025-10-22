package br.techmackgame;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Main implements ApplicationListener {

    SpriteBatch spriteBatch;
    FitViewport viewport;

    Player player;
    Truck truck;

    Array<Texture> objectTextures;
    FallingObject fallingObject;
    float spawnTimer = 0f;
    float spawnInterval = 3f;

    @Override
    public void create() {
        spriteBatch = new SpriteBatch();
        viewport = new FitViewport(8, 5);

        // Player
        Texture playerTexture = new Texture("standingRight.png");
        player = new Player(playerTexture, 1, 1, 0.5f, 1f, viewport);

        // Caminhão
        Texture truckTexture = new Texture("caminhao.png");
        float truckWidth = 4f;
        float truckHeight = 2f;
        float truckX = viewport.getWorldWidth() - truckWidth / 2;
        float truckY = 1f;
        truck = new Truck(truckTexture, truckX, truckY, truckWidth, truckHeight);

        // Objetos aleatórios (cada um é uma imagem separada)
        objectTextures = new Array<>();
        objectTextures.add(new Texture("abajur.png"));
        objectTextures.add(new Texture("brinquedos.png"));
        objectTextures.add(new Texture("notebook.png"));
        objectTextures.add(new Texture("roupas.png"));
        objectTextures.add(new Texture("travesseiro.png"));
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

        // Impede o player de entrar no caminhão
        if (player.getBounds().overlaps(truck.getBounds())) {
            if (player.getX() < truck.getX()) {
                player.setPosition(truck.getX() - player.getWidth(), player.getY());
            } else {
                player.setPosition(truck.getX() + truck.getWidth(), player.getY());
            }
        }

        // Controle de spawn de objetos
        spawnTimer += delta;
        if ((fallingObject == null || !fallingObject.isActive()) && spawnTimer > spawnInterval) {
            spawnTimer = 0f;
            spawnInterval = MathUtils.random(2f, 5f);

            Texture randomTexture = objectTextures.random();
            float startX = truck.getX(); // sai da traseira do caminhão
            float startY = truck.getY() + truck.getHeight();

            fallingObject = new FallingObject(randomTexture, startX, startY, 0.5f, 0.5f);
        }

        // Atualiza e verifica colisão com o player
        if (fallingObject != null) {
            fallingObject.update(delta);

            if (fallingObject.isActive() && player.getBounds().overlaps(fallingObject.getBounds())) {
                fallingObject.collect();
                System.out.println("🎯 Player pegou o objeto!");
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
        if (fallingObject != null) fallingObject.draw(spriteBatch);
        spriteBatch.end();
    }

    @Override public void pause() { }
    @Override public void resume() { }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        for (Texture t : objectTextures) t.dispose();
    }
}
