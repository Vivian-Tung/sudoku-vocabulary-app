package com.example.sudokuvocabulary;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

public class ToolBarView extends Toolbar {
    public ToolBarView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        Toolbar toolbar = findViewById(R.id.toolbar);

    }
}
