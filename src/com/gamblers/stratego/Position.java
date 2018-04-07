package com.gamblers.stratego;

import java.util.ArrayList;

public class Position {
  public static ArrayList<Integer> getNeighbour(int ownPosition) {
    ArrayList<Integer> neighboursPos = new ArrayList();
    neighboursPos.add(ownPosition-1);
    neighboursPos.add(ownPosition+1);
    neighboursPos.add(ownPosition-10);
    neighboursPos.add(ownPosition+10);
    return neighboursPos;
  }
}
