package com.gamblers.stratego;

public class NonMovablePieceException extends Throwable {
  public NonMovablePieceException() {
    super("Piece can't move");
  }
}
