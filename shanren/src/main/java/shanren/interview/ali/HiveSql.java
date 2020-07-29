package shanren.interview.ali;

/**
 * 用户提交的sql
 *
 * @author tanxianwen 2020年7月29日
 */
public class HiveSql {

    public HiveSql(String id, int priority, String sql) {
        super();
        this.id = id;
        this.priority = priority;
        this.sql = sql;
    }

    private String id;
    private int priority;
    private String sql;

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
