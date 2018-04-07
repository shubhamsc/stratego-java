package com.gamblers.stratego;

import java.util.ArrayList;
import java.util.HashMap;

public class Position {

  public static ArrayList<Integer> getNeighbour(int ownPosition) {
    ArrayList<Integer> neighboursPos = new ArrayList<>();
    neighboursPos.add(ownPosition-1);
    neighboursPos.add(ownPosition+1);
    neighboursPos.add(ownPosition-10);
    neighboursPos.add(ownPosition+10);
    return neighboursPos;
  }

  public static HashMap getPotentialMoves(int ownPosition, HashMap<BattlefieldObjects, ArrayList<Integer>> posMap) {
    ArrayList<Integer> neighboursPos = getNeighbour(ownPosition);
    HashMap<MovesTypes, ArrayList> potentialMoves = new HashMap<>();
    ArrayList<Integer> freeMoves = new ArrayList<>();
    ArrayList<Integer> attackingMoves = new ArrayList<>();

    for (Integer neighbourPos : neighboursPos) {
      if(isFreePosition(neighbourPos,posMap)){
        freeMoves.add(neighbourPos);
      }
      else if(isAttackPosition(neighbourPos,posMap)){
        attackingMoves.add(neighbourPos);
      }
    }
    potentialMoves.put(MovesTypes.FREE,freeMoves);
    potentialMoves.put(MovesTypes.ATTACK,attackingMoves);
    return potentialMoves;
  }

  private static boolean isAttackPosition(Integer neighbourPos, HashMap<BattlefieldObjects, ArrayList<Integer>> posMap) {
    return posMap.get(BattlefieldObjects.OPPONENTS).contains(neighbourPos);
  }

  private static boolean isFreePosition(int neighbourPos, HashMap<BattlefieldObjects, ArrayList<Integer>> posMap) {
    return !posMap.get(BattlefieldObjects.ARMY).contains(neighbourPos) && !isAttackPosition(neighbourPos,posMap);
  }

}
