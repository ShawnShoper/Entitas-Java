package com.ilargia.games.egdx.logicbricks.gen.actuator;

import com.ilargia.games.entitas.api.*;
import com.ilargia.games.egdx.logicbricks.component.actuator.DragActuator;

/**
 * ---------------------------------------------------------------------------
 * '<auto-generated>' This code was generated by CodeGeneratorApp.
 * ---------------------------------------------------------------------------
 */
public class ActuatorContext
		extends
			com.ilargia.games.entitas.Context<ActuatorEntity> {

	public ActuatorContext(int totalComponents, int startCreationIndex,
			ContextInfo contextInfo,
			EntityBaseFactory<ActuatorEntity> factoryMethod) {
		super(totalComponents, startCreationIndex, contextInfo, factoryMethod);
	}

	public ActuatorEntity getDragActuatorEntity() {
		return getGroup(ActuatorMatcher.DragActuator()).getSingleEntity();
	}

	public DragActuator getDragActuator() {
		return getDragActuatorEntity().getDragActuator();
	}

	public boolean hasDragActuator() {
		return getDragActuatorEntity() != null;
	}

	public ActuatorEntity setDragActuator(int targetEntity,
			boolean collideConnected, float maxForce) {
		if (hasDragActuator()) {
			throw new EntitasException(
					"Could not set DragActuator!" + this
							+ " already has an entity with DragActuator!",
					"You should check if the context already has a DragActuatorEntity before setting it or use context.ReplaceDragActuator().");
		}
		ActuatorEntity entity = createEntity();
		entity.addDragActuator(targetEntity, collideConnected, maxForce);
		return entity;
	}

	public ActuatorEntity replaceDragActuator(int targetEntity,
			boolean collideConnected, float maxForce) {
		ActuatorEntity entity = getDragActuatorEntity();
		if (entity == null) {
			entity = setDragActuator(targetEntity, collideConnected, maxForce);
		} else {
			entity.replaceDragActuator(targetEntity, collideConnected, maxForce);
		}
		return entity;
	}

	public ActuatorContext removeDragActuator() {
		destroyEntity(getDragActuatorEntity());
		return this;
	}
}