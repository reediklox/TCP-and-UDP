package Lab4.ClientTCP;
import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Client {

    public static boolean isDouble(String x){
        try{
            Double.parseDouble(x);
        }catch (Exception e){
            return false;
        }
        return true;
    }


    public static void main(String[] args){
        try{
            System.out.println("~~~Подключение к серверу...~~~");
            Socket clientSocket = new Socket("127.0.0.1", 1024);
            System.out.println("\n~~~Успешное подключение!~~~\n");

            BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
            ObjectOutputStream coos = new ObjectOutputStream(clientSocket.getOutputStream());
            ObjectInputStream cois = new ObjectInputStream(clientSocket.getInputStream());

            while (true) {
                List<String> p = new ArrayList<>();

                System.out.println("\n~~~Начало ввода данных~~~");
                System.out.println("~~~Для завершения программы введите: \"выход\"~~~\n");
                String message;
                while (true) {
                    System.out.println("\tВведите первое число: ");
                    message = stdin.readLine();
                    if (isDouble(message) || message.equalsIgnoreCase("выход")) {
                        p.add(message);
                        break;
                    } else {
                        System.out.println("Ошибка,нужно повторить.");
                    }
                }
                coos.writeObject(message);
                if (message.equalsIgnoreCase("выход")){
                    break;
                }

                String a = (String) cois.readObject();
                System.out.println(a);

                while (true) {
                    System.out.println("\n\tВведите второе число: ");
                    message = stdin.readLine();
                    if (isDouble(message)) {
                        p.add(message);
                        break;
                    } else {
                        System.out.println("Ошибка,нужно повторить.");
                    }
                }
                coos.writeObject(message);
                String b = (String) cois.readObject();
                System.out.println(b);

                while (true) {
                    System.out.println("\n\tВведите операнд: ");
                    message = stdin.readLine();
                    if (message.equals("+")
                            || message.equals("-")
                            || message.equals("*")
                            || message.equals("/")) {
                        p.add(message);
                        break;
                    } else {
                        System.out.println("Ошибка,нужно повторить.");
                    }
                }
                coos.writeObject(message);
                String c = (String) cois.readObject();
                System.out.println(c);

                System.out.println("\nВаш пример: " + p.get(0) + " " + p.get(2) + " " + p.get(1));
                System.out.println("Ваш ответ: " + (double) cois.readObject());
            }

            coos.close();
            cois.close();
            clientSocket.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
