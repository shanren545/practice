package shanren.interview.ali;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * 自然顺序，数字越小， 优先级越高
 *
 * @author tanxianwen 2020年7月29日
 */
public class SqlFutureTask extends FutureTask<SqlResult> implements Comparable<SqlFutureTask> {
    private String id;
    private int priority;

    public SqlFutureTask(String id, int priority, Callable<SqlResult> callable) {
        super(callable);
        this.priority = priority;
    }


    @Override
    public int compareTo(SqlFutureTask o) {
        if (null == o) {
            return -1;
        }

        return this.priority - o.priority;
    }


    public String getId() {
        return id;
    }
}
