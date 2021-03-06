package ilargia.entitas;

import ilargia.entitas.api.ContextInfo;
import ilargia.entitas.api.events.GroupChanged;
import ilargia.entitas.components.Position;
import ilargia.entitas.components.View;
import ilargia.entitas.factories.CollectionsFactories;
import ilargia.entitas.factories.EntitasCollections;
import ilargia.entitas.group.Group;
import ilargia.entitas.utils.TestComponentIds;
import ilargia.entitas.utils.TestEntity;
import ilargia.entitas.utils.TestMatcher;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;
import java.util.Stack;

import static org.junit.Assert.*;

public class GroupTest {

    private Group<TestEntity> group;
    private TestEntity entity;
    private Group group2;


    private void createCollections() {
        new EntitasCollections(new CollectionsFactories(){});
    }


    @Before
    public void setUp() throws Exception {
        createCollections();

        entity = new TestEntity();
        entity.initialize(0,10, new Stack[10], new ContextInfo("Test", TestComponentIds.componentNames(),
                TestComponentIds.componentTypes()), null);
        entity.clearEventsListener();
        entity.reactivate(0);
        entity.addComponent(TestComponentIds.Position, new Position(100, 100));
        entity.addComponent(TestComponentIds.View, new View(1));

        group = new Group<TestEntity>(TestMatcher.Position(), TestEntity.class);
        group2 = new Group(TestMatcher.Interactive(), Entity.class);

    }

    @Test
    public void handleEntityTest() {
        GroupChanged<TestEntity> lambda = (group, e, index, component) -> {
            entityEquals(entity, e);
        };
        group.OnEntityAdded(lambda);
        Set<GroupChanged> changed = group.handleEntity(entity);
        assertTrue(changed.contains(lambda));

    }

    private void entityEquals(TestEntity entity, Object entity2) {
        assertEquals(entity, entity2);
    }

    @Test
    public void handleEntityOnEntityRemovedTest() {
        GroupChanged<TestEntity> lambda = (group, e, idx, component) -> assertEquals(entity, e);
        group.OnEntityRemoved(lambda);
        Set<GroupChanged> changed = group.handleEntity(entity);
        assertEquals(0, changed.size());

        entity.removeComponent(TestComponentIds.Position);
        changed = group.handleEntity(entity);
        assertTrue(changed.contains(lambda));


    }

    @Test
    public void handleEntitySilentlyTest() {
        group.handleEntitySilently(entity);
        assertEquals(1, group.getEntities().length);

    }

    @Test
    public void handleEntitySilentlyOnEntityRemovedTest() {
        group.handleEntitySilently(entity);
        assertEquals(1, group.getEntities().length);

        entity.removeComponent(TestComponentIds.Position);
        group.handleEntitySilently(entity);
        assertFalse(group.containsEntity(entity));

    }


    @Test
    public void getSingleEntityTest() {
        assertNull(group.getSingleEntity());
        group.handleEntitySilently(entity);

        assertEquals(entity, group.getSingleEntity());

    }


}
