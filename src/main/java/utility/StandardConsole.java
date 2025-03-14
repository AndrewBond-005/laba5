/**
 * Класс для ввода команд и вывода результата в консоль.
 * Реализует интерфейс {@link Console}.
 *
 * @author Bondarenko Andrei
 * @since 1.0
 */
package utility;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class StandardConsole implements Console {
    private static final String P = "$ ";
    private static Scanner fileScanner = null;
    private static final Scanner defScanner = new Scanner(System.in);
    private static boolean repeatMode = false;

    /**
     * Получает слово для остановки ввода.
     *
     * @return строка "stop"
     */
    @Override
    public String getStopWord() {
        return "stop";
    }

    /**
     * Получает слово для выхода.
     *
     * @return строка "exit"
     */
    @Override
    public String getExitWord() {
        return "exit";
    }

    /**
     * Выводит объект в стандартный поток вывода.
     *
     * @param obj объект для вывода
     */
    public void print(Object obj) {
        System.out.print(obj.toString());
    }

    /**
     * Выводит объект в стандартный поток вывода с новой строки.
     *
     * @param obj объект для вывода
     */
    public void println(Object obj) {
        System.out.println(obj.toString());
    }

    /**
     * Выводит сообщение об ошибке в поток ошибок.
     *
     * @param obj сообщение ошибки
     */
    public void printError(Object obj) {
        System.err.println("Error: " + obj);
    }

    /**
     * Считывает строку ввода.
     *
     * @return считанная строка
     * @throws NoSuchElementException если ввод недоступен
     * @throws IllegalStateException если Scanner закрыт
     */
    public String readln() throws NoSuchElementException, IllegalStateException {
        String s;
        if (isCanReadln()) {
            s = (fileScanner != null ? fileScanner : defScanner).nextLine();
        } else {
            selectConsoleScanner();
            if (!isCanReadln()) {
                System.exit(0);
            }
            s = (fileScanner != null ? fileScanner : defScanner).nextLine();
        }
        if (fileScanner == null) {
            BackUp.println(s);
        }
        if (repeatMode) println(s);
        return s;
    }

    /**
     * Проверяет, доступен ли ввод данных.
     *
     * @return true, если доступен ввод данных, иначе false
     * @throws IllegalStateException если Scanner закрыт
     */
    public boolean isCanReadln() throws IllegalStateException {
        return (fileScanner != null ? fileScanner : defScanner).hasNextLine();
    }

    /**
     * Устанавливает внешний Scanner для чтения данных.
     *
     * @param scanner Scanner, используемый для чтения
     */
    public void setFileScanner(Scanner scanner) {
        fileScanner = scanner;
    }

    /**
     * Получает текущий Scanner для чтения из файла.
     *
     * @return Scanner, используемый для чтения
     */
    @Override
    public Scanner getFileScanner() {
        return fileScanner;
    }

    /**
     * Устанавливает стандартный Scanner для работы с консолью.
     */
    public void selectConsoleScanner() {
        fileScanner = null;
    }

    /**
     * Устанавливает режим повторения ввода.
     *
     * @param repeatMode true, если режим включен, иначе false
     */
    public void setRepeatMode(boolean repeatMode) {
        StandardConsole.repeatMode = repeatMode;
    }
}