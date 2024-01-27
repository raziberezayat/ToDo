
import java.time.LocalDateTime;

import java.util.*;
import java.net.*;
import java.io.*;

class Server {
    public static void main(String[] args) throws IOException {
        System.out.println("HI");
        ServerSocket serverSocket = new ServerSocket(8080);
        Vector<User> users = DataBase.usersLoader();

        while (true) {
            System.out.println("Waiting for client...");
            new ClientHandler(serverSocket.accept(), users).start();
        }
    }
}


class User {
    String email, userName, password;

    public User(String email, String userName, String password) {
        this.email = email;
        this.userName = userName;
        this.password = password;
    }
}
class  task{
    task[] subTasks;
    task[] superTasks;
    String name,estimateTime;
    Date date;
    list[] lists;
    boolean star;
    boolean isDone=false;
public task(String name,boolean star ,String estimateTime,Date date){
    {
this.date=date;
this.name=name;
this.star=star;
this.estimateTime=estimateTime;
    }

}
}
class list {
    User[] sharedUser;
    task[] tasks;
    String name;
    public list(String name, task[] tasks) {
this.tasks=tasks;
this.name=name;
    }
}
class ClientHandler extends Thread {
    Socket socket;
    DataOutputStream dos;
    DataInputStream dis;
    Vector<User> users;
   // Vector<Post> posts;

    public ClientHandler(Socket socket, Vector<User> users) throws IOException {
        this.socket = socket;
        this.users = users;
       // this.posts = posts;
        dos = new DataOutputStream(socket.getOutputStream());
        dis = new DataInputStream(socket.getInputStream());
        System.out.println("user created");
    }

    // convert sever message to string
    public String listener() throws IOException {
        System.out.println("listener");
        StringBuilder sb = new StringBuilder();
        int b = dis.read();
        while (b != 0) {
            sb.append((char) b);
            b = dis.read();
        }
        Scanner scanner = new Scanner(sb.toString());
        String command = scanner.nextLine();
        System.out.println("listener2");
        return sb.toString();
    }

    // send the response to server
    public void writer(String write) throws IOException {
        dos.writeBytes(write);
        dos.flush();
        dos.close();
        dis.close();
        socket.close();
        System.out.println(write);
    }

    @Override
    public void run() {
        super.run();
        String command;
        try {
            command = listener();
            System.out.println("command: " + command);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String[] split = command.split("~");
        for (String s : split)
            System.out.println(s);
        switch (split[0]) {
            case "signin": {
                // if both userName and password are correct the response is 1
                // signin~userName~password
                boolean signedIn = false;
                for (User user : users) {
                    if (user.userName.equals(split[1])) {
                        if (user.password.equals(split[2])) {
                            System.out.println("signed in");
                            try {
                                writer("1");
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            signedIn = true;
                            break;
                        }
                    }
                }
                if (!signedIn) {
                    try {
                        System.out.println("not signed in");
                        writer("0");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                break;
            }
            case "signup": {
                // checks the userName if it's taken, the response is zero and usr is not added
                // signup~email~userName~password
                boolean duplicate = false;
                String userName = split[2];
                String password =split[3];
                String email=split[1];
                boolean flagOK=false;
                for (User user : users) {
                    if (user.userName.equals(userName)) {
                        try {
                            writer("0");
                            duplicate = true;
                            break;
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
                if (!duplicate) {

                    if (PasswordValidator.isValidPassword(password)) {
                        if(EmailValidator.emailValidator(email)){
                            flagOK=true;
                        User user = new User(split[1], split[2], split[3]);
                        users.add(user);
                        try {
                            DataBase.addUser(user);
                            writer("1");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                    break;
                }}}
            if (flagOK){

                try {
                    writer("0");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            }
            case "addTask":{

            }
            case "taskIsDone":{

}

            case "addStarToTask":{

            }
            case "removeStarFromTask":{

            }
            case "removeTask":{

            }
            case "addList":{

            }
            case  "shareList":{

            }
            case "removeList":{

            }
            case "changeListName":{
            }
            case"archiveList":{

            }




        }

    }
}
//class password{
//    public static boolean isValidPassword(String password) {
//        // Check password length
//        if (password.length() < 8) {
//            return false;
//        }
//
//        // Check for at least one digit
//        boolean hasDigit = false;
//        for (char c : password.toCharArray()) {
//            if (Character.isDigit(c)) {
//                hasDigit = true;
//                break;
//            }
//        }
//        if (!hasDigit) {
//            return false;
//        }
//
//        // Check for at least one lowercase letter
//        boolean hasLowercase = false;
//        for (char c : password.toCharArray()) {
//            if (Character.isLowerCase(c)) {
//                hasLowercase = true;
//                break;
//            }
//        }
//        if (!hasLowercase) {
//            return false;
//        }
//
//        // Check for at least one uppercase letter
//        boolean hasUppercase = false;
//        for (char c : password.toCharArray()) {
//            if (Character.isUpperCase(c)) {
//                hasUppercase = true;
//                break;
//            }
//        }
//        if (!hasUppercase) {
//            return false;
//        }
//
//        // If all conditions are met, the password is valid
//        return true;
//    }
//}
