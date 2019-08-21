import java.io.File;
import java.util.*;
import java.io.*;

public class Worker
{
    public static void main(String[] args) {
        boolean writeToFile=true;              //для вывода в файл выставить true

        try {

            if (writeToFile){
                PrintStream out = new PrintStream(new FileOutputStream("out.log"));     //путь к файлу данных
                PrintStream err = new PrintStream(new FileOutputStream("err.log"));     //путь к логу ошибок
                System.setOut(out);
                System.setErr(err);
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        String way = new String();
        boolean needAll = false;            //если нужна обобщенная статистика по всем файлам в папке - выставить в true
        boolean showStat = true;             //отображение статистики

        File workDirectory = new File ("C:\\Users\\User\\Desktop\\SandBox\\Dialogs");
        for (File file : workDirectory.listFiles())
        {
            if (file.getAbsolutePath().indexOf(".json")>0) {
                System.out.println(file.getAbsolutePath());
                way = file.getAbsolutePath();

                AlphaTester.fullStorage(way);
                for (String string : Storage.strings) {
                    Message msg = new Message(string);
                }
                if (showStat){
                    System.out.println("\n\n\nБеседа: " + Storage.name + "\n\nAll Messages\n");
                    for (String str : Storage.countOfMessages.keySet()) {
                        System.out.println(str + "\t\t" + Storage.countOfMessages.get(str));
                    }
                    System.out.println("\n\nCommercial Messages");
                    for (String str : Storage.countOfCommercialMessages.keySet()) {
                        System.out.println(str + "\t\t" + Storage.countOfCommercialMessages.get(str));
                    }
                }
            }
            if (!needAll)
            {
                Storage.countOfCommercialMessages.clear();
                Storage.countOfMessages.clear();
            }
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
    String dateStr;

    String wayToDict = "C:\\Users\\User\\Desktop\\SandBox\\dict.txt";        //путь к словарю. Каждое слово с новой строки
    Message(String inputStr)
    {
        String tmp;
        tmp = inputStr.substring(4, inputStr.indexOf('\t'));
        senderID = Integer.parseInt(tmp);

        tmp = inputStr.substring(inputStr.indexOf("\"t\":\"")+5, inputStr.indexOf("\"d\":")-2);
        text = tmp;
        tmp = inputStr.substring( inputStr.indexOf("\"d\":")+4);
        try {
            int UNIXTime = Integer.parseInt(tmp)+diffUnixVKTime;
            date =new Date((long)UNIXTime*1000);

            Scanner readDict = new Scanner(new File(wayToDict));
            String word = new String();
            while (readDict.hasNextLine())
            {
                // System.out.println(1);
                word=readDict.nextLine();
                if (text.indexOf(word.trim())!=-1)
                {
                    isCommercial=true;
                    keyWord = word;
                }
            }
            readDict.close();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

        dateStr = date.getDate()+"."+(date.getMonth()+1)+"."+(date.getYear()+1900);
        //if (isCommercial)                                                          //если нужны только коммерческие сообщения - раскомментить
        //  System.out.println( "{At: "+ dateStr+"; By: "+senderID + "; " +          //если нужна только статистика - закомментить
        //            text + ";  isCommercial = "+isCommercial+"; keyword = "+keyWord+"}");
        String key = Integer.toString(senderID);
        if (Storage.countOfMessages.containsKey(key))
            Storage.countOfMessages.put(key, Storage.countOfMessages.get(key)+1);
        else
            Storage.countOfMessages.put(key, 1);
        if (isCommercial){
            if (Storage.countOfCommercialMessages.containsKey(key))
                Storage.countOfCommercialMessages.put(key, Storage.countOfCommercialMessages.get(key)+1);
            else
                Storage.countOfCommercialMessages.put(key, 1);
        }
    }
}

class AlphaTester
{
    public static void fullStorage( String path) {
        ArrayList<String> strings = new ArrayList<>();
        String readen = new String();
        try {
            Scanner scanner = new Scanner(new File(path));
            readen = scanner.nextLine();
            Storage.name = readen.split("\"")[13];
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
    public static String name;
    public static Map <String, Integer> countOfMessages = new HashMap<>();
    public static Map <String, Integer> countOfCommercialMessages = new HashMap<>();

}