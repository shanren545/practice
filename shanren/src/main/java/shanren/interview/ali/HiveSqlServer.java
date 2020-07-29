package shanren.interview.ali;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * hive sql执行的 server，负责处理客户提交的所有请求（提交sql， 取消sql）
 *
 * @author tanxianwen 2020年7月29日
 */
public class HiveSqlServer {
    /** hive sql 的执行器 */
    private HiveSqlExecutor executor;

    public HiveSqlServer(int concurrentNodes) {
        executor = new DefaultHiveSqlExecutor(concurrentNodes, concurrentNodes, 60, TimeUnit.SECONDS);
    }

    /**
     * 提交带优先级的hive sql到hive server执行
     * 
     * @param sql 带优先级的hive sql
     * @return SqlFutureTask, 其中包括taskId。可作为后续取消任务的标识
     */
    public SqlResult submitSql(HiveSql sql) {
        try {
            return executor.dispatch(new SqlTask(sql.getId(), sql.getPriority(), sql.getSql())).get();
        } catch (InterruptedException e) {
            // TODO
        } catch (ExecutionException e) {
            // TODO
        }

        return new SqlResult(SqlResult.ERROR);
    }

    /**
     * 取消指定id的hive sql执行
     * 
     * @param taskId 提交hive sql时返回的taskId
     * @return true：如果需求成功。 false：任务已经执行，取消失败。
     */
    public boolean cancelSql(String taskId) {
        return executor.cancel(taskId);
    }

    /**
     * 优雅停止hive sql server
     */
    public void shutdownGracefully() {
        executor.shutdownGracefully();
    }

}
