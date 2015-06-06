package io.github.andrew_su.passwordgenerator.generators;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class GeneratorModule {
    @Singleton
    @Provides
    NumberGenerator provideNumberGenerator() {
        return new NumberGenerator();
    }

    @Singleton
    @Provides
    LowercaseAlphabetGenerator provideLowercaseAlphabetGenerator() {
        return new LowercaseAlphabetGenerator();
    }

    @Singleton
    @Provides
    UppercaseAlphabetGenerator provideUppercaseAlphabetGenerator() {
        return new UppercaseAlphabetGenerator();
    }
}
