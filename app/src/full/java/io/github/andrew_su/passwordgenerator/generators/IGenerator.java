package io.github.andrew_su.passwordgenerator.generators;

public interface IGenerator {
    String generate(int length);
    String getValidCharacters();
}
