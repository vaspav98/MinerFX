package io.example.minerfx.sweeper;

public class Game {

    private Bomb bomb;
    private Flag flag;
    private GameState state;

    public GameState getState() {
        return state;
    }

    public Game(int cols, int rows, int totalBombs) {
        Ranges.setSize(new Coord(cols, rows));
        bomb = new Bomb(totalBombs);
        flag = new Flag();
    }

    public void start() {
        bomb.init();
        flag.init();
        state = GameState.PLAYED;
    }

    public Box getBox(Coord coord) {
        if (flag.get(coord) == Box.OPENED) {
            return bomb.get(coord);
        }
        return flag.get(coord);
    }

    public void pressLeftButton(Coord coord) {
        if (gameOver()) {
            return;
        }
        openBox(coord);
        checkWinner();
    }

    private void checkWinner() {
        if (state == GameState.PLAYED) {
            if (flag.getCountOfClosedBoxes() == bomb.getTotalBombs()) {
                state = GameState.WINNER;
            }
        }
    }

    private void openBox(Coord coord) {
        switch (flag.get(coord)) {
            case OPENED:
                break;
            case FLAGED:
                break;
            case CLOSED:
                switch (bomb.get(coord)) {
                    case ZERO:
                        openBoxesAround(coord);
                        break;
                    case BOMB:
                        openBombs(coord);
                        break;
                    default: flag.setOpenedToBox(coord);
                }
        }
    }

    private void openBombs(Coord bombed) {
        state = GameState.BOMBED;
        for (Coord coord : Ranges.getAllCoords()) {
            if (bomb.get(coord) == Box.BOMB && flag.get(coord) == Box.FLAGED) {
                continue;
            } else if (bomb.get(coord) == Box.BOMB) {
                flag.setOpenedToBox(coord);
            } else {
                flag.setNobombToFlagedSafeBox(coord);
            }


/*            if (bomb.get(coord) == Box.BOMB) {
                flag.setOpenedToBox(coord);
                flag.setOpenedToClosedBombBox(coord);
            } else if (bomb.get(coord) == Box.BOMB && flag.get(coord) == Box.FLAGED) {
                continue;

                flag.setNobombToFlagedSafeBox(coord);
            }*/
        }
        flag.setBombedToBox(bombed);

    }
    private void openBoxesAround(Coord coord) {
        flag.setOpenedToBox(coord);
        for (Coord around : Ranges.getCoordAround(coord)) {
            openBox(around);
        }
    }

    public void pressRightButton(Coord coord) {
        if (gameOver()) {
            return;
        }
        flag.toggleFlagedToBox(coord);
    }

    private boolean gameOver() {
        if (state == GameState.PLAYED) {
            return false;
        }
        start();
        return true;
    }

}
