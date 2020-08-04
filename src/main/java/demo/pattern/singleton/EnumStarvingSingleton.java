package demo.pattern.singleton;

/**
 * 通过反射可以直接入侵类的私有构造函数，实例化类的对象；
 * 枚举类无法通过反射入侵，因此能够解决上述问题；
 */
public class EnumStarvingSingleton {
    private EnumStarvingSingleton(){}
    public static EnumStarvingSingleton getInstance(){
        return ContainerHolder.HOLDER.instance;
    }
    private enum ContainerHolder{
        HOLDER;
        private EnumStarvingSingleton instance;
        ContainerHolder(){
            instance = new EnumStarvingSingleton();
        } 
    }
}
