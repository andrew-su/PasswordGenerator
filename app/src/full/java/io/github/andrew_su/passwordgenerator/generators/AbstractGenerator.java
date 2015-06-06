package io.github.andrew_su.passwordgenerator.generators;

public abstract class AbstractGenerator implements IGenerator {
    @Override
    public String generate(final int length) {
        String validCharacters = getValidCharacters();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; ++i) {
            builder.append(validCharacters.charAt(validCharacters.length()));
        }
        return builder.toString();
    }
}
