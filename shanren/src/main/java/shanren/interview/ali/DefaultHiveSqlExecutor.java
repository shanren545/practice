package shanren.interview.ali;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class DefaultHiveSqlExecutor extends ThreadPoolExecutor implements HiveSqlExecutor {

    private SqlFutureTaskCache taskCache = new SqlFutureTaskCache();
    // private AtomicInteger taskIdGenerator = new AtomicInteger(0);

    public DefaultHiveSqlExecutor(int coreNodeSize, int maxNodeSize, long keepAliveTime, TimeUnit unit) {
        super(coreNodeSize, maxNodeSize, keepAliveTime, unit, new PriorityBlockingQueue<Runnable>());
    }



    /*
     * (non-Javadoc)
     * 
     * @see shanren.interview.ali.Tdd#dispatch(shanren.interview.ali.SqlTask)
     */
    @Override
    public SqlFutureTask dispatch(SqlTask task) {
        Callable<SqlResult> callable = new Callable<SqlResult>() {

            @Override
            public SqlResult call() throws Exception {
                try {
                    return task.doSql();
                } finally {
                    taskCache.remove(task.getId());
                }
            }
        };
        SqlFutureTask future = new SqlFutureTask(task.getId(), task.getPriority(), callable);

        SqlFutureTask rt = taskCache.putIfAbsent(task.getId(), future);
        if (null != rt) {
            throw new IllegalArgumentException("提交的任务id不唯一");
        }

        execute(future);

        return future;
    }


    /*
     * (non-Javadoc)
     * 
     * @see shanren.interview.ali.Tdd#cancel(int)
     */
    @Override
    public boolean cancel(String sqlId) {
        Future<SqlResult> task = taskCache.remove(sqlId);
        return task == null ? false : task.cancel(true);
    }



    @Override
    public void shutdownGracefully() {
        try {
            awaitTermination(30, TimeUnit.SECONDS);
        } catch (InterruptedException expected) {}
    }
}
