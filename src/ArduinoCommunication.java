import com.fazecast.jSerialComm.SerialPort;

public class ArduinoCommunication {
    public static void main(String[] args) {
        // List all available serial ports
        SerialPort[] ports = SerialPort.getCommPorts();
        System.out.println("Available Ports:");
        for (int i = 0; i < ports.length; i++) {
            System.out.println(i + ": " + ports[i].getSystemPortName());
        }

        // Open the desired port (adjust index to match your Arduino's port)
        SerialPort port = ports[0]; // Change to the appropriate index
        port.setComPortParameters(9600, 8, 1, 0); // Baud rate: 9600
        port.setComPortTimeouts(SerialPort.TIMEOUT_WRITE_BLOCKING, 0, 0);

        if (port.openPort()) {
            System.out.println("Port opened successfully.");
        } else {
            System.out.println("Failed to open the port.");
            return;
        }
        while(true){
            try {
                // Sending ON command
                String command = "ON\n"; // Change to "OFF\n" to stop the motor
                port.getOutputStream().write(command.getBytes());
                port.getOutputStream().flush();
                System.out.println("Sent command: " + command.trim());

                // Wait a moment for Arduino to respond
                Thread.sleep(1000);

                // Read response from Arduino
                byte[] buffer = new byte[1024];
                int bytesRead = port.getInputStream().read(buffer);
                System.out.println("Recived:  " + new String(buffer, 0, bytesRead));

                // Wait to let the Arduino process the command
                Thread.sleep(2000);

                // Sending OFF command
                command = "OFF\n";
                port.getOutputStream().write(command.getBytes());
                port.getOutputStream().flush();
                System.out.println("Sent command: " + command.trim());
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
//                port.closePort();
//                System.out.println("Port closed.");
            }
        }

    }
}