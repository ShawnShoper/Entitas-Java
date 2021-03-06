package com.ilargia.games.entitas;


import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.ilargia.games.entitas.egdx.base.managers.BasePreferencesManager;
import com.ilargia.games.entitas.egdx.EGEventBus;
import com.ilargia.games.entitas.egdx.base.managers.BaseAssetsManager;
import com.ilargia.games.entitas.factories.CollectionsFactories;
import com.ilargia.games.entitas.factories.EntitasCollections;
import com.indignado.games.states.MatchOneState;
import com.indignado.games.util.TestFileHandleResolver;
import net.engio.mbassy.bus.MBassador;
import net.engio.mbassy.bus.error.IPublicationErrorHandler;
import net.engio.mbassy.bus.error.PublicationError;


public class MatchOne extends ApplicationAdapter {
    public static final int SCREEN_WIDTH = 600;
    public static final int SCREEN_HEIGHT = 600;
    public static float WIDTH = 8; //30 metres
    public static float HEIGHT = 12; // 20 metres

    public static float METRES_TO_PIXELS = 128;
    public static float PIXELS_TO_METRES = 1.0f / METRES_TO_PIXELS;
    private static MatchOneGame game;

    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "BASIC";
        config.width = SCREEN_WIDTH;
        config.height = SCREEN_HEIGHT;

        new LwjglApplication(new MatchOne(), config);
    }

    @Override
    public void create() {
        AssetManager assetsManager = new AssetManager(new TestFileHandleResolver());
        BasePreferencesManager preferencesManager = new BasePreferencesManager();
        MatchOneEngine engine = new MatchOneEngine();
        engine.addManager(new BaseAssetsManager(assetsManager, preferencesManager));
//        new Collections(new CollectionsFactory() {
//            @Override
//            public <T> List<T> createList(Class<T> clazz) {
//                if (clazz.equals(Integer.class))
//                    return (List<T>) new IntArrayList();
//                else
//                    return new ObjectArrayList();
//            }
//
//            @Override
//            public <T> Set createSet(Class<T> clazz) {
//                if (clazz.equals(Integer.class))
//                    return new IntArraySet();
//                else
//                    return new ObjectOpenHashSet();
//        }
//
//            @Override
//            public <K, V> Map createMap(Class<K> keyClazz, Class<V> valueClazz) {
//                return new Object2ObjectArrayMap();
//            }
//
//
//        });

        new EntitasCollections(new CollectionsFactories(){});

        MBassador bus = new MBassador(new IPublicationErrorHandler() {
            @Override
            public void handleError(PublicationError error) {
                Gdx.app.error("EBUS ERROR: ", error.toString());
            }
        });


        game = new MatchOneGame(engine, new EGEventBus(bus));
        game.init();
        game.pushState(new MatchOneState(engine));
    }

    @Override
    public void render() {
        game.update(Gdx.graphics.getDeltaTime());
    }

    @Override
    public void dispose() {


    }
}