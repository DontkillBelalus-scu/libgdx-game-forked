package pama1234.gdx.game.sandbox.platformer.entity;

import com.esotericsoftware.kryo.serializers.TaggedFieldSerializer.Tag;

import pama1234.Tools;
import pama1234.gdx.game.app.Screen0011;
import pama1234.gdx.game.sandbox.platformer.entity.util.OuterBox;
import pama1234.gdx.game.sandbox.platformer.metainfo.MetaCreature;
import pama1234.gdx.game.sandbox.platformer.player.GameMode;
import pama1234.gdx.game.sandbox.platformer.region.PathVarLighting;
import pama1234.gdx.game.sandbox.platformer.region.block.Block;
import pama1234.gdx.game.sandbox.platformer.world.WorldBase2D;
import pama1234.gdx.game.sandbox.platformer.world.world0001.WorldType0001Base;
import pama1234.math.UtilMath;
import pama1234.math.physics.MassPoint;
import pama1234.math.physics.PathVar;

/**
 * LivingEntity 类，表示游戏中的生物实体。
 * 包含生物的基本属性、生命值、光照处理以及与游戏世界的交互逻辑。
 */
public class LivingEntity extends GamePointEntity<MassPoint> {

    // 外部碰撞盒，用于处理生物的边界检测
    public OuterBox outerBox;

    // 游戏模式（如生存模式）
    @Tag(1)
    public GameMode gameMode = GameMode.survival;

    // 生物的元信息类型
    public MetaCreature<?> type;

    // 生物类型的ID（用于序列化）
    @Tag(2)
    public int typeId;

    // 生命值变量
    @Tag(3)
    public PathVar life;

    // 光照处理变量
    public PathVarLighting light;

    /**
     * 默认构造函数（仅用于 Kryo 序列化）。
     */
    @Deprecated
    public LivingEntity() {
        super(null, null, null);
    }

    /**
     * 构造函数：创建一个生物实体。
     *
     * @param p    游戏屏幕对象
     * @param pw   游戏世界对象
     * @param in   物理点对象
     * @param type 生物的元信息类型
     */
    public LivingEntity(Screen0011 p, WorldBase2D<? extends WorldType0001Base<?>> pw, MassPoint in, MetaCreature<?> type) {
        super(p, pw, in);
        this.type = type;
        this.typeId = type.id;
        point.step = 0.25f; // 设置物理点的步长
        outerBox = new OuterBox(this); // 初始化外部碰撞盒
        life = new PathVar(type.attr.maxLife); // 初始化生命值
        light = new PathVarLighting(); // 初始化光照处理
        initAtServer(); // 在服务器端初始化
    }

    /**
     * 反序列化初始化方法。
     * 用于在反序列化后重新初始化对象的依赖关系。
     *
     * @param p    游戏屏幕对象
     * @param pw   游戏世界对象
     * @param type 生物的元信息类型
     */
    public void deserializationInit(Screen0011 p, WorldBase2D<? extends WorldType0001Base<?>> pw, MetaCreature<?> type) {
        this.p = p;
        this.pw = pw;
        this.type = type;
        outerBox = new OuterBox(this); // 初始化外部碰撞盒
        light = new PathVarLighting(); // 初始化光照处理
    }

    /**
     * 更新生物的状态。
     */
    @Override
    public void update() {
        outerBox.prePointUpdate(); // 在物理点更新前处理碰撞盒逻辑
        super.update(); // 更新物理点状态
        life.update(); // 更新生命值
        outerBox.postPointUpdate(); // 在物理点更新后处理碰撞盒逻辑
        lightingUpdate(); // 更新光照
    }

    /**
     * 更新生物周围的光照。
     */
    public void lightingUpdate() {
        float cr = 0, cg = 0, cb = 0; // 累计光照值
        int tr = (outerBox.w + 1) * (outerBox.h + 1); // 总采样点数

        // 遍历外部碰撞盒内的所有方块
        for (int i = 0; i <= outerBox.w; i++) {
            for (int j = 0; j <= outerBox.h; j++) {
                Block tb = pw.getBlock(outerBox.x1 + i, outerBox.y1 + j); // 获取当前方块
                if (tb != null && tb.light != null) {
                    cr += tb.light.r(); // 累加红色光照
                    cg += tb.light.g(); // 累加绿色光照
                    cb += tb.light.b(); // 累加蓝色光照
                } else {
                    cr += 16; // 默认光照值
                    cg += 16;
                    cb += 16;
                }
            }
        }

        // 计算平均光照值
        cr /= tr;
        cg /= tr;
        cb /= tr;

        light.set(cr, cg, cb); // 设置光照值
        light.update(); // 更新光照状态
    }

    /**
     * 显示生物的状态（如生命值）。
     */
    @Override
    public void display() {
        displayLife(); // 显示生命值
    }

    /**
     * 显示生物的生命值条。
     */
    public void displayLife() {
        boolean flag = UtilMath.abs(life.des - life.pos) > 0.1f; // 判断生命值是否有变化
        if (!flag && UtilMath.abs(life.des - type.attr.maxLife) <= 0.1f) return; // 如果生命值接近最大值，则不显示

        p.beginBlend(); // 开始混合绘制
        p.fill(127, 127); // 绘制背景条
        p.rect(x1(), y1() - 4, type.attr.w, 2);

        p.fill(255, 63, 63, 191); // 绘制当前生命值条
        float tp = type.attr.w / type.attr.maxLife;
        float tw = tp * life.pos;
        p.rect(x1(), y1() - 4, tw, 2);

        if (flag) {
            if (life.des > life.pos) {
                p.fill(156, 95, 255, 191); // 绘制增加部分
                p.rect(x1() + tw, y1() - 4, tp * (life.des - life.pos), 2);
            } else {
                p.fill(255, 255, 63, 191); // 绘制减少部分
                p.rect(x1() + tw, y1() - 4, tp * (life.pos - life.des), 2);
            }
        }
        p.endBlend(); // 结束混合绘制
    }

    /**
     * 获取生物的中心X坐标。
     *
     * @return 生物的中心X坐标
     */
    public float cx() {
        return point.pos.x + type.attr.dx + type.attr.w / 2f;
    }

    /**
     * 获取生物的中心Y坐标。
     *
     * @return 生物的中心Y坐标
     */
    public float cy() {
        return point.pos.y + type.attr.dy + type.attr.h / 2f;
    }

    /**
     * 获取指定位置的方块。
     *
     * @param xIn X坐标
     * @param yIn Y坐标
     * @return 对应的方块
     */
    public Block getBlock(int xIn, int yIn) {
        return pw.regions.getBlock(xIn, yIn);
    }

    /**
     * 获取指定位置的方块（浮点坐标）。
     *
     * @param xIn X坐标
     * @param yIn Y坐标
     * @return 对应的方块
     */
    public Block getBlock(float xIn, float yIn) {
        return pw.regions.getBlock(xToBlockCordInt(xIn), yToBlockCordInt(yIn));
    }

    /**
     * 获取生物所在的方块X坐标。
     *
     * @return 生物所在的方块X坐标
     */
    public int blockX() {
        return xToBlockCordInt(x());
    }

    /**
     * 获取生物所在的方块Y坐标。
     *
     * @return 生物所在的方块Y坐标
     */
    public int blockY() {
        return yToBlockCordInt(y());
    }

    /**
     * 将浮点坐标转换为方块坐标（浮点）。
     *
     * @param in 输入坐标
     * @return 方
