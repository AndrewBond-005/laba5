package managers;

import models.Worker;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class CollectionManager {
    Set<Worker> tree = new TreeSet<>();
    private int currentId = 1;
    private final LocalDateTime lastInitTime = null;
    private LocalDateTime lastSaveTime = null;
    private final Map<Integer, Worker> workers = new HashMap<>();
    FileManager fileManager = null;

    public CollectionManager(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    public Worker getById(int id) {
        return workers.get(id);
    }

    public LocalDateTime getLastSaveTime() {
        return lastSaveTime;
    }

    public LocalDateTime getLastInitTime() {
        return lastInitTime;
    }

    public boolean isContain(Worker e) {
        return e == null || getById(e.getId()) != null;
    }

    public void update(Worker w) {
        remove(getById(w.getId()));
        add(w);
    }

    public int getFreeId() {
        while (getById(currentId) != null) {
            currentId++;
        }
        return currentId;
    }

    public boolean add(Worker a) {
        workers.put(a.getId(), a);
        return tree.add(a);
    }

    public void remove(Worker a) {
        workers.remove(a.getId());
        tree.remove(a);
    }

    public void removeAll() {
        tree.clear();
    }

    public Set<Worker> getCollection() {
        return tree;
    }

    public boolean loadCollection() {
        int id = fileManager.read(getCollection());
        currentId = ++id;
        return id > 0;
    }

    public void saveCollection() {
        fileManager.write(getCollection());
        lastSaveTime = LocalDateTime.now();
    }
}
