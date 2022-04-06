package io.github.daniil547;

public interface WebMvcTestDataProvider<I, O> {

    I input();

    O expectedOutput();
}
