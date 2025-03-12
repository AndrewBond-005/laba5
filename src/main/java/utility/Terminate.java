package utility;

public class Terminate extends Thread {

    public void run() {
        System.out.println("Завершение программы");
        BackUp.write();
    }
}
