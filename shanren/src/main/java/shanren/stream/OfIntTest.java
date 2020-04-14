package shanren.stream;

import java.util.Spliterator.OfPrimitive;
import java.util.function.Consumer;
import java.util.function.IntConsumer;

public class OfIntTest {

    public class OfIntx implements OfPrimitive<Integer, IntConsumer, OfIntx> {

        @Override
        public
        OfIntx trySplit(){
            return null;
        }

//        @Override
//        public
//        boolean tryAdvance(IntConsumer action){return false;}

//        @Override
//        public
//        boolean tryAdvance(Consumer<? super Integer> action){return false;};

        @Override
        public long estimateSize() {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public int characteristics() {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public boolean tryAdvance(Consumer<? super Integer> action) {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public boolean tryAdvance(IntConsumer action) {
            // TODO Auto-generated method stub
            return false;
        }
        

    }
    
    
}
