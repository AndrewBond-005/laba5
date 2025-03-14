/**
 * Класс, управляющий коллекцией объектов Worker.
 * Отвечает за хранение, обновление, удаление и сохранение коллекции.
 *
 * @author Bondarenko Andrei
 * @since 1.0
 */
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

    /**
     * Конструктор класса CollectionManager.
     *
     * @param fileManager экземпляр {@link FileManager} для управления файлами
     */
    public CollectionManager(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    /**
     * Получает Worker по его ID.
     *
     * @param id идентификатор Worker
     * @return объект {@link Worker} или null, если не найден
     */
    public Worker getById(int id) {
        return workers.get(id);
    }

    /**
     * Получает время последнего сохранения коллекции.
     *
     * @return объект {@link LocalDateTime}, представляющий время сохранения
     */
    public LocalDateTime getLastSaveTime() {
        return lastSaveTime;
    }

    /**
     * Получает время последней инициализации коллекции.
     *
     * @return объект {@link LocalDateTime}, представляющий время инициализации
     */
    public LocalDateTime getLastInitTime() {
        return lastInitTime;
    }

    /**
     * Проверяет, содержится ли объект в коллекции.
     *
     * @param e объект {@link Worker} для проверки
     * @return true, если объект содержится, иначе false
     */
    public boolean isContain(Worker e) {
        return e == null || getById(e.getId()) != null;
    }

    /**
     * Обновляет объект Worker в коллекции.
     *
     * @param w объект {@link Worker} для обновления
     */
    public void update(Worker w) {
        remove(getById(w.getId()));
        add(w);
    }

    /**
     * Получает свободный ID для нового объекта.
     *
     * @return свободный идентификатор
     */
    public int getFreeId() {
        while (getById(currentId) != null) {
            currentId++;
        }
        return currentId;
    }

    /**
     * Добавляет объект Worker в коллекцию.
     *
     * @param a объект {@link Worker} для добавления
     * @return true, если объект успешно добавлен, иначе false
     */
    public boolean add(Worker a) {
        workers.put(a.getId(), a);
        return tree.add(a);
    }

    /**
     * Удаляет объект Worker из коллекции.
     *
     * @param a объект {@link Worker} для удаления
     */
    public void remove(Worker a) {
        workers.remove(a.getId());
        tree.remove(a);
    }

    /**
     * Очищает коллекцию.
     */
    public void removeAll() {
        tree.clear();
    }

    /**
     * Получает коллекцию объектов Worker.
     *
     * @return множество объектов {@link Worker}
     */
    public Set<Worker> getCollection() {
        return tree;
    }

    /**
     * Загружает коллекцию из файла.
     *
     * @return true, если загрузка прошла успешно, иначе false
     */
    public boolean loadCollection() {
        int id = fileManager.read(getCollection());
        currentId = ++id;
        return id > 0;
    }

    /**
     * Сохраняет коллекцию в файл.
     */
    public void saveCollection() {
        fileManager.write(getCollection());
        lastSaveTime = LocalDateTime.now();
    }
}
