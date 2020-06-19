package shanren.cglib;

import java.lang.reflect.Method;
import java.net.URL;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class DaoInterceptor implements MethodInterceptor {

    @Override
    public Object intercept(Object object, Method method, Object[] objects, MethodProxy proxy) throws Throwable {
//        System.out.println(object.getClass().getCanonicalName());
//        System.out.println(method);
        URL url = object.getClass().getProtectionDomain().getCodeSource().getLocation();
        //System.out.println(url);
        System.out.println("Before Method Invoke");
        proxy.invokeSuper(object, objects);
        //proxy.invoke(new Dao(), objects);
        System.out.println("After Method Invoke");

        return object;
    }

}
