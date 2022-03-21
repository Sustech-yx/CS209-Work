import java.util.Arrays;

public class ByteBuffParser {
    public static int[] ParseByteBuff(Byte[] byteBuffer, int[] lengthOfBits) {
        //TODO:lengthOfBits stores the order and bits to be parsed
        //convert the contents of the byteBuffer to int according to lengthOfBits
        assert sumOfArray(lengthOfBits) == 8 * byteBuffer.length;
        int[] temp = new int[byteBuffer.length * 8];
        StringBuilder sb = new StringBuilder();
        for (Byte b : byteBuffer) {
            String str = Integer.toString(Byte.toUnsignedInt(b), 2);
            int n = str.length();
            while (n < 8) {
                sb.append('0');
                n ++;
            }
            sb.append(str);
        }
        char[] cs = sb.toString().toCharArray();
        for (int i = 0; i < temp.length; i ++) {
            temp[i] = (cs[i] - '0');
        }
//        System.out.println(Arrays.toString(temp));
        int[] res = new int[lengthOfBits.length];
        int pointer = 0;
        for (int i = 0; i < res.length; i ++) {
            int[] temp_arr = new int[lengthOfBits[i]];
            System.arraycopy(temp, pointer, temp_arr, 0, lengthOfBits[i]);
            int mul = 1;
            for (int j = lengthOfBits[i] - 1; j >= 0; j --) {
                temp_arr[j] *= mul;
                mul *= 2;
            }
            res[i] = sumOfArray(temp_arr);
            pointer += lengthOfBits[i];
        }
        return res;
    }

    public static int sumOfArray(int[] a) {
        int sum = 0;
        for (int e :
                a) {
            sum += e;
        }
        return sum;
    }

    public static void main(String[] args) {
        Byte[] buffer = {(byte) 128, (byte) 4};
        int[] lengthOfBits = {4, 4, 8}; //Notice:Each element <= 32
        //{(byte)128, (byte)4} ----> 10000000 00000100
        //                           |__||__| |______|
        //     split by lengthOfBits  4   4     8
        //                          1000 0000  00000100
        //               result:      8   0      4

        int[] result = ParseByteBuff(buffer, lengthOfBits);
        System.out.println(Arrays.toString(result));
        //8,0,4
    }
}


