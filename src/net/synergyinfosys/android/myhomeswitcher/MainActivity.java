package net.synergyinfosys.android.myhomeswitcher;

import java.util.List;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener, OnItemClickListener {

	public static final String TAG = "MyHomeSwitcher.MainActivity";

	Button mBtnDefaultLauncher, mBtnClearDefault;
	ListView mListView;

	ActivityManager mActivityManager = null;
	PackageManager mPackageManager = null;

	List<ResolveInfo> mLauncherList = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mActivityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
		mPackageManager = getPackageManager();
		mLauncherList = getAllLauncher();

		mBtnDefaultLauncher = (Button) findViewById(R.id.button_pick_default);
		mBtnClearDefault = (Button) findViewById(R.id.button_clear_default);
		mBtnDefaultLauncher.setOnClickListener(this);
		mBtnClearDefault.setOnClickListener(this);

		mListView = (ListView) findViewById(R.id.list_launcher);
		mListView.setAdapter(new LaucherListAdapter());
		mListView.setOnItemClickListener(this);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button_pick_default:
//			Intent launcher = new Intent();
//			launcher.setAction(Intent.ACTION_MAIN);
//			launcher.addCategory(Intent.CATEGORY_HOME);
//			
////			Intent i = Intent.createChooser(launcher, "选择默认Launcher");
////			startActivity(i);
//			startActivity( launcher );
			
			Toast.makeText(getApplicationContext(), "在高版本中无法清除其他应用默认设置", Toast.LENGTH_SHORT).show();
			
			break;
			
		case R.id.button_clear_default:
//			List<ResolveInfo> list = getAllLauncher();
//			Log.i(TAG, "Launcher:");
//			for (ResolveInfo info : list) {
//				Log.i(TAG, info.activityInfo.packageName);
//				mPackageManager.clearPackagePreferredActivities( info.activityInfo.packageName );
//			}
			Toast.makeText(getApplicationContext(), "在高版本中无法清除其他应用默认设置", Toast.LENGTH_SHORT).show();
			break;
		}
	}

	private List<ResolveInfo> getAllLauncher() {
		Intent launcher = new Intent();
		launcher.addCategory(Intent.CATEGORY_HOME);
		launcher.setAction(Intent.ACTION_MAIN);

		List<ResolveInfo> list = mPackageManager.queryIntentActivities(launcher, 0);

		return list;
	}

	public class LaucherListAdapter extends BaseAdapter {

		private class ItemHolder {
			ImageView appImage;
			TextView appName;
			Button switchButton;
		}

		private LayoutInflater mInflater = null;

		public LaucherListAdapter() {
			super();
			mInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		@Override
		public int getCount() {
			return mLauncherList.size();
		}

		@Override
		public Object getItem(int position) {
			return mLauncherList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ItemHolder holder;

			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.list_item, null);
				holder = new ItemHolder();
				holder.appImage = (ImageView) convertView.findViewById(R.id.ItemImage);
				holder.appName = (TextView) convertView.findViewById(R.id.ItemText);
				holder.switchButton = (Button) convertView.findViewById(R.id.Button_switch);
				convertView.setTag(holder);
			} else {
				holder = (ItemHolder) convertView.getTag();
			}

			ResolveInfo info = mLauncherList.get(position);
			final String pkgName = info.activityInfo.packageName;
			final String clsName = info.activityInfo.name;

			Drawable d = info.activityInfo.loadIcon(getPackageManager());
			holder.appImage.setImageDrawable(d);

			final String appName = info.activityInfo.loadLabel(getPackageManager()).toString();
			holder.appName.setText( appName );

			holder.switchButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Log.i( TAG, appName);
					Log.i( TAG, pkgName);
					Log.i( TAG, clsName);
					Log.i(TAG, ">>>>>>>>>>");
					ComponentName cn = new ComponentName( pkgName, clsName );
					Intent i = new Intent();
					i.setComponent(cn);
					i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(i);
				}
			});
			return convertView;
		}

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//		ResolveInfo info = mLauncherList.get(position);
//		String pkg = info.activityInfo.packageName;
//		String cls = info.activityInfo.name;

	}
}
