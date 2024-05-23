package mvp.view;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class UpdateMessageHandler {
    private final Text updateMessage;
    private ScheduledExecutorService scheduler;

    public UpdateMessageHandler(Text updateMessage) {
        this.updateMessage = updateMessage;
    }

    public void setUpdateMessage(String message) {
        updateMessage.setText(message);
    }

    public void showUpdateMessage(int duration) {
        updateMessage.setVisible(true);
        scheduler = Executors.newScheduledThreadPool(1);
        Runnable task = () -> {
            updateMessage.setVisible(false);
            setUpdateMessageFill(Color.BLACK);
        };
        scheduler.schedule(task, duration, TimeUnit.SECONDS);
        scheduler.shutdown();
    }

    public void setUpdateMessageFill(Paint color) {
        updateMessage.setFill(color);
    }
}
