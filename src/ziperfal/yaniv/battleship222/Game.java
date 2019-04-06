package ziperfal.yaniv.battleship222;
import java.util.Scanner;


public class Game
{
    public static final int NUM_OF_LINES = 5;
    public static int numOfShipsInGame;
    private static Cell gameBoard[][];
    private Battleship[] ships;


    public Game()
    {
        Cell[] tmp;
        ships = new Battleship[3];
        gameBoard = new Cell[NUM_OF_LINES][NUM_OF_LINES];
        int i , j;
        for (i = 0 ; i < NUM_OF_LINES ; i++)
            for (j = 0 ; j < NUM_OF_LINES ; j++)
                gameBoard[i][j] = new Cell();

        for (i = 0 ; i < 3 ; i++)
        {
            tmp = getShipPosition();
            ships[i] = new Battleship(tmp);
        }
    }


    public void printBoard()
    {
        int i , j;
        System.out.println("\n");
        for (i = 0 ; i < NUM_OF_LINES ; i++)
        {
            for (j = 0 ; j < NUM_OF_LINES ; j++)
                System.out.print("  " + gameBoard[i][j].getDisplayChar() + "  ");
            System.out.println("\n");
        }
    }

    public boolean checkIfCellIsValid(int r , int c)
    {
        if (gameBoard[r][c].getDisplayChar() == '~')    // Valid
            return true;
        return false;
    }

    public Cell getFirstCellOfAShip(int[] firstCellIndex)
    {
        Cell startCell = new Cell();
        boolean isCellValid;
        int r, c;
        while (true)
        {
            r = (int) Math.round(Math.random()* NUM_OF_LINES - 1);
            c = (int) Math.round(Math.random()* NUM_OF_LINES) - 1;
            if (r < 0)
                r = 0;
            if (c < 0)
                c = 0;
            isCellValid = checkIfCellIsValid(r , c);
            if(isCellValid == true)
            {
                firstCellIndex[0] = r;
                firstCellIndex[1] = c;
                break;
            }

        }
        startCell = gameBoard[r][c];
        return startCell;
    }


    public boolean generateRandomTrueOrFalse()
    {
        double tmp = Math.random();
        if (tmp <= 0.5)
            return true;
        return false;
    }


    public Cell[] getShipPosition()
    {
        int i;
        int shipLength;
        if (generateRandomTrueOrFalse() == true)
            shipLength = 3;
        else
            shipLength = 2;

        Cell[] shipCells = new Cell[shipLength];
        int[] firstCellIndex = new int[2];
        boolean isGoingHorizontal = false;
        boolean isGoingRight = false;
        boolean isGoingDown = false;
        while(true)
        {
            for (i = 0 ; i < shipLength ; i++)
                shipCells[i] = new Cell();
            i = 1;
            shipCells[0] = getFirstCellOfAShip(firstCellIndex);
            isGoingHorizontal = generateRandomTrueOrFalse();
            if (isGoingHorizontal)
            {
                if(firstCellIndex[1] + shipLength <= NUM_OF_LINES)
                    isGoingRight = true;
                else
                    isGoingRight = false;

            }
            else    // go vertical
            {
                if (firstCellIndex[0] + shipLength <= NUM_OF_LINES)
                    isGoingDown = true;
                else
                    isGoingDown = false;
            }

            /*
              At this point we know if we should go up or down. we need to check the next cells
              in order to know if the cell is "Free" or has a Ship..
              if one of the next cells has a ship --> continue (next iteration will start and all the
              process will start again. if all the cells are clean--> break;)
             */

            if (isGoingHorizontal)
            {
                if (isGoingRight)
                {
                    for(; i < shipLength ; i++)
                    {
                        if (gameBoard[firstCellIndex[0]][firstCellIndex[1] + i].getDisplayChar() != '~')  // continue. invalid cell
                            continue;
                        else
                        {
                            shipCells[i] = gameBoard[firstCellIndex[0]][firstCellIndex[1] + i];
                        }
                    }
                }
                else
                {
                    // We need to check the next cells from left..
                    for(; i < shipLength ; i++)
                    {
                        if (gameBoard[firstCellIndex[0]][firstCellIndex[1] - i].getDisplayChar() != '~')  // continue. invalid cell
                            continue;
                        else
                        {
                            shipCells[i] = gameBoard[firstCellIndex[0]][firstCellIndex[1] - i];
                        }
                    }
                }
            }
            else
            {
                if (isGoingDown)
                {
                    // We need to check the next cells from down..
                    for(; i < shipLength ; i++)
                    {
                        if (gameBoard[firstCellIndex[0]+i][firstCellIndex[1]].getDisplayChar() != '~')  // continue. invalid cell
                            continue;
                        else
                            shipCells[i] = gameBoard[firstCellIndex[0]+i][firstCellIndex[1]];
                    }
                }
                else
                {
                    // We need to check the next cells from up..
                    for(; i < shipLength ; i++)
                    {
                        if (gameBoard[firstCellIndex[0]-i][firstCellIndex[1]].getDisplayChar() != '~')  // continue. invalid cell
                            continue;
                        else
                            shipCells[i] = gameBoard[firstCellIndex[0]-i][firstCellIndex[1]];
                    }
                }
            }
            break;
        }

        for (i = 0 ; i < shipLength ; i++)
        {
            shipCells[i].setDisplayChar(Battleship.num);
        }
        Battleship.num++;
        return shipCells;
    }

    public boolean checkHit(int x , int y)
    {
        if (gameBoard[y][x].getDisplayChar() != '~' && gameBoard[y][x].getDisplayChar() !='X')
            return true;

        return false;
    }

    public void runGame()
    {
        boolean shouldRunGame = true;
        System.out.println("\nShips are already on the game board. Lets Start!");
        System.out.println("choose the coordinates of the Cell you wanna hit. It has to be in the range of 0 --> " + (NUM_OF_LINES-1));
        System.out.println("Insert '10' in order to see the ships status in the game..");
        System.out.println("Insert '-1' in order to QUIT..");

        while(shouldRunGame)
        {
            int x, y;
            boolean didChangeNumOfHits = false;
            Scanner reader = new Scanner(System.in);
            System.out.print("X: ");
            x = reader.nextInt();
            if (x == 10)
            {
                System.out.println("Game Status:");
                for(int i = 0 ; i < ships.length ; i++)
                    ships[i].printShip();
                System.out.println("\n");
                continue;
            }
            if (x == -1)
            {
                shouldRunGame = false;
                break;
            }
            System.out.print("Y: ");
            y = reader.nextInt();
            if (y == 10)
            {
                System.out.println("Game Status:");
                for(int i = 0 ; i < ships.length ; i++)
                    ships[i].printShip();
                continue;
            }
            if (y == -1)
            {
                shouldRunGame = false;
                break;
            }
            System.out.println("Shooting on {" + x + " , " + y + "}!");
            if (checkHit(x , y))
            {
                System.out.println("CABBOOMMMMMM! Nice Hit!");
                for (int i = 0 ; i < ships.length && didChangeNumOfHits == false; i++)
                {
                    for(int j = 0 ; j < ships[i].getShipPositions().length ; j++)
                    {
                        if (ships[i].getShipPositions()[j] == gameBoard[y][x])
                        {
                            ships[i].setNumOfHits(ships[i].getNumOfHits() + 1);
                            gameBoard[y][x].setDisplayChar('X');
                            didChangeNumOfHits = true;
                            if (ships[i].getNumOfHits() == ships[i].getLength())
                                System.out.println("Ship is down!!!!!!!");
                            if (ships[0].getNumOfHits() == ships[0].getLength() && ships[1].getNumOfHits() == ships[1].getLength() && ships[2].getNumOfHits() == ships[2].getLength())
                            {
                                shouldRunGame = false;
                                printBoard();
                                System.out.println("YOU WON!!!!!!");
                                break;
                            }
                        }
                    }
                }
            }
            else
                System.out.println("Missed! Try again...");
        }
        System.out.println("GOODBYE..");
    }
}
