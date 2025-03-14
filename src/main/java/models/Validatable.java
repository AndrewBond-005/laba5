package models;

/**
 * Интерфейс для проверки валидности объектов
 *
 * @author Bondarenko Andrei
 * @since 1.0
 */
public interface Validatable {
    /**
     * Проверяет валидность объекта
     *
     * @return строка с описанием ошибки валидации или null, если объект валиден
     */
    String validate();
}