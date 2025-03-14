package commands;

/**
 * Абстрактный базовый класс для всех команд, реализующий интерфейсы {@link Describable} и {@link Executable}.
 *
 * @author Bondarenko Andrei
 * @since 1.0
 */
public abstract class Command implements Describable, Executable {
    private final String name;
    private final String description;

    /**
     * Создает новый экземпляр команды с указанным именем и описанием.
     *
     * @param name имя команды
     * @param description описание команды
     */
    public Command(String name, String description) {
        this.name = name;
        this.description = description;
    }

    /**
     * Выполняет команду с заданными аргументами.
     *
     * @param arguments аргументы команды
     * @param scriptMode флаг режима выполнения скрипта
     * @return код завершения выполнения команды
     */
    public abstract int execute(String arguments, boolean scriptMode);

    /**
     * Возвращает имя команды.
     *
     * @return имя команды
     */
    public String getName() {
        return name;
    }

    /**
     * Возвращает описание команды.
     *
     * @return описание команды
     */
    public String getDescription() {
        return description;
    }

    /**
     * Сравнивает данный объект с другим для определения равенства.
     *
     * @param obj объект для сравнения
     * @return true, если объекты равны, false в противном случае
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Command command = (Command) obj;
        return name.equals(command.name) && description.equals(command.description);
    }

    /**
     * Возвращает хэш-код для данной команды.
     *
     * @return хэш-код команды
     */
    @Override
    public int hashCode() {
        return name.hashCode() + description.hashCode();
    }

    /**
     * Возвращает строковое представление команды.
     *
     * @return строковое представление команды
     */
    @Override
    public String toString() {
        return "Command{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}