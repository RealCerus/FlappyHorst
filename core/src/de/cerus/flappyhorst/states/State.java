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

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public abstract class State {

    protected StateManager stateManager;
    protected OrthographicCamera camera;
    protected Vector3 mouse;

    protected State(StateManager stateManager) {
        this.stateManager = stateManager;
        this.camera = new OrthographicCamera();
        this.mouse = new Vector3();
    }

    protected abstract void handleInput();

    public abstract void render(SpriteBatch spriteBatch);

    public abstract void update(float delta);

    public abstract void dispose();

    public OrthographicCamera getCamera() {
        return camera;
    }
}
