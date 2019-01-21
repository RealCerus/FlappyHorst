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
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import de.cerus.flappyhorst.FlappyHorst;
import de.cerus.flappyhorst.sprites.Horst;
import de.cerus.flappyhorst.sprites.Obstacle;

public class PlayState extends State {
    private final int MAX_TUBES = 15;
    private final int SPACE = 350;

    private Horst horst;
    private ShapeRenderer shapeRenderer;
    private boolean showDebug, pause, gameOver, toast;
    private BitmapFont font;
    private Texture background;
    private Sound jumpSound;
    private Sound gameOverSound;
    private Stage stage;
    private int touchedSpaceHitboxes = 0;
    private int highScore;
    private int diffuculty = 0;
    private Preferences preferences;

    private Rectangle lastTouched = null;

    private Array<Obstacle> obstacles;

    public PlayState(StateManager stateManager) {
        super(stateManager);
        horst = new Horst(-150, 300);
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        showDebug = false;
        font = new BitmapFont(Gdx.files.classpath("fonts/ka1-16.fnt"), Gdx.files.classpath("fonts/ka1-16.png"), false);
        background = new Texture(Gdx.files.classpath("background.png"));
        shapeRenderer = new ShapeRenderer();
        jumpSound = Gdx.audio.newSound(Gdx.files.absolute(FlappyHorst.getInstallationPath().getPath()+"\\sounds\\jump.mp3"));
        gameOverSound = Gdx.audio.newSound(Gdx.files.absolute(FlappyHorst.getInstallationPath().getPath()+"\\sounds\\game_over.mp3"));
        stage = new Stage(new ScreenViewport());
        preferences = Gdx.app.getPreferences("data");
        highScore = preferences.getInteger("highscore", 0);

        obstacles = new Array<Obstacle>();
        for (int i = 1; i <= MAX_TUBES; i++) {
            obstacles.add(new Obstacle(i * SPACE));
        }
    }

    @Override
    protected void handleInput() {
        if(Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT) && Gdx.input.isKeyPressed(Input.Keys.S) && Gdx.input.isKeyJustPressed(Input.Keys.NUM_1))
            diffuculty = 1;
        if(Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT) && Gdx.input.isKeyPressed(Input.Keys.S) && Gdx.input.isKeyJustPressed(Input.Keys.NUM_0))
            diffuculty = 0;
        if(Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT) && Gdx.input.isKeyPressed(Input.Keys.S) && Gdx.input.isKeyJustPressed(Input.Keys.NUM_2))
            diffuculty = 2;
        if (pause && !Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) return;
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) pause = !pause;
        if (Gdx.input.isKeyJustPressed(Input.Keys.F3)) showDebug = !showDebug;
        if (Gdx.input.justTouched() || Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            horst.jump(diffuculty);
            jumpSound.play(0.1f);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT) && Gdx.input.isKeyJustPressed(Input.Keys.T)) {
            toast = !toast;
            horst.setToastEnabled(toast);
        }
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();
        spriteBatch.draw(background, camera.position.x - (camera.viewportWidth / 2), 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        spriteBatch.draw(horst.getTexture(), horst.getPosition().x, horst.getPosition().y, horst.getTextureScaleWidth(), horst.getTextureScaleHeight());

        for (Obstacle obstacle : obstacles) {
            spriteBatch.draw(obstacle.getBottomObstacleTexture(), obstacle.getBottomObstaclePosition().x, obstacle.getBottomObstaclePosition().y, obstacle.getScaledBottomWidth(), obstacle.getScaledBottomHeight());
            spriteBatch.draw(obstacle.getTopObstacleTexture(), obstacle.getTopObstaclePosition().x, obstacle.getTopObstaclePosition().y, obstacle.getScaledTopWidth(), obstacle.getScaledTopHeight());
        }

        if (showDebug) {
            font.draw(spriteBatch, "Schwierigkeit: " + diffuculty + (diffuculty == 0 ? " (Normal)" : diffuculty == 1 ? " (Schwer)" : " (Extrem)"), camera.position.x-475, camera.position.y - 123);
            font.draw(spriteBatch, "FPS: " + Gdx.graphics.getFramesPerSecond(), camera.position.x-475, camera.position.y - 141);
            font.draw(spriteBatch, "Velocity X: " + horst.getVelocity().x, camera.position.x-475, camera.position.y - 159);
            font.draw(spriteBatch, "Velocity Y: " + horst.getVelocity().y, camera.position.x-475, camera.position.y - 177);
            font.draw(spriteBatch, "Position X: " + horst.getPosition().x, camera.position.x-475, camera.position.y - 195);
            font.draw(spriteBatch, "Position Y: " + horst.getPosition().y, camera.position.x-475, camera.position.y - 213);
        }
        if (pause) {
            String pause = "PAUSE";
            font.draw(spriteBatch, pause, camera.position.x, camera.viewportHeight / 2f);
        }
        font.draw(spriteBatch, "Score: " + touchedSpaceHitboxes, camera.position.x +300, camera.position.y + 200);
        font.draw(spriteBatch, "High Score: " + highScore, camera.position.x +300, camera.position.y + 180);
        spriteBatch.end();
        if (showDebug) {
            shapeRenderer.setProjectionMatrix(camera.combined);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.setColor(Color.RED);
            shapeRenderer.rect(horst.getHitbox().x, horst.getHitbox().y, horst.getHitbox().width, horst.getHitbox().height);

            for (Obstacle obstacle : obstacles) {
                shapeRenderer.setColor(Color.MAGENTA);
                shapeRenderer.rect(obstacle.getTopObstacleHitbox().x, obstacle.getTopObstacleHitbox().y, obstacle.getTopObstacleHitbox().width, obstacle.getTopObstacleHitbox().height);
                shapeRenderer.rect(obstacle.getBottomObstacleHitbox().x, obstacle.getBottomObstacleHitbox().y, obstacle.getBottomObstacleHitbox().width, obstacle.getBottomObstacleHitbox().height);
                shapeRenderer.setColor(Color.GREEN);
                shapeRenderer.rect(obstacle.getTopObstaclePosition().x, obstacle.getTopObstaclePosition().y, obstacle.getTopObstacleHitbox().width, (obstacle.getBottomObstaclePosition().y- obstacle.getTopObstaclePosition().y)+obstacle.getBottomObstacleHitbox().height);
            }
            shapeRenderer.end();
        }
    }

    @Override
    public void update(float delta) {
        handleInput();
        if (pause) return;
        horst.update(delta, diffuculty);
        camera.position.x = horst.getPosition().x;
        camera.update();

        for (Obstacle obstacle : obstacles) {
            if (camera.position.x - (camera.viewportWidth / 2) > obstacle.getTopObstaclePosition().x + obstacle.getScaledTopWidth())
                obstacle.reposition(obstacle.getTopObstaclePosition().x + (((obstacle.getScaledTopWidth() + SPACE) * MAX_TUBES) - 1000));
            if (obstacle.collidesWith(horst.getHitbox())) {
                if(touchedSpaceHitboxes > highScore){
                    preferences.putInteger("highscore", touchedSpaceHitboxes);
                    preferences.flush();
                }
                stateManager.set(new GameOverState(stateManager));
                gameOverSound.play(0.1f);
                return;
            }
            if (horst.getHitbox().overlaps(obstacle.getSpaceHitbox()) && (lastTouched == null || !lastTouched.equals(obstacle.getSpaceHitbox()))) {
                System.out.println("+1!");
                touchedSpaceHitboxes++;
                lastTouched = obstacle.getSpaceHitbox();
            }
        }
    }

    @Override
    public void dispose() {
        horst.dispose();
        for(Obstacle obstacle : obstacles)
            obstacle.dispose();
        stage.dispose();
    }
}
