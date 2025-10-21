package br.techmackgame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Truck extends GameObject {

    private Animation<TextureRegion> animation;
    private float stateTime;
    private boolean active;

    public Truck(Texture texture, float x, float y, float width, float height) {
        super(texture, x, y, width, height);
        // Cria animação com 1 frame por enquanto
        TextureRegion[] frames = new TextureRegion[1];
        frames[0] = new TextureRegion(texture);
        this.animation = new Animation<>(0.1f, frames);
        this.stateTime = 0f;
        this.active = true; // ativo por padrão para animar
    }

    public Truck(Animation<TextureRegion> animation, float x, float y, float width, float height) {
        super(animation.getKeyFrame(0).getTexture(), x, y, width, height);
        this.animation = animation;
        this.stateTime = 0f;
        this.active = true;
    }

    @Override
    public void update(float delta) {
        if (!active) return;

        stateTime += delta;
        // Truck fica parado no canto da tela, mas troca frames da animação
        TextureRegion frame = animation.getKeyFrame(stateTime, true);
        objectSprite.setRegion(frame);
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }

    public float getWidthUnits() {
        return objectSprite.getWidth();
    }

    public float getHeightUnits() {
        return objectSprite.getHeight();
    }
}
