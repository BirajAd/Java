import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

public class SHomework{
    public static void main(String[] args) throws IOException{
        System.out.println("Hey");
        URL reader = new URL("https://mankato.mnsu.edu/");
        Scanner in = new Scanner(reader.openStream());
        while(in.hasNext()){
            String wholeLine = in.next();
            if(wholeLine.contains("href=\"h")){
                int start = wholeLine.indexOf("htt");
                int end = wholeLine.lastIndexOf("\"");
                if (start<wholeLine.length() && end<wholeLine.length() && start>0 && end>0 && start<end) {
                    System.out.println(wholeLine.substring(start, end));
                }else{
                    System.out.println(wholeLine);
                }
            }
        }

    }
}