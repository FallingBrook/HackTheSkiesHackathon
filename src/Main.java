import java.io.IOException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {

        int garData = 0;
        int recData = 0;
        int comData = 0;
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