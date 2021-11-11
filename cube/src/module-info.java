module cube {
}
/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package cubesolver2;
import java.util.Scanner;
/**
*
* @author Lester
*/
public class CubeSolver2 {
/**
* @param args the command line arguments
*/
public static void main(String[] args) {
// TODO code application logic here
/*
Green = 1
White = 2
Blue = 3
Yellow = 4
Orange = 5
Red = 6
*/
//Get the colors from the user
int Cube_colors[][] = new int[6][9];
Cube_colors = getColors();
}
public static int[][] getColors()
{
int input[][] = new int[6][9];
//initiate the counter
int counter_0 = 0;
//initiate the scanner for the imput
Scanner in = new Scanner(System.in);
//Collect the data

//Get the data for the green side
System.out.println(&quot;Enter the colors for the green side\n1 = GREEN\n2 = WHITE\n3 =
BLUE\n4 = YELLOW\n5 = ORANGE\n6 = RED\n&quot;
+ &quot;The orange side should be on top\n&quot;
+ &quot;The red side is on the bottom\n&quot;
+ &quot;The white side to the right\n&quot;
+ &quot;And the yellow side to the left&quot;);
while(counter_0 &lt;= 8)
{
input[0][counter_0] = Integer.parseInt(in.nextLine());
counter_0++;
}
//Enter the colors for the white side
System.out.println(&quot;Enter the colors for the white side\n1 = GREEN\n2 = WHITE\n3 =
BLUE\n4 = YELLOW\n5 = ORANGE\n6 = RED\n&quot;
+ &quot;The orange side should be on top\n&quot;
+ &quot;The red side should be on the bottom\n&quot;
+ &quot;The blue side to the right\n&quot;
+ &quot;And the green side to the left&quot;);
//reset the counter
counter_0 = 0;
while(counter_0 &lt;= 8)
{
input[1][counter_0] = Integer.parseInt(in.nextLine());
counter_0++;
}
//Enter the colors for the blue side
System.out.println(&quot;Enter the colors for the blue side\n1 = GREEN\n2 = WHITE\n3 =
BLUE\n4 = YELLOW\n5 = ORANGE\n6 = RED\n&quot;
+ &quot;Orange on top\n&quot;
+ &quot;red on bottom\n&quot;
+ &quot;yellow to the right\n&quot;
+ &quot;And Red on bottom&quot;);
counter_0 = 0;
while(counter_0 &lt;= 8)
{
input[2][counter_0] = Integer.parseInt(in.nextLine());
counter_0++;
}
//Enter the colors for the yellow side
System.out.println(&quot;Enter the colors for the yellow side\n1 = GREEN\n2 = WHITE\n3 =
BLUE\n4 = YELLOW\n5 = ORANGE\n6 = RED\n&quot;
+ &quot;Orange on top\n&quot;
+ &quot;red on bottom\n&quot;

+ &quot;green to right\n&quot;
+ &quot;blue to left&quot;);
counter_0 = 0;
while(counter_0 &lt;= 8)
{
input[3][counter_0] = Integer.parseInt(in.nextLine());
counter_0++;
}
//Enter the colors for the orange side
System.out.println(&quot;Enter the colors for the orange side\n1 = GREEN\n2 = WHITE\n3
= BLUE\n4 = YELLOW\n5 = ORANGE\n6 = RED&quot;);
counter_0 = 0;
while(counter_0 &lt;= 8)
{
input[4][counter_0] = Integer.parseInt(in.nextLine());
counter_0++;
}
//Enter the colors for the red side
System.out.println(&quot;Enter the colors for the red side\n1 = GREEN\n2 = WHITE\n3 =
BLUE\n4 = YELLOW\n5 = YELLOW\n6 = RED&quot;);
counter_0 = 0;
while(counter_0 &lt;= 8)
{
input[5][counter_0] = Integer.parseInt(in.nextLine());
counter_0++;
}
return input;
}
}