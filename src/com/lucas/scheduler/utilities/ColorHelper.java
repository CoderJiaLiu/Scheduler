package com.lucas.scheduler.utilities;

public class ColorHelper {
    private static final int ALPHA_MOVE_BIT = 24;
    private static final int RED_MOVE_BIT = 16;
    private static final int GREEN_MOVE_BIT = 8;
    private static final int BLUE_MOVE_BIT = 0;

    public static int getNtoMColor(int red, int green, int blue, int m) {
        int redBit = 0xFF / m * red;
        int greenBit = 0xFF / m * green;
        int blueBit = 0xFF / m * blue;
        int alpha = 0xFF;
        return (alpha << ALPHA_MOVE_BIT) | (redBit << RED_MOVE_BIT)
                | (greenBit << GREEN_MOVE_BIT) | (blueBit << BLUE_MOVE_BIT);
    }
    
    public static int getInverseColor(int color) {
        int oldredBit = color & (0xFF << RED_MOVE_BIT);
        int oldgreenBit = color & (0xFF << GREEN_MOVE_BIT);
        int oldblueBit = color & (0xFF << BLUE_MOVE_BIT);

        int alpha = 0xFF;
        int redBit = 0xFF - (oldredBit >> RED_MOVE_BIT);
        int greenBit = 0xFF - (oldgreenBit >> GREEN_MOVE_BIT);
        int blueBit = 0xFF - (oldblueBit >> BLUE_MOVE_BIT);

        return (alpha << ALPHA_MOVE_BIT) | (redBit << RED_MOVE_BIT)
                | (greenBit << GREEN_MOVE_BIT) | (blueBit << BLUE_MOVE_BIT);
    }
}
