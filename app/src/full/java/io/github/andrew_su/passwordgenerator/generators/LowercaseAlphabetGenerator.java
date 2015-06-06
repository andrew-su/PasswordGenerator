package io.github.andrew_su.passwordgenerator.generators;

public class LowercaseAlphabetGenerator extends AbstractGenerator {
    private static final String sValidCharacters = "abcdefghijklmnopqrstuvwxyz";

    @Override
    public String getValidCharacters() {
        return sValidCharacters;
    }
}
