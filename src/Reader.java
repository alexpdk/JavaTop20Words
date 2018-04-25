import java.io.File;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Reader {

    private static class WordCounter implements Comparable<WordCounter>{
        String word;
        int value = 0;

        WordCounter(String w){
            word = w;
        }
        @Override
        public int compareTo(WordCounter counter) {
            return this.value - counter.value;
        }
        @Override
        public String toString() {
            return String.format("%s %d", word, value);
        }
    }

    private static HashMap<String, WordCounter> words = new HashMap<>();
    private static Pattern lettersWithDash = Pattern.compile("[\\p{L}-]+");

    private static void findWordsIn(String line){
        Matcher m = lettersWithDash.matcher(line);
        int index = 0;
        while (m.find(index)) {
            String word = m.group().toLowerCase();
            words.putIfAbsent(word, new WordCounter(word));
            words.get(word).value++;
            index = m.end();
        }
    }
    private static void printTopList(){
        ArrayList<WordCounter> list = new ArrayList<>(words.values());
        list.sort(Comparator.reverseOrder());

        for(int i=0; i<list.size() && i<20; i++){
            System.out.println(list.get(i));
        }
    }

    public static void main(String args[]){
        File f = new File("res/input2.txt");
        try(Scanner sc = new Scanner(f)){
            while(sc.hasNextLine()){
                findWordsIn(sc.nextLine());
            }
            printTopList();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
