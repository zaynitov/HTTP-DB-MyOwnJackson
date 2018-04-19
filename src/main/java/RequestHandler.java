import java.io.*;
import java.util.Set;
import java.util.TreeSet;

import Json.JSONFormatterImpl;
import org.apache.commons.io.FilenameUtils;

public class RequestHandler {
    static String root = "C:\\Users\\AZAYNITOV\\IdeaProjects\\session6\\src\\main\\resources\\";
    static File dir = new File(root);


    public static int findFreeID() {
        Set<Integer> setOfId = new TreeSet<>();
        for (File item : dir.listFiles()) {

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

    public static int createUser(String request) throws Exception {


        String name = request.substring((request.indexOf("name=") + 5), request.indexOf("name=") + 8);
        String ageString = request.substring((request.indexOf("age=") + 4), request.indexOf("age=") + 7);
        String salaryString = request.substring((request.indexOf("salary=") + 7), request.indexOf("salary=") + 10);
        ;
        Integer age = Integer.parseInt(ageString);
        Integer salary = Integer.parseInt(salaryString);
        User user = new User(name, age, salary);

        System.out.println(user);

        int resultId = findFreeID();


        String fileName = resultId + ".bin";

        System.out.println(fileName);

        FileOutputStream fos = new FileOutputStream(root + fileName);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(user);
        oos.flush();
        oos.close();
        //save file

        return resultId;
    }


    public static int deleteUser(String request) {
        String idString = request.substring((request.indexOf("delete/") + 7));
        System.out.println(idString);
        Integer Id = Integer.parseInt(idString);

        for (File item : dir.listFiles()) {

            if (Integer.parseInt(FilenameUtils.removeExtension(item.getName())) == Id) {
                String fileName = Id + ".bin";
                File fileToDelete = new File(root + fileName);


                fileToDelete.delete();

///return
            }

///return


        }

        return 0;
    }

    public static void getList() throws Exception {


        for (File item : dir.listFiles()) {


            FileInputStream fis = new FileInputStream(item.getName());
            ObjectInputStream oin = new ObjectInputStream(fis);
            User ts = (User) oin.readObject();

            new JSONFormatterImpl().marshall(ts);


        }

    }


}
//   /user/create?name=XXX&age=YYY&salary=ZZZ
