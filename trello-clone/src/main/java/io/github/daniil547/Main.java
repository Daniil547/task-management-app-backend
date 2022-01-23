package io.github.daniil547;

import io.github.daniil547.common.db.Migrator;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class, Migrator.class);

        context.close();
    }
}