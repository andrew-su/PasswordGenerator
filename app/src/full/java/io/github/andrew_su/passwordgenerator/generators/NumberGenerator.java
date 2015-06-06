package io.github.andrew_su.passwordgenerator.generators;

public class NumberGenerator extends AbstractGenerator {
    private static final String sValidCharacters = "0123456789";

    @Override
    public String getValidCharacters() {
        return sValidCharacters;
    }
}
