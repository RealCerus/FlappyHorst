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
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class Obstacle {

    private final int FLUCTUATION = 200;
    private final int GAP = 175;
    private final int LOWEST_OPENING = 60;

    private Texture topObstacleTexture, bottomObstacleTexture;
    private Vector2 topObstaclePosition, bottomObstaclePosition;
    private Rectangle topObstacleHitbox, bottomObstacleHitbox, spaceHitbox;
    private Random random;

    public Obstacle(float x) {
        topObstacleTexture = new Texture(Gdx.files.classpath("top.png"));
        bottomObstacleTexture = new Texture(Gdx.files.classpath("bottom.png"));
        random = new Random();

        topObstaclePosition = new Vector2(x, random.nextInt(FLUCTUATION) + GAP + LOWEST_OPENING);
        bottomObstaclePosition = new Vector2(x, topObstaclePosition.y - GAP - getScaledBottomHeight());

        topObstacleHitbox = new Rectangle(topObstaclePosition.x, topObstaclePosition.y, getScaledTopWidth(), getScaledTopHeight());
        bottomObstacleHitbox = new Rectangle(bottomObstaclePosition.x, bottomObstaclePosition.y, getScaledBottomWidth(), getScaledBottomHeight());

        //spaceHitbox = new Rectangle(topObstaclePosition.x, topObstaclePosition.y, topObstacleHitbox.width, (bottomObstaclePosition.y-topObstaclePosition.y)+bottomObstacleHitbox.height);
        spaceHitbox = new Rectangle(bottomObstaclePosition.x, bottomObstaclePosition.y, topObstacleHitbox.width, Gdx.graphics.getHeight());
    }

    public float getScaledTopHeight() {
        return topObstacleTexture.getHeight() / 4f;
    }

    public float getScaledBottomHeight() {
        return bottomObstacleTexture.getHeight() / 4f;
    }

    public float getScaledTopWidth() {
        return topObstacleTexture.getWidth() / 4f;
    }

    public float getScaledBottomWidth() {
        return bottomObstacleTexture.getWidth() / 4f;
    }

    public Texture getTopObstacleTexture() {
        return topObstacleTexture;
    }

    public Texture getBottomObstacleTexture() {
        return bottomObstacleTexture;
    }

    public Vector2 getTopObstaclePosition() {
        return topObstaclePosition;
    }

    public Vector2 getBottomObstaclePosition() {
        return bottomObstaclePosition;
    }

    public void reposition(float x) {
        topObstaclePosition.set(x, random.nextInt(FLUCTUATION) + GAP + LOWEST_OPENING);
        bottomObstaclePosition.set(x, topObstaclePosition.y - GAP - getScaledBottomHeight());
        topObstacleHitbox.set(topObstaclePosition.x, topObstaclePosition.y, getScaledTopWidth(), getScaledTopHeight());
        bottomObstacleHitbox.set(bottomObstaclePosition.x, bottomObstaclePosition.y, getScaledBottomWidth(), getScaledBottomHeight());
        spaceHitbox.set(bottomObstaclePosition.x, bottomObstaclePosition.y, topObstacleHitbox.width, Gdx.graphics.getHeight());
    }

    public Rectangle getBottomObstacleHitbox() {
        return bottomObstacleHitbox;
    }

    public Rectangle getTopObstacleHitbox() {
        return topObstacleHitbox;
    }

    public boolean collidesWith(Rectangle rectangle) {
        return rectangle.overlaps(topObstacleHitbox) || rectangle.overlaps(bottomObstacleHitbox);
    }

    public void dispose() {
        this.bottomObstacleTexture.dispose();
        this.topObstacleTexture.dispose();
    }

    public Rectangle getSpaceHitbox() {
        return spaceHitbox;
    }
}
