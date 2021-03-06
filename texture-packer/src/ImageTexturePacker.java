import com.badlogic.gdx.tools.texturepacker.TexturePacker;

/**
 * Created by barry on 4/16/16 @ 12:59 PM.
 */
public class ImageTexturePacker {

    private static final String INPUT_DIR = "animations/";
    private static final String OUTPUT_DIR = "../core/assets/animations/";
    private static final String PACK_FILE = "animations";

    private static final String LOADING_INPUT_DIR = "loading/";
    private static final String LOADING_OUTPUT_DIR = "../core/assets/animations/";
    private static final String LOADING_PACK_FILE = "loading";

    private static final String SPRITES_INPUT_DIR = "sprites/";
    private static final String SPRITES_OUTPUT_DIR = "../core/assets/sprites/";
    private static final String SPRITES_PACK_FILE = "sprites";

    private static final float[] HUNDRED_PERCENT = new float[] {1f};
    private static final float[] FIFTY_PERCENT = new float[] {0.5f};

    public static void main(String[] args){
        // create the packing's settings
        TexturePacker.Settings settings = new TexturePacker.Settings();

        // adjust the padding settings
        settings.scale = HUNDRED_PERCENT;//FIFTY_PERCENT;
        settings.paddingX = 2;
        settings.paddingY = 2;
        settings.edgePadding = false;
        settings.maxWidth = 2048;//4096;
        settings.maxHeight = 2048;//4096;

        // pack the images
        settings.combineSubdirectories = true;
        TexturePacker.process(settings, INPUT_DIR, OUTPUT_DIR, PACK_FILE);

        settings.combineSubdirectories = true;
        TexturePacker.process(settings, SPRITES_INPUT_DIR, SPRITES_OUTPUT_DIR, SPRITES_PACK_FILE);

        settings.combineSubdirectories = false;
        //settings.scale = FIFTY_PERCENT;
        TexturePacker.process(settings, LOADING_INPUT_DIR, LOADING_OUTPUT_DIR, LOADING_PACK_FILE);
    }
}
