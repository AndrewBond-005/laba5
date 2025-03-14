package models.ask;

import models.Country;
import models.EyeColor;
import models.HairColor;
import models.Person;
import utility.Console;

import java.util.NoSuchElementException;

import static models.ask.AskEnum.askEnum;

public class AskPerson {
    public static Person askPerson(Console console, boolean scriprtMode) throws AskBreak {
        try {
            Double weight;
            while (true) {
                console.print("Введите вес weight: ");
                var line = console.readln().trim();
                if (line.equals(console.getStopWord()) || line.equals(console.getExitWord()))
                    throw new AskBreak(line);

                if (line.isEmpty()) {
                    weight = null;
                    break;
                }
                try {
//                    if (line.equalsIgnoreCase("this") && UpdateID.worker != null && UpdateID.worker.getPerson() != null) {
//                        weight = UpdateID.worker.getPerson().getWeight();
//                    } else {
                    weight = Double.parseDouble(line);
//                    }
                    if (weight > 0) break;
                    else {
                        console.println("Ошибка! Введено отрицательное число!");
                        if (scriprtMode) return null;
                    }
                } catch (NumberFormatException e) {
                    console.println("Ошибка! Вес -  положительное число!");
                    if (scriprtMode) return null;
                }
            }
            // BackUp.println(String.valueOf(weight));
            EyeColor eyeColor = askEnum(EyeColor.class, console, scriprtMode);
            if (scriprtMode && eyeColor==null) return null;
            HairColor hairColor = askEnum(HairColor.class, console, scriprtMode);
            if (scriprtMode && hairColor==null) return null;
            Country nationality = askEnum(Country.class, console, scriprtMode);
            if (scriprtMode && nationality==null) return null;

            return new Person(weight, eyeColor, hairColor, nationality);
        } catch (NoSuchElementException | IllegalStateException e) {
            console.printError("Ошибка чтения");
            return null;
        }
    }
}
