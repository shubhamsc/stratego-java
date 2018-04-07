package com.gamblers.stratego;

import java.util.ArrayList;

public enum Piece {
  MARSHAL(10),MINER(3);


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
