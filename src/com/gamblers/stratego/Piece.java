package com.gamblers.stratego;

import java.util.ArrayList;
import java.util.HashMap;

import static com.gamblers.stratego.Movable.TRUE;
import static com.gamblers.stratego.Movable.FALSE;

public enum Piece {
  MARSHAL(10, TRUE){
    @Override
    public ArrayList<DeadPieces> attackedBy(Piece piece) {
      ArrayList<DeadPieces> killedPieces = new ArrayList<>();
      if(piece.equals(SPY)) {
        killedPieces.add(DeadPieces.DEFENDER);
      } else {
        killedPieces.add(DeadPieces.ATTACKER);
      }
      return killedPieces;
    }
  },
  MINER(3, TRUE),
  BOMB(0, FALSE){
    @Override
    public ArrayList<DeadPieces> attackedBy(Piece piece) {
      ArrayList<DeadPieces> killedPieces = new ArrayList<>();
      if(piece.equals(MINER)) {
        killedPieces.add(DeadPieces.DEFENDER);
      } else {
        killedPieces.add(DeadPieces.ATTACKER);
      }
      return killedPieces;
    }
  },
  SPY(1, TRUE),
  SCOUT(2, TRUE){
    @Override
    public HashMap getPotentialMoves(int ownPosition, HashMap<BattlefieldObjects, ArrayList<Integer>> posMap) {
      return Position.getPotentialMovesForScout(ownPosition,posMap);
    }
  };


  private final int rank;
  private Movable movable;

  Piece(int rank, Movable movable) {
    this.rank = rank;
    this.movable = movable;
  }

  public ArrayList<DeadPieces> attackedBy(Piece piece) {
    ArrayList<DeadPieces> killedPieces = new ArrayList<>();
    if(this.rank<= piece.rank){
      killedPieces.add(DeadPieces.DEFENDER);
    }
    if(this.rank>= piece.rank){
      killedPieces.add(DeadPieces.ATTACKER);
    }
    return killedPieces;
  }

  public HashMap getPotentialMoves(int ownPosition, HashMap<BattlefieldObjects, ArrayList<Integer>> posMap) throws NonMovablePieceException {
    if(this.movable==TRUE)
    return Position.getPotentialMoves(ownPosition,posMap);
    throw new NonMovablePieceException();
  }
}
