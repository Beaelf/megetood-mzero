package demo.pattern.singleton;

import java.lang.reflect.InvocationTargetException;

public class LazyDoubleCheckSingleton {

    /**
     * volatile 保证其可见性与禁止重排序
     */
    private volatile static LazyDoubleCheckSingleton instance;

    private LazyDoubleCheckSingleton(){}

    /**
     * synchronized：保证其原子性
     * 双重检查锁检测机制：保证线程操作资源过程不被打断
     * @return
     */
    public static LazyDoubleCheckSingleton getInstance(){
        //第一次检测
        if (instance==null){
            //同步
            synchronized (LazyDoubleCheckSingleton.class){
                if (instance == null){
                    /*
                        对象初始化的三个步骤
                        memory = allocate(); //1.分配对象内存空间
                        instance(memory);    //2.初始化对象
                        instance = memory;   //3.设置instance指向刚分配的内存地址，此时instance！=null
                    */
                    instance = new LazyDoubleCheckSingleton();
                }

            }
        }
        return instance;
    }

    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        System.out.println(LazyDoubleCheckSingleton.getInstance());
        System.out.println(LazyDoubleCheckSingleton.getInstance());
    }
}

