package controller;

import java.util.List;

/**
 * Created by duarteduarte on 19/02/15.
 */
public class SavedGame {
    String savename = null;
    List<String> activeHelps = null;
    List<String> usedHelps = null;

    public SavedGame(String savename, List<String> activeHelps, List<String> usedHelps){
        this.savename = savename;
        this.activeHelps = activeHelps;
        this.usedHelps = usedHelps;
    }

    public String getSavename() {return savename;}
    public List<String> getActiveHelps() {return activeHelps;}
    public List<String> getUsedHelps() {return usedHelps;}

    public void setActiveHelps(List<String> activeHelps) {this.activeHelps = activeHelps;}
    public void setSavename(String savename) {this.savename = savename;}
    public void setUsedHelps(List<String> usedHelps) {this.usedHelps = usedHelps;}

    @Override
    public String toString() {
        return savename;
    }
}
