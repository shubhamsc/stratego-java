package com.gamblers.stratego;

import java.util.ArrayList;
import java.util.HashMap;

public class Position {

  private static ArrayList<Integer> getNeighboursPos(int ownPosition) {
    ArrayList<Integer> neighboursPos = new ArrayList<>();
    neighboursPos.add(getLeftPositions(ownPosition));
    neighboursPos.add(getRightPosition(ownPosition));
    neighboursPos.add(getBackPosition(ownPosition));
    neighboursPos.add(getHeadPosition(ownPosition));
    neighboursPos.removeIf((Integer pos)->pos<0 || pos>99);
    return neighboursPos;
  }

  private static int getHeadPosition(int position) {
     return getNeighbours(position, 10);
  }

  private static int getNeighbours(int position, int movement) {
    return position+movement;
  }

  private static int getBackPosition(int position) {
    return getNeighbours(position, -10);
  }

  private static int getRightPosition(int position) {
    return getNeighbours(position, 1);
  }

  private static int getLeftPositions(int position) {
    return getNeighbours(position, -1);
  }

  public static HashMap<MovesTypes, ArrayList<Integer>> getPotentialMoves(int ownPosition, HashMap<BattlefieldObjects, ArrayList<Integer>> posMap) {
    ArrayList<Integer> neighboursPos = getNeighboursPos(ownPosition);
    HashMap<MovesTypes, ArrayList<Integer>> potentialMoves = new HashMap<MovesTypes, ArrayList<Integer>>();
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

  public static HashMap<MovesTypes, ArrayList<Integer>> getPotentialMovesForScout(int ownPosition, HashMap<BattlefieldObjects, ArrayList<Integer>> posMap) {
    HashMap<MovesTypes, ArrayList<Integer>> potentialMoves = new HashMap<>();
    ArrayList<Integer> freeMoves = getFreePositions(ownPosition,posMap);
    potentialMoves.put(MovesTypes.FREE,freeMoves);
    ArrayList<Integer> attackingMoves = getAttackPositions(ownPosition, posMap);
    potentialMoves.put(MovesTypes.ATTACK, attackingMoves);
    return potentialMoves;
  }

  private static ArrayList<Integer> getAttackPositions(int ownPosition, HashMap<BattlefieldObjects, ArrayList<Integer>> posMap) {
    ArrayList<Integer> attackingPositions = new ArrayList<>();
    int rightPos = getAttackingPos(ownPosition,rightMax(ownPosition),1, posMap);
    int leftPos = getAttackingPos(ownPosition,leftMax(ownPosition),-1, posMap);
    int headPos = getAttackingPos(ownPosition,topMax(ownPosition),10, posMap);
    int backPos = getAttackingPos(ownPosition,bottomMax(ownPosition),-10, posMap);
    attackingPositions.add(rightPos);
    attackingPositions.add(leftPos);
    attackingPositions.add(headPos);
    attackingPositions.add(backPos);
    attackingPositions.removeIf((Integer pos)->pos<0);
    return attackingPositions;
  }

  private static int getAttackingPos(int position, int maxPos, int movement, HashMap<BattlefieldObjects, ArrayList<Integer>> posMap) {
    while (position!=maxPos){
      if(isAttackPosition(position+movement,posMap)){
        return position;
      }
      position += movement;
    }
    return -1;
  }


  private static ArrayList<Integer> getFreePositions(int ownPosition, HashMap<BattlefieldObjects, ArrayList<Integer>> posMap) {
    ArrayList<Integer> freePositions = new ArrayList<>();
    ArrayList<Integer> rightPos = getPath(ownPosition,rightMax(ownPosition),1, posMap);
    ArrayList<Integer> leftPos = getPath(ownPosition,leftMax(ownPosition),-1, posMap);
    ArrayList<Integer> headPos = getPath(ownPosition,topMax(ownPosition),10, posMap);
    ArrayList<Integer> backPos = getPath(ownPosition,bottomMax(ownPosition),-10, posMap);
    freePositions.addAll(rightPos);
    freePositions.addAll(leftPos);
    freePositions.addAll(headPos);
    freePositions.addAll(backPos);
    freePositions.removeIf((Integer pos)->pos<0 || pos>99);
    return freePositions;
  }

  private static int bottomMax(int position) {
    return position%10;
  }

  private static int topMax(int position) {
    return 90+position%10;
  }

  private static int leftMax(int position) {
    return position-(position%10);
  }

  private static int rightMax(int position) {
    return (position/10)*10+9;
  }

  private static ArrayList<Integer> getPath(int position, int maxPos, int movement, HashMap<BattlefieldObjects, ArrayList<Integer>> posMap) {
    ArrayList<Integer> positions = new ArrayList<>();
    while (position!=maxPos && isFreePosition(position+movement,posMap)){
      position += movement;
      positions.add(position);
    }
    return positions;
  }
}
