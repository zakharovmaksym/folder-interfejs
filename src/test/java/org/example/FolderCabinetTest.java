package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
class FolderCabinetTest {
    private FolderCabinet folderCabinet;
    @BeforeEach
    void setUp() {
        Folder folder1 = new TestFolder("Documents", "SMALL");
        Folder folder2 = new TestFolder("Pictures", "MEDIUM");
        Folder folder3 = new TestFolder("Music", "LARGE");



        MultiFolder multiFolder = new TestMultiFolder("Media", "LARGE", Arrays.asList(folder2, folder3));

        folderCabinet = new FolderCabinet(new ArrayList<>(Arrays.asList(folder1, multiFolder)));
    }

    @Test
    void testFindFolderByName_FoundInRoot() {
        Optional<Folder> result = folderCabinet.findFolderByName("Documents");
        assertTrue(result.isPresent());
        assertEquals("Documents", result.get().getName());
    }

    @Test
    void testFindFolderByName_FoundInMultiFolder() {
        Optional<Folder> result = folderCabinet.findFolderByName("Music");
        assertTrue(result.isPresent());
        assertEquals("Music", result.get().getName());
    }

    @Test
    void testFindFolderByName_NotFound() {
        Optional<Folder> result = folderCabinet.findFolderByName("NonExisting");
        assertFalse(result.isPresent());
    }

    @Test
    void testFindFoldersBySize_Small() {
        List<Folder> result = folderCabinet.findFoldersBySize("SMALL");
        assertEquals(1, result.size());
        assertEquals("Documents", result.get(0).getName());
    }

    @Test
    void testFindFoldersBySize_Large() {
        List<Folder> result = folderCabinet.findFoldersBySize("LARGE");
        assertEquals(3, result.size());  // Jeden z root, jeden z MultiFolder
        assertTrue(result.stream().anyMatch(folder -> folder.getName().equals("Media")));
        assertTrue(result.stream().anyMatch(folder -> folder.getName().equals("Music")));
    }

    @Test
    void testCount() {
        int result = folderCabinet.count();
        assertEquals(4, result);
    }











}

