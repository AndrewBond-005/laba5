package commands;

/**
 * Интерфейс, определяющий методы для получения имени и описания объекта.
 *
 * @author Bondarenko Andrei
 * @since 1.0
 */
public interface Describable {

    /**
     * Возвращает имя объекта.
     *
     * @return имя объекта
     */
    String getName();

    /**
     * Возвращает описание объекта.
     *
     * @return описание объекта
     */
    String getDescription();
}