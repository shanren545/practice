package shanren.interview.ali;

import java.util.UUID;
import java.util.Vector;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        Vector<String> taskIds = new Vector<>();

        // 5个并发
        HiveSqlServer server = new HiveSqlServer(5);

        for (int threadIndex = 0; threadIndex < 10; threadIndex++) {
            new Thread(() -> {
                for (int i = 100; i > 0; i--) {
                    final String taskId = generateTaskId();
                    taskIds.add(taskId);
                    server.submitSql(new HiveSql(taskId, i, "select * from a;"));
                }
            }).start();
        }

        // 测试取消结果
        // Thread.sleep(1000);
        // for (String taskId : taskIds) {
        // boolean result = server.cancelSql(taskId);
        // System.out.println("任务[" + taskId + "]取消结果：" + result);
        // }
        Thread.sleep(60000);
        server.shutdownGracefully();
    }

    private static String generateTaskId() {
        return UUID.randomUUID().toString();
    }

}
