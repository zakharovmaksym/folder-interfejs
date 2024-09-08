package org.example;

import java.util.List;

public class TestMultiFolder  implements MultiFolder {
    private final String name;
    private final String size;
    private final List<Folder> folders;

    public TestMultiFolder(String name, String size, List<Folder> folders) {
        this.name = name;
        this.size = size;
        this.folders = folders;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getSize() {
        return size;
    }

    @Override
    public List<Folder> getFolders() {
        return folders;
    }
}
