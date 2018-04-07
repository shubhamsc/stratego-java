package com.gamblers.stratego;

import java.util.ArrayList;
import java.util.HashMap;

public class Piece {
  private final int id;
  private final PieceName name;
  private final int rank;

  public Piece(int id, PieceName name, int rank) {
    this.id = id;
    this.name = name;
    this.rank = rank;
  }

  public HashMap getPotentialMoves(int ownPosition, HashMap<BattlefieldObjects, ArrayList<Integer>> posMap) {
    ArrayList<Integer> neighboursPos = Position.getNeighbour(ownPosition);
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

  private boolean isAttackPosition(Integer neighbourPos, HashMap<BattlefieldObjects, ArrayList<Integer>> posMap) {
    return posMap.get(BattlefieldObjects.OPPONENTS).contains(neighbourPos);
  }

  private boolean isFreePosition(int neighbourPos, HashMap<BattlefieldObjects, ArrayList<Integer>> posMap) {
    return !posMap.get(BattlefieldObjects.ARMY).contains(neighbourPos) && !isAttackPosition(neighbourPos,posMap);
  }

  public ArrayList<DeadPieces> attackedBy(Piece piece) {
    ArrayList<DeadPieces> killedPieces = new ArrayList<>();
    if(this.rank<=piece.rank){
      killedPieces.add(DeadPieces.DEFENDER);
    }
    if(this.rank>=piece.rank){
      killedPieces.add(DeadPieces.ATTACKER);
    }
    return killedPieces;
  }
}
