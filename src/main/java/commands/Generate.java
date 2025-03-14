package commands;

import managers.CollectionManager;
import models.*;
import utility.Console;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;

import static java.lang.Math.abs;

/**
 * Команда 'generate'. Добавляет заданное количество случайно сгенерированных элементов в коллекцию.
 *
 * @author Bondarenko Andrei
 * @since 1.0
 */
public class Generate extends Command {
    private final CollectionManager collectionManager;
    private final Console console;

    /**
     * Создает новый экземпляр команды {@link Generate}.
     *
     * @param console консоль для взаимодействия с пользователем
     * @param collectionManager менеджер коллекции для управления элементами
     */
    public Generate(Console console, CollectionManager collectionManager) {
        super("generate {count}", "добавить новых элементов в коллекцию");
        this.collectionManager = collectionManager;
        this.console = console;
    }

    /**
     * Выполняет команду генерации и добавления заданного количества элементов {@link Worker} в коллекцию.
     *
     * @param arguments аргументы команды (количество элементов для генерации, опционально)
     * @param scriptMode флаг режима выполнения скрипта
     * @return код завершения: 0 - успех, -1 - ошибка в скрипте
     */
    @Override
    public int execute(String arguments, boolean scriptMode) {
        int count = (int) (1000 * 1000);
        if (arguments != null) {
            try {
                count = Integer.parseInt(arguments.trim());
            } catch (Exception e) {
                console.println("Целое число не распознано");
                if (scriptMode) {
                    return -1;
                }
                return 0;
            }
            if (count < 0) {
                console.println("Введите неотрицательное число или ничего");
                if (scriptMode) {
                    return -1;
                }
                return 0;
            }
        }
        console.println("Добавление новых " + count + " Worker");
        for (int i = 1; i <= count; i++) {
            Worker worker = generateWorker();
            collectionManager.add(worker);
        }
        console.println(count + " Worker" + " успешно добавлено!");
        return 0;
    }

    /**
     * Генерирует случайный объект {@link Worker} с уникальными атрибутами.
     *
     * @return сгенерированный объект {@link Worker}
     */
    private Worker generateWorker() {
        Random random = new Random();
        int rn = abs(abs(random.nextInt()) + 666);
        int letters = (rn % 7) + 3;
        StringBuilder name = new StringBuilder();
        name.append((char) ((rn % 26) + 65));
        for (int j = 1; j <= letters - 1; j++) {
            name.append((char) (random.nextInt(26) + 97));
        }
        int id = collectionManager.getFreeId();
        int salary = (rn % 67) * (rn % 83) + 1;
        float x = (float) (((rn % 53 + 1) * (rn % 101) * (rn % 43 + 1) + 1) * (0.0001) * ((rn % 7) % 2 == 0 ? 1 : -1));
        Long y = (long) ((rn % 83 + 1) * (rn % 73 + 1) * ((rn % 11) % 2 == 0 ? 1 : -1));
        Coordinates coordinates = new Coordinates(x, y);
        var creationDate = LocalDateTime.now();
        LocalDate endDate = LocalDate.ofEpochDay(creationDate.toLocalDate().toEpochDay() + (rn % 47) * (rn % 97));
        Position position = null;
        Status status = null;
        for (Position pos : Position.values()) {
            if (pos.ordinal() == rn % 5) {
                position = pos;
                break;
            }
        }
        for (Status stat : Status.values()) {
            if (stat.ordinal() == rn % 3) {
                status = stat;
                break;
            }
        }
        Person person = generatePerson();
        return new Worker(id, name.toString(), coordinates, creationDate, salary, endDate, position, status, person);
    }

    /**
     * Генерирует случайный объект {@link Person} с различными атрибутами.
     *
     * @return сгенерированный объект {@link Person}
     */
    private static Person generatePerson() {
        Random random = new Random();
        var rn = (abs(abs(random.nextInt()) + 666));
        Double weight = (float) ((rn % 53) * (rn % 43) * (rn % 101) + 1) * (0.0001); //Поле не может быть null, Значение поля должно быть больше 0
        EyeColor eyeColor = null; //Поле может быть null
        HairColor hairColor = null; //Поле может быть null
        Country nationality = null; //Поле не может быть null
        for (EyeColor eyeClr : EyeColor.values()) {
            if (eyeClr.ordinal() == rn % 5) {
                eyeColor = eyeClr;
                break;
            }
        }
        rn = (abs(abs(random.nextInt()) + 666));
        for (HairColor hairClr : HairColor.values()) {
            if (hairClr.ordinal() == rn % 5) {
                hairColor = hairClr;
                break;
            }
        }
        rn = (abs(abs(random.nextInt()) + 666));
        for (Country national : Country.values()) {
            if (national.ordinal() == rn % 5) {
                nationality = national;
                break;
            }
        }
        return new Person(weight, eyeColor, hairColor, nationality);
    }
}