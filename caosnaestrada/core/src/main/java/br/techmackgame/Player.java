package br.techmackgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;

//Esta classe representa a nave. Ela herda de GameObject e implementa a lógica de movimento do jogador, lendo as entradas do teclado ou toque.
public class Player extends GameObject{
    private Vector2 touchPos;
    private Viewport viewport;
    private Texture standingLeft;
    private Texture standingRight;

    public Player(Texture texture, float x, float y, float width, float height, Viewport viewport) {
        super(texture, x, y, width, height);
        this.viewport = viewport;
        this.touchPos = new Vector2();
    this.standingRight = texture; // Por padrão, personagem parado pra direita
    // se não houver uma textura específica para standingLeft, usamos a mesma (ou poderia ser carregada separadamente)
    this.standingLeft = texture;
    }

    @Override
    public void update(float delta) {
        float speed = 4f;
        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();
        float spaceshipWidth = objectSprite.getWidth();
        float spaceshipHeight = objectSprite.getHeight();

            if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
                objectSprite.translateX(speed * delta); // mover para a direita
                // ao mover para a direita, usar a textura de standingRight
                if (objectSprite.getTexture() != standingRight) {
                    objectSprite.setRegion(standingRight);
                    objectSprite.setSize(objectSprite.getWidth(), objectSprite.getHeight());
                }
            } else if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
                objectSprite.translateX(-speed * delta); // mover para a esquerda
                // ao mover para a esquerda, usar a textura de standingLeft
                if (objectSprite.getTexture() != standingLeft) {
                    objectSprite.setRegion(standingLeft);
                    objectSprite.setSize(objectSprite.getWidth(), objectSprite.getHeight());
                }
            } 

            // Movimento via toque ou mouse
            if (Gdx.input.isTouched()) {
                touchPos.set(Gdx.input.getX(), Gdx.input.getY());
                viewport.unproject(touchPos); // conversao da unidadae da viewport para o vetor entender
                objectSprite.setCenterX(touchPos.x);
                objectSprite.setCenterY(touchPos.y);
            }

            // Garante que o jogador não saia da tela
        objectSprite.setX(MathUtils.clamp(objectSprite.getX(), 0, worldWidth - spaceshipWidth));
        objectSprite.setY(MathUtils.clamp(objectSprite.getY(), 0, worldHeight - spaceshipHeight));
    }
}

