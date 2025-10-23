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
    private TextureRegion[] framesCorridaDireita;

    public Player(Texture texture, float x, float y, float width, float height, Viewport viewport) {
        super(texture, x, y, width, height);
        this.viewport = viewport;
        this.touchPos = new Vector2();
        this.standingRight = texture;
        this.standingLeft = texture;
        
        // Carregar frames da animação de correr para direita
        framesCorridaDireita = new TextureRegion[13];
        for (int i = 0; i < 13; i++) {
                Texture t = new Texture("RunRight" + (i+1) + ".png");
            framesCorridaDireita[i] = new TextureRegion(t);
        }
        runAnimationRight = new Animation<>(0.1f, framesCorridaDireita);
        
        // Para a esquerda, vamos usar os mesmos frames da direita mas invertidos horizontalmente
        TextureRegion[] framesEsquerda = new TextureRegion[13];
        for (int i = 0; i < 13; i++) {
            framesEsquerda[i] = new TextureRegion(framesCorridaDireita[i]);
            framesEsquerda[i].flip(true, false); // Inverte horizontalmente
        }
        runAnimationLeft = new Animation<>(0.1f, framesEsquerda);
    }

    @Override
    public void update(float delta) {
        float speed = 4f;
        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();
        float playerWidth = objectSprite.getWidth();
        float playerHeight = objectSprite.getHeight();

        stateTime += delta; // Avança o tempo da animação

        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            objectSprite.translateX(speed * delta); // mover para a direita
            frameAtual = runAnimationRight.getKeyFrame(stateTime, true);
            objectSprite.setRegion(frameAtual);
        } else if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            objectSprite.translateX(-speed * delta); // mover para a esquerda
            frameAtual = runAnimationLeft.getKeyFrame(stateTime, true);
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

