package models.ask;

import commands.UpdateID;
import models.Country;
import models.EyeColor;
import models.HairColor;
import models.Person;
import utility.Console;

import java.util.NoSuchElementException;

import static models.ask.AskEnum.askEnum;

public class AskPerson {
    public static Person askPerson(Console console) throws AskBreak {
        try {
            Double weight;
            while (true) {
                console.print("Введите вес weight: ");
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (line.isEmpty()) {
                    weight = null;
                    break;
                }
                try {
                    if (line.equalsIgnoreCase("this") && UpdateID.worker != null && UpdateID.worker.getPerson() != null) {
                        weight = UpdateID.worker.getPerson().getWeight();
                    } else {
                        weight = Double.parseDouble(line);
                    }
                    if (weight > 0) break;
                    else
                        console.println("Ошибка! Введено отрицательное число!");
                } catch (NumberFormatException e) {
                    console.println("Ошибка! Вес -  положительное число!");
                }
            }
            // BackLog.println(String.valueOf(weight));
            EyeColor eyeColor = askEnum(EyeColor.class, console);
            HairColor hairColor = askEnum(HairColor.class, console);
            Country nationality = askEnum(Country.class, console);

            return new Person(weight, eyeColor, hairColor, nationality);
        } catch (NoSuchElementException | IllegalStateException e) {
            console.printError("Ошибка чтения");
            return null;
        }
    }
}
