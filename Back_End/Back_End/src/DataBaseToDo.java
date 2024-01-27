import java.io.*;
import java.util.*;
import java.time.LocalDateTime;

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
        BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\Lenovo\\Desktop\\untitled\\DataBase\\users.txt"));
        String line = reader.readLine();
        while (line != null) {
            String[] elements = line.split("~");
            users.add(new User(elements[0], elements[1], elements[2]));
            line = reader.readLine();
        }
        reader.close();
        return users;
    }



    // save new user to file
    static public void addUser(User user) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\Lenovo\\Desktop\\untitled\\DataBase\\users.txt", true));
        writer.append(user.email).append("~").append(user.userName).append("~").append(user.password).append("\n");
        writer.close();
    }

    static public void deleteUser(String userName) throws IOException {
        File file = new File("DataBase/users.txt");
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