package Lab4.ServerUDP;
import java.net.*;
import java.io.*;

public class Server {
    public final static int DefaultPort = 1024;
    public final String QiutServer = "Q";

    public void runServer() throws IOException{
        DatagramSocket s = null;

        try {
            byte[] buf = new byte[512];
            s = new DatagramSocket(DefaultPort);

            System.out.println("~~~Сервер запущен~~~");

            while (true){
                DatagramPacket recv = new DatagramPacket(buf, buf.length);
                double[] unknowns = new double[3];
                String what = null;

                for (int i = 0; i < 3; i++) {
                    s.receive(recv);

                    what = new String(recv.getData()).trim();

                    if (what.equals(QiutServer)) {
                        System.out.println("\n~~~Клиент "
                                + recv.getAddress()
                                + " отключился~~~\n");
                        break;
                    }

                    unknowns[i] = Double.parseDouble(what);


                }

                if (what.equals(QiutServer)) {
                    break;
                }

                System.out.println("\n~~~Сервер получил данные от пользователя~~~\n"
                                    + "\tx: " + unknowns[0] + "\n"
                                    + "\ty: " + unknowns[1] + "\n"
                                    + "\tz: " + unknowns[2] + "\n");

                double Answer = Math.log(Math.pow(unknowns[1], -Math.sqrt(Math.abs(unknowns[0]))))
                                        * (unknowns[0] - unknowns[1] / 2)
                                        + Math.pow(Math.sin(Math.atan(unknowns[2])), 2)
                                        + Math.exp(unknowns[0] + unknowns[1]);

                System.out.println("Результат после подсчета: " + Answer);

                byte[] sendData = String.valueOf(Answer).getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendData,
                                                                sendData.length,
                                                                recv.getAddress(),
                                                                recv.getPort());

                s.send(sendPacket);
            }
            System.out.println("\n~~~Сервер прекратил свою работу~~~");
        } finally {
            if (s != null) {
                s.close();
            }
        }
    }

    public static void main(String[] a){
        try{
            Server server = new Server();
            server.runServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
