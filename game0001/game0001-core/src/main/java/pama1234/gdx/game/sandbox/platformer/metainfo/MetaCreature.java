package pama1234.gdx.game.sandbox.platformer.metainfo;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import pama1234.gdx.game.sandbox.platformer.entity.LivingEntity;
import pama1234.gdx.game.sandbox.platformer.metainfo.info0001.center.MetaCreatureCenter0001;
import pama1234.gdx.game.sandbox.platformer.world.world0001.World0001;
import pama1234.server.game.app.server0002.game.metainfo.MetaInfoBase;
import pama1234.server.game.app.server0002.game.metainfo.io.PlainAttribute;
import pama1234.server.game.app.server0002.game.metainfo.io.RuntimeAttribute;
import pama1234.server.game.app.server0002.game.metainfo.io.StoredAttribute;

/**
 * MetaCreature 类，用于定义生物的元信息。
 * 包含生物的普通属性、存储属性和运行时属性。
 */
public class MetaCreature<T extends LivingEntity>
        extends MetaInfoBase<MetaCreatureAttribute, MetaCreatureStoredAttribute, MetaCreatureRuntimeAttribute> {

    // 生物中心对象，用于管理生物的元信息
    public MetaCreatureCenter0001<?> pc;

    // 初始化属性对象
    {
        attr = new MetaCreatureAttribute();
        sttr = new MetaCreatureStoredAttribute();
        rttr = new MetaCreatureRuntimeAttribute();
    }

    /**
     * 生物的普通属性类
     */
    public static class MetaCreatureAttribute extends PlainAttribute {
        // 生物的宽度和高度
        public int w, h;
        // 生物的移动速度
        public float dx, dy;
        // 生物的最大生命值
        public float maxLife = 32;
        // 生物的移动速度
        public float moveSpeed = 1;
        // 生物是否无敌（暂未实现）
        public boolean immortal;
        // 生物的自然数量上限
        public int count, naturalMaxCount;
    }

    /**
     * 生物的存储属性类
     */
    public static class MetaCreatureStoredAttribute extends StoredAttribute {
        // 生物的生成数据
        public StoredSpawnData[] spawnDatas;
        // 生物的纹理数据
        public byte[][][] tilesPngs;
    }

    /**
     * 生物的运行时属性类
     */
    public static class MetaCreatureRuntimeAttribute extends RuntimeAttribute {
        // 生物的生成数据
        public SpawnData[] spawnDatas;
        // 生物的纹理区域
        public TextureRegion[][] tiles;
    }

    /**
     * 构造函数：创建一个生物元信息对象。
     *
     * @param pc        生物中心对象
     * @param name      生物名称
     * @param id        生物ID
     * @param maxLife   生物的最大生命值
     * @param tiles     生物的纹理区域
     */
    public MetaCreature(MetaCreatureCenter0001<?> pc, String name, int id, float maxLife, TextureRegion[][] tiles) {
        super(name, id);
        this.pc = pc;
        this.attr.maxLife = maxLife;
        this.rttr.tiles = tiles;
    }

    /**
     * 构造函数：创建一个生物元信息对象（自动初始化纹理区域）。
     *
     * @param pc        生物中心对象
     * @param name      生物名称
     * @param id        生物ID
     * @param maxLife   生物的最大生命值
     * @param tileWidth 纹理宽度
     * @param tileHeight 纹理高度
     */
    public MetaCreature(MetaCreatureCenter0001<?> pc, String name, int id, float maxLife, int tileWidth, int tileHeight) {
        this(pc, name, id, maxLife, new TextureRegion[tileHeight][tileWidth]);
    }

    /**
     * 创建一个生物实例。
     *
     * @param world 世界对象
     * @param x     生物的初始X坐标
     * @param y     生物的初始Y坐标
     * @return 创建的生物实例
     */
    public T createCreature(World0001 world, float x, float y) {
        return null; // 需要在子类中实现具体逻辑
    }

    /**
     * 初始化方法（默认为空实现，可在子类中覆盖）。
     */
    @Override
    public void init() {}

    /**
     * 加载运行时属性（默认为空实现，可在子类中覆盖）。
     */
    @Override
    public void loadRuntimeAttribute() {}

    /**
     * 保存运行时属性（默认为空实现，可在子类中覆盖）。
     */
    @Override
    public void saveRuntimeAttribute() {}

    /**
     * 生物的生成数据类。
     */
    public static class SpawnData {
        // 生成的方块类型
        public MetaBlock<?, ?> block;
        // 生成概率
        public float rate;

        public SpawnData(MetaBlock<?, ?> block, float rate) {
            this.block = block;
            this.rate = rate;
        }
    }

    /**
     * 生物的存储生成数据类。
     */
    public static class StoredSpawnData {
        // 方块ID
        public int blockId;
        // 生成概率
        public float rate;
    }
}
