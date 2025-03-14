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
    public int execute(String arguments, boolean scriprtMode) {
        if (arguments != null) {
            console.println("Введен лишний аргумент");
            return 1;
        }
        console.println("Выход из программы");
        BackUp.remove_last();
        System.exit(0);
        return 1;
    }

}
