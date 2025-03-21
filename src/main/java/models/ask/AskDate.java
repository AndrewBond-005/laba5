package models.ask;

import commands.Exit;
//import commands.UpdateID;
import utility.Console;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.NoSuchElementException;

/**
 * Класс для запроса даты у пользователя
 *
 * @author Bondarenko Andrei
 * @since 1.0
 */
public class AskDate {
    /**
     * Запрашивает у пользователя дату окончания работы через консоль
     *
     * @param console объект {@link Console} для ввода-вывода
     * @param scriprtMode флаг режима скрипта (true - скрипт, false - интерактивный режим)
     * @return объект {@link LocalDate} с введенной датой или null при пустом вводе или ошибке
     * @throws AskBreak если пользователь ввел стоп-слово или слово выхода
     */
    public static LocalDate askDate(Console console, boolean scriprtMode) throws AskBreak {
        try {
            LocalDate endDate;
            while (true) {
                console.print("Введите значение даты окончания работы EndDate (Exemple: " +
                        LocalDateTime.now().format(DateTimeFormatter.ISO_DATE) + " or 2025-05-25): ");
                var line = console.readln().trim();
                if (line.equals(console.getStopWord()) || line.equals(console.getExitWord()))
                    throw new AskBreak(line);
                if (line.isEmpty()) {
                    endDate = null;
                    break;
                }
                try {
//                    if (line.equalsIgnoreCase("this") && UpdateID.worker != null) {
//                        endDate = UpdateID.worker.getEndDate();
//                    } else {
                    endDate = LocalDate.parse(line, DateTimeFormatter.ISO_DATE);
//                    }
                    break;
                } catch (DateTimeParseException e) {
                    console.println("Ошибка! Введённая строка не соответсвует формату даты");
                    if (scriprtMode) return null;
                }
            }
            //BackUp.println(String.valueOf(endDate));
            return endDate;
        } catch (NoSuchElementException | IllegalStateException e) {
            return null;
        }
    }
}