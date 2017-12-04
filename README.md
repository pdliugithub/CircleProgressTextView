# CircleProgressTextView
CircleProgressTextView

![使用效果图片](https://github.com/pdliugithub/CircleProgressTextView/blob/master/gif/screen02.gif "效果展示")

## Use in help :

***

Step 1. Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}

Step 2. Add the dependency

	dependencies {
			compile 'com.github.pdliugithub:CircleProgressTextView:v1.0.1'
	}

Step 3. Add the code

	<com.rossia.life.circleprogress.CircleProgressTextView
		android:id="@+id/circle_progress_tv"
		android:layout_width="wrap_content"
		android:layout_height="wrap_content"
		android:padding="10dp"
		app:progress_paint_width="5dp"
		app:circle_paint_width="5dp"
		android:layout_centerHorizontal="true"
		android:text="跳过"
		app:progress="120" />
***

> ## Medal

![Medal](https://jitpack.io/v/pdliugithub/CircleProgressTextView.svg "medal展示")（https://jitpack.io/#pdliugithub/CircleProgressTextView "链接""）
