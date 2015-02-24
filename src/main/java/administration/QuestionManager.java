package administration;

import com.mongodb.*;
import util.Password;

import java.io.*;

/**
 * Created by duarteduarte on 19/02/15.
 */
public class QuestionManager {
    private boolean isAuthenticated = false;
    private DB db_exodusGame = null;
    private BufferedReader br = null;
    private BufferedWriter bw = null;
    private DBCollection questions = null;

    public QuestionManager(DB db_exodusGame) {
        this.db_exodusGame = db_exodusGame;
    }

    private boolean authenticateManager() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        boolean res = false;
        try {
            System.out.println("username: ");
            String username = br.readLine();

            System.out.println("password: ");
            String password = br.readLine();

            Password passController = new Password();

            BasicDBObject user = (BasicDBObject) this.db_exodusGame.getCollection("admins").findOne(new BasicDBObject("_id", username));

            res = passController.validator(password, user);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.isAuthenticated = res;

        return res;
    }

    private int menu() {
        System.out.println("1 - Add new question");
        System.out.println("2 - Remove question");
        System.out.println("3 - Change question");
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
                addQuestion();
                lastChoose = menu();
                break;
            case 2: //Remove user
                removeQuestion();
                lastChoose = menu();
                break;
            case 3: //Change user information
                updateQuestion();
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
        if(this.questions==null)
            this.questions = db_exodusGame.getCollection("users");

        try {
            bw.write("Insert question");
            String question = br.readLine();
            bw.write("Insert answer 1");
            String answer1 = br.readLine();
            bw.write("Insert answer 2");
            String answer2 = br.readLine();
            bw.write("Insert answer 3");
            String answer3 = br.readLine();
            bw.write("Insert answer 4");
            String answer4 = br.readLine();
            bw.write("Correct answer");
            String correctAnswer = br.readLine();
            bw.write("Difficult");
            String difficult = br.readLine();
            this.questions.insert(new BasicDBObject("_id",question).append("answer1", answer1).append("answer2", answer2)
            .append("answer3", answer3).append("answer4", answer4).append("correctAnswer", correctAnswer).append("difficult", difficult));
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




