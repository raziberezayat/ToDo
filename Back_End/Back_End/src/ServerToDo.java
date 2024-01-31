
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
  String user;
    String name,estimateTimeDay,estimateTimeHour;
    String year,mounth,day;
 String list;
    String star;//if star=1 is important
    String isDone="0";//if is done=1 its done
public task(String user,String list,String name,String star ,String estimateTimeDay,String estimateTimeHour,String year,String mounth,String day ,String isDone  ){
    {
        this.user=user;
        this.list= list;
this.year=year;
this.mounth=mounth;
this.day=day;
this.name=name;
this.star=star;
this.estimateTimeDay=estimateTimeDay;
this.estimateTimeDay=estimateTimeHour;
this.isDone=isDone;
    }

}
}
class list {
    String username;
   List<User> sharedUser;
   List<task> tasks;
    String name;
    public list(String username,String name, List<task> tasks) {
        this.username=username;
this.tasks=tasks;
this.name=name;

    }
}
class ClientHandler extends Thread {
    Socket socket;
    DataOutputStream dos;
    DataInputStream dis;
    Vector<User> users;
    Vector<list> lists;
    Vector<task> tasks;

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
            case "changeinfo": {
                // changeinfo~email~userName~password~newEmail~newUserName~newPassword
                String oldInfo = split[1] + "~" + split[2] + "~" + split[3];
                String newInfo = split[4] + "~" + split[5] + "~" + split[6];
                User oldUser = new User(split[1], split[2], split[3]);
                User newUser = new User(split[4], split[5], split[6]);
                for (User user : users) {
                    if (user.userName.equals(oldUser.userName)) {
                        users.remove(user);
                        users.add(newUser);
                        break;
                    }
                }
                try {
                    DataBase.changeInfo(oldInfo, newInfo);
                    writer("1");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            }
            case "addlist":{
                list newList=new list(split[1],split[2],null);
lists.add(newList);

//String json=(split[1]+"~"+split[2]+"~"+split[3]+"~"+"0"+split[4]+"~"+split[5]+"~"+split[6]+"~"+split[7]+"~"+split[8]+"~"+"0");

                try {
    DataBase.addList(newList);
    writer("1");
}catch (Exception e){
    System.out.println("Exception in add task");
}
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
