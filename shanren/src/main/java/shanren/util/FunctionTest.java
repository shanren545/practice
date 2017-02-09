package shanren.util;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public interface FunctionTest {

    void test(Function<String, String> fun);



    public static void main(String[] args) {
        // TODO Auto-generated method stub
        FunctionTest test = (Function<String, String> t) -> System.out.println(t.apply("test"));
        test.test((String dd) -> {
            dd = dd + dd;
            return "asd" + dd;
        });

        List<String> list = new ArrayList<>();

        List<Integer> ids = new ArrayList<>();
        list.forEach((String item) -> ids.add(Integer.parseInt(item)));

    }

}
