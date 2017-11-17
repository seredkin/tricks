import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BookStats {

    private static boolean compareBooksByChars(String book1, String book2) {
        final ConcurrentHashMap<Integer, Integer> book1Stats = new ConcurrentHashMap<>();
        final ConcurrentHashMap<Integer, Integer> book2Stats = new ConcurrentHashMap<>();
        book1.chars().parallel().forEach(value -> book1Stats.compute(value, (key, val) -> 1 + Optional.ofNullable(val).orElse(0)));
        book2.chars().parallel().forEach(value -> book2Stats.compute(value, (key, val) -> 1 + Optional.ofNullable(val).orElse(0)));
        return book1Stats.equals(book2Stats);
    }

    private static boolean compareStreamsByChars(Stream<Integer> book1, Stream<Integer> book2){
        return book1.collect(Collectors.groupingBy(Object::hashCode)).equals(book2.collect(Collectors.groupingBy(Object::hashCode)));
    }

    public static void main(String[] args) {
        final String book1 = "This is a sample text of a book with some Chinese: 「冲气以为和」的「冲」字，古籍图像上是「冲」，但是扫描成了「沖」，不过这两个字在古代通用，应该是自动" +
                " and Japanese 原文]篭毛與 美篭母乳 布久思毛與 美夫君志";

        boolean same = compareBooksByChars(book1, new StringBuffer(book1).reverse().toString());
        String differentBook = new StringBuffer(book1).append(" The Diff ").reverse().toString();
        List<char[]> list = Arrays.asList(differentBook.toCharArray());
        Collections.shuffle(list);
        differentBook = Arrays.toString(list.toArray());
        boolean notTheSame = compareBooksByChars(book1, differentBook);
        boolean sameStreams = compareStreamsByChars(book1.chars().boxed(), new StringBuffer(book1).reverse().toString().chars().boxed());
        boolean differentStreams = compareStreamsByChars(book1.chars().boxed(), differentBook.chars().boxed());


        System.out.println("same = " + same);
        System.out.println("notTheSame = " + notTheSame);
        System.out.println("sameStreams = " + sameStreams);
        System.out.println("differentStreams = " + differentStreams);
    }
}
