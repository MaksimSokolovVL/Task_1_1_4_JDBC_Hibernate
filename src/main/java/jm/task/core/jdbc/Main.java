package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {

    public static void main(String[] args) {

        UserServiceImpl userService = new UserServiceImpl();

        userService.createUsersTable();

        for (int i = 0; i < 4; i++) {
            userService.saveUser(String.format("Name%d", i + 1),
                    String.format("Last name%d", i + 2), (byte) (3 + (i * 2)));
        }

        userService.getAllUsers().forEach(System.out::println);

        userService.cleanUsersTable();

        userService.dropUsersTable();
    }
}
