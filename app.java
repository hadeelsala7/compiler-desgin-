import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

// هديل صلاح//// 20181473 
//سارة شحادة 20180209
//أصالةالقيق /// 20183151
//بيسان أبو جندي /// 20193053 
public class app {

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static void main(String[] args) {

        File file = new File("p.txt");
        ArrayList<Token> token = new ArrayList<Token>();

        StringBuffer contents = new StringBuffer();

        BufferedReader reader = null;

        ArrayList<String> keywords = new ArrayList<String>(
                Arrays.asList("PROGRAM", "INTEGER", "BOOLEAN", "BEGIN", "END",
                        "PROCEDURE", "IF", "THEN", "ELSE", "FOR", "TRUE", "FALSE", "VAR",
                        ":", "DO", "READ", "WRITE", "MOD", "DIV", "AND", "OR", "NOT", ">",
                        "<", ">=", "<=", "=", "+", "*", "<>", ":=", ";", ",", "..",
                        "ARRAY", "OF", ".", "(", ")", "]", "[", "-", "/"));

        try {

            reader = new BufferedReader(new FileReader(file));

            String text = null;
            String dd = null;
            String[] word;
            var lineNo = 1;

            while ((text = reader.readLine()) != null) {
                if (text.trim().length() == 0) { // i romoved spaces from the line then check if the line is empty ^^
                    continue; // Skip blank lines
                }
                contents.append(text).append("\n");
                dd = contents.toString().toUpperCase();// update1 convert string,convert uppercase
                word = dd.split("\\s");

                for (int i = 0; i < word.length; ++i) {

                    if (keywords.contains(word[i])) { // for keywords
                        token.add(new Token(word[i], "KeyWord", lineNo));
                        System.out.println(word[i] + "\t  KeyWord \t" + lineNo);

                    } else if (isNumeric(word[i])) {

                        token.add(new Token(word[i], "Numeric", lineNo));
                        System.out.println(word[i] + "\t  Numeric \t" + lineNo);

                    } else if (word[i].matches("^\\s*$")) {

                        continue;
                    }

                    else {
                        token.add(new Token(word[i], "identifier", lineNo));
                        System.out.println(word[i] + "\t  identifier \t" + lineNo);

                    }

                } // for loop
                lineNo++;
                contents = new StringBuffer();
                dd = null;
                word = null;

            } // while loop
            System.out.println("__________________________________________");
            Grammar grammer = new Grammar(token);
            grammer.Program();

        } // try
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // show file contents here

    }

}
