package io.github.andrew_su.passwordgenerator.generators;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = GeneratorModule.class)
public interface GeneratorComponent {
    NumberGenerator provideNumberGenerator();
}
