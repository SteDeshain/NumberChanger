package com.stedeshain.numberchanger;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.ImageView;

public class HintImageView extends ImageView
{
	private Paint mPaint;
	
	private int originPicHeight;
	private int originPicWidth;
	
	public HintImageView(Context context)
	{
		super(context);
		initView();
	}
	public HintImageView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		initView();
	}
	
	private void initView()
	{
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		//fit my honor8
		//mPaint.setTextSize(48); //48? I can't remember it for sure
		
		//fit my father's honor8s
		mPaint.setTextSize(MainActivity.numSize);
		//mPaint.setTypeface(Typeface.DEFAULT_BOLD);
	}
	
	@Override
	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		
		//fit my honor8
		/*
		mPaint.setColor(Color.rgb(0xeb, 0xeb, 0xeb));
		canvas.drawRect(350, 990, 380, 1050, mPaint);
		
		//canvas.drawCircle(100, 100, 100, mPaint);
		mPaint.setColor(Color.rgb(0x88, 0x88, 0x88));
		canvas.drawText("" + MainActivity.writeInteger, 354, 1028, mPaint);
		*/

		//fit my father's honorV8
		mPaint.setColor(Color.rgb(0xeb, 0xeb, 0xeb));
		//mPaint.setColor(Color.rgb(0xff, 0xff, 0xff));	// test back rect position
		canvas.drawRect(MainActivity.rectX, MainActivity.rectY, 
				MainActivity.rectX + MainActivity.rectWidth, MainActivity.rectY + MainActivity.rectHeight, 
				mPaint);
		
		mPaint.setColor(Color.rgb(0x88, 0x88, 0x88));
		mPaint.setTextSize(MainActivity.numSize);
		canvas.drawText("" + MainActivity.writeInteger, MainActivity.numX, MainActivity.numY, mPaint);	//474
	}
}
