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
        AlphaTester.fullStorage();
        for (String string : Storage.strings)
        {
            Message msg = new Message(string);
        }

    }
}

class Message
{
    Date date = new Date();
    final int diffUnixVKTime = 1138665602;
    String text;
    boolean isCommercial;
    String keyWord = "NULL";
    int senderID;

    String wayToDict = "C:\\Users\\User\\Desktop\\SandBox\\ParseJSON\\dict.txt";
    Message(String inputStr)
    {
        String tmp;
        tmp = inputStr.substring(4, inputStr.indexOf('\t'));
        senderID = Integer.parseInt(tmp);

        tmp = inputStr.substring(inputStr.indexOf("\"t\":\"")+5, inputStr.indexOf("\"d\":")-2);
        text = tmp;
        tmp = inputStr.substring( inputStr.indexOf("\"d\":")+4);
        int UNIXTime = Integer.parseInt(tmp)+diffUnixVKTime;
        date =new Date((long)UNIXTime*1000);
        try {
            Scanner readDict = new Scanner(new File("C:\\Users\\User\\Desktop\\SandBox\\dict.txt"));
            String word = new String();
            while (readDict.hasNextLine())
            {
                //System.out.println(1);
                word=readDict.nextLine();
                if (text.indexOf(word.trim())!=-1)
                {
                    isCommercial=true;
                    keyWord = word;
                }
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println( "At: "+ date.getDate()+"."+date.getMonth()+"."+(date.getYear()+1900)+"; By: "+senderID + "; " + text + "  isCommercial = "+isCommercial);
    }
}

class AlphaTester
{
    public static void fullStorage() {
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
                            str2.indexOf("\"i\"")==-1 && str2.indexOf("\"t\":\"\"") == -1 && str2.indexOf("\"e\"")==-1)
                        withoutQuote+=str2+"\t";
                }
                withoutQuote = withoutQuote.replace('{', ' ').replace('}', ' ').replace(']', ' ').trim();

                if (withoutQuote.charAt(1)=='f' && withoutQuote.indexOf("\"d\":")==withoutQuote.lastIndexOf("\"d\":") && withoutQuote.indexOf("\"t\"")!=-1
                && withoutQuote.indexOf("\"t\"") < withoutQuote.indexOf("\"d\"") && withoutQuote.indexOf("\"t\":")==withoutQuote.lastIndexOf("\"t\":"))
                {
                    //System.out.println(withoutQuote);
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
