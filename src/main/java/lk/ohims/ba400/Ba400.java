package lk.ohims.ba400;

import java.io.IOException;
import java.net.Socket;

import javax.swing.JOptionPane;

public class Ba400 {

    private static final String LIMS_MW_IP_ADDRESS = "127.0.0.1";
    private static final int LIMS_MW_PORT = 6000;

    public static void main(String[] args) throws IOException {
        // Connect to the LIMS middleware
        Socket limsSocket = new Socket(LIMS_MW_IP_ADDRESS, LIMS_MW_PORT);
        System.out.println("Connected to the LIMS middleware");

        // Prompt the user for the next action
        String action = promptForAction();

        // Process the user's action
        while (!action.equals("exit")) {
            switch (action) {
                case "1":
                    // Send an ENQ message to the LIMS middleware
                    sendEnqAndWaitForAck(limsSocket);
                    break;
                case "2":
                    // Process a patient with flags message
                    sendPatientWithFlagsMessage(limsSocket);
                    break;
                case "3":
                    // Process a QC without flags message
                    sendQcWithoutFlagsMessage(limsSocket);
                    break;
                case "4":
                    // Query all
                    sendQueryAll(limsSocket);
                    break;
                case "5":
                    // Host query
                    hostQuery(limsSocket);
                    break;
                case "6":
                    // Order download
                    orderDownload(limsSocket);
                    break;
                default:
                    System.out.println("Invalid action. Please try again.");
                    break;
            }

            // Prompt the user for the next action
            action = promptForAction();
        }

        // Close the socket
        limsSocket.close();
        System.out.println("Disconnected from the LIMS middleware");
    }

    private static String promptForAction() {
        return JOptionPane.showInputDialog("Please choose the next action:\n"
                + "1. Send an ENQ message\n"
                + "2. Process a patient with flags message\n"
                + "3. Process a QC without flags message\n"
                + "4. Query all\n"
                + "5. Host query\n"
                + "6. Order download\n"
                + "Type 'exit' to exit the application");
    }

    private static String sendEnqAndWaitForAck(Socket socket) throws IOException {
        // Send the ENQ message
        socket.getOutputStream().write(5);
        socket.getOutputStream().flush();
        System.out.println("ENQ message sent");

        // Wait for the ACK message
        byte[] buffer = new byte[1];
        int bytesRead = socket.getInputStream().read(buffer);
        if (bytesRead != -1 && buffer[0] == 6) {
            System.out.println("ACK message received");
            return "ACK message received";
        } else {
            System.out.println("Error: Did not receive an ACK message");
            return "Error: Did not receive an ACK message";
        }
    }

    private static void sendQcWithoutFlagsMessage(Socket limsSocket) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private static String sendPatientWithFlagsMessage(Socket limsSocket) {
        System.out.println("sendPatientWithFlagsMessage");
        try {
            // Create the HL7 message
            String hl7Message = "MSH|^~\\&|BA400|Biosystems|Host|Host provider|20130705093914||OUL^R22^OUL_R22|b023f4e1-dd4b-4ef5-9181-81babdd3eea3|P|2.5.1|||ER|AL||UNICODE UTF-8|||LAB-29^IHE\r"
                    + "PID|||xb004||^^|||\r"
                    + "SPM|1|2400007004||SER|||||||P|||||||\r"
                    + "OBR||20130628083831163IDALRIME||CHOLESTEROL^CHOLESTEROL^A400|||||||||||||||||||||||||\r"
                    + "ORC|OK||||CM||||20130705093914\r"
                    + "OBX|1|NM|CHOLESTEROL^CHOLESTEROL^A400||-0.0191002265|mg/dL^mg/dL^A400||002~029|||F|||||BIOSYSTEMS||A400^Biosystems~834000815^Biosystems|20130628114722\r"
                    + "OBR||20130628083831163IDA0RIME||CK^CK^A400|||||||||||||||||||||||||\r"
                    + "ORC|OK||||CM||||20130705093914\r"
                    + "OBX|1|NM|CK^CK^A400||4.2266469|U/L^U/L^A400||002~029|||F|||||BIOSYSTEMS||A400^Biosystems~834000815^Biosystems|20130628115237\r"
                    + "\u001c\r";

//            // Send the ENQ message
//            sendEnqAndWaitForAck(limsSocket);

            // Send the HL7 message
            limsSocket.getOutputStream().write(hl7Message.getBytes());
            limsSocket.getOutputStream().flush();
            System.out.println("HL7 message sent:\n" + hl7Message);

            // Wait for the response message
            byte[] buffer = new byte[1024];
            int bytesRead = limsSocket.getInputStream().read(buffer);
            if (bytesRead != -1) {
                String response = new String(buffer, 0, bytesRead);
                System.out.println("Response message received:\n" + response);
                return response;
            } else {
                System.out.println("Error: Did not receive a response message");
                return "Error: Did not receive a response message";
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }

    private static void sendQueryAll(Socket limsSocket) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private static void hostQuery(Socket limsSocket) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private static void orderDownload(Socket limsSocket) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
