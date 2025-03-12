package commands;

import utility.BackUp;
import utility.Console;


public class Exit extends Command {
    private final Console console;

    public Exit(Console console) {
        super("exit", "выход из программы");
        this.console = console;
    }


    @Override
    public boolean execute(String arguments) {
        if (arguments != null) {
            console.println("Введен лишний аргумент");
            return false;
        }
        console.println("Выход из программы");
        BackUp.remove_last();
        System.exit(0);
        return false;
    }

}
