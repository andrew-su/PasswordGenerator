package io.github.andrew_su.passwordgenerator2;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import io.github.andrew_su.passwordgenerator2.datasets.PasswordTable;
import io.github.andrew_su.passwordgenerator2.providers.PasswordContentProvider;

public class PasswordActivity extends ActionBarActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int LOADER_ID = 2;
    @InjectView(R.id.listview)
    ListView mListView;
    private CursorAdapter mAdapter;

    public static void startInstance(final Context context) {
        Intent intent = new Intent(context, PasswordActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        ButterKnife.inject(this);
        mAdapter = new CursorAdapter(this, null, false) {
            @Override
            public View newView(final Context context, final Cursor cursor, final ViewGroup parent) {
                return ((LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE)).inflate(R.layout.list_item_credentials, parent, false);
            }

            @Override
            public void bindView(final View view, final Context context, final Cursor cursor) {
                if (view != null) {
                    String accountName = cursor.getString(cursor.getColumnIndex(PasswordTable.Columns.ACCOUNT_NAME));
                    String username = cursor.getString(cursor.getColumnIndex(PasswordTable.Columns.USERNAME));
                    String password = cursor.getString(cursor.getColumnIndex(PasswordTable.Columns.PASSWORD));

                    ((TextView) view.findViewById(R.id.account_name)).setText(accountName);
                    ((TextView) view.findViewById(R.id.username)).setText(username);
                    ((TextView) view.findViewById(R.id.password)).setText(password);
                }
            }
        };
        mListView.setAdapter(mAdapter);
        getSupportLoaderManager().initLoader(LOADER_ID, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(final int id, final Bundle args) {
        return new CursorLoader(this, PasswordContentProvider.Uris.PASSWORD, null, null, null, String.format("%s ASC", PasswordTable.Columns.ACCOUNT_NAME));
    }

    @Override
    public void onLoadFinished(final Loader<Cursor> loader, final Cursor data) {
        mAdapter.changeCursor(data);
    }

    @Override
    public void onLoaderReset(final Loader<Cursor> loader) {

    }
}
