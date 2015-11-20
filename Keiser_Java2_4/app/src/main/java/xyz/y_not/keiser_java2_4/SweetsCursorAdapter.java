package xyz.y_not.keiser_java2_4;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class SweetsCursorAdapter extends CursorAdapter {

    public SweetsCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        String title = cursor.getString(
                cursor.getColumnIndex(DBOpenHelper.SWEETS_TITLE));
        TextView listTitle = (TextView) view.findViewById(R.id.listItem);
        listTitle.setText(title);
    }
}
