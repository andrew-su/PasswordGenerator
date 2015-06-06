package io.github.andrew_su.passwordgenerator.generators;

public class UppercaseAlphabetGenerator extends AbstractGenerator {
    public static final String sValidCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    @Override
    public String getValidCharacters() {
        return sValidCharacters;
    }
}
