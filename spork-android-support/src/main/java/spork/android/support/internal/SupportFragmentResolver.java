package spork.android.support.internal;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import spork.BindException;
import spork.android.BindFragment;
import spork.android.interfaces.FragmentResolver;

/**
 * Resolves Fragment instances from v4 support library types.
 */
public class SupportFragmentResolver implements FragmentResolver {

	@Override
	public @Nullable Object resolveFragment(Object object, int id) {
		if (object instanceof FragmentActivity) {
			return ((FragmentActivity) object).getSupportFragmentManager().findFragmentById(id);
		} else if (object instanceof Fragment) {
			return ((Fragment) object).getFragmentManager().findFragmentById(id);
		} else {
			return null;
		}
	}

	@Override
	public @Nullable Object resolveFragment(Object object, String idName) {
		if (object instanceof FragmentActivity) {
			FragmentActivity activity = (FragmentActivity) object;
			int id = activity.getResources().getIdentifier(idName, "id", activity.getPackageName());

			if (id == 0) {
				throw new BindException(BindFragment.class, activity.getClass(), "Fragment not found by name for id '" + idName + "'");
			}

			return activity.getSupportFragmentManager().findFragmentById(id);
		} else if (object instanceof Fragment) {
			Fragment fragment = (Fragment) object;
			int id = fragment.getResources().getIdentifier(idName, "id", fragment.getActivity().getPackageName());

			if (id == 0) {
				throw new BindException(BindFragment.class, fragment.getClass(), "Fragment not found by name for id '" + idName + "'");
			}

			return fragment.getFragmentManager().findFragmentById(id);
		} else {
			return null;
		}
	}
}