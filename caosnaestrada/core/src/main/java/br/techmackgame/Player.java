package br.techmackgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;


//Essa classe representa a nave. Ela herda de GameObject e implementa a lógica de movimento do jogador, lendo as entradas do teclado.
public class Player extends GameObject{
    private Vector2 touchPos;
    private Viewport viewport;
    private Texture standingLeft;
    private Texture standingRight;
    private Animation<TextureRegion> runAnimationLeft;
    private Animation<TextureRegion> runAnimationRight;
    TextureRegion frameAtual;
    private float stateTime = 0f;

    public Player(Texture texture, float x, float y, float width, float height, Viewport viewport) {
        super(texture, x, y, width, height);
        this.viewport = viewport;
        this.touchPos = new Vector2();
        this.standingRight = texture; // Por padrão, personagem parado pra direita
        this.standingLeft = texture;
    }

    @Override
    public void update(float delta) {
        float speed = 4f;
        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();
        float playerWidth = objectSprite.getWidth();
        float playerHeight = objectSprite.getHeight();

        // Movimento teclado
            if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
                objectSprite.translateX(speed * delta); // mover para a direita
                
                TextureRegion[] framesCorridaDireita = new TextureRegion[4];  // ao mover para a direita, usar a textura de standingRight
               
                for (int i = 0; i < framesCorridaDireita.length; i++){
                    Texture t = new Texture("RunRight" + (i+1) + ".png");
                    framesCorridaDireita[i] = new TextureRegion(t);
                }

                runAnimationRight = new Animation<>(0.1f, framesCorridaDireita);
                // avançar tempo da animação e pegar frame atual
                stateTime += delta;
                frameAtual = runAnimationRight.getKeyFrame(stateTime, true);
                objectSprite.setRegion(frameAtual);

            } else if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
                objectSprite.translateX(-speed * delta); // mover para a esquerda

                TextureRegion[] framesCorridaEsquerda = new TextureRegion[4];  // ao mover para a direita, usar a textura de standingRight
               
                for (int i = 0; i < framesCorridaEsquerda.length; i++){
                    Texture t = new Texture("RunLeft" + (i+1) + ".png");
                    framesCorridaEsquerda[i] = new TextureRegion(t);
                }

                runAnimationLeft = new Animation<>(0.1f, framesCorridaEsquerda);
                // avançar tempo da animação e pegar frame atual
                stateTime += delta;
                frameAtual = runAnimationRight.getKeyFrame(stateTime, true);
                objectSprite.setRegion(frameAtual);

            } 

            // Movimento via toque ou mouse
            if (Gdx.input.isTouched()) {
                touchPos.set(Gdx.input.getX(), Gdx.input.getY());
                viewport.unproject(touchPos); // conversao da unidadae da viewport para o vetor entender
                objectSprite.setCenterX(touchPos.x);
                objectSprite.setCenterY(touchPos.y);
            }

            // Garante que o jogador não saia da tela
            objectSprite.setX(MathUtils.clamp(objectSprite.getX(), 0, worldWidth - playerWidth));
            objectSprite.setY(MathUtils.clamp(objectSprite.getY(), 0, worldHeight - playerHeight));
    }
}

