package org.example;

public class TestFolder  implements Folder{
    private final String name;
    private final String size;

    public TestFolder(String name, String size) {
        this.name = name;
        this.size = size;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getSize() {
        return size;
    }

}
