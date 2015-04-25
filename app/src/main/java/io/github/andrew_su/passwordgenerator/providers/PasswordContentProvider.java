package io.github.andrew_su.passwordgenerator.providers;

import android.net.Uri;

import io.github.andrew_su.passwordgenerator.datasets.PasswordTable;
import io.pivotal.arca.provider.DatabaseConfiguration;
import io.pivotal.arca.provider.DatabaseProvider;

public class PasswordContentProvider extends DatabaseProvider {
    public static final String AUTHORITY = "io.github.andrew_su.passwordgenerator.providers.PasswordContentProvider";
    public static final int DATABASE_VERSION = 1;

    @Override
    public boolean onCreate() {
        registerDataset(AUTHORITY, Paths.PASSWORD, PasswordTable.class);
        registerDataset(AUTHORITY, Paths.PASSWORD + "/*", PasswordTable.class);
        return true;
    }

    public DatabaseConfiguration onCreateDatabaseConfiguration() {
        return new DatabaseConfiguration.DefaultDatabaseConfiguration(getContext()) {
            @Override
            public int getDatabaseVersion() {
                return DATABASE_VERSION;
            }

            @Override
            public String getDatabaseName() {
                return AUTHORITY;
            }
        };
    }

    public static final class Uris {
        public static final Uri PASSWORD = Uri.parse("content://" + AUTHORITY + "/" + Paths.PASSWORD);
    }

    private static final class Paths {
        public static final String PASSWORD = "password";
    }
}
