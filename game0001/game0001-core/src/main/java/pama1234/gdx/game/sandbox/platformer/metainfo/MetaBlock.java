package pama1234.gdx.game.sandbox.platformer.metainfo;

import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.asset.ImageAsset;
import pama1234.gdx.game.sandbox.platformer.metainfo.MetaInfoCenters.MetaBlockCenter;
import pama1234.gdx.game.sandbox.platformer.metainfo.io.MetaPropertiesCenter;
import pama1234.gdx.game.sandbox.platformer.region.block.Block;
import pama1234.gdx.game.sandbox.platformer.world.WorldBase2D;
import pama1234.gdx.game.sandbox.platformer.world.world0001.World0001;
import pama1234.math.UtilMath;
import pama1234.server.game.app.server0002.game.metainfo.MetaInfoBase;
import pama1234.server.game.app.server0002.game.metainfo.io.PlainAttribute;
import pama1234.server.game.app.server0002.game.metainfo.io.RuntimeAttribute;
import pama1234.server.game.app.server0002.game.metainfo.io.StoredAttribute;
import pama1234.server.game.app.server0002.game.metainfo.io.TextureRegionInfo;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * MetaBlock 类，用于定义方块的元信息。
 * 包含方块的普通属性、存储属性和运行时属性。
 */
public class MetaBlock<M extends MetaWorld<?, ?, ?>, C extends MetaBlockCenter<M>>
        extends MetaInfoBase<MetaBlockAttribute, MetaBlockStoredAttribute, MetaBlockRuntimeAttribute> {

    // 方块的完整类型常量
    public static class FullBlockType {
        public static final int STONE_TYPE = 0, LEAF_TYPE = 1, PLANK_TYPE = 2, PLATFORM_TYPE = 3;
    }

    // 方块的类型常量，影响破坏方块需要的工具种类
    public static final int NO_TYPE = 0, EVERY_TYPE = 1, DIRT_TYPE = 2, STONE_TYPE = 3, WOOD_TYPE = 4;

    // 方块中心对象
    public C pc;

    // 初始化属性对象
    {
        attr = new MetaBlockAttribute();
        sttr = new MetaBlockStoredAttribute();
        rttr = new MetaBlockRuntimeAttribute();
    }

    // 方块的普通属性类
    public static class MetaBlockAttribute extends PlainAttribute {
        // 是否显示
        public boolean display = true, empty = true, light = false;
        // 建造和破坏所需时间
        public int buildTime = 1, destroyTime = 1;
        // 硬度和光照强度
        public float hardness = 1, lightIntensity = 0;
        // 方块类型
        public int blockType = NO_TYPE;
        // 是否为完整方块
        public boolean fullBlock = true;
        // 完整方块类型
        public int fullBlockType = -1;
        // 是否为工作站
        public boolean workStation = false;
        // 方块宽度和高度
        public int width = 1, height = 1;
        // 方块占用格子信息
        public boolean[][] rectSolid = new boolean[1][1];
        // 显示类型数量
        public int displayTypeSize = 1;
        // 默认显示类型
        public int defaultDisplayType = 0;
    }

    // 方块的存储属性类
    public static class MetaBlockStoredAttribute extends StoredAttribute {
        // 纹理区域信息数组
        public TextureRegionInfo[] tiles;
        // 物品掉落存储属性数组
        public StoredItemDropAttr[] itemDrop;

        // 更新器和显示更新器程序单元
        public ProgramUnit updater, displayUpdater;
        // 转换程序单元
        public ProgramUnit from, to;
        // 显示程序单元
        public ProgramUnit displayer;
    }

    // 方块的运行时属性类
    public static class MetaBlockRuntimeAttribute extends RuntimeAttribute {
        // 纹理区域数组
        public TextureRegion[] tiles;
        // 物品掉落属性数组
        public ItemDropAttr[] itemDrop;
        // 方块更新器
        public BlockUpdater updater = lightUpdater, displayUpdater = defaultDisplayUpdater;
        // 方块转换器
        public BlockChanger from, to;
        // 方块显示器
        public BlockDisplayer displayer = defaultBlockDisplayer;
    }

    // 加载运行时属性
    @Override
    public void loadRuntimeAttribute() {
        if (sttr.tiles != null) {
            rttr.tiles = new TextureRegion[sttr.tiles.length];
            for (int i = 0; i < sttr.tiles.length; i++) {
                if (sttr.tiles[i] != null) {
                    rttr.tiles[i] = MetaPropertiesCenter.newTextureRegion(ImageAsset.tilesTexture, sttr.tiles[i]);
                }
            }
        }
    }

    // 保存运行时属性
    @Override
    public void saveRuntimeAttribute() {
        if (rttr.tiles != null) {
            sttr.tiles = new TextureRegionInfo[rttr.tiles.length];
            for (int i = 0; i < rttr.tiles.length; i++) {
                if (rttr.tiles[i] != null) {
                    sttr.tiles[i] = MetaPropertiesCenter.newTextureRegionInfo("image/tiles.png", rttr.tiles[i]);
                }
            }
        }
    }

    // 获取光照强度
    public static int getLighting(float intensity) {
        return UtilMath.constrain(UtilMath.floor(intensity * 16), 0, 255);
    }

    // 构造函数
    public MetaBlock(C pc, String name, int id) {
        super(name, id);
        this.pc = pc;
        this.name = name;
        attr.empty = true;
        rttr.itemDrop = new ItemDropAttr[0];
    }

    // 构造函数（带完整参数）
    public MetaBlock(C pc,
                     String name, int id,
                     int tilesSize,
                     BlockUpdater updater, BlockUpdater displayUpdater, BlockDisplayer displayer,
                     int displayTypeSize,
                     BlockChanger from, BlockChanger to) {
        this(pc, name, id, tilesSize, displayTypeSize, from, to);
        this.rttr.updater = updater;
        this.rttr.displayUpdater = displayUpdater;
        this.rttr.displayer = displayer;
    }

    // 构造函数（带部分参数）
    public MetaBlock(C pc,
                     String name, int id,
                     int tilesSize,
                     int displayTypeSize,
                     BlockChanger from, BlockChanger to) {
        super(name, id);
        this.pc = pc;
        this.name = name;
        this.rttr.tiles = new TextureRegion[tilesSize];
        attr.display = true;
        this.attr.displayTypeSize = displayTypeSize;
        this.rttr.from = from;
        this.rttr.to = to;
    }

    // 初始化方法（默认为空实现，可在子类中覆盖）
    @Override
    public void init() {}

    // 更新方块逻辑
    public void update(World0001 world, Block block, int x, int y) {
        if (rttr.updater != null) {
            rttr.updater.update(world, block, x, y);
        }
    }

    // 更新方块显示逻辑
    public void updateDisplay(World0001 world, Block block, int x, int y) {
        rttr.displayUpdater.update(world, block, x, y);
    }

    // 显示方块
    public void display(TilemapRenderer renderer, Screen0011 screen, World0001 world, Block block, int x, int y) {
        rttr.displayer.display(renderer, screen, world, block, x, y);
    }

    // 获取显示类型
    public int getDisplayType() {
        return attr.defaultDisplayType;
    }

    // 初始化方块
    public void initBlock(WorldBase2D<?> world, Block block) {}

    // 方块转换从
    public void from(World0001 world, Block block, MetaBlock<?, ?> type, int x, int y) {
        if (rttr.from != null) {
            rttr.from.change(world, block, type, x, y);
        }
    }

    // 方块转换到
    public void to(World0001 world, Block block, MetaBlock<?, ?> type, int x, int y) {
        if (rttr.to != null) {
            rttr.to.change(world, block, type, x, y);
        }
    }

    // 初始化完整方块的 Lambda 表达式
    public void initFullBlockLambda() {
        rttr.displayUpdater = fullBlockDisplayUpdater;
        rttr.displayer = fullBlockDisplayer;
    }

    // 设置光照强度
    public void setLightIntensity(float intensity) {
        attr.light = true;
        attr.lightIntensity = intensity;
    }

    // 初始化物品掉落
    public void initItemDrop() {}

    // 物品掉落属性类
    public static class ItemDropAttr {
        // 物品元信息
        public MetaItem item;
        // 最小和最大掉落数量
        public int min, max;
        // 掉落概率
        public float probability;

        public ItemDropAttr(MetaItem item, int amount) {
            this.item = item;
            min = max = amount;
            probability = 1;
        }

        // 计算掉落数量
        public int dropNumber(World0001 world) {
            if (probability == 1 || world.random(1) < probability) {
                return (int) world.random(min, max);
            } else {
                return 0;
            }
        }
    }

    // 存储的物品掉落属性类
    public static class StoredItemDropAttr {
        // 物品ID
        public int itemId;
        // 最小和最大掉落数量
        public int min, max;
        // 掉落概率
        public float probability;
    }

    // 方块更新器函数式接口
    @FunctionalInterface
    public interface BlockUpdater {
        void update(World0001 world, Block block, int x, int y);
    }

    // 方块显示器函数式接口
    @FunctionalInterface
    public interface BlockDisplayer {
        void display(TilemapRenderer renderer, Screen0011 screen, World0001 world, Block block, int x, int y);
    }

    // 方块转换器函数式接口
    @FunctionalInterface
    public interface BlockChanger {
        void change(World0001 world, Block block, MetaBlock<?, ?> type, int x, int y);
    }

    // 瓦片地图渲染器接口
    public interface TilemapRenderer {
        void tint(float x, float y, float z);
        void tile(TextureRegion region, boolean next);
        void tile(TextureRegion region, float x, float y);
        void tile(TextureRegion region, float x, float y, float w, float h);
        void begin();
        void end();
    }

    // 空操作更新器
    public static final BlockUpdater doNothing = (w, b, x, y) -> {};

    // 光照更新器
    public static final BlockUpdater lightUpdater = (w, b, x, y) -> b.light.update();

    // 完整方块显示更新器
    public static final BlockUpdater fullBlockDisplayUpdater = (world, block, x, y) -> {
        // 计算相邻方块的显示类型
        int typeCache = calculateAdjacentTypeCache(world, block, x, y);
        block.displayType[0] = typeCache;

        typeCache = calculateDiagonalTypeCache(world, block, x, y);
        block.displayType[1] = typeCache;

        if (block.updateLighting) {
            lightingUpdate(block, x, y, world);
        }
    };

    // 默认显示更新器
    public static final BlockUpdater defaultDisplayUpdater = (world, block, x, y) -> {
        if (block.updateLighting) {
            lightingUpdate(block, x, y, world);
        }
    };

    // 计算相邻方块的显示类型
    private static int calculateAdjacentTypeCache(World0001 world, Block block, int x, int y) {
        int typeCache = 0;
        if (isNotFullBlockOrNotSameType(block, world.getBlock(x, y - 1))) typeCache += 1; // up
        if (isNotFullBlockOrNotSameType(block, world.getBlock(x, y + 1))) typeCache += 2; // down
        if (isNotFullBlockOrNotSameType(block, world.getBlock(x - 1, y))) typeCache += 4; // left
        if (isNotFullBlockOrNotSameType(block, world.getBlock(x + 1, y))) typeCache += 8; // right
        return typeCache;
    }

    // 计算对角线方块的显示类型
    private static int calculateDiagonalTypeCache(World0001 world, Block block, int x, int y) {
        int typeCache = 0;
        if (isNotFullBlockOrNotSameType(block, world.getBlock(x - 1, y - 1))) typeCache += 1;
        if (isNotFullBlockOrNotSameType(block, world.getBlock(x - 1, y + 1))) typeCache += 2;
        if (isNotFullBlockOrNotSameType(block, world.getBlock(x + 1, y + 1))) typeCache += 4;
        if (isNotFullBlockOrNotSameType(block, world.getBlock(x + 1, y - 1))) typeCache += 8;
        return typeCache;
    }

    // 判断方块是否不是完整方块或类型不同
    public static boolean isNotFullBlockOrNotSameType(Block b, Block a) {
        return Block.isNotFullBlock(a) || (a.type.attr.fullBlockType != b.type.attr.fullBlockType);
    }

    // 判断方块是否不是完整方块或类型不同
    public static boolean isNotFullBlockOrNotSameType(Block a, int fullBlockType) {
        return Block.isNotFullBlock(a) || (a.type.attr.fullBlockType != fullBlockType);
    }

    // 光照更新
    public static void lightingUpdate(Block block, int x, int y, World0001 world) {
        float cr = 0;
        int lDist = world.settings.lightDist;
        for (int i = -lDist; i <= lDist; i++) {
            for (int j = -lDist; j <= lDist; j++) {
                float mag = UtilMath.mag(i, j);
                if (mag > lDist) continue;
                Block adjacentBlock = world.regions.getBlock(x + i, y + j);
                if (Block.isNotFullBlock(adjacentBlock)) {
                    cr += world.skyLight();
                }
                if (adjacentBlock != null && adjacentBlock.type.attr.light) {
                    cr += adjacentBlock.type.attr.lightIntensity * (1 - mag / lDist);
                }
            }
        }
        block.light.set(worldLighting(world.settings.lightCount, cr));
    }

    // 计算世界光照强度
    public static int worldLighting(float input, float count) {
        return UtilMath.constrain(UtilMath.floor(UtilMath.map(count * 2, 0, input, 0, 16)), 0, 16);
    }

    // 默认方块显示器
    public static final BlockDisplayer defaultBlockDisplayer = (r, p, world, block, x, y) -> {
        r.tint(
                getLighting(block.light.r()),
                getLighting(block.light.g()),
                getLighting(block.light.b()));
        if (block.displayType == null) {
            r.tile(block.type.rttr.tiles[0], x, y);
        } else {
            r.tile(block.type.rttr.tiles[block.displayType[0]], x, y);
        }
    };

    // 完整方块显示器
    public static final BlockDisplayer fullBlockDisplayer = (r, p, world, block, x, y) -> {
        r.tint(
                getLighting(block.light.r()),
                getLighting(block.light.g()),
                getLighting(block.light.b()));
        int tp_0 = block.displayType[0];
        r.tile(block.type.rttr.tiles[tp_0], x, y);
        int tp_1 = block.displayType[1];
        if (tp_1 != 0) {
            if ((tp_0 & 2) + (tp_0 & 8) == 0 && (tp_1 & 4) != 0) {
                r.tile(block.type.rttr.tiles[16], x, y);
            }
            if ((tp_0 & 2) + (tp_0 & 4) == 0 && (tp_1 & 2) != 0) {
                r.tile(block.type.rttr.tiles[17], x, y);
            }
        }
    };
}
