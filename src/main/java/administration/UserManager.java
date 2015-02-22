package administration;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;

import java.io.*;

/**
 * Created by duarteduarte on 19/02/15.
 */
public class UserManager {

    public UserManager(DB db_exodusGame) {
        this.db_exodusGame = db_exodusGame;
    }

    private boolean isAuthenticated = false;
    private DB db_exodusGame = null;
    private BufferedReader br = null;
    private BufferedWriter bw = null;
    private DBCollection users = null;

    private int menu() {
        System.out.println("1 - Add new user");
        System.out.println("2 - Remove user");
        System.out.println("3 - Change user information");
        System.out.println("4 - Exit panel");

        int selectedOption = -1;

        br = new BufferedReader(new InputStreamReader(System.in));
        bw = new BufferedWriter(new OutputStreamWriter(System.out));

        String option = null;
        try {
            option = br.readLine();
            if (option.matches("\\d+")) {
                selectedOption = Integer.parseInt(option);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return selectedOption;
    }

    public void controllerQM() {
        int lastChoose = -1;
        switch (lastChoose) {
            case -1:
                lastChoose = menu();
            case 1: //Add new user
                addUser();
                lastChoose = menu();
                break;
            case 2: //Remove user
                removeUser();
                lastChoose = menu();
                break;
            case 3: //Change user information
                updateUser();
                lastChoose = menu();
                break;
            case 4: //Exit panel
                break;
            default:
                System.out.println("Wrong option, please choose another");
                lastChoose = menu();
        }
    }

    public void addUser(){
        if(this.users==null)
            this.users = db_exodusGame.getCollection("users");

        try {
            bw.write("Insert username");
            String username = br.readLine();
            this.users.insert(new BasicDBObject("_id",username).append("points", 0));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeUser(){
        if(this.users==null)
            this.users = db_exodusGame.getCollection("users");

        try {
            bw.write("Username to remove");
            String username = br.readLine();
            this.users.remove(new BasicDBObject("_id", username));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateUser(){
        if(this.users==null)
            this.users = db_exodusGame.getCollection("users");

        try {
            bw.write("Username to update");
            String username = br.readLine();
            this.users.update(new BasicDBObject("_id",username).append("points",0));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
