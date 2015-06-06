package io.github.andrew_su.passwordgenerator2.datasets;

import android.content.ContentProviderOperation;

import io.github.andrew_su.passwordgenerator2.providers.PasswordContentProvider;
import io.pivotal.arca.provider.Column;
import io.pivotal.arca.provider.SQLiteTable;
import io.pivotal.arca.provider.Unique;

public final class PasswordTable extends SQLiteTable {
    public static ContentProviderOperation getOperation(String accountName, String username, String password) {
        ContentProviderOperation.Builder builder = ContentProviderOperation.newInsert(PasswordContentProvider.Uris.PASSWORD);
        builder.withValue(Columns.ACCOUNT_NAME, accountName);
        builder.withValue(Columns.USERNAME, username);
        builder.withValue(Columns.PASSWORD, password);
        return builder.build();
    }

    public static final class Columns implements SQLiteTable.Columns {
        @Unique(Unique.OnConflict.REPLACE)
        @Column(Column.Type.TEXT)
        public static final String ACCOUNT_NAME = "account_name";

        @Unique(Unique.OnConflict.REPLACE)
        @Column(Column.Type.TEXT)
        public static final String USERNAME = "username";

        @Column(Column.Type.TEXT)
        public static final String PASSWORD = "password";
    }
}
