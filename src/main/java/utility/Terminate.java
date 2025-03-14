package utility;

/**
 * Класс для корректного завершения программы в отдельном потоке
 *
 * @author Bondarenko Andrei
 * @since 1.0
 */
public class Terminate extends Thread {

    /**
     * Выполняет действия при завершении программы
     * <p>Выводит сообщение о завершении и записывает данные из {@link BackUp}</p>
     */
    public void run() {
        System.out.println("Завершение программы");
        BackUp.write();
    }
}