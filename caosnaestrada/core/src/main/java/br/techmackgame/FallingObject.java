package br.techmackgame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

public class FallingObject extends GameObject {

    private float velocityX;
    private float velocityY;
    private float gravity = -9.8f; // aceleração para baixo
    private boolean active = true;
    private float groundY = 1f; // altura do chão (ajuste conforme o seu cenário)

    public FallingObject(Texture texture, float startX, float startY, float width, float height) {
        super(texture, startX, startY, width, height);
        reset(startX, startY);
    }

    /** Define um novo arco aleatório a partir da posição inicial */
    public void reset(float startX, float startY) {
        objectSprite.setPosition(startX, startY);
        bounds.setPosition(startX, startY);
        active = true;

        // Arco aleatório (horizontal e vertical)
        velocityX = MathUtils.random(-2f, -4f); // indo para a esquerda (traseira do caminhão)
        velocityY = MathUtils.random(2.5f, 4f);  // altura inicial do pulo
    }

    @Override
    public void update(float delta) {
        if (!active) return;

        // Movimento parabólico
        velocityY += gravity * delta;
        translate(velocityX * delta, velocityY * delta);

        // Caiu no chão → desativa
        if (objectSprite.getY() <= groundY) {
            active = false;
        }
    }

    public void draw(SpriteBatch batch) {
        if (active) super.draw(batch);
    }

    public boolean isActive() {
        return active;
    }

    public void collect() {
        active = false;
    }
}
