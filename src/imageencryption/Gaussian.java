/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imageencryption;

/**
 *
 * @author HP
 */
public class Gaussian
{
	private double matrix[][];
	private int rows;
	private int columns;
	private int rkey;
	private int ckey;
	private double key[][];
	private double p[][];
	private double decrypted[][];
	
	public Gaussian(double matrix[][],int m,int n)
	{
		this.matrix=matrix;
		this.rows=m;
		this.columns=n;
		this.rkey=this.ckey=m;
		matrix=new double[rows][columns];
		key=new double[rkey][ckey];
		p=new double[rows][rows];
		decrypted=new double[rkey][columns];
	}

	 
	public void encrypt()
	{
		System.out.println("\nEncrypting...");
		int pivotrow;
		double temp;
		double temp1;
		for(int i=0;i<rows;i++)
		{
			
			pivotrow=i;
			for (int j=i+1;j<rows;j++)
			{
				if(matrix[j][i] > matrix[pivotrow][i] || matrix[pivotrow][i]!=0)
				{
					pivotrow=j;
					
					break;
				}	
				else if(matrix[pivotrow][i]==0)
				{
					continue;
				}	
			}
			
			for (int l=i+1;l<rows;l++)
			{
				if(matrix[i][i]==0)
				{
					for (int k=i;k<columns;k++)
					{
						temp1=matrix[i][k];
						matrix[i][k]=matrix[pivotrow][k];
						matrix[pivotrow][k]=temp1;
					   p[i][pivotrow]=1;
					}
					
				}

				if(matrix[i][i]==0 && matrix[l][i]==0)
				{
					if(matrix[i][i+1]!=0)
					{
						double factortodivide=matrix[i][i+1];
						temp=matrix[l][i+1]/factortodivide;
						key[l][i]=temp;
						for (int m=i;m<columns;m++)
						{
							matrix[l][m] = matrix[l][m]- matrix[i][m]* temp;
						}
					}		
				}	
				else if(matrix[i][i]!=0)
				{
					temp=matrix[l][i]/matrix[i][i];
					key[l][i]=temp;
					for (int m=i;m<columns;m++)
					{
						matrix[l][m] = matrix[l][m]- matrix[i][m]* temp;
					}
				}
			}	
		}
	 	System.out.println("\nEncryption successful..Image saved..");
	
	}
	
	
	public double[][] getencryptedmatrix()
	{
		return matrix;
	}
	
	
	public double[][] decrypt()
	{
		System.out.println("\nDecrypting...");
		double sum=0;
		for ( int c = 0 ; c < rkey ; c++ )
		{
            for ( int d = 0 ; d < columns; d++ )
            {   
               for ( int k = 0 ; k < rows ; k++ )
               {
                  sum = sum + key[c][k]*matrix[k][d];
               }
 				   decrypted[c][d] = sum;
               sum = 0;
            }
		}
		System.out.println("\nChecking decrypted image....");
		double original[][]=do_row_exchange();
		return(original);
			
	}

		
	public void init_key()
	{
		System.out.println("\nInitialising key...");
		for(int i=0;i<rows;i++)
		{
			for(int j=0;j<rows;j++)
			{
				if(i==j)
				{
					key[i][j]=1;
				}
				else
				{	
					key[i][j]=0;
				}
			}
		}
	}
	
	
	public double[][] do_row_exchange()
	{
		double temp;
		for(int i=rows-1;i>=0;i--)
		{
			for(int j=rows-1;j>=0;j--)
			{
				if(p[i][j]==1)
				{
					for (int k=0;k<columns;k++)
					{
						temp=decrypted[i][k];
						decrypted[i][k]=decrypted[j][k];
						decrypted[j][k]=temp;
					}
				}
			}
		}
		return decrypted;
	}		
			
					
}

