package Lab4.ClientUDP;
import java.net.*;
import java.io.*;
import java.util.ArrayList;

public class Client {
    public String[] arr = new String[]{"x", "y", "z"};
    public void runClient() throws IOException{
        DatagramSocket s = null;
        try {
            byte[] buf = new byte[512];
            s = new DatagramSocket();
            BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("~~~Клиент~~~");

            DatagramPacket sendPacket = new DatagramPacket(buf,
                                                            buf.length,
                                                            InetAddress.getByName("127.0.0.1"),
                                                            1024);

            ArrayList<String> arrayList = new ArrayList<>();
            String some = null;
            while (true) {
                System.out.println("\n~~~Ввод данных~~~");
                System.out.println("~~~Для завершения программы: \"выход\"~~~\n");
                for (int i = 0; i < 3; i++) {
                    System.out.println("Введите " + arr[i]);
                    some = stdin.readLine();
                    arrayList.add(some);

                    if (some.equalsIgnoreCase("выход")) {
                        byte[] quit = {'Q'};
                        sendPacket.setData(quit);
                        sendPacket.setLength(quit.length);
                        s.send(sendPacket);
                        break;
                    }

                    byte[] data = some.getBytes();
                    sendPacket.setData(data);
                    sendPacket.setLength(data.length);

                    s.send(sendPacket);
                }

                if (some.equalsIgnoreCase("выход")) {
                    break;
                }

                System.out.println("\n~~~На сервер было отправлено~~~\n\tx: "
                        + arrayList.get(0)
                        + "\n\ty: "
                        + arrayList.get(1)
                        + "\n\tz: "
                        + arrayList.get(2)
                        + "\n");

                DatagramPacket recv = new DatagramPacket(buf, buf.length);
                s.receive(recv);

                String Answer = new String(recv.getData()).trim();
                System.out.println("Полученный ответ: " + Answer);
            }
        }finally {
            if (s != null) {
                s.close();
            }
        }
    }
    public static void main(String[] a){
        try{
            Client client = new Client();
            client.runClient();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
