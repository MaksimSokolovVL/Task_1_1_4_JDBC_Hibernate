package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {

    public static void main(String[] args) {

        UserService userService = new UserServiceImpl();

        userService.createUsersTable();

        for (int i = 0; i < 4; i++) {
            String name = "Name" + (i + 1);
            userService.saveUser(name, String.format("Last name%d", i + 2), (byte) (3 + (i * 2)));

            System.out.printf("User с именем – %s добавлен в базу данных\n", name);
        }

        userService.getAllUsers().forEach(System.out::println);

        userService.cleanUsersTable();

        userService.dropUsersTable();
    }
}
