package shanren.cglib;

import net.sf.cglib.core.DebuggingClassWriter;
import net.sf.cglib.proxy.Enhancer;

public class CglibTest {
    
    public static void main(String[] args) {
        testCglib();
    }
    
    public static void testCglib() {
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "d:/cglib");
        
        DaoInterceptor daoProxy = new DaoInterceptor();

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Dao.class);
        enhancer.setCallback(daoProxy);
        enhancer.setInterceptDuringConstruction(false);
        

        Dao dao = (Dao) enhancer.create();
        dao.update();
        //dao.select();
    }
}



