package timothyyudi.tryendless;

import java.io.Serializable;

/**
 * Created by root on 11/22/2016.
 */

public class DummyModel implements Serializable{

    String title;
    String content;

    public DummyModel(String title, String content) {
        this.title = title;
        this.content = content;
    }

}
