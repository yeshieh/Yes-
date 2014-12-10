package com.example.yes_cheese;

public class StepList {
	int col;
	int row;
	int type;
	static int[][]chess = new int[100][100];
	
	public StepList(int col,int row,int type)
	{
		super();
		this.col = col;
		this.row = row;
		this.type = type;
	}
	public StepList(int[][] chess)
	{
		super();
		StepList.chess = chess;
	}
	
	public void setStringArray(int[][] chess) {
	    StepList.chess = chess;
	}
	 
	public int[][] getStringArray() {
	    return chess;
	}
	public void Setrow(int row)
	{
		this.row = row;
	}
	public int Getrow()
	{
		return row;
	}
	public void Setcol(int col)
	{
		this.col = col;
	}
	public int Getcol()
	{
		return col;
	}
	public void Settype(int type)
	{
		this.type = type;
	}
	public int Gettype()
	{
		return type;
	}

}
