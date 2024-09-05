package org.example;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        Folder folder1 = new Folder() {
            @Override
            public String getName() {
                return "Documents";
            }

            @Override
            public String getSize() {
                return "SMALL";
            }
        };
        Folder folder2 = new Folder() {
            @Override
            public String getName() {
                return "Music";
            }

            @Override
            public String getSize() {
                return "MEDIUM";
            }
        };
        Folder folder3 = new Folder() {
            @Override
            public String getName() {
                return "Pictures";
            }

            @Override
            public String getSize() {
                return "LARGE";
            }
        };

        FolderCabinet cabinet = new FolderCabinet(Arrays.asList(folder1, folder2, folder3));

        // Znajdowanie folderu po nazwie
        Optional<Folder> foundFolder = cabinet.findFolderByName("Music");
        foundFolder.ifPresent(folder -> System.out.println("Found folder: " + folder.getName()));

        // Zliczanie wszystkich folderów
        int folderCount = cabinet.count();
        System.out.println("Total folders: " + folderCount);

        // Znajdowanie folderów o rozmiarze "LARGE"
        List<Folder> largeFolders = cabinet.findFoldersBySize("LARGE");
        System.out.println("Large folders: " + largeFolders.size());
    }}