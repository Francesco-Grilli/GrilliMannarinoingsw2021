package it.polimi.ingsw.GrilliMannarino.GameData;

import java.util.ArrayList;

public enum LorenzoToken {
  FAITHTWO(0, null), FAITHREROLL(1, null), DELETETWOYELLOW(2, Faction.YELLOW), DELETETWOGREEN(3, Faction.GREEN), DELETETWOBLUE(4, Faction.BLUE), DELETETWOPURPLE(5, Faction.PURPLE);

  private final Integer number;
  private final Faction fac;

  LorenzoToken(Integer number, Faction fac) {
    this.number = number;
    this.fac = fac;
  }

  public Integer getNumber(){
    return number;
  }

  public Faction getFaction(){
    return fac;
  }

  public static ArrayList<LorenzoToken> getLorenzoTokens(){
    ArrayList<LorenzoToken> lorenzoTokens = new ArrayList<>();
    lorenzoTokens.add(LorenzoToken.FAITHTWO);
    lorenzoTokens.add(LorenzoToken.FAITHREROLL);
    lorenzoTokens.add(LorenzoToken.DELETETWOYELLOW);
    lorenzoTokens.add(LorenzoToken.DELETETWOGREEN);
    lorenzoTokens.add(LorenzoToken.DELETETWOBLUE);
    lorenzoTokens.add(LorenzoToken.DELETETWOPURPLE);
    return lorenzoTokens;
  }
}
