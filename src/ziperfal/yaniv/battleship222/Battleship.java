package ziperfal.yaniv.battleship222;

public class Battleship
{
    public static char num = '1';
    private int length;
    private int numOfHits;
    private Cell[] shipPositions;

    public Battleship(Cell[] shipPositions)
    {
        this.shipPositions = shipPositions;
        this.length = shipPositions.length;
        this.numOfHits = 0;
    }

    public int getLength() { return length; }
    public int getNumOfHits() { return numOfHits; }
    public Cell[] getShipPositions() { return shipPositions; }
    public void setNumOfHits(int numOfHits) { this.numOfHits = numOfHits; }

    public void printShip ()
    {
        switch (this.numOfHits) {
            case 0:
                {
                    if (this.length == 3)
                        System.out.println("B-B-B");
                    else
                        System.out.println("B-B");
                    break;
                }
            case 1:
            {
                if (this.length == 3)
                    System.out.println("B-B-X");
                else
                    System.out.println("B-X");
                break;
            }
            case 2:
            {
                if (this.length == 3)
                    System.out.println("B-X-X");
                else
                    System.out.println("X-X");
                break;
            }
            case 3:
            {
                System.out.println("X-X-X");
                break;
            }
        }
    }
}
