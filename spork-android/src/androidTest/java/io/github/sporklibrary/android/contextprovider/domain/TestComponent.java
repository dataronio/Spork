package io.github.sporklibrary.android.contextprovider.domain;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import io.github.sporklibrary.Spork;
import io.github.sporklibrary.android.annotations.BindResource;
import io.github.sporklibrary.annotations.ComponentParent;
import io.github.sporklibrary.android.interfaces.ContextProvider;
import io.github.sporklibrary.android.test.R;

public class TestComponent implements ContextProvider
{
	private final Activity activity;

	@BindResource(R.drawable.spork_test_drawable)
	private Drawable drawable;

	public TestComponent(@ComponentParent Activity activity)
	{
		this.activity = activity;

		Spork.bind(this);
	}

	@Override
	public Context getContext()
	{
		return activity;
	}

	public Drawable getDrawable()
	{
		return drawable;
	}
}