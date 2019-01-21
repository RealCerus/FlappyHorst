/*
 *  Copyright (c) 2018 Cerus
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Contributors:
 * Cerus
 *
 */

package de.cerus.flappyhorst.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class Horst {

    private final int GRAVITY = -15;
    private final int MOVEMENT = 100;

    boolean toastEnabled;
    private Vector3 position, velocity;
    private Texture texture0, texture1, toast;
    private Rectangle hitbox;

    public Horst(float x, float y) {
        position = new Vector3(x, y, 0);
        velocity = new Vector3(0, 0, 0);
        texture0 = new Texture(Gdx.files.classpath("horst0.png"));
        texture1 = new Texture(Gdx.files.classpath("horst1.png"));
        toast = new Texture(Gdx.files.classpath("toast.png"));
        hitbox = new Rectangle(x, y, getTextureScaleWidth(), getTextureScaleHeight());
        toastEnabled = false;
    }

    public void update(float delta, int difficulty) {
        if (position.y > -5)
            velocity.add(0, GRAVITY, 0);
        velocity.scl(delta);
        position.add(difficulty == 0 ? MOVEMENT * delta : difficulty == 1 ? (MOVEMENT * delta)*2 : (MOVEMENT * delta)*3, velocity.y, 0);
        if(position.y < -5)
            position.y = -5;
        if(position.y > 440)
            position.y = 440;

        velocity.scl(1 / delta);
        hitbox.setPosition(position.x, position.y);
    }

    public float getTextureScaleWidth() {
        return toastEnabled ? 100 : 1920f / 12f;
    }

    public float getTextureScaleHeight() {
        return toastEnabled ? 100 : 1080f / 12f;
    }

    public Texture getTexture() {
        if(toastEnabled) return toast;
        if (velocity.y > 10)
            return texture0;
        else return texture1;
    }

    public Vector3 getPosition() {
        return position;
    }

    public Vector3 getVelocity() {
        return velocity;
    }

    public void jump(int difficulty) {
        velocity.y = difficulty == 0 ? 250 : difficulty == 1 ? 350 : 450;
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    public void dispose(){
        texture0.dispose();
        texture1.dispose();
    }

    public boolean isToastEnabled() {
        return toastEnabled;
    }

    public void setToastEnabled(boolean toastEnabled) {
        this.toastEnabled = toastEnabled;
        hitbox = new Rectangle(hitbox.x, hitbox.y, getTextureScaleWidth(), getTextureScaleHeight());
    }
}
