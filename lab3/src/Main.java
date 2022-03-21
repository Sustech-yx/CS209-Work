import java.io.*;
import java.nio.*;
import java.util.ArrayList;
import java.util.zip.*;


// 浏览java的源码和字节码
public class Main {
    public static void main(String[] args) {
        try {
            ZipInputStream zis = new ZipInputStream(
                    new FileInputStream(".\\lab3\\Practice3\\src.zip"));
            ZipEntry entry = zis.getNextEntry();
            String pattern1 = ".java";
            String temp;
            String[] dic;
            ArrayList<String> result_list = new ArrayList<>(300);

            while (entry != null) {
                temp = entry.getName();
                dic = temp.split("/");
                if (dic[0].equals("java") &&
                        (dic[1].equals("io") || dic[1].equals("nio")) &&
                        checkPostFix(pattern1, temp)) {
                    result_list.add(temp);
                }
                entry = zis.getNextEntry();
            }

            showResult(result_list, "java");

            zis.close();
        } catch (IOException e) {
            System.out.println("IOException.");
        }

        try {
            ZipInputStream zis = new ZipInputStream(
                    new FileInputStream(".\\lab3\\Practice3\\rt.jar"));
            ZipEntry entry = zis.getNextEntry();

            String pattern2 = ".class";
            String temp;
            String[] dic;
            ArrayList<String> result_list = new ArrayList<>(300);

            while (entry != null) {
                temp = entry.getName();
                dic = temp.split("/");
                if (dic[0].equals("java") &&
                        (dic[1].equals("io") || dic[1].equals("nio")) &&
                        checkPostFix(pattern2, temp)) {
                    result_list.add(temp);
                }
                entry = zis.getNextEntry();
            }

            showResult(result_list, "class");
        } catch (IOException e) {
            System.out.println("IOException.");
        }
    }

    private static boolean checkPostFix (String pattern, String m) {
        int a = pattern.length();
        int b = m.length();
        if (a > b) return false;
        if (a == b) return pattern.equals(m);
        for (int i = 1; i <= a; i ++) {
            if (m.charAt(b-i) != pattern.charAt(a-i)) {
                return false;
            }
        }
        return true;
    }

    private static void showResult (ArrayList<String> ls, String st) {
        System.out.printf("Count of .%s file in java.io/java.nio: %d\n", st, ls.size());
        for (String s : ls) {
            System.out.println(s);
        }
    }
}
