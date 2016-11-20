package com.ilargia.games.core;

import com.ilargia.games.entitas.Pool;
import com.ilargia.games.entitas.interfaces.FactoryEntity;
import java.util.Stack;
import com.ilargia.games.entitas.interfaces.IComponent;
import com.ilargia.games.entitas.PoolMetaData;

/**
 * ---------------------------------------------------------------------------
 * '<auto-generated>' This code was generated by CodeGeneratorApp. Changes to
 * this file may cause incorrect behavior and will be lost.
 * 
 * ---------------------------------------------------------------------------
 */
public class Pools extends com.ilargia.games.entitas.Pools<Entity> {

	public Pool core;

	public Pools() {
		core = createCorePool();
	}

	public Pool<Entity> createCorePool() {
		return createPool("Core", CoreComponentIds.totalComponents,
				CoreComponentIds.componentNames(),
				CoreComponentIds.componentTypes(), factoryEntity());
	}

	public Pool<Entity>[] allPools() {
		return new Pool[]{core};
	}

	public FactoryEntity<Entity> factoryEntity() {
		return (int totalComponents, Stack<IComponent>[] componentPools,
				PoolMetaData poolMetaData) -> {
			return new Entity(totalComponents, componentPools, poolMetaData);
		};
	}
}