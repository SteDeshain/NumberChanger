package com.stedeshain.numberchanger;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity
{
	public static final int NUMBER = 0;
	public static final int NUMBER_X = 1;
	public static final int NUMBER_Y = 2;
	public static final int NUMBER_SIZE = 3;
	public static final int RECT_X = 4;
	public static final int RECT_Y = 5;
	public static final int RECT_WIDTH = 6;
	public static final int RECT_HEIGHT = 7;
	public static final int MODE_COUNT = 8;
	
	private static Activity mainActivity = null;

	public static final int CHOOSE_PIC = 0;
	
	public static int modifyMode = NUMBER;

	public static int writeInteger = 4;
	
	public static int numX = 324;
	public static int numY = 1492;
	public static int numSize = 63;
	
	public static int rectX = 324;
	public static int rectY = 1443;
	public static int rectWidth = 35;
	public static int rectHeight = 64;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);

		mainActivity = this;
	}

	public void selectPictureFromAlbum(View view)
	{
		Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		mainActivity.startActivityForResult(intent, CHOOSE_PIC);
	}

	public static String getSavePath()
	{
		return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + "/Camera";
		
//		boolean hasSDCard = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
//		if(hasSDCard)
//		{
//			return Environment.getExternalStorageDirectory().toString() + "/saving_picture";
//		}
//		else
//			return "/data/data/com.stedeshain.numberchanger/saving_picture";
	}

	public void savePicture(View view)
	{
		ImageView picImage = (ImageView)findViewById(R.id.image_pic);
		if(picImage.getVisibility() != View.VISIBLE)
		{
			return;
		}

		picImage.buildDrawingCache(true);
		picImage.buildDrawingCache();
		Bitmap bitmap = picImage.getDrawingCache();
		
		String directory = getSavePath();
		File temp = new File(directory);
		if(!temp.exists())
		{
			temp.mkdir();
		}
		Calendar calendar = Calendar.getInstance();
		String fileName = calendar.getTimeInMillis() + ".jpg";
		File file = new File(directory + "/" + fileName);
		try
		{
			file.createNewFile();
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
			bos.flush();
			bos.close();
			Toast.makeText(getApplicationContext(), "±£¥Ê≥…π¶", Toast.LENGTH_SHORT).show();
			
			//refresh album
			Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
			Uri uri = Uri.fromFile(file);
			intent.setData(uri);
			sendBroadcast(intent);

		}
		catch(IOException e)
		{
			e.printStackTrace();
			Toast.makeText(getApplicationContext(), "±£¥Ê ß∞‹", Toast.LENGTH_SHORT).show();
		}
		
		picImage.setDrawingCacheEnabled(false);
	}

	public void decreaseInteger(View view)
	{
		switch(modifyMode)
		{
		case NUMBER:
			writeInteger--;
			break;
		case NUMBER_X:
			numX--;
			break;
		case NUMBER_Y:
			numY--;
			break;
		case NUMBER_SIZE:
			numSize--;
			break;
		case RECT_X:
			rectX--;
			break;
		case RECT_Y:
			rectY--;
			break;
		case RECT_WIDTH:
			rectWidth--;
			break;
		case RECT_HEIGHT:
			rectHeight--;
			break;
		default:
			break;
		}
		
		ImageView picImage = (ImageView)findViewById(R.id.image_pic);
		picImage.invalidate();
	}

	public void increaseInteger(View view)
	{
		switch(modifyMode)
		{
		case NUMBER:
			writeInteger++;
			break;
		case NUMBER_X:
			numX++;
			break;
		case NUMBER_Y:
			numY++;
			break;
		case NUMBER_SIZE:
			numSize++;
			break;
		case RECT_X:
			rectX++;
			break;
		case RECT_Y:
			rectY++;
			break;
		case RECT_WIDTH:
			rectWidth++;
			break;
		case RECT_HEIGHT:
			rectHeight++;
			break;
		default:
			break;
		}
		
		ImageView picImage = (ImageView)findViewById(R.id.image_pic);
		picImage.invalidate();
	}
	
	public void addModifyMode(View view)
	{
		modifyMode++;
		modifyMode %= MODE_COUNT;
		freshText();
	}
	public void subModifyMode(View view)
	{
		modifyMode--;
		if(modifyMode < 0)
			modifyMode = MODE_COUNT - 1;
		else
			modifyMode %= MODE_COUNT;
		freshText();
	}
	private void freshText()
	{
		TextView textView = (TextView)findViewById(R.id.text_modify_varity);
		switch(modifyMode)
		{
		case NUMBER:
			textView.setText(" ˝◊÷");
			break;
		case NUMBER_X:
			textView.setText(" ˝◊÷x");
			break;
		case NUMBER_Y:
			textView.setText(" ˝◊÷y");
			break;
		case NUMBER_SIZE:
			textView.setText("◊÷ÃÂ");
			break;
		case RECT_X:
			textView.setText("±≥æ∞x");
			break;
		case RECT_Y:
			textView.setText("±≥æ∞y");
			break;
		case RECT_WIDTH:
			textView.setText("±≥æ∞øÌ");
			break;
		case RECT_HEIGHT:
			textView.setText("±≥æ∞∏ﬂ");
			break;
		default:
			textView.setText("error");
			break;
		}
		textView.invalidate();
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);

		TextView hintText = (TextView)findViewById(R.id.text_hint);
		ImageView picImage = (ImageView)findViewById(R.id.image_pic);

		if(resultCode == RESULT_OK) // choose a picture
		{
			try
			{
				Bitmap bmp = BitmapFactory.decodeStream(this.getContentResolver().openInputStream(data.getData()),
						null, new BitmapFactory.Options());
//				int picWidth = bmp.getWidth();
//				int picHeight = bmp.getHeight();
//				int newHeight = picImage.getMeasuredHeight();
//				int newWidth = (int)((float)newHeight / (float)picHeight * (float)picWidth);
//				picImage.measure(MeasureSpec.makeMeasureSpec(newWidth, MeasureSpec.EXACTLY), 
//						MeasureSpec.makeMeasureSpec(newWidth, MeasureSpec.EXACTLY));
//				picImage.layout(0, 0, picImage.getMeasuredWidth(), picImage.getMeasuredHeight());
				picImage.setImageBitmap(bmp);
//				((HintImageView)picImage).setOriginPicWidth(bmp.getWidth());
//				((HintImageView)picImage).setOriginPicHeight(bmp.getHeight());
				
				picImage.setVisibility(View.VISIBLE);
				hintText.setVisibility(View.GONE);
			}
			catch(FileNotFoundException e)
			{
				e.printStackTrace();
				Log.e(this.getClass().getName(), e.getMessage());
			}
		}
		else
		{
			hintText.setVisibility(View.VISIBLE);
			picImage.setVisibility(View.GONE);
		}
	}
}
