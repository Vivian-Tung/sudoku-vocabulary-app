package com.example.sudokuvocabulary;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

public class ToolBarView extends Toolbar implements Toolbar.OnMenuItemClickListener {
    Toolbar toolbar;

    public ToolBarView(@NonNull Context context) {
        this(context, null);
    }

    public ToolBarView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE
        );
        inflater.inflate(R.layout.toolbar, this, true);

        this.toolbar = findViewById(R.id.toolbar);
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        return menuItem != null;
    }
}
