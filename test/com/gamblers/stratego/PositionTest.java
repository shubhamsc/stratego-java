package com.gamblers.stratego;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class PositionTest {

  private HashMap<BattlefieldObjects, ArrayList<Integer>> posMap;
  private int ownPosition = 23;
  private ArrayList<Integer> armyPos;
  private ArrayList<Integer> opponentPos;
  private ArrayList<Integer> freeMoves;
  private ArrayList<Integer> attackingMoves;

  @Before
  public void setUp() {
    posMap = new HashMap<>();
    armyPos = new ArrayList<>();
    opponentPos = new ArrayList<>();
    freeMoves = new ArrayList<>();
    attackingMoves = new ArrayList<>();
  }

  @Test
  public void shouldGivePotentialMoveOfAPieceForEmptySpaces() {
    armyPos.add(1);
    armyPos.add(2);
    posMap.put(BattlefieldObjects.ARMY , armyPos);
    posMap.put(BattlefieldObjects.OPPONENTS , new ArrayList<>());
    freeMoves.add(22);
    freeMoves.add(24);
    freeMoves.add(13);
    freeMoves.add(33);
    HashMap potentialMoves = Position.getPotentialMoves(ownPosition,posMap);
    assertThat(potentialMoves.get(MovesTypes.FREE),is(freeMoves));
  }

  @Test
  public void shouldAvoidToGiveThatPotentialMoveWhichIsTakenByOurOwnArmy() {
    armyPos.add(22);
    armyPos.add(33);
    posMap.put(BattlefieldObjects.ARMY , armyPos);
    posMap.put(BattlefieldObjects.OPPONENTS , new ArrayList<>());
    freeMoves.add(24);
    freeMoves.add(13);
    HashMap potentialMoves = Position.getPotentialMoves(ownPosition,posMap);
    assertThat(potentialMoves.get(MovesTypes.FREE),is(freeMoves));
  }

  @Test
  public void shouldGivePotentialMovesWithAttackingMoves() {
    armyPos.add(22);
    opponentPos.add(33);
    posMap.put(BattlefieldObjects.ARMY , armyPos);
    posMap.put(BattlefieldObjects.OPPONENTS , opponentPos);
    freeMoves.add(24);
    freeMoves.add(13);
    attackingMoves.add(33);
    HashMap potentialMoves = Position.getPotentialMoves(ownPosition,posMap);
    assertThat(potentialMoves.get(MovesTypes.FREE),is(freeMoves));
    assertThat(potentialMoves.get(MovesTypes.ATTACK),is(attackingMoves));
  }

  @Test
  public void shouldGiveAllPossibleMovesForAPieceAtBottomLeftCorner() {
    int ownPosition = 0;
    posMap.put(BattlefieldObjects.ARMY , armyPos);
    posMap.put(BattlefieldObjects.OPPONENTS , opponentPos);
    freeMoves.add(1);
    freeMoves.add(10);
    HashMap potentialMoves = Position.getPotentialMoves(ownPosition,posMap);
    assertThat(potentialMoves.get(MovesTypes.FREE),is(freeMoves));
  }

  @Test
  public void shouldGiveAllPossibleMovesForAPieceAtTopRightCorner() {
    int ownPosition = 99;
    posMap.put(BattlefieldObjects.ARMY , armyPos);
    opponentPos.add(98);
    posMap.put(BattlefieldObjects.OPPONENTS , opponentPos);
    freeMoves.add(89);
    HashMap potentialMoves = Position.getPotentialMoves(ownPosition,posMap);
    assertThat(potentialMoves.get(MovesTypes.FREE),is(freeMoves));
  }

  @Test
  public void shouldGiveAllPossibleMovesForScoutAtBottomLeftCorner() {
    int ownPosition = 0;
    armyPos.add(3);
    armyPos.add(30);
    posMap.put(BattlefieldObjects.ARMY,armyPos);
    posMap.put(BattlefieldObjects.OPPONENTS,opponentPos);
    freeMoves.add(1);
    freeMoves.add(2);
    freeMoves.add(10);
    freeMoves.add(20);
    HashMap potentialMoves = Position.getPotentialMovesForScout(ownPosition,posMap);
    assertThat(potentialMoves.get(MovesTypes.FREE),is(freeMoves));
  }

  @Test
  public void shouldGiveAllPossibleMovesForScoutAtTopRightCorner() {
    int ownPosition = 99;
    armyPos.add(69);
    posMap.put(BattlefieldObjects.ARMY,armyPos);
    opponentPos.add(96);
    posMap.put(BattlefieldObjects.OPPONENTS,opponentPos);
    freeMoves.add(98);
    freeMoves.add(97);
    freeMoves.add(89);
    freeMoves.add(79);
    attackingMoves.add(96);
    HashMap potentialMoves = Position.getPotentialMovesForScout(ownPosition,posMap);
    assertThat(potentialMoves.get(MovesTypes.FREE),is(freeMoves));
  }
}
