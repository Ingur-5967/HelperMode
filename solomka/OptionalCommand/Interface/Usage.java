package ru.solomka.OptionalCommand.Interface;

import java.io.IOException;

public interface Usage<C, A> {

        void execute(C sender, A args) throws IOException;

    }
