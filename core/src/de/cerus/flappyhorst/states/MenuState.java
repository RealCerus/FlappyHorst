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
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;

public class MenuState extends State {
    private Texture background, playButton;
    private BitmapFont font;
    private Desktop desktop;

    public MenuState(StateManager stateManager) {
        super(stateManager);
        background = new Texture(Gdx.files.classpath("background.png"));
        playButton = new Texture(Gdx.files.classpath("play_button.png"));
        font = new BitmapFont();
        desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.begin();
        spriteBatch.draw(background, 0, 0);
        spriteBatch.draw(playButton, (Gdx.graphics.getWidth()/2f)-(playButton.getWidth()/2f), Gdx.graphics.getHeight()/2f);
        font.draw(spriteBatch, "Steuerung:", 10, 475);
        font.draw(spriteBatch, "Leertaste / linke & rechte Maustaste - Springen", 10, 460);
        font.draw(spriteBatch, "Escape - Pause", 10, 445);
        font.draw(spriteBatch, "F3  - Debug", 10, 430);

        font.draw(spriteBatch, "Version 1.0 SNAPSHOT", 10, -200);
        font.draw(spriteBatch, "Made by Cerus", 10, -210);
        font.draw(spriteBatch, "Sourcecode available at GitHub", 10, -220);
        spriteBatch.end();
    }

    @Override
    public void update(float delta) {
        handleInput();
    }

    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()){
            float x = Gdx.input.getX();
            float y = Gdx.input.getY();
            if(y <= 225 && x >= 10 && x <= 30 && desktop != null){
                try {
                    desktop.browse(new java.net.URL("").toURI());
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
                return;
            }
            stateManager.set(new PlayState(stateManager));
        }
    }

    @Override
    public void dispose() {
        background.dispose();
        playButton.dispose();
    }
}
