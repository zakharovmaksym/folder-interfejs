package org.example;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;



@Data
public class FolderCabinet implements Cabinet {

    private static final Logger logger = LoggerFactory.getLogger(FolderCabinet.class);
    public List<Folder> folders;

    public FolderCabinet(List<Folder> folders) {
        this.folders = folders != null ? folders : new ArrayList<>();
    }




    @Override
    public Optional<Folder> findFolderByName(String name) {
        logger.info("Find folder by name: {}", name);
        return findFolderByName(name, folders);
    }

    private Optional<Folder> findFolderByName(String name, List<Folder> folders) {
        for (Folder folder : folders) {
            if (folder.getName().equals(name)) {
                return Optional.of(folder);
            }
            if (folder instanceof MultiFolder) {
                Optional<Folder> result = findFolderByName(name, ((MultiFolder) folder).getFolders());
                if (result.isPresent()) {
                    return result;
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Folder> findFoldersBySize(String size) {
        logger.info("Searching for folders with size: {}", size);
        return findFoldersBySize(size, folders);
    }

    private List<Folder> findFoldersBySize(String size, List<Folder> folders) {
        List<Folder> result = new ArrayList<>();
        for (Folder folder : folders) {
            if (folder.getSize().equals(size)) {
                result.add(folder);
            }
            if (folder instanceof MultiFolder) {
                result.addAll(findFoldersBySize(size, ((MultiFolder) folder).getFolders()));
            }
        }
        return result;
    }

    @Override
    public int count() {
        int total = count(folders);
        logger.info("Total number of folders: {}", total);
        return total;
    }

    private int count(List<Folder> folders) {
        int count = 0;
        for (Folder folder : folders) {
            count++;
            if (folder instanceof MultiFolder) {
                count += count(((MultiFolder) folder).getFolders());
            }
        }
        return count;
    }

    public void add(Folder folder) {
        if (folder == null) {
            throw new IllegalArgumentException("Folder cannot be null");
        }
        Optional<Folder> existingFolder = findFolderByName(folder.getName());
        if (existingFolder.isPresent()) {
            throw new IllegalArgumentException("Folder already exists");
        }
        folders.add(folder);  // Sprawdź, czy folders jest mutable
        logger.info("Added folder: {}", folder.getName());
    }
    public void removeFolder(String name) {
        if (name == null || name.isEmpty()) {
            throw new FolderNotFoundException("Folder with name " + name + " not found");
        }
        Optional<Folder> folderToRemove = findFolderByName(name);
        if (folderToRemove.isPresent()) {
            folders.remove(folderToRemove.get());
            logger.info("Removed folder: {}", folderToRemove.get().getName());
        } else {
            throw new FolderNotFoundException("Folder with name " + name + " not found");
        }
    }

    public void editFolder(String currentName, String newName, String newSize) {
        if (currentName == null || newName == null || newSize == null || newName.isEmpty() || newSize.isEmpty()) {
            throw new IllegalArgumentException("Folder name and size cannot be null or empty");
        }
        Optional<Folder> folderToEdit = findFolderByName(currentName);
        if (folderToEdit.isPresent()) {
            Folder folder = folderToEdit.get();

            if (!(folder instanceof EditableFolder)) {
                throw new IllegalArgumentException("Folder is not editable");
            }

            EditableFolder editableFolder = (EditableFolder) folder;

            if (!newName.equals(currentName) && findFolderByName(newName).isPresent()) {
                throw new IllegalArgumentException("Folder with name " + newName + " already exists");
            }
            editableFolder.setName(newName);
            editableFolder.setSize(newSize);
            logger.info("Edited folder: new name {}, new size {}", newName, newSize);
        } else {
            throw new FolderNotFoundException("Folder with name " + currentName + " not found");
        }


    }





}
