package com.gamblers.stratego;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class PieceTest {

  @Test
  public void ShouldGiveMinerAsAKilledPieceWhenItAttackOnMarshal() {
    ArrayList killedPieces = Piece.MINER.attackedBy(Piece.MARSHAL);
    assertTrue(killedPieces.contains(DeadPieces.DEFENDER));
  }

  @Test
  public void ShouldGiveMinerAsAKilledPieceWhenMarshalAttackOnIt() {
    ArrayList killedPieces = Piece.MARSHAL.attackedBy(Piece.MINER);
    assertTrue(killedPieces.contains(DeadPieces.ATTACKER));
  }

  @Test
  public void ShouldGiveBothAsAKilledPiecesWhenMinerAttackMiner() {
    ArrayList killedPieces = Piece.MINER.attackedBy(Piece.MINER);
    assertTrue(killedPieces.contains(DeadPieces.ATTACKER));
    assertTrue(killedPieces.contains(DeadPieces.DEFENDER));
  }

  @Test
  public void shouldGiveMarshalAsDeadPieceWhenMarshalAttackOnBomb() {
    ArrayList killedPieces = Piece.BOMB.attackedBy(Piece.MARSHAL);
    assertTrue(killedPieces.contains(DeadPieces.ATTACKER));
  }

  @Test
  public void shouldGiveBombAsDeadPieceWhenMinerAttackOnBomb() {
    ArrayList killedPieces = Piece.BOMB.attackedBy(Piece.MINER);
    assertTrue(killedPieces.contains(DeadPieces.DEFENDER));
  }
}