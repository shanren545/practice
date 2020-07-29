package shanren.interview.ali;

/**
 * hive sql 任务的内部表示 自然顺序，数字越小， 优先级越高
 *
 * @author tanxianwen 2020年7月29日
 */
public class SqlTask {
    private String id;
    private int priority;
    private String sql;

    public SqlTask(String id, int priority, String sql) {
        super();
        this.id = id;
        this.priority = priority;
        this.sql = sql;
    }


    public SqlResult doSql() throws Exception {
        System.out.println("执行sql:" + toString());

        Thread.sleep(100L);
        return new SqlResult(SqlResult.OK);
    }


    public int getPriority() {
        return priority;
    }


    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "[priority=" + priority + ", sql=" + sql + "]";
    }


    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }



}
