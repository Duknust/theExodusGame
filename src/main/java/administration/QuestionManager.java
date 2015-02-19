package administration;

import com.mongodb.BasicDBObject;
import com.mongodb.Cursor;
import com.mongodb.DB;
import com.mongodb.DBObject;
import util.Password;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by duarteduarte on 19/02/15.
 */
public class QuestionManager {
    private boolean isAuthenticated = false;
    private DB db_exodusGame = null;

    public QuestionManager(DB db_exodusGame){
        this.db_exodusGame = db_exodusGame;
    }

    private boolean authenticateManager(){
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        boolean res = false;
        try {
            System.out.println("username: ");
            String username = br.readLine();

            System.out.println("password: ");
            String password = br.readLine();

            Password passController = new Password();

            BasicDBObject user = (BasicDBObject)this.db_exodusGame.getCollection("admins").findOne(new BasicDBObject("_id", username));

            res = passController.validator(password, user);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.isAuthenticated = res;

        return res;
    }

    public void controllerQM(){
        int lastChoose = -1;
        switch (lastChoose){
            case 1: //Add new user
                break;
            case 2: //Remove user
                break;
            case 3: //Change user information
                break;
            case 4: //Exit panel
                break;
            default:
                System.out.println("Wrong option, please choose another");
                lastChoose= menu();
        }

    }

    private int menu(){
        System.out.println("1 - Add new user");
        System.out.println("2 - Remove user");
        System.out.println("3 - Change user information");
        System.out.println("4 - Exit panel");

        int selectedOption = -1;

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String option = null;
        try {
            option = br.readLine();
            if (option.matches("\\d+")){
                selectedOption = Integer.parseInt(option);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return selectedOption;
    }
}
