package ziperfal.yaniv.battleship222;

public class Cell
{
    private char displayChar;

    public Cell()
    {
        displayChar = '~';
    }

    public char getDisplayChar() { return displayChar; }

    public void setDisplayChar(char displayChar) {
        this.displayChar = displayChar;
    }
}
