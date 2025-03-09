package utility;

import commands.ExecuteScript;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BackLog {
    private static final List<String> backLog = new ArrayList<>();
    private static String fileName = "back_log.txt";
    private static int startWriteFromLine = 1;

    public static void setFileName(String fileName) {
        BackLog.fileName = fileName;
    }

    private BackLog() {
        throw new AssertionError("Попытка создания экземпляров утилитарного класса BackLog");
    }

    public static void clear() {
        backLog.clear();
    }

    public static void println(String s) {
        backLog.add(s + '\n');

    }

    public static void print(String s) {
        backLog.add(s);
    }

    public static List<String> getBackLog() {
        return backLog;
    }

    public static void read(ExecuteScript executeScript, Console console) {
        File file = new File(fileName);
        if (!file.exists()) {
            //console.printError("Файл " + fileName + " не существует");
            return;
        }
        if (file.length() == 0) {
            return;
        }
        if (!file.canRead()) {
            console.printError("Файл " + fileName + " не доступен для чтения");
            return;
        }
        String name;
        try (Scanner scanner = new Scanner(new File(fileName))) {
            name = scanner.nextLine().trim();
        } catch (FileNotFoundException e) {
            console.printError("Файл " + fileName + " не существует");
            return;
        }
        console.print("Обнаружена незавершённая команда: " + name + ". Введите 'yes' " +
                "если хотите продолжить её ввод или исполнение ");

        if (!console.readln().trim().equalsIgnoreCase("yes")) {
            console.setRepeatMode(false);
            return;
        }
        startWriteFromLine = 2;

        console.setRepeatMode(true);
        executeScript.execute(fileName);
        console.setRepeatMode(false);
    }

    public static void write() {
        try (FileWriter writer = new FileWriter(fileName)) {
            var arr = getBackLog();
            if (arr.isEmpty()) {
                System.out.println("Данные в BackLog отсутствуют. Запись в файл не выполнена.");
                return;
            }
            arr = arr.subList(startWriteFromLine - 1, arr.size());
            for (var line : arr) {
                writer.write(line);
            }
            writer.flush();
            System.out.println("ввод сохранёнв back_log");
        } catch (IOException e) {
            System.out.println("не удалось сохранить данные в back_log");
        }
    }
}
