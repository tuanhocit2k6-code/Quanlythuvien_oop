import controller.ControllerSingleton;
import javax.swing.UIManager;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException e) {
            LOGGER.log(Level.WARNING, "Không thể khởi tạo giao diện Nimbus; sử dụng giao diện mặc định.", e);
        }
        ControllerSingleton.getInstance().runDemo();
    }
}
