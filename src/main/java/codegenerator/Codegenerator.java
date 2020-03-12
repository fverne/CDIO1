package codegenerator;

import java.lang.Math.*;

public class Codegenerator {
    private char[] capitalLetters = {65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90};
    private char[] smallLetters = {97, 98, 99, 100, 101, 102, 103, 104, 105, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122};
    private char[] numbers = {48, 49, 50, 51, 52, 53, 54, 55, 56, 57};

    public String generateCode() {
        char[] code = new char[8];
        code[0] = getCapitalLetter();
        code[1] = getSmallLetter();
        code[2] = getNumber();
        for (int i = 3; i < 8; i++) {
            int num = (int) (Math.random() * 3);
            switch (num) {
                case 0:
                    code[i] = getCapitalLetter();
                    break;
                case 1:
                    code[i] = getSmallLetter();
                    break;
                default:
                    code[i] = getNumber();
            }
        }
        return new String(code);
    }

    private char getCapitalLetter() {
        int num = (int) (Math.random() * capitalLetters.length);
        return capitalLetters[num];
    }

    private char getSmallLetter() {
        int num = (int) (Math.random() * smallLetters.length);
        return smallLetters[num];
    }

    private char getNumber() {
        int num = (int) (Math.random() * numbers.length);
        return numbers[num];
    }
}
