package shanren.jmx;

import java.lang.management.ManagementFactory;
import java.util.Arrays;
import java.util.Collections;

import javax.management.MBeanServer;

import com.google.common.collect.Streams;

public class MBeanTest {

    public static void main(String[] args) {
        MBeanServer ms = ManagementFactory.getPlatformMBeanServer();
        Arrays.asList(ms.getDomains()).stream().forEach(System.out::println);


        System.out.println(ManagementFactory.getMemoryMXBean().getHeapMemoryUsage());
    }

}
