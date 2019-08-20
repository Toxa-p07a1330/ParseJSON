import javax.xml.crypto.Data;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Worker
{
    public static void main(String[] args) {

    }
}

class Message
{
    Date date;
    String text;
    boolean isCommercial;
    String keyWord = "NULL";

    Message(String inputStr)
    {

    }
}

class AlphaTester
{
    public static void main(String[] args) {
        ArrayList<String> strings = new ArrayList<>();
        String path = "C:\\Users\\User\\Downloads\\dialog-35.json";
        String readen = new String();
        try {
            Scanner scanner = new Scanner(new File(path));
            readen = scanner.nextLine();
            String withoutQuote = new String();
            for (String str : readen.split("},"))
            {

                for (String str2 : str.split(","))
                {
                    if (str2.indexOf("\"a\"")==-1 && str2.indexOf("\"n\"")==-1 && str2.indexOf("\"m\"")==-1 && str2.indexOf("\"o\"")==-1 && str2.indexOf("\"s\"")==-1
                            && str2.indexOf("\"z\"")==-1 && str2.indexOf("\"w\"")==-1 && str2.indexOf("\"q\"")==-1 &&
                            str2.indexOf("\"i\"")==-1 && str2.indexOf("\"t\":\"\"") == -1)
                        withoutQuote+=str2+"\t";
                }
                withoutQuote = withoutQuote.replace('{', ' ').replace('}', ' ').replace(']', ' ').trim();

                if (withoutQuote.charAt(1)=='f' && withoutQuote.indexOf("\"d\":")==withoutQuote.lastIndexOf("\"d\":") && withoutQuote.indexOf("\"t\"")!=-1
                && withoutQuote.indexOf("\"t\"") < withoutQuote.indexOf("\"d\""))
                {
                    System.out.println(withoutQuote);
                    strings.add(withoutQuote);
                }
                withoutQuote="";
            }


        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Storage.strings = strings;
    }
}

class Storage
{
    public static ArrayList<String> strings;
}
