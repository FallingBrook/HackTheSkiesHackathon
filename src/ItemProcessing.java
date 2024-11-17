import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

public class ItemProcessing {


    public static void NewGarbageItem(int data, String Name) throws IOException, InterruptedException {
        PrintData(data, Name);
    }
    public static void NewCompostItem(int data, String Name) throws IOException, InterruptedException {
        PrintData(data, Name);
    }
    public static void NewRecyclableItem(int data, String Name) throws IOException, InterruptedException {
        PrintData(data, Name);
    }

    private static void PrintData(int data, String Name) throws FileNotFoundException {
        PrintWriter outputFile = new PrintWriter(Name+".txt");
        outputFile.println(data);
        outputFile.close();
    }
}
