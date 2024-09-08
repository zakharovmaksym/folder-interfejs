package org.example;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;


@Data
public class FolderCabinet implements Cabinet {

    private static final Logger logger = LoggerFactory.getLogger(FolderCabinet.class);
    private Map<String, Folder> folderMap = new HashMap<>();

    public FolderCabinet(List<Folder> folders) {
        if (folders != null) {
            for (Folder folder : folders) {
                addFolderToMap(folder);
            }
        }
    }

    private void addFolderToMap(Folder folder) {
        folderMap.put(folder.getName(), folder);
        if (folder instanceof MultiFolder) {
            for (Folder subFolder : ((MultiFolder) folder).getFolders()) {
                addFolderToMap(subFolder);
            }
        }
    }




    @Override
    public Optional<Folder> findFolderByName(String name) {
        logger.info("Find folder by name: {}", name);
        return Optional.ofNullable(folderMap.get(name));
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
        List<Folder> result = new ArrayList<>();
        for (Folder folder : folderMap.values()) {
            if (folder.getSize().equals(size)) {
                result.add(folder);
            }
            if (folder instanceof MultiFolder) {
                result.addAll(findFoldersBySize(size, ((MultiFolder) folder).getFolders()));
            }
        }
        return result;
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
        int total = folderMap.size();
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



    }






