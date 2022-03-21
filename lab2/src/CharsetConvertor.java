import java.io.*;
import java.nio.charset.Charset;

public class CharsetConvertor {
    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("Argument missing.");
            return;
        }
        String oldCharSet = args[1];
        String newCharSet = args[2];
        String fileName = args[0];

        if (!Charset.isSupported(oldCharSet)) {
            System.out.println("Old char set not supported.");return;
        }
        if (!Charset.isSupported(newCharSet)) {
            System.out.println("New char set not supported.");
        }

        char[] cbuf = new char[65535];
        boolean flag = true;
        char[] real_buf = null;
        try (FileInputStream fis = new FileInputStream(fileName);
             InputStreamReader isr = new InputStreamReader(fis, oldCharSet);
             BufferedReader bReader = new BufferedReader(isr);){
            int len = bReader.read(cbuf);
            real_buf = new char[len];
            System.arraycopy(cbuf, 0, real_buf, 0, len);
            flag = false;
        } catch (FileNotFoundException e) {
            System.out.println("The pathname does not exist.");
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            System.out.println("The Character Encoding is not supported.");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Failed or interrupted when doing the I/O operations");
            e.printStackTrace();
        } finally {
            if (flag)
                return;
        }
        String str = new String(real_buf);
        try (OutputStreamWriter osw = new OutputStreamWriter(
                new FileOutputStream(fileName + "_" + newCharSet + ".txt"), newCharSet)) {
            osw.write(str);
            osw.flush();//osw.close();
        } catch (FileNotFoundException e) {
            System.out.println("The pathname does not exist.");
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            System.out.println("The Character Encoding is not supported.");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Failed or interrupted when doing the I/O operations");
            e.printStackTrace();
        }
    }
}
