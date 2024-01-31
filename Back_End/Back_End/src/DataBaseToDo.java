import java.io.*;
import java.util.*;

class DataBase {
    HashMap<String, Controller> dataBase = new HashMap<>();
    static DataBase db;
    static public DataBase getDb() {
        if (db == null)
            db = new DataBase();
        return db;
    }

    // load users from file
    static public Vector<User> usersLoader() throws IOException {
        Vector<User> users = new Vector<>();
        BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\Reza\\Desktop\\ApProject\\ToDO develop backend\\ToDo\\Back_End\\Back_End\\src\\DataBase\\User.txt"));
        String line = reader.readLine();
        while (line != null) {
            String[] elements = line.split("~");
            users.add(new User(elements[0], elements[1], elements[2]));
            line = reader.readLine();
        }
        reader.close();
        return users;
    }
    static public Vector<list> listLoader () throws IOException {
        Vector<list> lists = new Vector<>();
        BufferedReader reader = new BufferedReader(new FileReader("DataBase/Lists.txt"));
        String line = reader.readLine();
        while (line != null) {

            // user~name~/^user^list^name^star^day^hour^year^mounth^day^isDone/...
            String[] elements = line.split("~");
            Vector<task> tasks = new Vector<>();

            String  time = (elements[5] + elements[6] + elements[7] + elements[8] + elements[9]);
            String reply = elements[10];

            String[] separatedReplies = reply.split("/");
            for (String s : separatedReplies) {
                String[] objects = s.split("\\^");
                tasks.add(new task(objects[1],objects[2],objects[3],objects[4],objects[5],objects[6],objects[7],objects[8],objects[9],objects[10]));
            }
            lists.add(new list(elements[0], elements[1],tasks));
            line = reader.readLine();
        }
        reader.close();
        return lists;
    }



    // save new user to file
    static public void addUser(User user) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\Reza\\Desktop\\ApProject\\ToDO develop backend\\ToDo\\Back_End\\Back_End\\src\\DataBase\\User.txt", true));
        writer.append(user.email).append("~").append(user.userName).append("~").append(user.password).append("\n");
        writer.close();
    }

    static public void deleteUser(String userName) throws IOException {
        File file = new File("DataBase/Users.txt");
        File tempFile = new File("DataBase/temp.txt");
        BufferedReader reader = new BufferedReader(new FileReader(file));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
        String currentLine;
        while((currentLine = reader.readLine()) != null) {
            // trim newline when comparing with lineToRemove
            String trimmedLine = currentLine.trim();

            if(trimmedLine.contains(userName))
                continue;
            writer.write(currentLine + System.getProperty("line.separator"));
        }
        writer.close();
        reader.close();
        file.delete();
        boolean successful = tempFile.renameTo(file);
        System.out.println(successful);
    }
    static public void addList(list list) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("DataBase/List.txt", true));
  writer.append(list.username).append(list.name);
        writer.append("\n");
        writer.close();
    }
    // saving changes user pass or email or userName
    static public void changeInfo(String oldLine, String newLine) throws IOException {
        Scanner scanner = new Scanner(new File("DataBase/Users.txt"));
        StringBuilder buffer = new StringBuilder();
        while (scanner.hasNextLine())
            buffer.append(scanner.nextLine()).append(System.lineSeparator());
        String fileContents = buffer.toString();
        System.out.println("Contents of the file: "+fileContents);
        scanner.close();
        fileContents = fileContents.replaceAll(oldLine, newLine);
        FileWriter writer = new FileWriter("DataBase/Users.txt");
        System.out.println("new data: " + fileContents);
        writer.append(fileContents);
        writer.flush();
        writer.close();
    }


}
    // save new post to file

class Controller {
    File file;
    FileWriter fw;
    RandomAccessFile raf;

    public Controller(String str) {
        file = new File (str);
        try {
            raf = new RandomAccessFile(file, "rw");
            String last = readFile();
            fw = new FileWriter(file);
            writeFile(last);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    String readFile() throws IOException {
        StringBuilder recovery = new StringBuilder();
        String i;
        while ((i = raf.readLine()) != null) {
            recovery.append(i).append("\n");
        }
        raf.seek(0);
        return recovery.toString();
    }

    void writeFile(String str, boolean ... reset) throws IOException {
        if (reset.length != 0) {
            fw = new FileWriter(file);
        }
        fw.write(str);
        fw.flush();
    }

    String getRow(String id) throws IOException {
        String[] split = this.readFile().split("\n");
        for (String str : split) {
            if (str.startsWith(id)) {
                return str;
            }
        }
        return "invalid";
    }
}