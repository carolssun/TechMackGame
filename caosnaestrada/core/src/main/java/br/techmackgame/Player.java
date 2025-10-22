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
    private TextureRegion[] framesCorridaEsquerda; // Array para animação de correr para esquerda

    public Player(Texture texture, float x, float y, float width, float height, Viewport viewport) {
        super(texture, x, y, width, height);
        this.viewport = viewport;
        this.touchPos = new Vector2();
        this.standingRight = texture;
        this.standingLeft = texture;
        
        // Carregar frames da animação de correr para direita
        framesCorridaDireita = new TextureRegion[15];
        for (int i = 0; i < 15; i++) {
            String filename = "RunRight" + (i+1) + ".png";
            System.out.println("Carregando imagem: " + filename);
            Texture t = new Texture(filename);
            framesCorridaDireita[i] = new TextureRegion(t);
        }
        System.out.println("Animação direita criada com " + framesCorridaDireita.length + " frames");
        runAnimationRight = new Animation<>(0.05f, framesCorridaDireita);
        
        // Carregar frames da animação de correr para esquerda
        framesCorridaEsquerda = new TextureRegion[15];
        for (int i = 0; i < 15; i++) {
            String filename = "RunLeft" + (i+1) + ".png";
            System.out.println("Carregando imagem: " + filename);
            Texture t = new Texture(filename);
            framesCorridaEsquerda[i] = new TextureRegion(t);
        }
        System.out.println("Animação esquerda criada com " + framesCorridaEsquerda.length + " frames");
        runAnimationLeft = new Animation<>(0.05f, framesCorridaEsquerda);
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
            System.out.println("Animando frame direita: " + frameAtual); // Debug
        } else if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            objectSprite.translateX(-speed * delta); // mover para a esquerda
            frameAtual = runAnimationLeft.getKeyFrame(stateTime, true);
            objectSprite.setRegion(frameAtual);
            System.out.println("Animando frame esquerda: " + frameAtual); // Debug
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

