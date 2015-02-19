//created by duknust
//find in https://github.com/Duknust

package controller;

import com.mongodb.*;
import com.mongodb.MongoClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExodusController {
    private DB db_exodusGame = null;
    private DBCollection coll_Questions = null;
    private HashMap<String, Question> usedQuestions = new HashMap<String, Question>();
    private User activeUser = null;
    private SavedGame activeGame = null;

    public void main(String[] args) throws IOException {
        MongoClient c =  new MongoClient(new MongoClientURI("mongodb://localhost"));
        DB db_exodusGame = c.getDB("exodusGame");

        int i = 0;
        while(db_exodusGame==null && i<3){
            System.out.println("Retrying... ["+i+"]");
            db_exodusGame = c.getDB("exodusGame");

        }
        if (db_exodusGame!=null){
            DBCollection coll_Questions = db_exodusGame.getCollection("questions");
            int lastChoose = 0;
            while(lastChoose != 5){
                switch (lastChoose){
                    case 0:
                        lastChoose=startMenu();
                    case 1:

                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                    default:
                        System.out.println("Wrong option, please choose another");
                }
            }

        } else {
            System.out.println("Connection failed");
        }

        System.out.println("the exodus game shall rise again\n");
    }

    public int startMenu(){
        System.out.println("1 - New Game");
        System.out.println("2 - Load Game");
        System.out.println("3 - Best Scores");
        System.out.println("4 - Credits");
        System.out.println("5 - Exit Game");

        int selectedOption = -1;

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String option = null;
        try {
            option = br.readLine();
            if (option.matches("-?\\d+")){
                selectedOption = Integer.parseInt(option);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return selectedOption;
    }

    public ArrayList<SavedGame> getSavedGames(){
        DBCollection coll_SavedGames = db_exodusGame.getCollection("savedGames");
        DBCursor cursor = coll_SavedGames.find();

        ArrayList<SavedGame> savedGames = new ArrayList<SavedGame>();

        while (cursor.hasNext()){
            DBObject savedGameObject = cursor.next();
            String savename = (String)savedGameObject.get("savename");
            List<String> activeHelps = (List<String>)savedGameObject.get("activeHelps");
            List<String> usedHelps = (List<String>)savedGameObject.get("usedHelps");

            savedGames.add(new SavedGame(savename,activeHelps,usedHelps));
        }
        return savedGames;
    }

    public boolean loadGame(String savename){
        DBCollection coll_SavedGames = db_exodusGame.getCollection("savedGames");
        DBObject savedGameObject = coll_SavedGames.findOne(new BasicDBObject("_id", savename));

        List<String> activeHelps = (List<String>)savedGameObject.get("activeHelps");
        List<String> usedHelps = (List<String>)savedGameObject.get("usedHelps");
        this.activeGame = new SavedGame(savename,activeHelps,usedHelps);

        return this.activeGame==null?false:true;
    }

    public boolean saveGame(String savename){
        if(activeGame!=null) {
            DBCollection coll_SavedGames = db_exodusGame.getCollection("savedGames");
            WriteResult wr = coll_SavedGames.insert(new BasicDBObject("_id", savename)
                    .append("activeHelps", this.activeGame.getActiveHelps())
                    .append("usedHelps", this.activeGame.getUsedHelps()));

            if (wr.getUpsertedId()==null) {
                this.activeGame.setSavename(savename);
                return true;
            } else
                return false;
        }
        else
            return false;
    }

    public Question getNewQuestionByDifficult(int difficult){
        DBCursor cursor = coll_Questions.find(new BasicDBObject("difficult", difficult));
        Question ques = null;

        while (cursor.hasNext()){
            DBObject obj = cursor.next();
            String key = (String)obj.get("_id");
            if (!this.usedQuestions.containsKey(key)){

                String question = (String)obj.get("question");
                String answer1 = (String)obj.get("answer1");
                String answer2 = (String)obj.get("answer2");
                String answer3 = (String)obj.get("answer3");
                String answer4 = (String)obj.get("answer4");
                String correctAnswer = (String)obj.get("correctAnswer");

                ques = new Question(question, answer1, answer2, answer3, answer4, correctAnswer);

                this.usedQuestions.put(key,ques);
            }
        }
        return ques;;
    }

    public Question getNewQuestionByTheme(int theme){
        DBCursor cursor = coll_Questions.find(new BasicDBObject("theme", theme));
        Question ques = null;

        while (cursor.hasNext()){
            DBObject obj = cursor.next();
            String key = (String)obj.get("_id");
            if (!this.usedQuestions.containsKey(key)){

                String question = (String)obj.get("question");
                String answer1 = (String)obj.get("answer1");
                String answer2 = (String)obj.get("answer2");
                String answer3 = (String)obj.get("answer3");
                String answer4 = (String)obj.get("answer4");
                String correctAnswer = (String)obj.get("correctAnswer");

                ques = new Question(question, answer1, answer2, answer3, answer4, correctAnswer);

                this.usedQuestions.put(key,ques);
            }
        }
        return ques;
    }

    public User getUserByUsername(String username){
        DBObject userObject = coll_Questions.findOne(new BasicDBObject("_id", username));

        int points = Integer.parseInt((String) userObject.get("points"));
        String stage = (String)userObject.get("stage");
        String status = (String)userObject.get("status");

        User user = new User(username, points, stage, status);

        this.activeUser = user;

        return user;
    }

}