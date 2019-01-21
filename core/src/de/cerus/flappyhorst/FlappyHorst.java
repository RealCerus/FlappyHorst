package de.cerus.flappyhorst;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import de.cerus.flappyhorst.states.MenuState;
import de.cerus.flappyhorst.states.StateManager;

import java.io.File;

public class FlappyHorst extends Game {

    private SpriteBatch spriteBatch;
    private StateManager stateManager;
    private static File installationPath;

    public FlappyHorst(File installationPath) {
        FlappyHorst.installationPath = installationPath;
    }

    public static File getInstallationPath(){
        return installationPath;
    }

    @Override
    public void create() {
        spriteBatch = new SpriteBatch();
        stateManager = new StateManager();
        Gdx.gl.glClearColor(1, 0, 0, 1);
        stateManager.push(new MenuState(stateManager));
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stateManager.update(Gdx.graphics.getDeltaTime());
        stateManager.render(spriteBatch);
    }

    @Override
    public void dispose() {
    }
}
