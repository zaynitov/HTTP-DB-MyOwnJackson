
import java.net.*;
import java.io.*;

public class Server {
    private static final int serverPort = 5545;

    public static void main(String[] ar) throws Exception {
        System.out.println("All goot");

        ServerSocket serverSocket = new ServerSocket(serverPort);
        Socket socket = null;
        while (true) {
            socket = serverSocket.accept();


        // Получаем входной и выходной потоки сокета для обмена сообщениями с сервером
         /*      DataOutputStream out = new DataOutputStream(sout);*/



        InputStream sin = socket.getInputStream();
        String headerTempData = getHeaderToArray(sin);
            System.out.println(headerTempData);
            String[] output = headerTempData.split(" ");

        String headerData = output[1];
        System.out.println(headerData);






        PrintWriter out = new PrintWriter(socket.getOutputStream());
out.println(headerData);

       out.println("HTTP/1.1 200 OK");
        out.println("Content-Type: text/html");
        out.println("\r\n");
        out.println("<p> Hello world </p>");
        out.flush();

        out.close();


         System.out.println("HIHHI");

                    if (headerData.contains("create")) {
                        int userID = RequestHandler.createUser(headerData);
                        System.out.println(userID);
                    }
                    if (headerData.contains("delete"))
                        RequestHandler.deleteUser(headerData);
                    if (headerData.contains("list"))
                        RequestHandler.getList();
String result;



                   // out.writeUTF(result);



        }

    }
    public static String getHeaderToArray(InputStream inputStream) {

        String headerTempData = "";

        // chain the InputStream to a Reader
        Reader reader = new InputStreamReader(inputStream);
        try {
            int c;
            while ((c = reader.read()) != -1) {
                System.out.print((char) c);
                headerTempData += (char) c;

                if (headerTempData.contains("\r"))
                    break;
            }
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }

        return headerTempData;
    }
}