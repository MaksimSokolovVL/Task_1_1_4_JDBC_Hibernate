package jm.task.core.jdbc.dao;

public class SqlData {

    private SqlData() {
    }

    public static final String CREATE_USER_TAB = """
            CREATE TABLE IF NOT EXISTS public.users (
            id BIGINT NOT NULL AUTO_INCREMENT,
            first_name VARCHAR(45) NOT NULL,
            second_name VARCHAR(45) NOT NULL,
            age TINYINT NOT NULL,
            PRIMARY KEY (`id`))
            ENGINE = InnoDB;""";

    public static final String DROP_USERS_TAB = "DROP TABLE IF EXISTS users;";
    public static final String ADD_USER = """
            INSERT INTO public.users (first_name, second_name, age)
            VALUES ('%s', '%s', %d);""";
    public static final String DELETE_USER = """
            DELETE
            FROM public.users
            WHERE id = %d;""";
    public static final String SELECT_FROM_PUBLIC_USERS = "SELECT * FROM public.users;";
    public static final String CLEAN_USERS_TAB = "TRUNCATE TABLE public.users;";
}
