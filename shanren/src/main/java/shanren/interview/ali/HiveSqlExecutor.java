package shanren.interview.ali;


/**
 * hive sql的执行器
 *
 * @author tanxianwen 2020年7月29日
 */
public interface HiveSqlExecutor {

    /**
     * 分发带优先级的sql任务到执行器执行
     * 
     * @param sql 带优先级的sql任务
     * @return taskId。 返回 执行hive sql的taskId，作为后续取消任务的标识
     */
    SqlFutureTask dispatch(SqlTask task);


    /**
     * 取消指定id的hive sql执行
     * 
     * @param taskId 提交hive sql时返回的taskId
     * @return true：如果需求成功。 false：任务已经执行，取消失败。
     */
    boolean cancel(String taskId);

    /**
     * 优雅停止执行器
     */
    void shutdownGracefully();
}
