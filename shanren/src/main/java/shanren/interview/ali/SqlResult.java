package shanren.interview.ali;

/**
 * sql执行结果，可能携带数据
 *
 * @author tanxianwen 2020年7月29日
 */
public class SqlResult {
    public static final int ERROR = 1;
    public static final int OK = 0;

    private int code = 0;
    private Object data;



    public SqlResult(int code) {
        super();
        this.code = code;
    }

    public SqlResult(int code, Object data) {
        super();
        this.code = code;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
