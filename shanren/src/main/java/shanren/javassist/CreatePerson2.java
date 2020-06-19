package shanren.javassist;

import java.lang.reflect.Method;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtField;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.Modifier;

public class CreatePerson2 {
    /**
     * 创建一个Person 对象
     *
     * @throws Exception
     */
    public static void createPseson() throws Exception {
        ClassPool pool = ClassPool.getDefault();
        
        pool.appendClassPath("d:/bytecode");
        // 获取接口
        CtClass codeClassI = pool.get("shanren.javassist.PersonI");
        // 获取上面生成的类
        CtClass ctClass = pool.get("shanren.javassist.Person");
        // 使代码生成的类，实现 PersonI 接口
        ctClass.setInterfaces(new CtClass[]{codeClassI});

        // 以下通过接口直接调用 强转
        PersonI person2 = (PersonI)ctClass.toClass().newInstance();
        System.out.println(person2.getName());
        person2.setName("xiaolv");
        person2.printName();
    }

    public static void main(String[] args) {
        CtClass.debugDump = "d:/bytecode/debug";
        try {
            createPseson();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
