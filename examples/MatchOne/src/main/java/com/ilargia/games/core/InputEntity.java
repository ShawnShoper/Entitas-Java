package com.ilargia.games.core;

import com.ilargia.games.entitas.api.*;
import com.ilargia.games.entitas.Entity;
import java.util.Stack;
import com.ilargia.games.components.BurstMode;
import com.ilargia.games.entitas.api.IComponent;
import com.ilargia.games.components.Input;

/**
 * ---------------------------------------------------------------------------
 * '<auto-generated>' This code was generated by CodeGeneratorApp.
 * ---------------------------------------------------------------------------
 */
public class InputEntity extends Entity {

	public BurstMode BurstModeComponent = new BurstMode();

	public InputEntity() {
	}

	public boolean isBurstMode() {
		return hasComponent(InputComponentIds.BurstMode);
	}

	public InputEntity setBurstMode(boolean value) {
		if (value != hasComponent(InputComponentIds.BurstMode)) {
			if (value) {
				addComponent(InputComponentIds.BurstMode, BurstModeComponent);
			} else {
				removeComponent(InputComponentIds.BurstMode);
			}
		}
		return this;
	}

	public Input getInput() {
		return (Input) getComponent(InputComponentIds.Input);
	}

	public boolean hasInput() {
		return hasComponent(InputComponentIds.Input);
	}

	public InputEntity addInput(int x, int y) {
		Input component = (Input) recoverComponent(InputComponentIds.Input);
		if (component == null) {
			component = new Input(x, y);
		} else {
			component.x = x;;
			component.y = y;
		}
		addComponent(InputComponentIds.Input, component);
		return this;
	}

	public InputEntity replaceInput(int x, int y) {
		Input component = (Input) recoverComponent(InputComponentIds.Input);
		if (component == null) {
			component = new Input(x, y);
		} else {
			component.x = x;;
			component.y = y;
		}
		replaceComponent(InputComponentIds.Input, component);
		return this;
	}

	public InputEntity removeInput() {
		removeComponent(InputComponentIds.Input);
		return this;
	}
}