package shanren.interview.ali;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 执行任务的缓存， 为了使取消任务更有效率，采用缓存的形式
 *
 * @author tanxianwen 2020年7月29日
 */
public class SqlFutureTaskCache {

    private ConcurrentMap<String, SqlFutureTask> cache = new ConcurrentHashMap<>();

    public SqlFutureTask putIfAbsent(String taskId, SqlFutureTask task) {
        return cache.putIfAbsent(taskId, task);
    }

    public SqlFutureTask remove(String taskId) {
        return cache.remove(taskId);
    }

    public SqlFutureTask get(String taskId) {
        return cache.remove(taskId);
    }
}
