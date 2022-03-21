import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FileTypeParser {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Missing Argument.");
            return;
        }

        try (FileInputStream fis = new FileInputStream(args[0])){

            byte[] buffer = new byte[65535];

            int byteNum = fis.read(buffer);
            System.out.println("File path: " + args[0]);
            System.out.print("File header: [");
            for(int i = 0; i < 4; i++){
                System.out.printf("%02X",buffer[i]);
            }
            System.out.println("]");
            System.out.print("File type: ");
            if (buffer[0] == (byte) 0x89
                    && buffer[1] == (byte) 0x50
                    && buffer[2] == (byte) 0x4E
                    && buffer[3] == (byte) 0x47) {
                System.out.println("png");
            } else if (buffer[0] == (byte) 0x50
                    && buffer[1] == (byte) 0x4b
                    && buffer[2] == (byte) 0x03
                    && buffer[3] == (byte) 0x04) {
                System.out.println("zip or jar");
            } else if (buffer[0] == (byte) 0xCA
                    && buffer[1] == (byte) 0xFE
                    && buffer[2] == (byte) 0xBA
                    && buffer[3] == (byte) 0xBE) {
                System.out.println("class");
            } else {
                System.out.println("Unknown");
            }

        } catch (FileNotFoundException e) {
            System.out.println("The pathname does not exist.");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Failed or interrupted when doing the I/O operations");
            e.printStackTrace();
        }
    }
}
