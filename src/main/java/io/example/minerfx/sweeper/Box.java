package io.example.minerfx.sweeper;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.List;

public enum Box {
    ZERO,
    NUM1,
    NUM2,
    NUM3,
    NUM4,
    NUM5,
    NUM6,
    NUM7,
    NUM8,
    BOMB,
    OPENED,
    CLOSED,
    FLAGED,
    BOMBED,
    NOBOMB;

    public Image image;

    Box getNextNumberBox() {
        return Box.values()[this.ordinal() + 1];
    }

}

