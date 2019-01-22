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

package de.cerus.flappyhorst.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameOverState extends State {
    private BitmapFont font, font16;

    public GameOverState(StateManager stateManager) {
        super(stateManager);
        font = new BitmapFont(Gdx.files.classpath("fonts/ka1-42.fnt"), Gdx.files.classpath("fonts/ka1-42.png"), false);
        font16 = new BitmapFont(Gdx.files.classpath("fonts/ka1-16.fnt"), Gdx.files.classpath("fonts/ka1-16.png"), false);
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched())
            stateManager.set(new PlayState(stateManager));
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.begin();
        spriteBatch.setProjectionMatrix(camera.combined);
        final GlyphLayout gameOverLayout = new GlyphLayout(font, "Game Over");
        final GlyphLayout clickToRestartLayout = new GlyphLayout(font, "Klicke zum neustarten");

        font.draw(spriteBatch, gameOverLayout, (camera.viewportWidth - gameOverLayout.width) / 2, camera.viewportHeight / 2);
        font.draw(spriteBatch, clickToRestartLayout, (camera.viewportWidth - clickToRestartLayout.width) / 2, camera.viewportHeight / 3);

        spriteBatch.end();
    }

    @Override
    public void update(float delta) {
        handleInput();
    }

    @Override
    public void dispose() {
        font.dispose();
        font16.dispose();
    }
}
