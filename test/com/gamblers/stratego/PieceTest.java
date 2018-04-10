package com.gamblers.stratego;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class PieceTest {

  private HashMap<BattlefieldObjects, ArrayList<Integer>> posMap;
  private ArrayList<Integer> armyPos;
  private ArrayList<Integer> opponentPos;
  private ArrayList<Integer> freeMoves;
  private ArrayList<Integer> attackingMoves;

  @Before
  public void setUp() {
    armyPos = new ArrayList<>();
    opponentPos = new ArrayList<>();
    posMap = new HashMap<>();
    freeMoves = new ArrayList<>();
    attackingMoves = new ArrayList<>();
  }

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

  @Test
  public void shouldGiveMarshalAsDeadPieceWhenSpyAttackOnMarshal() {
    ArrayList killedPieces = Piece.MARSHAL.attackedBy(Piece.SPY);
    assertTrue(killedPieces.contains(DeadPieces.DEFENDER));
  }

  @Test
  public void ShouldGivePotentialMovesForAPiece() throws NonMovablePieceException {
    int ownPosition = 12;
    posMap.put(BattlefieldObjects.ARMY,armyPos);
    posMap.put(BattlefieldObjects.OPPONENTS,opponentPos);
    HashMap expectedMoves = Piece.MARSHAL.getPotentialMoves(ownPosition, posMap);
    freeMoves.add(11);
    freeMoves.add(13);
    freeMoves.add(2);
    freeMoves.add(22);
    HashMap<MovesTypes, ArrayList<Integer>> potentialMoves = new HashMap<>();
    potentialMoves.put(MovesTypes.FREE,freeMoves);
    potentialMoves.put(MovesTypes.ATTACK,attackingMoves);
    assertThat(expectedMoves.get(MovesTypes.FREE),is(potentialMoves.get(MovesTypes.FREE)));
  }

  @Test
  public void ShouldGiveAllPotentialMovesForScout() throws NonMovablePieceException{
    int ownPosition = 12;
    armyPos.add(15);
    armyPos.add(42);
    posMap.put(BattlefieldObjects.ARMY,armyPos);
    posMap.put(BattlefieldObjects.OPPONENTS,opponentPos);
    HashMap expectedMoves = Piece.SCOUT.getPotentialMoves(ownPosition, posMap);
    freeMoves.add(13);
    freeMoves.add(14);
    freeMoves.add(11);
    freeMoves.add(10);
    freeMoves.add(22);
    freeMoves.add(32);
    freeMoves.add(2);
    HashMap<MovesTypes, ArrayList<Integer>> potentialMoves = new HashMap<>();
    potentialMoves.put(MovesTypes.FREE,freeMoves);
    potentialMoves.put(MovesTypes.ATTACK,attackingMoves);
    assertThat(expectedMoves.get(MovesTypes.FREE),is(potentialMoves.get(MovesTypes.FREE)));
  }

  @Test(expected = NonMovablePieceException.class)
  public void ShouldNotGivePotentialMovesForANotMovablePiece() throws NonMovablePieceException {
    int ownPosition = 0;
    posMap.put(BattlefieldObjects.ARMY,armyPos);
    posMap.put(BattlefieldObjects.OPPONENTS,opponentPos);
    HashMap expectedMoves = Piece.BOMB.getPotentialMoves(ownPosition, posMap);
    assertNull(expectedMoves.get(MovesTypes.FREE));
  }
}