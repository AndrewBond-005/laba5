package models.ask;

import commands.UpdateID;
import models.*;
import utility.Console;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import static models.ask.AskCoordinates.askCoordinates;
import static models.ask.AskDate.askDate;
import static models.ask.AskEnum.askEnum;
import static models.ask.AskPerson.askPerson;

public class AskWorker {
    public static Worker askWorker(Console console, int id) throws AskBreak {
        try {
            String name;
            while (true) {
                console.print("Введите имя name: ");
                name = console.readln().trim();
                ////if (name.equals("exit")) throw new AskBreak();
                if (name.equals("this") && UpdateID.worker != null) {
                    name = UpdateID.worker.getName();
                }
                if (!name.isEmpty()) break;
                else
                    console.print("Имя не может быть пустой строкой!");
            }
            //BackUp.println(name);
            Coordinates coordinates = askCoordinates(console);

            var creationDate = LocalDateTime.now();
            int salary;
            while (true) {
                console.print("Введите зарплату salary: ");
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                try {
                    if (line.equalsIgnoreCase("this") && UpdateID.worker != null) {
                        salary = UpdateID.worker.getSalary();
                    } else {
                        salary = Integer.parseInt(line);
                    }

                    if (salary > 0) break;
                    else console.print(" Введено неположительное число!");
                } catch (NumberFormatException e) {
                    console.print("Ошибка! зарпалата - целое положительное число!");
                }
            }

            //BackUp.println(String.valueOf(salary));
            LocalDate endDate = askDate(console);
            Position position = askEnum(Position.class, console);
            Status status = askEnum(Status.class, console);
            Person person = askPerson(console);
            return new Worker(id, name, coordinates, creationDate, salary, endDate, position, status, person);
        } catch (NoSuchElementException | IllegalStateException e) {
            return null;
        }
    }
}
