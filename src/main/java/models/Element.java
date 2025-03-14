package models;

/**
 * Абстрактный класс для элементов коллекции
 *
 * @author Bondarenko Andrei
 * @since 1.0
 */
public abstract class Element<E extends Element> implements Comparable<E>, Validatable {
    /**
     * Возвращает идентификатор элемента
     *
     * @return идентификатор элемента
     */
    abstract public int getId();
}