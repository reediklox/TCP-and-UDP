package Lab4.ServerTCP;
import java.net.*;
import java.io.*;
import java.math.MathContext;

public class Server {
    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    public static void main(String[] args){
        ServerSocket serverSocket = null;
        Socket clientAccept = null;
        ObjectOutputStream soos = null;
        ObjectInputStream sois = null;

        try{
            System.out.println("~~~Сервер запущен без проблем!~~~\n\n");

            serverSocket = new ServerSocket(1024);
            clientAccept = serverSocket.accept();

            System.out.println("~~~Клиент "+ clientAccept.getLocalAddress() +" подключился~~~");

            soos = new ObjectOutputStream(clientAccept.getOutputStream());
            sois = new ObjectInputStream(clientAccept.getInputStream());
            while (true) {
                String[] message = new String[3];
                for (int i = 0; i < 3; i++) {
                    message[i] = (String) sois.readObject();
                    if (message[i].equalsIgnoreCase("выход")) {
                        break;
                    }
                    String Num = "Была принята цифра: ";
                    String Op = "Был принят операнд: ";
                    String serverMessage = "Сообщение от сервера: ";
                    if (isNumeric(message[i])) {
                        System.out.println(Num + message[i]);
                        soos.writeObject(serverMessage + "\"" + Num + message[i] + "\"");
                    } else {
                        System.out.println(Op + message[i]);
                        soos.writeObject(serverMessage + "\"" + Op + message[i] + "\"");
                    }
                }

                if (!message[0].equalsIgnoreCase("выход")) {
                    double Answer = 0.0,
                            NumOne = Double.parseDouble(message[0]),
                            NumTwo = Double.parseDouble(message[1]);

                    if (message[2].equals("+")) {
                        Answer = NumOne + NumTwo;
                    }
                    if (message[2].equals("-")) {
                        Answer = NumOne - NumTwo;

                    }
                    if (message[2].equals("*")) {
                        Answer = NumOne * NumTwo;

                    }
                    if (message[2].equals("/")) {
                        Answer = NumOne / NumTwo;

                    }
                    System.out.println("\n\nБыл передан ответ: " + Answer);
                    soos.writeObject(Answer);
                }else{
                    break;
                }
            }

        }catch (Exception e){
        }finally {
            try{
                serverSocket.close();
                clientAccept.close();
                sois.close();
                soos.close();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
