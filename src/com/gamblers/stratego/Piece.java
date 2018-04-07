package com.gamblers.stratego;

import java.util.ArrayList;

public enum Piece {
  MARSHAL(10),MINER(3), BOMB(0){
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
  };


  private final int rank;

  Piece(int rank) {

    this.rank = rank;
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
}
