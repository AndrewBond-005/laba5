package models;

import java.util.Objects;

/**
 * Представляет личную информацию о человеке, включая вес, цвет глаз, цвет волос и национальность.
 *
 * @author Bondarenko Andrei
 * @since 1.0
 */
public class Person implements Validatable {
    private Double weight; //Поле не может быть null, Значение поля должно быть больше 0
    private EyeColor eyeColor; //Поле может быть null
    private HairColor hairColor; //Поле может быть null
    private Country nationality; //Поле не может быть null

    /**
     * Создает новый экземпляр класса {@link Person} с указанными атрибутами.
     *
     * @param weight вес человека
     * @param eyeColor цвет глаз человека
     * @param hairColor цвет волос человека
     * @param nationality национальность человека
     */
    public Person(Double weight, EyeColor eyeColor, HairColor hairColor, Country nationality) {
        this.weight = weight;
        this.eyeColor = eyeColor;
        this.hairColor = hairColor;
        this.nationality = nationality;
    }

    /**
     * Создает экземпляр {@link Person} из строки с разделителями.
     *
     * @param str строка с данными о человеке в формате "вес;цвет глаз;цвет волос;национальность"
     */
    public Person(String str) {
        var line = str.split(";");
        try {
            weight = Double.parseDouble(line[0]);
        } catch (NumberFormatException e) {
            weight = null;
        }
        try {
            eyeColor = line[1].equals("null") ? null : EyeColor.valueOf(line[1]);
        } catch (NullPointerException | IllegalArgumentException e) {
            eyeColor = null;
        }
        try {
            hairColor = line[2].equals("null") ? null : HairColor.valueOf(line[2]);
        } catch (NullPointerException | IllegalArgumentException e) {
            hairColor = null;
        }
        try {
            nationality = line[3].equals("null") ? null : Country.valueOf(line[3]);
        } catch (NullPointerException | IllegalArgumentException e) {
            nationality = null;
        }
    }

    /**
     * Проверяет валидность полей человека и возвращает строку с ошибками валидации.
     *
     * @return строка, содержащая список некорректных полей; пустая строка, если все поля валидны
     */
    @Override
    public String validate() {
        StringBuilder s = new StringBuilder();
        if (weight == null || weight <= 0) s.append("weight; ");
        if (eyeColor == null) s.append("eyeColor; ");
        if (hairColor == null) s.append("hairColor; ");
        if (nationality == null) s.append("nationality; ");
        return s.toString();
    }

    /**
     * Устанавливает вес человека.
     *
     * @param weight новый вес для установки
     */
    public void setWeight(Double weight) {
        this.weight = weight;
    }

    /**
     * Устанавливает цвет глаз человека.
     *
     * @param eyeColor новый цвет глаз {@link EyeColor} для установки
     */
    public void setEyeColor(EyeColor eyeColor) {
        this.eyeColor = eyeColor;
    }

    /**
     * Устанавливает цвет волос человека.
     *
     * @param hairColor новый цвет волос {@link HairColor} для установки
     */
    public void setHairColor(HairColor hairColor) {
        this.hairColor = hairColor;
    }

    /**
     * Устанавливает национальность человека.
     *
     * @param nationality новая национальность {@link Country} для установки
     */
    public void setNationality(Country nationality) {
        this.nationality = nationality;
    }

    /**
     * Возвращает вес человека.
     *
     * @return вес человека
     */
    public Double getWeight() {
        return weight;
    }

    /**
     * Возвращает цвет глаз человека.
     *
     * @return цвет глаз {@link EyeColor}
     */
    public EyeColor getEyeColor() {
        return eyeColor;
    }

    /**
     * Возвращает цвет волос человека.
     *
     * @return цвет волос {@link HairColor}
     */
    public HairColor getHairColor() {
        return hairColor;
    }

    /**
     * Возвращает национальность человека.
     *
     * @return национальность {@link Country}
     */
    public Country getNationality() {
        return nationality;
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
        Person person = (Person) o;
        return Objects.equals(weight, person.weight) && eyeColor == person.eyeColor && hairColor == person.hairColor && nationality == person.nationality;
    }

    /**
     * Возвращает хэш-код для данного человека.
     *
     * @return хэш-код данного объекта
     */
    @Override
    public int hashCode() {
        return Objects.hash(weight, eyeColor, hairColor, nationality);
    }

    /**
     * Возвращает строковое представление человека.
     *
     * @return строковое представление в формате "вес;цвет глаз;цвет волос;национальность"
     */
    @Override
    public String toString() {
        return weight + ";" + eyeColor +
                ";" + hairColor + ";" + nationality;
    }
}