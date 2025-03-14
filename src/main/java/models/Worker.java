package models;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Представляет работника с различными атрибутами, такими как идентификатор, имя, координаты, зарплата и личная информация.
 *
 * @author Bondarenko Andrei
 * @since 1.0
 */
public class Worker extends Element<Worker> implements Validatable, Serializable, Comparable<Worker> {
    private int id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private int salary; //Значение поля должно быть больше 0
    private LocalDate endDate; //Поле может быть null
    private Position position; //Поле не может быть null
    private Status status; //Поле не может быть null
    private Person person; //Поле не может быть null

    /**
     * Проверяет валидность полей работника и возвращает строку с ошибками валидации.
     *
     * @return строка, содержащая список некорректных полей; пустая строка, если все поля валидны
     */
    @Override
    public String validate() {
        StringBuilder s = new StringBuilder();
        if (id <= 0) s.append("id; ");
        if (name == null || name.isEmpty()) s.append("name; ");
        if (coordinates == null || !coordinates.validate().isEmpty()) s.append("coordinates; ");
        if (creationDate == null) s.append("creationDate; ");
        if (salary <= 0) s.append("salary; ");
        if (endDate == null) s.append("endDate; ");
        if (position == null) s.append("position; ");
        if (status == null) s.append("status; ");
        if (person == null) s.append("person; ");
        if (person == null) {
            s.append("person=null");
        } else if (!person.validate().isEmpty()) {
            s.append("person: ").append(person.validate());
        }
        return s.toString();
    }

    /**
     * Создает новый экземпляр класса {@link Worker} с указанными атрибутами.
     *
     * @param id уникальный идентификатор работника
     * @param name имя работника
     * @param coordinates координаты работника
     * @param creationDate дата и время создания работника
     * @param salary зарплата работника
     * @param endDate дата окончания (может быть null)
     * @param position должность работника
     * @param status статус работника
     * @param person личная информация о работнике
     */
    public Worker(int id, String name, Coordinates coordinates, LocalDateTime creationDate, int salary,
                  LocalDate endDate, Position position, Status status, Person person) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.salary = salary;
        this.endDate = endDate;
        this.position = position;
        this.status = status;
        this.person = person;
    }

    /**
     * Создает экземпляр {@link Worker} из массива строк.
     *
     * @param line массив строк с данными о работнике
     * @return новый экземпляр {@link Worker}
     * @throws NullPointerException если обязательные поля равны null или некорректны
     */
    public static Worker fromArray(String[] line) throws NullPointerException {
        Integer id;
        String name;
        Coordinates coordinates;
        LocalDateTime creationDate;
        Integer salary;
        LocalDate endDate;
        Position position;
        Status status;
        Person person;
        try {
            id = Integer.parseInt(line[0]);
        } catch (NumberFormatException e) {
            id = null;
        }
        name = line[1];
        coordinates = new Coordinates(line[2]);
        try {
            creationDate = LocalDateTime.parse(line[3], DateTimeFormatter.ISO_DATE_TIME);
        } catch (DateTimeParseException e) {
            creationDate = null;
        }
        try {
            salary = Integer.parseInt(line[4]);
        } catch (NumberFormatException e) {
            salary = null;
        }
        try {
            endDate = LocalDate.parse(line[5], DateTimeFormatter.ISO_DATE);
        } catch (DateTimeParseException e) {
            endDate = null;
        }
        try {
            position = line[6].equals("null") ? null : Position.valueOf(line[6]);
        } catch (NullPointerException | IllegalArgumentException e) {
            position = null;
        }
        try {
            status = line[7].equals("null") ? null : Status.valueOf(line[7]);
        } catch (NullPointerException | IllegalArgumentException e) {
            status = null;
        }
        person = new Person(line[8]);
        return new Worker(id, name, coordinates, creationDate, salary, endDate, position, status, person);
    }

    /**
     * Преобразует экземпляр {@link Worker} в массив строк.
     *
     * @param worker работник для преобразования
     * @return массив строк, представляющий атрибуты работника
     */
    public static String[] toArray(Worker worker) {
        List<String> list = new ArrayList<String>();
        list.add("" + worker.getId());
        list.add(worker.getName());
        list.add(worker.getCoordinates().toString());
        list.add(worker.getCreationDate().format(DateTimeFormatter.ISO_DATE_TIME));
        list.add("" + worker.getSalary());
        list.add(worker.getEndDate().format(DateTimeFormatter.ISO_DATE));
        list.add(worker.getPosition() == null ? "null" : worker.getPosition().toString());
        list.add(worker.getStatus() == null ? "null" : worker.getStatus().toString());
        list.add(worker.getPerson() == null ? "null" : worker.getPerson().toString());
        String[] array = new String[list.size()];
        for (var i = 0; i < list.size(); i++) {
            array[i] = (String) list.get(i);
        }
        return array;
    }

    /**
     * Возвращает идентификатор работника.
     *
     * @return уникальный идентификатор работника
     */
    @Override
    public int getId() {
        return id;
    }

    /**
     * Возвращает имя работника.
     *
     * @return имя работника
     */
    public String getName() {
        return name;
    }

    /**
     * Возвращает координаты работника.
     *
     * @return координаты {@link Coordinates} работника
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }

    /**
     * Возвращает дату и время создания работника.
     *
     * @return дата и время создания {@link LocalDateTime}
     */
    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    /**
     * Возвращает зарплату работника.
     *
     * @return зарплата работника
     */
    public int getSalary() {
        return salary;
    }

    /**
     * Возвращает дату окончания работы.
     *
     * @return дата окончания {@link LocalDate}
     */
    public LocalDate getEndDate() {
        return endDate;
    }

    /**
     * Возвращает должность работника.
     *
     * @return должность {@link Position} работника
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Возвращает статус работника.
     *
     * @return статус {@link Status} работника
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Устанавливает идентификатор работника.
     *
     * @param id новый идентификатор для установки
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Устанавливает имя работника.
     *
     * @param name новое имя для установки
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Устанавливает координаты работника.
     *
     * @param coordinates новые {@link Coordinates} для установки
     */
    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    /**
     * Устанавливает дату и время создания работника.
     *
     * @param creationDate новая дата и время {@link LocalDateTime} для установки
     */
    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * Устанавливает зарплату работника.
     *
     * @param salary новая зарплата для установки
     */
    public void setSalary(int salary) {
        this.salary = salary;
    }

    /**
     * Устанавливает дату окончания работы.
     *
     * @param endDate новая дата окончания {@link LocalDate} для установки
     */
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    /**
     * Устанавливает должность работника.
     *
     * @param position новая должность {@link Position} для установки
     */
    public void setPosition(Position position) {
        this.position = position;
    }

    /**
     * Устанавливает статус работника.
     *
     * @param status новый статус {@link Status} для установки
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * Устанавливает личную информацию о работнике.
     *
     * @param person новая информация {@link Person} для установки
     */
    public void setPerson(Person person) {
        this.person = person;
    }

    /**
     * Возвращает личную информацию о работнике.
     *
     * @return личная информация {@link Person} о работнике
     */
    public Person getPerson() {
        return person;
    }

    /**
     * Сравнивает данный объект с другим для определения равенства.
     *
     * @param o объект для сравнения
     * @return true, если объекты равны, false в противном случае
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Worker worker = (Worker) o;
        return id == worker.id && salary == worker.salary && Objects.equals(name, worker.name) &&
                Objects.equals(coordinates, worker.coordinates) && Objects.equals(creationDate, worker.creationDate) &&
                Objects.equals(endDate, worker.endDate) && position == worker.position && status == worker.status
                && Objects.equals(person, worker.person);
    }

    /**
     * Сравнивает данного работника с другим для определения порядка.
     *
     * @param o работник для сравнения
     * @return отрицательное число, ноль или положительное число, если данный работник меньше, равен или больше указанного
     */
    @Override
    public int compareTo(Worker o) {
        if (id != o.id) return (Integer.compare(id, o.id));
        if (salary != o.salary) return Integer.compare(salary, o.salary);
        if (name.compareTo(o.getName()) != 0) return name.compareTo(o.getName());
        if (coordinates.getX() != o.getCoordinates().getX())
            return (int) (coordinates.getX() - o.getCoordinates().getX());
        if (!Objects.equals(coordinates.getY(), o.getCoordinates().getY()))
            return (int) (coordinates.getY() - o.getCoordinates().getY());
        if (creationDate != o.creationDate)
            return (int) (creationDate.toEpochSecond((ZoneOffset) ZoneId.systemDefault()) -
                    o.creationDate.toEpochSecond((ZoneOffset) ZoneId.systemDefault()));
        if (endDate != o.endDate) return (int) (endDate.toEpochDay() - o.endDate.toEpochDay());
        if (position != o.position) return position.ordinal() - o.position.ordinal();
        if (status != o.status) return status.ordinal() - o.status.ordinal();
        if (!Objects.equals(this.getPerson().getWeight(), o.getPerson().getWeight()))
            return (int) (this.getPerson().getWeight() - o.getPerson().getWeight());
        if (this.getPerson().getEyeColor().ordinal() != o.getPerson().getEyeColor().ordinal())
            return (int) (this.getPerson().getEyeColor().ordinal() - o.getPerson().getEyeColor().ordinal());
        if (this.getPerson().getHairColor().ordinal() != o.getPerson().getHairColor().ordinal())
            return (int) (this.getPerson().getHairColor().ordinal() - o.getPerson().getHairColor().ordinal());
        if (this.getPerson().getNationality().ordinal() != o.getPerson().getNationality().ordinal())
            return (int) (this.getPerson().getNationality().ordinal() - o.getPerson().getNationality().ordinal());
        return 0;
    }

    /**
     * Возвращает хэш-код для данного работника.
     *
     * @return хэш-код данного работника
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, name, coordinates, creationDate, salary, endDate, position, status, person);
    }

    /**
     * Возвращает строковое представление работника.
     *
     * @return строковое представление данного работника
     */
    @Override
    public String toString() {
        return "Worker{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", coordinates=" + coordinates +
                ", creationDate=" + creationDate +
                ", salary=" + salary +
                ", endDate=" + endDate +
                ", position=" + position +
                ", status=" + status +
                ", person=" + person +
                '}';
    }
}