//created by duknust
//find in https://github.com/Duknust

package controller;

import com.mongodb.*;
import com.mongodb.MongoClient;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExodusController {
    private DB db_exodusGame = null;
    private DBCollection coll_Questions = null;
    private HashMap<String, Question> usedQuestions = new HashMap<String, Question>();
    private User activeUser = null;
    private SavedGame activeGame = null;

    public static void main(String[] args) throws IOException {
        MongoClient c =  new MongoClient(new MongoClientURI("mongodb://localhost"));
        DB db_exodusGame = c.getDB("exodusGame");
        int i =0;
        DBCollection coll_Questions = db_exodusGame.getCollection("questions");

        DBCursor cursor = images.find();
        cursor.next();

        while (cursor.hasNext()){
            Object id = cursor.curr().get("_id");
            DBCursor cursoralbum = albuns.find(new BasicDBObject("images", id));
            if(!cursoralbum.hasNext()){
                images.remove(new BasicDBObject("_id", id));
            }
            cursor.next();
        }
        System.out.println("the end\n");
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

                Question ques = new Question(question, answer1, answer2, answer3, answer4, correctAnswer);

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