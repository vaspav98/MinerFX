package io.example.minerfx.sweeper;

class Bomb {

    private Matrix bombMap;
    private int totalBombs;

    Bomb(int totalBombs) {
        this.totalBombs = totalBombs;
        fixBombsCount();
    }

    void init() {
        bombMap = new Matrix(Box.ZERO);
        for (int i = 0; i < totalBombs; i++) {
            placeBomb();
        }
    }

    Box get(Coord coord) {
        return bombMap.get(coord);
    }

    private void fixBombsCount() {
        int maxBombs = Ranges.getSize().getX() * Ranges.getSize().getY() / 2;
        if (totalBombs > maxBombs) {
            totalBombs = maxBombs;
        }
    }

    private void placeBomb() {
        Coord coord = Ranges.getRandomCoord();
        while (bombMap.get(coord) == Box.BOMB) {
            coord = Ranges.getRandomCoord();
        }
        bombMap.set(coord, Box.BOMB);
        incNumbersAroundBomb(coord);
    }

    private void incNumbersAroundBomb(Coord coord) {
        for (Coord around : Ranges.getCoordAround(coord)) {
            if (Box.BOMB != bombMap.get(around)) {
                bombMap.set(around, bombMap.get(around).getNextNumberBox());
            }
        }
    }

    int getTotalBombs() {
        return totalBombs;
    }
}

