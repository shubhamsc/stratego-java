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

  @Before
  public void setUp() {

  }

  @Test
  public void shouldGivePotentialMoveOfAPieceForEmptySpaces() {
    ArrayList<Integer> armyPos = new ArrayList<>();
    posMap = new HashMap<>();
    armyPos.add(1);
    armyPos.add(2);
    posMap.put(BattlefieldObjects.ARMY , armyPos);
    posMap.put(BattlefieldObjects.OPPONENTS , new ArrayList<>());
    ArrayList<Integer> freeMoves = new ArrayList<>();
    freeMoves.add(22);
    freeMoves.add(24);
    freeMoves.add(13);
    freeMoves.add(33);
    HashMap potentialMoves = Position.getPotentialMoves(ownPosition,posMap);
    assertThat(potentialMoves.get(MovesTypes.FREE),is(freeMoves));
  }

  @Test
  public void shouldAvoidToGiveThatPotentialMoveWhichIsTakenByOurOwnArmy() {
    posMap = new HashMap<>();
    ArrayList<Integer> armyPos = new ArrayList<>();
    armyPos.add(22);
    armyPos.add(33);
    posMap.put(BattlefieldObjects.ARMY , armyPos);
    posMap.put(BattlefieldObjects.OPPONENTS , new ArrayList<>());
    ArrayList<Integer> freeMoves = new ArrayList<>();
    freeMoves.add(24);
    freeMoves.add(13);
    HashMap potentialMoves = Position.getPotentialMoves(ownPosition,posMap);
    assertThat(potentialMoves.get(MovesTypes.FREE),is(freeMoves));
  }

  @Test
  public void shouldGivePotentialMovesWithAttackingMoves() {
    posMap = new HashMap<>();
    ArrayList<Integer> armyPos = new ArrayList<>();
    armyPos.add(22);
    ArrayList<Integer> opponentPos = new ArrayList<>();
    opponentPos.add(33);
    posMap.put(BattlefieldObjects.ARMY , armyPos);
    posMap.put(BattlefieldObjects.OPPONENTS , opponentPos);
    ArrayList<Integer> freeMoves = new ArrayList<>();
    freeMoves.add(24);
    freeMoves.add(13);
    ArrayList<Integer> attackingMoves = new ArrayList<>();
    attackingMoves.add(33);
    HashMap potentialMoves = Position.getPotentialMoves(ownPosition,posMap);
    assertThat(potentialMoves.get(MovesTypes.FREE),is(freeMoves));
    assertThat(potentialMoves.get(MovesTypes.ATTACK),is(attackingMoves));
  }
}
