
import java.net.*;
import java.io.*;

public class Server {

    public static void main(String[] args) throws Exception {

        PropertyFileHandler propertyFileHandler = new PropertyFileHandler();
        propertyFileHandler.initPortAndRoot(args[0]);
        int serverPort = propertyFileHandler.getPort();
        String root = propertyFileHandler.getRoot();

        ServerSocket serverSocket = new ServerSocket(serverPort);
        Socket socket = null;
        while (true) {
            String answer = null;
            socket = serverSocket.accept();

            InputStream sin = socket.getInputStream();
            String headerTempData = getHeaderToArray(sin);
            String[] output = headerTempData.split(" ");
            String headerData = output[1];


            PrintWriter out = new PrintWriter(socket.getOutputStream());
            out.println("HTTP/1.1 20local0 OK");

            if (headerData.contains("create")) {
                out.println("Content-Type: text/html");
                Integer userID = RequestHandler.createUser(headerData, root);
                answer = userID.toString();
            }
            if (headerData.contains("delete")) {
                out.println("Content-Type: text/html");
                if (RequestHandler.deleteUser(headerData, root)) {
                    answer = "200 OK";
                } else {
                    answer = "404 Not Found";
                }
            }
            if (headerData.contains("list")) {
                out.println("Content-Type: text/json");
                answer = RequestHandler.getList(root);
            }
            if ((headerData.contains("/user/")) && (answer == null)) {
                out.println("Content-Type: text/json");
                answer = RequestHandler.getUser(headerData, root);
            }
            if (answer == null) {
                out.println("Content-Type: text/html");
                answer = "404 Not Found";
            }

            out.println("\r\n");
            String smth =  answer;
            out.println(smth);
            out.flush();
            out.close();
        }

    }

    public static String getHeaderToArray(InputStream inputStream) {

        String headerTempData = "";
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