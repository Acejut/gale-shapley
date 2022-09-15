//CSC401 HW01
//Justin Ace Ruiz

import java.util.*;

public class main
{
	static int hPrefs[][] = new int[][]
			{{1, 0, 3, 4, 2},
			 {3, 1, 0, 2, 4},
			 {1, 4, 2, 3, 0},
			 {0, 3, 2, 1, 4},
			 {1, 3, 0, 4, 2}};
			 
	
	static int sPrefs[][] = new int[][]
			{{4, 0, 1, 3, 2},
			 {2, 1, 3, 0, 4},
			 {1, 2, 3, 4, 0},
			 {0, 4, 3, 2, 1},
			 {3, 1, 4, 2, 0}};
			 
	static String[] hNames = {"Atlanta", "Boston", "Chicago", "Detroit", "El Paso"};
			 
	static int size = hPrefs.length;
			 
	static int finalMatches[];

	public static void main(String[] args)
	{
		printStudentInfo();
		
		System.out.println("==========Original Example Pairing==========\n");
		printHospitalInfo();
		galeShapely();
		printResult();
		System.out.println("\n=================================================\n");
		
		shufflePrefRows();
		
		System.out.println("\n==========Random Row Example Pairing==========\n");
		printHospitalInfo();
		galeShapely();
		printResult();
		System.out.println("\n=================================================\n");
		
	}
	
	private static void printHospitalInfo()
	{
		System.out.println("-------Hospital Order and Preference--------");
		for (int i = 0; i < size; i++)
		{
			System.out.printf("(%d)%s student preference is: ", i, hNames[i]);
			for (int j = 0; j < size; j++)
				System.out.printf("%c ", hPrefs[i][j] + 118);
			System.out.println();
		}
		System.out.println();
	}
	
	private static void galeShapely()
	{
		int openHospitals = size;
		int studentMatches[] = new int[size];
		boolean hospitalMatched[] = new boolean[size];
		
		//initialize all students to unmatched
		for (int i = 0; i < size; i++)
			studentMatches[i] = -1;
		
		
	
		//while there are open hospitals
		while (openHospitals > 0)
		{
			//choose the first unmatched hospital
			int curHospital;
			for (curHospital = 0; curHospital < size; curHospital++)
				if (hospitalMatched[curHospital] == false)
					break;
			
			//choose a student on the order of the chosen hospital's preference
			for (int j = 0; (j < size) && (!hospitalMatched[curHospital]); j++)
			{
				int curStudent = hPrefs[curHospital][j];

				//student is available so they match
				if (studentMatches[curStudent] == -1)
				{
					studentMatches[curStudent] = curHospital;
					hospitalMatched[curHospital] = true;
					openHospitals--;
				}
				//student is not available, but check what the student prefers
				else
				{
					//grab the hospital the student is currently matched to
					int curMatch = studentMatches[curStudent];
					if (tradeUp(sPrefs, curStudent, curHospital, curMatch))
					{
						//student has decided to trade up. Unmatch the previous hospital, and match w/the new hospital
						studentMatches[curStudent] = curHospital;
						hospitalMatched[curMatch] = false;
						hospitalMatched[curHospital] = true;
					}
				}
			}
		}
		finalMatches = studentMatches;
	}
	
	private static boolean tradeUp(int sPrefs[][], int curStudent, int curHospital, int curMatch)
	{
		for (int i = 0; i < size; i++)
		{
			if (sPrefs[curStudent][i] == curMatch)
				return false;
			
			if (sPrefs[curStudent][i] == curHospital)
				return true;
		}
		return true;
	}
	
	private static void printResult()
	{
		System.out.println("---------------Pairing Results---------------");
		
		for (int i = 0; i < size; i++)
			System.out.printf("Hospital %s matches with student %c\n", hNames[i], finalMatches[i]+118);
	}
	
	private static void shufflePrefRows()
	{
		for (int k = 0; k < 20; k++)
		{
			for (int i = 0; i < size; i++)
			{
				int i1 = (int)  (Math.random() * size);
				
				int[] temp = hPrefs[i];
				String temp2 = hNames[i];
				
				hPrefs[i] = hPrefs[i1];
				hPrefs[i1] = temp;
				
				hNames[i] = hNames[i1];
				hNames[i1] = temp2;
			}
		}
	}
	
	private static void printStudentInfo()
	{
		System.out.println("----------Student Preference----------");
		for (int i = 0; i < size; i++)
			System.out.println("Student " + (char) (i+118) + " preference is " + printRow(sPrefs[i]));
		System.out.println();
	}
	
	private static String printRow(int[] pref)
	{
		String s = "";
		for (int i = 0; i < size; i++)
			s += "'" + hNames[pref[i]] + "' ";
		return s;
	}
}