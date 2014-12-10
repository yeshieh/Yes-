package com.example.yes_cheese;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.renderscript.Int2;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Toast;

public class InitUI extends View implements OnTouchListener {
	int x0 = 18;
	int y0 = 300;
	int xi;
	int yi;
	int Count = 9;
	int eWidth = 50;
	int Length = 450;
	int type = 1;
	int turn = 1;
	int back = 0;
	String winner;
	List<StepList> slist = new ArrayList<StepList>();
	List<Integer> dlist = new ArrayList<Integer>();
	int ChessPosition[][] = new int[Count + 1][Count + 1];
	int ChessPriority[][] = new int[Count + 1][Count + 1];

	public InitUI(Context context, AttributeSet attributeSet) {
		super(context, attributeSet);
		setOnTouchListener(this);
	}

	public InitUI(Context context) {
		super(context);
		setOnTouchListener(this);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		// /������
		Paint painter = new Paint();
		painter.setColor(Color.BLACK);
		painter.setTypeface(Typeface.DEFAULT_BOLD);
		for (int i = 0; i < Count + 1; i++) {
			canvas.drawLine(x0, y0 + i * eWidth, Length + x0, y0 + i * eWidth,
					painter);
		}

		for (int j = 0; j < Count + 1; j++) {
			canvas.drawLine(x0 + j * eWidth, y0, x0 + j * eWidth, Length + y0,
					painter);
		}
		// /˭�Ļغ�
		if (type == 1)
			painter.setColor(Color.WHITE);
		else if (type == 2)
			painter.setColor(Color.BLACK);
		canvas.drawCircle(x0 + 10, y0 - 30, 10, painter);
		painter.setTextSize(20);
		canvas.drawText("Your Turn!", x0 + 20, y0 - 30, painter);
		// /����
		painter.setColor(Color.BLACK);
		painter.setTextSize(40);
		painter.setTypeface(Typeface.create("����", Typeface.BOLD_ITALIC));
		canvas.drawText("����ڵ�������", 140, 100, painter);
		// /������
		for (int i = 0; i < Count + 1; i++) {
			for (int j = 0; j < Count + 1; j++) {
				if (ChessPosition[i][j] == 1)
					painter.setColor(Color.WHITE);
				else if (ChessPosition[i][j] == 2)
					painter.setColor(Color.BLACK);
				else
					painter.setColor(Color.TRANSPARENT);
				canvas.drawCircle(i * eWidth + x0, j * eWidth + y0, 25, painter);
				// mes = mes + " " + Integer.toString(ChessPosition[i][j]);
			}
		}
		// /����
		back = 0;
		if (ChessPosition[xi][yi] != 0) {
			back = 1;
			if(back==1)
			{
			painter.setColor(Color.BLACK);
			canvas.drawText("��Ҫ����!", x0 + 200, y0 - 30, painter);
			}
		}
		// ��������
		new StepList(ChessPosition);
		// ���沽��
		StepList sl = new StepList(xi, yi, type);
		slist.add(sl);

		// /���ʤ��
		if (checkWinner()) {
			String wi = type == 2 ? Fillyourname.user1 : Fillyourname.user2;
			if (type == 2)
				winner = Fillyourname.user1 + "�ٱ���" + Fillyourname.user2;
			else
				winner = Fillyourname.user2 + "�ٱ���" + Fillyourname.user1;
			new AlertDialog.Builder(getContext())
					.setTitle("Winner!" + wi)
					.setMessage(winner)
					.setPositiveButton("����һ��",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									Reset();
								}
							})
					.setNegativeButton("�˳�",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									 dialog.dismiss(); 
				                        Fillyourname.instance.finish(); 
								}
							}).show();
		} else {
			if (Fillyourname.entertype == 2) {
				if (turn == 2) {
					AIJudge();
				}
			}
		}
	}

	// �˹������жϲ���
	private void AIJudge() {
		int xj = 0;
		int yj = 0;
		// /ˮƽ����
		for (int i = 0; i < Count + 1; i++) {
			for (int j = 0; j < Count + 1; j++) {
				if (ChessPosition[i][j] == 0) {
					ChessPosition[i][j] = 1;
					ChessPriority[i][j] = checkHorizontal(i, j).get(1);
					ChessPosition[i][j] = 2;
					if (ChessPriority[i][j] < checkHorizontal(i, j).get(1)&&checkHorizontal(i, j).get(0)==1)
						ChessPriority[i][j] = checkHorizontal(i, j).get(1);
					ChessPosition[i][j] = 0;
				}
			}
		}
		// /��ֱ����
		for (int i = 0; i < Count + 1; i++) {
			for (int j = 0; j < Count + 1; j++) {
				if (ChessPosition[i][j] == 0) {
					ChessPosition[i][j] = 1;
					if (ChessPriority[i][j] < checkVertical(i, j).get(1)&&checkVertical(i, j).get(0)==1)
						ChessPriority[i][j] = checkVertical(i, j).get(1);
					ChessPosition[i][j] = 2;
					if (ChessPriority[i][j] < checkVertical(i, j).get(1)&&checkVertical(i, j).get(0)==1)
						ChessPriority[i][j] = checkVertical(i, j).get(1);
					ChessPosition[i][j] = 0;
				}
			}
		}
		// /б������
		for (int i = 0; i < Count + 1; i++) {
			for (int j = 0; j < Count + 1; j++) {
				if (ChessPosition[i][j] == 0) {
					ChessPosition[i][j] = 1;
					if (ChessPriority[i][j] < checkObliqueDown(i, j).get(1)&&checkObliqueDown(i, j).get(0)==1)
						ChessPriority[i][j] = checkObliqueDown(i, j).get(1);
					ChessPosition[i][j] = 2;
					if (ChessPriority[i][j] < checkObliqueDown(i, j).get(1)&&checkObliqueDown(i, j).get(0)==1)
						ChessPriority[i][j] = checkObliqueDown(i, j).get(1);
					ChessPosition[i][j]=0;
				}
			}
		}
		// /б������
		for (int i = 0; i < Count + 1; i++) {
			for (int j = 0; j < Count + 1; j++) {
				if (ChessPosition[i][j] == 0) {
					ChessPosition[i][j] = 1;
					if (ChessPriority[i][j] < checkObliqueUp(i, j).get(1)&&checkObliqueUp(i, j).get(0)==1)
						ChessPriority[i][j] = checkObliqueUp(i, j).get(1);
					ChessPosition[i][j] = 2;
					if (ChessPriority[i][j] < checkObliqueUp(i, j).get(1)&&checkObliqueUp(i, j).get(0)==1)
						ChessPriority[i][j] = checkObliqueUp(i, j).get(1);
					ChessPosition[i][j]=0;
				}
			}
		}
		for (int i = 0; i < Count + 1; i++) {
			for (int j = 0; j < Count + 1; j++) {
				if (ChessPriority[i][j] >= ChessPriority[xj][yj]) {
					xj = i;
					yj = j;
				}
			}
		}
		for (int i = 0; i < Count + 1; i++) {
			for (int j = 0; j < Count + 1; j++) {
				ChessPriority[i][j] = 0;
			}
		}
		ChessPosition[xj][yj] = 2;
		xi = xj;
		yi = yj;
		type = type == 1 ? 2 : 1;
		turn = 1;
		postInvalidate();
	}

	// /�ж�ʤ��
	private boolean checkWinner() {
		int yy = yi;
		int xx = xi;
		if (checkHorizontal(xx, yy).get(1) == 5)
			return true;
		else if (checkVertical(xx, yy).get(1) == 5)
			return true;
		else if (checkObliqueUp(xx, yy).get(1) == 5)
			return true;
		else if (checkObliqueDown(xx, yy).get(1) == 5)
			return true;
		else
			return false;
	}

	private List<Integer> checkVertical(int xx, int yy) {
		dlist.clear();
		// /�ٽ�ֵ
		int abletowin = 0;
		int yo = 0;
		int Counter = 0;
		/* �Дഹֱ */
		for (int i = 0; i < 5; i++) {
			if (yy - i < 0)
				break;
			else if (ChessPosition[xx][yy - i] == ChessPosition[xx][yy])
				yo = yy - i;
			else
				break;
		}
		for (int j = 0; j < 5; j++) {
			if (yo + j > Count)
				break;
			if ((ChessPosition[xx][yy] == 1 || ChessPosition[xx][yy] == 2)
					&& ChessPosition[xx][yo + j] == ChessPosition[xx][yy]) {
				Counter = Counter + 1;
			} else
				break;
		}
		if(yo+4>Count)
			abletowin = 0;
		else
		{
			abletowin = 1;
		}
		dlist.add(abletowin);
		dlist.add(Counter);
		return dlist;
	}

	private List<Integer> checkHorizontal(int xx, int yy) {
		dlist.clear();
		int abletowin = 0;
		int xo = 0;
		int Counter = 0;
		/* �Д�ˮƽ */
		for (int i = 0; i < 5; i++) {
			if (xx - i < 0)
				break;
			else if (ChessPosition[xx - i][yy] == ChessPosition[xx][yy])
				xo = xx - i;
			else
				break;
		}
		for (int j = 0; j < 5; j++) {
			if (xo + j > Count)
				break;
			if ((ChessPosition[xx][yy] == 1 || ChessPosition[xx][yy] == 2)
					&& ChessPosition[xo + j][yy] == ChessPosition[xx][yy]) {
				Counter = Counter + 1;
			} else
				break;
		}
		if(xo+4>Count)
			abletowin = 0;
		else
		{
			abletowin = 1;
			for(int i = 0;i <= 5-Counter;i++)
			{
				if(xo+Counter+i>Count)
					break;
				if(ChessPosition[xo+Counter+i][yy]!=ChessPosition[xx][yy]&&ChessPosition[xo+Counter+i][yy]!=0)
				{
					abletowin = 0;
				}
			}
		}
		dlist.add(abletowin);
		dlist.add(Counter);
		return dlist;
	}

	private List<Integer> checkObliqueDown(int xx, int yy) {
		dlist.clear();
		int abletowin = 0;
		int xo = 0;
		int yo = 0;
		int Counter = 0;
		/* �Д�б�� */
		for (int i = 0; i < 5; i++) {
			if (xx - i < 0 || yy - i < 0)
				break;
			else if (ChessPosition[xx - i][yy - i] == ChessPosition[xx][yy]) {
				xo = xx - i;
				yo = yy - i;
			} else
				break;
		}
		for (int j = 0; j < 5; j++) {
			if (xo + j > Count || yo + j > Count)
				break;

			if ((ChessPosition[xx][yy] == 1 || ChessPosition[xx][yy] == 2)
					&& ChessPosition[xo + j][yo + j] == ChessPosition[xx][yy]) {
				Counter = Counter + 1;
			} else
				break;
		}
		if(xo+4>Count||yo+4>Count)
			abletowin = 0;
		else
		{
			abletowin = 1;
			for(int i = 0;i <= 5-Counter;i++)
			{
				if(yo+Counter+i>Count||xx+Counter+i>Count)
					break;
				if(ChessPosition[xo+Counter+i][yo+Counter+i]!=ChessPosition[xx][yy]&&ChessPosition[xo+Counter+i][yo+Counter+i]!=0)
				{
					abletowin = 0;
				}
			}
		}
		dlist.add(abletowin);
		dlist.add(Counter);
		return dlist;
	}

	private List<Integer> checkObliqueUp(int xx, int yy) {
		dlist.clear();
		int abletowin = 0;
		int xo = 0;
		int yo = 0;
		int Counter = 0;
		/* �Д�б�� */
		for (int i = 0; i < 5; i++) {
			if (xx - i < 0 || yy + i > Count)
				break;
			else if (ChessPosition[xx - i][yy + i] == ChessPosition[xx][yy]) {
				xo = xx - i;
				yo = yy + i;
			} else {
				break;
			}
		}
		for (int j = 0; j < 5; j++) {
			if (xo + j > Count || yo - j < 0)
				break;
			if ((ChessPosition[xx][yy] == 1 || ChessPosition[xx][yy] == 2)
					&& ChessPosition[xo + j][yo - j] == ChessPosition[xx][yy]) {
				Counter = Counter + 1;
			} else
				break;
		}
		if(xo+4>Count||yo-4<0)
			abletowin = 0;
		else
		{
			abletowin = 1;
			for(int i = 1;i < 5;i++)
			{
				if(yo - i < 0||xo + i > Count)
					break;
				if(ChessPosition[xo+i][yo-i]!=ChessPosition[xx][yy]&&ChessPosition[xo+i][yo-i]!=0)
				{
					abletowin = 0;
				}
			}
		}
		dlist.add(abletowin);
		dlist.add(Counter);
		return dlist;
	}
	
	// /�˹��������ȼ�
	private int Priority(int xx,int yy)
	{
		int Counter = 0;
		int one = 1;
		int two = 2;
		int three = 3;
		int four = 4;
		int doub_three = 5;
		int three_four = 6;
		int flag = 0;
		return flag;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {

		float x = event.getX();
		float y = event.getY();
		// /����
		if (x > 218 & x < 350 & y > 230 & y < 310 && back==1) {
			StepList sl;
			if(Fillyourname.entertype == 2){
				sl = slist.get(slist.size() - 2);
			}else {type = type == 1 ? 2 : 1;
				sl = slist.get(slist.size() - 1);
			}
			
			int prex = sl.Getcol();
			int prey = sl.Getrow();
			ChessPosition[prex][prey] = 0;
			postInvalidate();
			return true;
		}
		if (x < x0 || x > x0 + Length || y < y0 || y > y0 + Length)
			return false;

		else {
			xi = (int) (x - x0 + 25) / eWidth;
			yi = (int) (y - y0 + 25) / eWidth;
			if (ChessPosition[xi][yi] == 1 || ChessPosition[xi][yi] == 2
					|| xi > Count || yi > Count)
				return false;
			ChessPosition[xi][yi] = type;
			if (Fillyourname.entertype == 2) {
				if (turn == 1)
					turn = 2;
			}
			type = type == 1 ? 2 : 1;
			postInvalidate();
		}

		return false;
	}

	// /���¿�ʼ
	public void Reset() {
		int i = 0;
		for (; i < Count + 1; i++) {
			for (int j = 0; j < Count + 1; j++) {
				ChessPosition[i][j] = 0;
			}
		}
		type = 1;
		turn = 1;
		postInvalidate();
	}

}
