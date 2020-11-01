package Manager;

import java.util.UUID;

public abstract class AlarmFireListener {
    //for updating ui
    public abstract void fire(UUID id,String path);

}
