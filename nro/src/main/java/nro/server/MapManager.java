package nro.server;
import java.util.ArrayList;
import java.util.List;
import nro.models.map.Map;
import nro.models.map.Zone;

public class MapManager implements Runnable {
    private List<Map> maps = new ArrayList<>();

    public void addMap(Map map) {
        maps.add(map);
    }

    @Override
    public void run() {
        while (true) {
            try {
                long st = System.currentTimeMillis();
                for (Map map : maps) {
                    for (Zone zone : map.zones) {
                        zone.update();
                    }
                }
                long timeDo = System.currentTimeMillis() - st;
                Thread.sleep(1000 - timeDo);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
