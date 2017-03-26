import java.awt.*;
import java.util.*;

/**
 * Created by root on 17-1-25.
 */
public class TestFont {
    public static void main(String[] args) {
        java.util.List<Font> font = new ArrayList<>();
        font.add(new Font("宋体", Font.BOLD, 12));
        font.add(new Font("楷体", Font.BOLD, 12));
        font.add(new Font("仿宋", Font.BOLD, 12));
        System.out.println(font.get(0).getName());
    }
}
