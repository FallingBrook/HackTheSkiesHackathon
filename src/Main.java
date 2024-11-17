import java.io.File;
import java.io.IOException;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        // This creates the file in the program
        File garFile = new File("Garbage.txt");
        File recFile = new File("Recycling.txt");
        File comFile = new File("Compost.txt");

        // This gives the file, myFile, to the Scanner
        Scanner garFileSc = new Scanner(garFile);
        Scanner recFileSc = new Scanner(recFile);
        Scanner comFileSc = new Scanner(comFile);

        int garData = garFileSc.nextInt();
        int recData = recFileSc.nextInt();
        int comData = comFileSc.nextInt();
        while(true){
            String newObject = ItemRecognition.YAY();
            assert newObject != null;
            if (newObject.equals("Garbage")){
                garData++;
                ItemProcessing.NewGarbageItem(garData, "Garbage");
                ArduinoCommunication.SendSignal("Garbage");
            }
            else if (newObject.equals("Recycling")){
                recData++;
                ItemProcessing.NewRecyclableItem(recData, "Recycling");
                ArduinoCommunication.SendSignal("Recycling");
            }
            else if (newObject.equals("Compost")){
                comData++;
                ItemProcessing.NewCompostItem(comData, "Compost");
                ArduinoCommunication.SendSignal("Compost");
            }
            else
                continue;

        }
    }
}