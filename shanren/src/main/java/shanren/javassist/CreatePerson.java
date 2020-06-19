package shanren.javassist;

import java.lang.reflect.Method;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtField;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.Modifier;

public class CreatePerson {
    /**
     * 创建一个Person 对象
     *
     * @throws Exception
     */
    public static void createPseson() throws Exception {
        ClassPool pool = ClassPool.getDefault();

        // 1. 创建一个空类
        CtClass cc = pool.makeClass("shanren.javassist.Person");

        // 2. 新增一个字段 private String name;
        // 字段名为name
        CtField param = new CtField(pool.get("java.lang.String"), "name", cc);
        // 访问级别是 private
        param.setModifiers(Modifier.PRIVATE);
        // 初始值是 "xiaoming"
        cc.addField(param, CtField.Initializer.constant("xiaoming"));

        // 3. 生成 getter、setter 方法
        cc.addMethod(CtNewMethod.setter("setName", param));
        cc.addMethod(CtNewMethod.getter("getName", param));

        // 4. 添加无参的构造函数
        CtConstructor cons = new CtConstructor(new CtClass[] {}, cc);
        cons.setBody("{name = \"xiaohong\";}");
        cc.addConstructor(cons);

        // 5. 添加有参的构造函数
        cons = new CtConstructor(new CtClass[] {pool.get("java.lang.String")}, cc);
        // $0=this / $1,$2,$3... 代表方法参数
        cons.setBody("{$0.name = $1;}");
        cc.addConstructor(cons);

        // 6. 创建一个名为printName方法，无参数，无返回值，输出name值
        CtMethod ctMethod = new CtMethod(CtClass.voidType, "printName", new CtClass[] {}, cc);
        ctMethod.setModifiers(Modifier.PUBLIC);
        ctMethod.setBody("{System.out.println(name);}");
        cc.addMethod(ctMethod);

        // 这里会将这个创建的类对象编译为.class文件
        cc.writeFile("d:/bytecode");

//        Object person = cc.toClass().newInstance();
//        // 设置值
//        Method setName = person.getClass().getMethod("setName", String.class);
//        setName.invoke(person, "cunhua");
//        // 输出值
//        Method execute = person.getClass().getMethod("printName");
//        execute.invoke(person);
        
        
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
