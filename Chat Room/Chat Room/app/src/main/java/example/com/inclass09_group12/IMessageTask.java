package example.com.inclass09_group12;

import android.widget.ImageView;

public interface IMessageTask {
    public void setMessageImage(ImageView img, String path);
    void deleteMsg(Message message);
}
