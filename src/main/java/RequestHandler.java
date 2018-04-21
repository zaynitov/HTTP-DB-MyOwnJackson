import java.io.*;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

import Json.JSONFormatterImpl;
import org.apache.commons.io.FilenameUtils;

public class RequestHandler {

    public static int createUser(String request, String root) throws Exception {

        String name = request.substring((request.indexOf("name=") + 5), request.indexOf("&"));
        String ageString = request.substring((request.indexOf("age=") + 4), request.lastIndexOf("&"));
        String salaryString = request.substring((request.indexOf("salary=") + 7));

        Integer age = Integer.parseInt(ageString);
        Double salary = Double.parseDouble(salaryString);
        User user = new User(name, age, salary);

        int resultId = findFreeID(root);
        FileOutputStream fos = new FileOutputStream(root + resultId + ".bin");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(user);
        oos.flush();
        oos.close();
        return resultId;
    }


    public static boolean deleteUser(String request, String root) {
        String idString = request.substring((request.indexOf("delete/") + 7));
        Integer Id = Integer.parseInt(idString);
        File dir = new File(root);
        for (File item : dir.listFiles()) {
            if (item.getName().equals("config.properties")) continue;
            if (Integer.parseInt(FilenameUtils.removeExtension(item.getName())) == Id) {
                String fileName = Id + ".bin";
                File fileToDelete = new File(root + fileName);
                return (fileToDelete.delete());
            }
        }
        return false;
    }

    public static String getList(String root) throws Exception {
        File dir = new File(root);
        ArrayList<User> users = new ArrayList<>();
        for (File item : dir.listFiles()) {
            if (item.getName().equals("config.properties")) continue;

            FileInputStream fis = new FileInputStream(item.getPath());
            ObjectInputStream oin = new ObjectInputStream(fis);
            User user = (User) oin.readObject();
            users.add(user);
        }
        return new JSONFormatterImpl().marshall(users);

    }

    public static String getUser(String request, String root) throws Exception{
        String idString = request.substring((request.indexOf("user/") + 5));
               Integer id = Integer.parseInt(idString);

        File dir = new File(root);
        User user = null;
        for (File item : dir.listFiles()) {
            if (item.getName().equals("config.properties")) continue;

            if (Integer.parseInt(FilenameUtils.removeExtension(item.getName())) == id) {
                FileInputStream fis = new FileInputStream(item.getPath());
                ObjectInputStream oin = new ObjectInputStream(fis);
                user = (User) oin.readObject();
            }
        }
        if (user == null) {
            return "404 Not Found";
        }
        return new JSONFormatterImpl().marshall(user);
    }


    public static int findFreeID(String root) {
        Set<Integer> setOfId = new TreeSet<>();
        File dir = new File(root);

        if (dir.listFiles() == null) {
 return 1;
        }
        for (File item : dir.listFiles()) {

            if (item.getName().equals("config.properties")) continue;
            String fileNameWithOutExt = FilenameUtils.removeExtension(item.getName());
            int idReal = Integer.parseInt(fileNameWithOutExt);
            setOfId.add(idReal);
        }
        int prev = 0;
        for (Integer integer : setOfId) {
            int next = integer;
            int dif = next - prev;
            if (dif > 1) return prev + 1;
            prev = next;
        }
        return ++prev;
    }

}