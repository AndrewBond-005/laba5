package utility;

import java.util.Scanner;

/**
 * Консоль для ввода команд и вывода результата
 *
 * @author Bondarenko Andrei
 * @since 1.0
 */
public interface Console {
    /**
     * Получает стоп-слово для прекращения определенных операций
     *
     * @return строка со стоп-словом
     */
    public String getStopWord();

    /**
     * Получает слово для выхода из программы
     *
     * @return строка со словом для выхода
     */
    public String getExitWord();

    /**
     * Выводит объект в консоль без переноса строки
     *
     * @param obj объект для вывода
     */
    void print(Object obj);

    /**
     * Выводит объект в консоль с переносом строки
     *
     * @param obj объект для вывода
     */
    void println(Object obj);

    /**
     * Считывает строку из ввода
     *
     * @return прочитанная строка
     */
    String readln();

    /**
     * Проверяет возможность чтения из текущего ввода
     *
     * @return true если чтение возможно, false в противном случае
     */
    boolean isCanReadln();

    /**
     * Выводит сообщение об ошибке
     *
     * @param obj объект или сообщение об ошибке для вывода
     */
    void printError(Object obj);

    /**
     * Устанавливает сканнер для чтения из файла
     *
     * @param scanner объект {@link Scanner} для чтения из файла
     */
    void setFileScanner(Scanner scanner);

    /**
     * Получает текущий сканнер для чтения из файла
     *
     * @return текущий объект {@link Scanner} для чтения из файла
     */
    Scanner getFileScanner();

    /**
     * Переключает консоль на использование стандартного ввода
     */
    void selectConsoleScanner();

    /**
     * Устанавливает режим повтора ввода
     *
     * @param repeatMode true для включения режима повтора, false для выключения
     */
    void setRepeatMode(boolean repeatMode);
}