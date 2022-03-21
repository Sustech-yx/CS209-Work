import java.io.*;
import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextProcessor {
    private String path;
    private HashMap<String, ArrayList<Position>> word_map;
    private ArrayList<String> line_list;
    private ArrayList<Position> wrong_letter;
    private List<String> allWords;
    private static final String REGEX = "([\\w][\\w'\\-]*[\\w]|[\\w])";

    public TextProcessor(String path) {
        this.path = path;
        word_map = new HashMap<>();
        line_list = new ArrayList<>();
        wrong_letter = new ArrayList<>();
        try {
            allWords = getFileContent(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<String> getAnotherFileContent(String path) throws IOException {
        Pattern p = Pattern.compile(REGEX);
        List<String> res = new ArrayList<>();
        BufferedReader bf = new BufferedReader(new FileReader(path));
        String data;
        Matcher m;
        String s;
        while ((data = bf.readLine()) != null) {
            m = p.matcher(data);
            while (m.find()) {
                s = m.group().toLowerCase(Locale.ROOT);
                res.add(s);
            }
        }
//        System.out.println(res);
        bf.close();
        Comparator<String> c = (s1, s2) ->{ return s1.length() - s2.length();};
        return res;
    }

    private List<String> getFileContent(String path) throws IOException {
        Pattern p = Pattern.compile(REGEX);
        List<String> res = new ArrayList<>();
        BufferedReader bf = new BufferedReader(new FileReader(path));
        boolean isStart = true;
        int line = 1;
        String data;
        Matcher m;
        Position pos;
        String s;
        while ((data = bf.readLine()) != null) {
            m = p.matcher(data);
            while (m.find()) {
                s = m.group().toLowerCase(Locale.ROOT);
                pos = new Position(line, m.start()+1);
                word_map.computeIfAbsent(s, k -> new ArrayList<>());
                word_map.get(s).add(pos);
                res.add(s);
                if (isStart && Character.isLowerCase(m.group().charAt(0))) {
                    wrong_letter.add(pos);
                }
                isStart = m.end() != data.length() &&
                        (data.charAt(m.end()) == '!' ||
                                data.charAt(m.end()) == '?' ||
                                data.charAt(m.end()) == '.');
            }
            line_list.add(data);
            line ++;
        }
        bf.close();
        return res;
    }

    public TreeMap<String, Integer> getCommonWords(int n, String[] stopwords) {
        HashMap<String, Integer> freqMap = new HashMap<>();
        Integer i;
        for (String s : allWords) {
            if ((i = freqMap.get(s)) == null) {
                freqMap.put(s, 1);
            } else {
                freqMap.replace(s, i+1);
            }
        }

        List<Map.Entry<String, Integer>> entryList = new ArrayList<>();
        freqMap.entrySet()
                .stream()
                .filter(e->!Arrays.asList(stopwords).contains(e.getKey()))
                .forEach(entryList::add);
        entryList.sort((o1, o2) -> {
            if (o1.getValue().equals(o2.getValue()))
                return o1.getKey().compareTo(o2.getKey());
            else
                return -Integer.compare(o1.getValue(), o2.getValue());
        });

        TreeMap<String, Integer> res = new TreeMap<>();
        entryList.stream()
                .limit(n)
                .forEach(e->res.put(e.getKey(), e.getValue()));
        return res;
    }

    public ArrayList<Position> highlightWord(Position pos) {
        String line = line_list.get(pos.getLine() - 1);
        int col = pos.getCol() - 1;
        Pattern p = Pattern.compile("\\b\\S+\\b");
        Matcher m = p.matcher(line);
        String target = null;
        while (m.find()) {
            if (m.start() <= col && col < m.end()) {
                target = m.group().toLowerCase(Locale.ROOT);
                break;
            }
        }
        if (target == null) return new ArrayList<>();
        else return word_map.get(target);
    }

    public List<Position> fixLowercaseFirstLetter() {
        return wrong_letter;
    }

    public String encrypt() {
        StringBuilder temp, sb = new StringBuilder();
        Pattern p = Pattern.compile("\\b\\S+\\b");
        Matcher m;
        char flag;
        int start;
        for (String line : line_list) {
            m = p.matcher(line);
            start = 0;
            while (m.find()) {
                sb.append(line, start, m.start());
                temp = new StringBuilder(m.group());
                temp.reverse();
                flag = Character.toLowerCase(temp.charAt(0));
                if (flag == 'a' || flag == 'e' || flag == 's') {
                    temp.insert(1, (int) temp.charAt(0));
                }
                temp.append(m.group().length());
                sb.append(temp);
                start = m.end();
            }
            if (start == line.length() - 1)
                sb.append(line.charAt(line.length() - 1));
            else if (start < line.length() - 1)
                sb.append(line, start, line.length());
            sb.append("\n");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    private HashSet<List<String>> generateNgramList (int ngram, List<String> words) {
        HashSet<List<String>> res = new HashSet<>();
        List<String> temp;
        for (int i = 0; i <= words.size() - ngram; i ++) {
//            System.out.println(words.subList(i, i+ngram));
//            res.add(words.subList(i, i+ngram));
            temp = new ArrayList<>(ngram);
            for (int j = 0; j < ngram; j++) {
                temp.add(words.get(i+j));
            }
            res.add(temp);
        }
        return res;
    }

//    static class P {
//        int a;
//        char b;
//
//        public P(int a, char b) {
//            this.a = a;
//            this.b = b;
//        }
//    }

    public HashSet<List<String>> ngramSim(int ngram, String pathForAnotherFile) {
        HashSet<List<String>> set1 = generateNgramList(ngram, allWords);
        HashSet<List<String>> set2;
        try {
            set2 = generateNgramList(ngram, getAnotherFileContent(pathForAnotherFile));
//            System.out.println(set2);
            set1.retainAll(set2);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return set1;
    }
    BinaryOperator<Integer> op1 = (a,b)->(a+b*b);
    Function<Integer, Integer> op2 = i->i*i;
    UnaryOperator<Integer> op3 = i->i*i;
}