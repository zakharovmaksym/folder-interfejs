package org.example;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
public class FolderCabinet implements Cabinet {

    public List<Folder> folders;

    public FolderCabinet(List<Folder> folders) {
        this.folders = folders;
    }
    @Override
    public Optional<Folder> findFolderByName(String name) {
        return findFolderByName(name,folders);
    }

    public Optional<Folder> findFolderByName(String name, List<Folder> folders) {
        for (Folder folder : folders) {
            if (folder.getName().equals(name)) {
                return Optional.of(folder);
            }if (folder instanceof MultiFolder){
                Optional<Folder> result = findFolderByName(name,((MultiFolder) folder).getFolders());
                 if(result.isPresent()){
                     return  result;
                 }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Folder> findFoldersBySize(String size) {
        return findFoldersBySize(size,folders);
    }

    private List<Folder> findFoldersBySize(String size, List<Folder> folders) {
        List<Folder> result = new ArrayList<>();
        for (Folder folder : folders) {
            if (folder.getSize().equals(size)) {
                result.add(folder);
            }
            if (folder instanceof MultiFolder){
                result.addAll(findFoldersBySize(size,((MultiFolder) folder).getFolders()));
            }
        }
        return result;
    }

    @Override
    public int count() {
        return count(folders);
    }

    private int count(List<Folder> folders) {
         int count = 0;
         for (Folder folder : folders) {
             count++;
             if (folder instanceof MultiFolder){
                 count += count(((MultiFolder) folder).getFolders());
             }
         }
        return count;
    }




}
