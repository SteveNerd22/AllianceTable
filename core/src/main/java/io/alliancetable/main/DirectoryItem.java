package io.alliancetable.main;

import java.util.List;

public class DirectoryItem {
    private String name;
    private String type;
    private List<DirectoryItem> contents; // per le cartelle

    // Getters e setters (o usa lombok per ridurre il boilerplate)

    public String getName() {
        return name;
    }
    public String getType() {
        return type;
    }
    public List<DirectoryItem> getContents() {
        return contents;
    }

    @Override
    public String toString() {
        return "DirectoryItem{" +
            "name='" + name + '\'' +
            ", type='" + type + '\'' +
            ", contents=" + contents +
            '}';
    }
}
