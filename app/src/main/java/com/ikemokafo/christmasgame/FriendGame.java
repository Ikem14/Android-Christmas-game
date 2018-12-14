package com.ikemokafo.christmasgame;

import android.widget.ImageView;

public class FriendGame {

    private static ImageView[] blocks;
    public static String winner;
    public static int set;
    public static final int CHRISTMAS = 0;
    public static final int SANTA = 1;

    //Tests to see if image is in the same set of blocks
    private static boolean sameSet(int one, int two, int three, int tempSet) {
        boolean value = blocks[one - 1].getId() == blocks[two - 1].getId() && blocks[two - 1].getId() == blocks[three - 1].getId();
        if (value) {
            if (blocks[one - 1].getId() == CHRISTMAS)
                winner = "CHRISTMAS WINS";
            else
                winner = "SANTA WINS";
            set = tempSet;
        }
        return value;
    }


    //check if set is complete
    public static boolean isCompleted(int position, ImageView[] blocks) {
        FriendGame.blocks = blocks;
        boolean isComplete = false;
        switch (position) {
            case 1:
                isComplete = sameSet(1, 2, 3, 1) ||
                        sameSet(1, 4, 7, 4) ||
                        sameSet(1, 5, 9, 7);
                break;
            case 2:
                isComplete = sameSet(1, 2, 3, 1) ||
                        sameSet(2, 5, 8, 5);
                break;
            case 3:
                isComplete = sameSet(1, 2, 3, 1) ||
                        sameSet(3, 6, 9, 6) ||
                        sameSet(3, 5, 7, 8);
                break;
            case 4:
                isComplete = sameSet(4, 5, 6, 2) ||
                        sameSet(1, 4, 7, 4);
                break;
            case 5:
                isComplete = sameSet(4, 5, 6, 2) ||
                        sameSet(2, 5, 8, 5) ||
                        sameSet(1, 5, 9, 7) ||
                        sameSet(3, 5, 7, 8);
                break;
            case 6:
                isComplete = sameSet(4, 5, 6, 2) ||
                        sameSet(3, 6, 9, 6);
                break;
            case 7:
                isComplete = sameSet(7, 8, 9, 3) ||
                        sameSet(1, 4, 7, 4) ||
                        sameSet(3, 5, 7, 8);
                break;
            case 8:
                isComplete = sameSet(7, 8, 9, 3) ||
                        sameSet(2, 5, 8, 5);
                break;
            case 9:
                isComplete = sameSet(7, 8, 9, 3) ||
                        sameSet(3, 6, 9, 6) ||
                        sameSet(1, 5, 9, 7);
                break;
        }
        return isComplete;
    }
}
