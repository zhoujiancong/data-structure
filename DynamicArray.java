package dynamicarray;

import java.util.Arrays;
import java.util.Iterator;
import java.util.function.Consumer;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class DynamicArray implements Iterable<Integer> {

    private int size;
    private int capacity = 10;
    //懒汉式思维 也就是说 这个数组如果不用 我就先不给他分配内存 用了再分配
    private int[] array;


    public void addLast(int element) {
        //按照索引插入其实也可以做到尾插
        //所以直接调用那个方法
        //其实这也是减少代码的冗余度
        add(size, element);
    }

    public void add(int index, int element) {
        //先检查长度 扩容
        checkAndGrow();
        //满足条件 则移动数组
        if (index >= 0 && index < size) {
            System.arraycopy(array, index, array, index + 1, size - index);
            //不满足条件 就抛出异常
        } else if (index < 0 || index > size) {
            throw new IndexErrorException("Index out of bounds");
        }

        //将元素插入
        //如果size == index 即尾插 也是满足这个代码
        array[index] = element;
        size++;

    }

    public void foreach(Consumer<Integer> consumer) {
        //通过函数式接口
        //传入相对应的接口 来实现遍历
        //具体是怎么遍历的需要看 accept方法是怎么实现的

        for (int i : array) {
            consumer.accept(i);
        }

    }


    //通过实现Iterable接口 通过里面的方法来遍历
    //外部想要使用的时候 可以通过增强for 也可以通过调用下面这个方法\
    @Override
    public Iterator<Integer> iterator() {
        return new Iterator<Integer>() {
            int index = 0;

            @Override
            public boolean hasNext() {
                return index < size;
            }

            @Override
            public Integer next() {
                return array[index++];
            }
        };
    }

    //通过stream流遍历
    public IntStream stream() {
        //获取到了stream流 外面通过forEach()调用
        return Arrays.stream(array);

    }

    public int removeByIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexErrorException("Index out of bounds");
        }

        int removed = array[index];
        System.arraycopy(array, index + 1, array, index, size - index - 1);
        return removed;
    }

    public void checkAndGrow() {

        if (size == 0) {
            array = new int[capacity];
            return;
        }

        if (size == capacity) {
            capacity += capacity >> 1;
            int[] newArray = new int[capacity];
            System.arraycopy(array, 0, newArray, 0, size);
            array = newArray;
        }

    }

}

