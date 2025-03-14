/**
 * Класс для создания и управления резервными копиями коллекции
 *
 * @author Bondarenko Andrei
 * @since 1.0
 */
package utility;

import commands.ExecuteScript;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BackUp {
    private static final List<String> backLog = new ArrayList<>();
    private static String fileName = "back_up.txt";
    private static int startWriteFromLine = 1;

    /**
     * Устанавливает имя файла для резервного копирования.
     *
     * @param fileName имя файла
     */
    public static void setFileName(String fileName) {
        BackUp.fileName = fileName;
    }

    /**
     * Приватный конструктор, предотвращающий создание экземпляра утилитарного класса.
     */
    private BackUp() {
        throw new AssertionError("Попытка создания экземпляров утилитарного класса BackUp");
    }

    /**
     * Очищает резервную копию.
     */
    public static void clear() {
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write("");
            writer.flush();
            backLog.clear();
            System.out.println("back_up очищен");
        } catch (IOException e) {
            System.out.println("ошибка очистки back_up");
        }
    }

    /**
     * Удаляет последнюю строку из резервного копирования.
     */
    public static void remove_last() {
        backLog.remove(backLog.size() - 1);
    }

    /**
     * Добавляет строку в резервную копию с новой строки.
     *
     * @param s строка для записи
     */
    public static void println(String s) {
        backLog.add(s + '\n');
    }

    /**
     * Добавляет строку в резервную копию без новой строки.
     *
     * @param s строка для записи
     */
    public static void print(String s) {
        backLog.add(s);
    }

    /**
     * Возвращает список строк резервного копирования.
     *
     * @return список строк
     */
    public static List<String> getBackLog() {
        return backLog;
    }

    /**
     * Читает резервные данные и восстанавливает выполнение скрипта.
     *
     * @param executeScript экземпляр {@link ExecuteScript} для выполнения команд
     * @param console       консольный интерфейс для взаимодействия с пользователем
     */
    public static void read(ExecuteScript executeScript, Console console) {
        File file = new File(fileName);
        if (!file.exists()) {
            return;
        }
        if (file.length() == 0) {
            return;
        }
        if (!file.canRead()) {
            console.printError("Файл " + fileName + " не доступен для чтения");
            return;
        }
        console.print("Обнаружены несохранённые изменения.");
        console.println("Введите 'yes' если хотите продолжить " +
                "работу с того места, где вы остановились в прошлый раз ");

        if (!console.readln().trim().equalsIgnoreCase("yes")) {
            console.setRepeatMode(false);
            clear();
            return;
        }
        startWriteFromLine = 2;

        console.setRepeatMode(true);
        executeScript.execute(fileName, false);
        console.setRepeatMode(false);
    }

    /**
     * Записывает резервные данные в файл.
     */
    public static void write() {
        try (FileWriter writer = new FileWriter(fileName, true)) {
            var arr = getBackLog();
            if (arr.isEmpty()) {
                System.out.println("Данные в BackUp отсутствуют. Запись в файл не выполнена.");
                return;
            }
            arr = arr.subList(startWriteFromLine - 1, arr.size());
            for (var line : arr) {
                writer.write(line);
            }
            writer.flush();
            System.out.println("ввод сохранён в back_up.txt");
        } catch (IOException e) {
            System.out.println("не удалось сохранить данные в back_log.txt");
        }
    }