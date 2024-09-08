package org.example;

public class FolderNotFoundException extends RuntimeException {
  public FolderNotFoundException(String message) {
    super(message);
  }
}
