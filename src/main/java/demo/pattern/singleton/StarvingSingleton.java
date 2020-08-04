package demo.pattern.singleton;

/**
 * 饿汉模式
 */
public class StarvingSingleton {
    /**
     * 类加载时就实例化
     * 私有的构造函数，与类实例
     */
    private static final StarvingSingleton starvingSingleton = new StarvingSingleton();
    private StarvingSingleton(){ }

    /**
     * 获取实例的唯一入口
     * @return  实例
     */
    public static StarvingSingleton getInstance(){
        return starvingSingleton;
    }

}

