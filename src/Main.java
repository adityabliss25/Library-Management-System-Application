import com.library.view.MainDashboard;
import com.library.database.DatabaseHandler;
import com.library.view.AddBookFrame;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        DatabaseHandler.initializeDatabase();
        SwingUtilities.invokeLater(() -> {
            new MainDashboard().setVisible(true);
        });
    }
}