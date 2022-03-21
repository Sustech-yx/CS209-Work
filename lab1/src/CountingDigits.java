public class CountingDigits {

    public static int getNumberOfDigitZero(long value) {
        //TODO:to count the number of digit 0 appearng in a long
        String str = Long.toString(value, 2);
        int res = 64 - str.length();
        for (int i = 0; i < str.length(); i ++) {
            if (str.charAt(i) == '0') res ++;
        }
        return res;
        //

    }

    public static void main(String[] args) {
        System.out.println(getNumberOfDigitZero(257));
    }
}
